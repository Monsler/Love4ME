import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class LoveGraphics {
    public static class RectFunc extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            String mode = args.arg(1).checkjstring();
            if (mode.equals("fill")) {
                int x = args.arg(2).checkint();
                int y = args.arg(3).checkint();
                int w = args.arg(4).checkint();
                int h = args.arg(5).checkint();
                LoveCanvas.graphics.fillRect(x, y, w, h);
            } else if (mode.equals("line")) {
                int x = args.arg(2).checkint();
                int y = args.arg(3).checkint();
                int w = args.arg(4).checkint();
                int h = args.arg(5).checkint();
                LoveCanvas.graphics.drawRect(x, y, w, h);
            }
            return LuaValue.NIL;
        }
    }

    public static LuaValue create() {
        LuaValue graphicsTable = LuaValue.tableOf();
        graphicsTable.set("rectangle", new RectFunc());
        return graphicsTable;
    }
}