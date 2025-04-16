import org.luaj.vm2.Globals;
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

        // Definition of Love2D functions
        engine.set("graphics", LoveGraphics.create());
        engine.set("window", LoveWindow.create());

        globals.set("love", engine);

        globals.load(mainFile).call();
    }

    public static void setColor(int r, int g, int b) {
        rOld = graphics.getRedComponent();
        gOld = graphics.getGreenComponent();
        bOld = graphics.getBlueComponent();
        graphics.setColor(r, g, b);
    }

    public void paint(Graphics g) {
        graphics = g;

        // Draw background before main draw event
        setColor(0, 0, 0);
        g.fillRect(0, 0, getWidth(), getHeight());
        setColor(255, 255, 255);
        globals.load("love.draw()").call();
        //g.drawString(g.getClipWidth()+"+"+g.getClipHeight(), g.getClipWidth()/2, g.getClipHeight()/2, Graphics.TOP | Graphics.HCENTER);
        repaint();
    }
}
