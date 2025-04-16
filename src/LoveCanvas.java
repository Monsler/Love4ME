import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jme.JmePlatform;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;

public class LoveCanvas extends GameCanvas {
    public LuaValue engine;
    public static Globals globals;
    public static Graphics graphics;


    protected LoveCanvas(boolean suppressKeyEvents) {
        super(suppressKeyEvents);
        String mainFile;
        setFullScreenMode(true);

        try {
            mainFile = ResourceReader.readResourceToString("game/main.lua");
        } catch (Exception ignored) {
            mainFile = "";
        }
        globals = JmePlatform.standardGlobals();
        engine = LuaValue.tableOf();
        engine.set("graphics", LoveGraphics.create());
        globals.set("love", engine);
        globals.load(mainFile).call();
    }

    public void paint(Graphics g) {
        graphics = g;
        // Draw background before main draw event
        g.setColor(0, 0, 0);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(255, 255, 255);
        globals.load("love.draw()").call();
        repaint();
    }
}
