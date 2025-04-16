import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

public class Main extends MIDlet {
    protected void startApp() {
        Display display = Display.getDisplay(this);
        display.setCurrent(new LoveCanvas(false, this));
    }

    protected void pauseApp() {

    }

    protected void destroyApp(boolean b) {
        LoveCanvas.globals.load("love.quit()").call();
    }
}
