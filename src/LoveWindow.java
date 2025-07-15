import org.luaj.vm2.Lua;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.VarArgFunction;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;

public class LoveWindow {
    public static class GetDimensionsFunc extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            try {
                if (LoveCanvas.currentCanvas != null) {
                    return LuaValue.varargsOf(LuaValue.valueOf(LoveCanvas.canvasWidth), LuaValue.valueOf(LoveCanvas.canvasHeight));
                }
                return LuaValue.varargsOf(LuaValue.valueOf(0), LuaValue.valueOf(0));
            }catch (Exception error) {
                LoveCanvas.showError(error.getMessage());
                return NIL;
            }
        }
    }

    public static class showMessageBoxFunc extends VarArgFunction {
        public Varargs invoke(Varargs value) {
            try {
                String title = value.arg(1).checkjstring();
                String message = value.arg(2).checkjstring();
                String type = value.arg(3).checkjstring();
                AlertType typeMsg;
                if (type.equals("info")) {
                    typeMsg = AlertType.INFO;
                } else if (type.equals("error")) {
                    typeMsg = AlertType.INFO;
                } else if (type.equals("warning")) {
                    typeMsg = AlertType.WARNING;
                } else {
                    typeMsg = AlertType.ALARM;
                }
                Alert alert = new Alert(title, message, null, typeMsg);
                alert.setTimeout(Alert.FOREVER);
                Main.display.setCurrent(alert, LoveCanvas.currentCanvas);
            } catch (LuaError error) {
                LoveCanvas.showError(error.getMessage());
            }
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
