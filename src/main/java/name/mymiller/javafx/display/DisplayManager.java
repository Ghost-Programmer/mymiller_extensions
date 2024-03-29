package name.mymiller.javafx.display;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.stage.Screen;
import name.mymiller.lang.singleton.Singleton;
import name.mymiller.lang.singleton.SingletonInterface;
import name.mymiller.task.AbstractService;
import name.mymiller.task.TaskManager;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages the the JavaFX Displays.
 *
 * @author jmiller
 */
@Singleton
public class DisplayManager extends AbstractService implements SingletonInterface<DisplayManager> {
    /**
     * Global Instance
     */
    static private DisplayManager globalInstance = null;
    /**
     * Indicates if the system has be initialized.
     */
    private boolean inited = false;
    /**
     * Holds the Diplays for each screen
     */
    private DisplayScreen[] displayScreens = null;

    /**
     *
     */
    public DisplayManager() {
        super();

    }

    /**
     * @return Global Instance
     */
    public static DisplayManager getInstance() {
        if (DisplayManager.globalInstance == null) {
            DisplayManager.globalInstance = new DisplayManager();
        }

        return DisplayManager.globalInstance;
    }

    /**
     * @return total number of displays available on this system.
     */
    public int availableDisplays() {
        this.waitOnInited();

        int i = 0;
        for (final DisplayScreen screen : this.displayScreens) {
            if (screen == null) {
                i++;
            }
        }

        return i;
    }

    /**
     * Clear the display on given screen
     *
     * @param screen Screen number to clear
     * @return the displaced DisplaceScreen
     */
    public DisplayScreen clearDisplay(int screen) {
        final DisplayScreen displaced = this.getDisplay(screen);

        Platform.runLater(displaced::hide);
        this.displayScreens[screen] = null;

        return displaced;
    }

    /**
     * Display this Screen on the system.
     *
     * @param display DisplayScreen to show
     * @throws NoDisplayAvailableException No Open Displays available
     */
    public void display(DisplayScreen display) throws NoDisplayAvailableException {
        this.waitOnInited();

        if (this.availableDisplays() == 0) {
            throw new NoDisplayAvailableException(this.displayScreens.length);
        }

        for (int i = 0; i < this.displayScreens.length; i++) {
            if (this.displayScreens[i] == null) {
                this.display(display, i);
                break;
            }
        }
    }

    /**
     * Display this Screen on the system.
     *
     * @param display DisplayScreen to show
     * @param screen  Screen number to display on
     */
    public void display(DisplayScreen display, int screen) {
        Logger.getLogger(DisplayManager.class.getName()).info("Display: " + display + " Screen: " + screen);
        this.waitOnInited();
        this.displayScreens[screen] = display;
        if (display != null) {
            Logger.getLogger(DisplayManager.class.getName()).info("Running Display Later");
            this.displayScreens[screen].setDisplay(screen);
            Platform.runLater(display);
        }
    }

    /**
     * Get the DisplayScreen for the given display
     *
     * @param screen Screen Number to get the display on
     * @return Display Screen for the given display
     */
    public DisplayScreen getDisplay(int screen) {
        return this.displayScreens[screen];
    }

    /**
     *
     * @return a map showing screens and the named of displays
     */
    public Map<Integer,String> getDisplayList() {
        Map<Integer,String> map = new HashMap<>();
        ObservableList<Screen> screens = Screen.getScreens();

        for(int i = 0; i < screens.size(); i++ ) {
            if(this.displayScreens[i] != null) {
                map.put(i,this.displayScreens[i].getDisplayName());
            } else {
                map.put(i," EMPTY");
            }
        }

        return map;
    }

    /**
     * Initialize the Display Manager
     *
     * @param displayCount          Number of displays available
     */
    public void init(int displayCount) {
        this.displayScreens = new DisplayScreen[displayCount];

        this.inited = true;
    }

    /**
     * Move the display from one screen to another
     *
     * @param fromScreen Screen to move from
     * @param toScreen   Screen to move to
     * @return the displaced Screen
     */
    public DisplayScreen moveDisplay(int fromScreen, int toScreen) {
        final DisplayScreen displaced = this.getDisplay(toScreen);
        this.displayScreens[toScreen] = this.displayScreens[fromScreen];
        if (this.displayScreens[toScreen] != null) {
            this.displayScreens[fromScreen] = null;
            if (displaced != null) {
                displaced.hide();
            }
            this.displayScreens[toScreen].setDisplay(toScreen);
            return displaced;
        } else {
            System.err.println("No Screen to move");
        }
        return null;
    }

    @Override
    protected void service() {
        Application.launch(ProcessingApplication.class);
    }

    @Override
    public void start() {
        Logger.getLogger(DisplayManager.class.getName()).info("Starting Display Manager");
        this.setShutdown(false);
        TaskManager.getInstance().createService("Display Manager", DisplayManager.getInstance());
    }

    @Override
    protected void stop(int delay) {
        Platform.runLater(() -> {
            try {
                for (final DisplayScreen screen : this.displayScreens) {
                    if (screen != null) {
                        screen.stop();
                    }
                }
                Platform.exit();
            } catch (final Exception e) {
                Logger.getLogger(DisplayManager.class.getName()).log(Level.SEVERE, "Failed to stop Display Manager", e);
            }
        });
    }

    /**
     * Method to wait on Display Manager to be initilized.
     */
    private void waitOnInited() {
        while (!this.inited) {
            try {
                Thread.sleep(50);
            } catch (final InterruptedException e) {
            }
        }
    }
}
