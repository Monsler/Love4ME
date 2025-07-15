import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class LoveTouch {
    public static class getPositionFunc extends VarArgFunction {
        public Varargs invoke() {
            return LuaValue.varargsOf(LuaValue.valueOf(LoveCanvas.touchX), LuaValue.valueOf(LoveCanvas.touchY));
        }
    }

    public static LuaValue create() {
        LuaValue touchTable = LuaValue.tableOf();
        touchTable.set("getPosition", new getPositionFunc());
        return touchTable;
    }
}
