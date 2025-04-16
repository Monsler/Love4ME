import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import java.io.IOException;

public class LoveGraphics {
    public static class RectFunc extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            String mode = args.arg(1).checkjstring();
            int x = args.arg(2).checkint();
            int y = args.arg(3).checkint();
            int w = args.arg(4).checkint();
            int h = args.arg(5).checkint();
            if (mode.equals("fill")) {
                LoveCanvas.graphics.fillRect(x, y, w, h);
            } else if (mode.equals("line")) {
                LoveCanvas.graphics.drawRect(x, y, w, h);
            }
            return LuaValue.NIL;
        }
    }

    public static class CircleFunc extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            String mode = args.arg(1).checkjstring();
            int x = args.arg(2).checkint();
            int y = args.arg(3).checkint();
            int radius = args.arg(4).checkint();
            if (mode.equals("fill")) {
                LoveCanvas.graphics.fillArc(x - radius, y - radius, radius * 2, radius * 2, 0, 360);
            } else if (mode.equals("line")) {
                LoveCanvas.graphics.drawArc(x - radius, y - radius, radius * 2, radius * 2, 0, 360);
            }
            return LuaValue.NIL;
        }
    }

    public static class PrintFunc extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            String text = args.arg(1).checkjstring();
            int x = args.arg(2).checkint();
            int y = args.arg(3).checkint();
            LoveCanvas.graphics.drawString(text, x, y, Graphics.TOP | Graphics.HCENTER);
            return LuaValue.NIL;
        }
    }

    public static class SetColor extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            float r = args.arg(1).checkint()*255;
            float g = args.arg(2).checkint()*255;
            float b = args.arg(3).checkint()*255;
            LoveCanvas.setColor((int)r, (int)g, (int)b);
            return LuaValue.NIL;
        }
    }

    public static class NewImage extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            Image image = null;
            try {
                image = Image.createImage("/game/"+args.arg(1).checkjstring());
            } catch (Exception e) {
                LoveCanvas.currentMidlet.notifyDestroyed();
            }
            return LuaValue.userdataOf(image);
        }
    }

    public static class draw extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            Image image = (Image) args.arg(1).checkuserdata();
            int x = args.arg(2).checkint();
            int y = args.arg(3).checkint();
            LoveCanvas.graphics.drawImage(image, x, y, Graphics.TOP | Graphics.HCENTER);
            return LuaValue.NIL;
        }
    }


    public static LuaValue create() {
        LuaValue graphicsTable = LuaValue.tableOf();
        graphicsTable.set("rectangle", new RectFunc());
        graphicsTable.set("setColor", new SetColor());
        graphicsTable.set("circle", new CircleFunc());
        graphicsTable.set("print", new PrintFunc());
        graphicsTable.set("newImage", new NewImage());
        graphicsTable.set("draw", new draw());
        return graphicsTable;
    }
}