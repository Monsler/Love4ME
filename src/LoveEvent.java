import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class LoveEvent {
    public static class QuitFunc extends OneArgFunction {
        public LuaValue call(LuaValue value) {
            if (!value.isnil()) {
                if (value.checkjstring().equals("restart")) {
                    LoveCanvas.restart();
                }
            } else {
                LoveCanvas.currentMidlet.notifyDestroyed();
            }

            return LuaValue.NIL;
        }
    }

    public static LuaValue create() {
        LuaValue eventTable = LuaValue.tableOf();
        eventTable.set("quit", new QuitFunc());
        return eventTable;
    }
}
