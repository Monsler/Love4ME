import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.VarArgFunction;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;

public class LoveWindow {
    public static class GetDimensionsFunc extends VarArgFunction {
        public Varargs invoke() {
            if (LoveCanvas.graphics != null) {
                return LuaValue.varargsOf(LuaValue.valueOf(LoveCanvas.graphics.getClipWidth()), LuaValue.valueOf(LoveCanvas.graphics.getClipHeight()));
            }
            return LuaValue.varargsOf(LuaValue.valueOf(0), LuaValue.valueOf(0));
        }
    }

    public static class showMessageBoxFunc extends OneArgFunction {
        public LuaValue call(LuaValue value) {
            String title = value.arg(1).checkjstring();
            String message = value.arg(2).checkjstring();
            String type = value.arg(3).checkjstring();
            AlertType typeMsg;
            switch (type) {
                case "info":
                    typeMsg = AlertType.INFO;
                    break;
                case "error":
                    typeMsg = AlertType.ERROR;
                    break;
                case "warning":
                    typeMsg = AlertType.WARNING;
                    break;
                default:
                    typeMsg = AlertType.ALARM;
                    break;
            }
            Alert alert = new Alert(title, message, null, typeMsg);
            alert.setTimeout(Alert.FOREVER);
            Main.display.setCurrent(alert, LoveCanvas.currentCanvas);
            return NIL;
        }
    }

    public static LuaValue create() {
        LuaValue windowTable = LuaValue.tableOf();
        windowTable.set("getDimensions", new GetDimensionsFunc());
        windowTable.set("showMessageBox", new showMessageBoxFunc());
        return windowTable;
    }
}
