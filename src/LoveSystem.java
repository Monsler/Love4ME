import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

import javax.microedition.io.ConnectionNotFoundException;

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

    public static class openUrl extends OneArgFunction {
        public LuaValue call(LuaValue luaValue) {
            try {
                LoveCanvas.currentMidlet.platformRequest(luaValue.checkjstring());
            } catch (ConnectionNotFoundException e) {
                return LuaValue.valueOf(false);
            }

            return LuaValue.valueOf(true);
        }
    }

    public static LuaValue create() {
        LuaValue systemTable = LuaValue.tableOf();
        systemTable.set("getOS", new getOs());
        systemTable.set("vibrate", new vibrate());
        systemTable.set("openURL", new openUrl());
        return systemTable;
    }
}
