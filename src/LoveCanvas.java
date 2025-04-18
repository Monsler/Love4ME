import org.luaj.vm2.Globals;
import org.luaj.vm2.Lua;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jme.JmePlatform;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.midlet.MIDlet;

public class LoveCanvas extends GameCanvas {
    public LuaValue engine;
    public static Globals globals;
    public static Graphics graphics;
    public static int rOld, gOld, bOld;
    public static MIDlet currentMidlet;

    public static int fps;
    private int frames;
    private long lastTime = System.currentTimeMillis();

    protected LoveCanvas(boolean suppressKeyEvents, MIDlet current) {
        super(suppressKeyEvents);
        String mainFile;
        currentMidlet = current;
        setFullScreenMode(true);
        try {
            mainFile = ResourceReader.readResourceToString("game/main.lua");
        } catch (Exception ignored) {
            mainFile = null;
            currentMidlet.notifyDestroyed();
        }
        rOld = 0;
        gOld = 0;
        bOld = 0;
        globals = JmePlatform.standardGlobals();
        engine = LuaValue.tableOf();

        // Definition of Love4ME functions
        engine.set("graphics", LoveGraphics.create());
        engine.set("window", LoveWindow.create());
        engine.set("timer", LoveTimer.create());
        engine.set("system", LoveSystem.create());
        engine.set("event", LoveEvent.create());
        engine.set("thread", LoveThread.create());

        globals.set("require", new RequireFunction());
        globals.set("love", engine);

        globals.load(mainFile).call();
        globals.load("love.load()").call();
    }

    private String getKey(int key) {
        String keyString;
        switch (key) {
            case -5:
                keyString = "ok";
                break;
            case -3:
                keyString = "left";
                break;
            case -4:
                keyString = "right";
                break;
            case -2:
                keyString = "down";
                break;
            case -1:
                keyString = "up";
                break;
            case -10:
                keyString = "accept";
                break;
            case 49:
                keyString = "1";
                break;
            case 50:
                keyString = "2";
                break;
            case 51:
                keyString = "3";
                break;
            case 52:
                keyString = "4";
                break;
            case 53:
                keyString = "5";
                break;
            case 54:
                keyString = "6";
                break;
            case 55:
                keyString = "7";
                break;
            case 56:
                keyString = "8";
                break;
            case 57:
                keyString = "9";
                break;
            case 42:
                keyString = "star";
                break;
            case 48:
                keyString = "0";
                break;
            case 35:
                keyString = "hashtag";
                break;
            default:
                keyString = "unknown";
                break;
        }
        return keyString;
    }

    public void keyPressed(int key) {
        LuaValue love = globals.get("love");

        if (!love.get("keypressed").isfunction()) {
            return;
        }
        love.get("keypressed").call(LuaValue.valueOf(getKey(key)));
    }

    public void keyReleased(int key) {
        LuaValue love = globals.get("love");
        if (!love.get("keyreleased").isfunction()) {
            return;
        }
        love.get("keyreleased").call(LuaValue.valueOf(getKey(key)));
    }

    public static void restart() {
        Main.display.setCurrent(new LoveCanvas(false, currentMidlet));
    }

    public static void setColor(int r, int g, int b) {
        rOld = graphics.getRedComponent();
        gOld = graphics.getGreenComponent();
        bOld = graphics.getBlueComponent();
        graphics.setColor(r, g, b);
    }

    public void paint(Graphics g) {
        graphics = g;
        ++frames;
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime >= 1000) {
            fps = frames;
            frames = 0;
            lastTime = currentTime;
        }

        // Draw background before main draw event
        setColor(0, 0, 0);
        g.fillRect(0, 0, getWidth(), getHeight());
        setColor(255, 255, 255);
        globals.load("love.draw()").call();
        repaint();
    }
}
