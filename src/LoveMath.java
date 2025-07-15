import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class LoveMath {
    public static class ColorFromBytes extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            float r = args.arg(1).tofloat()/255.0f;
            float g = args.arg(2).tofloat()/255.0f;
            float b = args.arg(3).tofloat()/255.0f;
            return LuaValue.varargsOf(LuaValue.valueOf(r), LuaValue.valueOf(g), LuaValue.valueOf(b));
        }
    }
    public static class ColorToBytes extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            float r = args.arg(1).tofloat()*255.0f;
            float g = args.arg(2).tofloat()*255.0f;
            float b = args.arg(3).tofloat()*255.0f;
            return LuaValue.varargsOf(LuaValue.valueOf(r), LuaValue.valueOf(g), LuaValue.valueOf(b));
        }
    }

    public static class Random extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            LuaValue min = args.arg(1);
            LuaValue max = args.arg(2);
            java.util.Random random = new java.util.Random();
            random.setSeed(System.currentTimeMillis());
            if (max.isnil()) {
                int maxInt = min.checkint();
                return LuaValue.valueOf(random.nextInt(maxInt));
            } else if (!max.isnil() && !min.isnil()) {
                int minInt = min.checkint();
                int maxInt = max.checkint();
                return  LuaValue.valueOf(random.nextInt((maxInt-minInt)+1)+maxInt);
            } else {
                return LuaValue.valueOf(random.nextInt());
            }
        }
    }


    public static LuaValue create() {
        LuaValue mathTable = LuaValue.tableOf();
        mathTable.set("colorFromBytes", new ColorFromBytes());
        mathTable.set("colorToBytes", new ColorToBytes());
        mathTable.set("random", new Random());
        return mathTable;
    }
}
