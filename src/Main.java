import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class Main extends MIDlet {
    protected void startApp() throws MIDletStateChangeException {
        Display display = Display.getDisplay(this);
        display.setCurrent(new LoveCanvas(false));
    }

    protected void pauseApp() {

    }

    protected void destroyApp(boolean b) throws MIDletStateChangeException {

    }
}
