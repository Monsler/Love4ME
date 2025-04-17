import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

public class LoveSystem {
    public static class getOs extends ZeroArgFunction {
        public LuaValue call() {
            return LuaValue.valueOf(System.getProperty("microedition.platform"));
        }
    }

    public static class vibrate extends OneArgFunction {
        public LuaValue call(LuaValue luaValue) {
            double seconds = luaValue.checkdouble()*1000;
            Main.display.vibrate((int) seconds);
            return NIL;
        }
    }

    public static LuaValue create() {
        LuaValue systemTable = LuaValue.tableOf();
        systemTable.set("getOS", new getOs());
        systemTable.set("vibrate", new vibrate());
        systemTable.set("fire", LuaValue.valueOf(LoveCanvas.FIRE));
        systemTable.set("down", LuaValue.valueOf(LoveCanvas.DOWN));
        systemTable.set("left", LuaValue.valueOf(LoveCanvas.LEFT));
        systemTable.set("right", LuaValue.valueOf(LoveCanvas.RIGHT));
        systemTable.set("up", LuaValue.valueOf(LoveCanvas.UP));
        return systemTable;
    }
}
