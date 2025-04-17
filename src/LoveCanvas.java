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

        globals.set("love", engine);

        globals.load(mainFile).call();
    }

    public void keyPressed(int key) {
        LuaValue love = globals.get("love");
        if (!love.get("keypressed").isfunction()) {
            return;
        }
        love.get("keypressed").call(LuaValue.valueOf(key));
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
