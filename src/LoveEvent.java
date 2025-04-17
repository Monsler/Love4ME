import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class LoveEvent {
    public static class QuitFunc extends ZeroArgFunction {
        public LuaValue call() {
            LoveCanvas.currentMidlet.notifyDestroyed();
            return null;
        }
    }

    public static LuaValue create() {
        LuaValue eventTable = LuaValue.tableOf();
        eventTable.set("quit", new QuitFunc());
        return eventTable;
    }
}
