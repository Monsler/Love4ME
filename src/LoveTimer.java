import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

public class LoveTimer {
    public static class SleepFunc extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            int time = args.arg(1).checkint();
            try {
                Thread.sleep(time);
            } catch (InterruptedException ignored) {
            }
            return LuaValue.NIL;
        }
    }

    public static class GetFps extends ZeroArgFunction {
        public LuaValue call() {
            return LuaValue.valueOf(LoveCanvas.fps);
        }
    }

    public static LuaValue create() {
        LuaValue timerTable = LuaValue.tableOf();
        timerTable.set("sleep", new SleepFunc());
        timerTable.set("getFPS", new GetFps());
        return timerTable;
    }
}
