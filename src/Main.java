import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

public class Main extends MIDlet {
    public static Display display;
    protected void startApp() {
        display = Display.getDisplay(this);
        display.setCurrent(new LoveCanvas(false, this));
    }

    protected void pauseApp() {

    }

    protected void destroyApp(boolean b) {
        LoveCanvas.globals.load("love.event.quit()").call();
    }
}
