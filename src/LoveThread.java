import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jme.JmePlatform;

public class LoveThread {
    public static class NewThreadFunc extends OneArgFunction {
        public LuaValue call(LuaValue luaValue) {
            LuaValue out = tableOf();
            final String code = luaValue.checkjstring();
            final Thread thread = new Thread() {
                public void run() {
                    Globals globals = JmePlatform.standardGlobals();
                    LuaValue engine = tableOf();
                    engine.set("graphics", LoveGraphics.create());
                    engine.set("window", LoveWindow.create());
                    engine.set("timer", LoveTimer.create());
                    engine.set("system", LoveSystem.create());
                    engine.set("event", LoveEvent.create());
                    engine.set("thread", LoveThread.create());

                    globals.set("require", new RequireFunction());
                    globals.set("love", engine);

                    globals.load(code).call();
                }
            };
            out.set("start", new OneArgFunction() {
                public LuaValue call(LuaValue luaValue) {
                    thread.start();
                    return NIL;
                }
            });
            return out;
        }
    }

    public static LuaValue create() {
        LuaValue out = LuaValue.tableOf();
        out.set("newThread", new NewThreadFunc());
        return out;
    }
}
