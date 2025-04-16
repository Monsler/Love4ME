import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class LoveWindow {
    public static class GetDimensionsFunc extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            if (LoveCanvas.graphics != null) {
                return LuaValue.varargsOf(LuaValue.valueOf(LoveCanvas.graphics.getClipWidth()), LuaValue.valueOf(LoveCanvas.graphics.getClipHeight()));
            }
            return LuaValue.varargsOf(LuaValue.valueOf(0), LuaValue.valueOf(0));
        }
    }

    public static LuaValue create() {
        LuaValue windowTable = LuaValue.tableOf();
        windowTable.set("getDimensions", new GetDimensionsFunc());
        return windowTable;
    }
}
