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
            java.util.Random random = new java.util.Random();
            random.setSeed(System.currentTimeMillis());
            int n = args.narg();
            if (n == 0) {
                return LuaValue.valueOf(random.nextDouble());
            } else if (n == 1) {
                int max = args.arg(1).checkint();
                return LuaValue.valueOf(random.nextInt(max) + 1);
            } else {
                int min = args.arg(1).checkint();
                int max = args.arg(2).checkint();
                return LuaValue.valueOf(random.nextInt((max - min + 1)) + min);
            }
        }
    }

    public static class Noise extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            double x = args.arg(1).checkdouble();
            double y = args.arg(2).checkdouble();
            double result = SimplexNoise.noise(x, y);
            return LuaValue.valueOf(result);
        }
    }


    public static LuaValue create() {
        LuaValue mathTable = LuaValue.tableOf();
        mathTable.set("colorFromBytes", new ColorFromBytes());
        mathTable.set("colorToBytes", new ColorToBytes());
        mathTable.set("random", new Random());
        mathTable.set("noise", new Noise());
        return mathTable;
    }
}
