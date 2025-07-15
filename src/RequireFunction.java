import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import java.io.IOException;

public class RequireFunction extends OneArgFunction {
    public LuaValue call(LuaValue luaValue) {
        String loader;
        try {
            loader = ResourceReader.readResourceToString("src/" +luaValue.checkjstring().replace('.', '/')+".lua");
        } catch (IOException e) {
            return NIL;
        }
        LuaValue chunk = LoveCanvas.globals.load(loader);
        LuaValue result = chunk.call();
        return result.isnil() ? LuaValue.TRUE : result;
    }
}
