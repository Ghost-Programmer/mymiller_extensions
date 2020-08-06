/*******************************************************************************
 * Copyright 2018 MyMiller Consulting LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package name.mymiller.javafx.display;

import javafx.application.Application;
import javafx.application.Platform;
import name.mymiller.job.AbstractService;
import name.mymiller.job.JobManager;
import name.mymiller.lang.singleton.Singleton;
import name.mymiller.lang.singleton.SingletonInterface;
import java.util.logging.Logger;

import java.util.logging.Level;

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
     * Base processing application for JavaFX
     */
    private ProcessingApplication processingApplication = null;

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

        Platform.runLater(() ->displaced.hide());
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
     * Initialize the Display Manager
     *
     * @param displayCount          Number of displays available
     * @param processingApplication ProcessingApplication handling the JavaFX System
     */
    public void init(int displayCount, ProcessingApplication processingApplication) {
        this.processingApplication = processingApplication;
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
        JobManager.getInstance().createService("Display Manager", DisplayManager.getInstance());
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
        while (this.inited == false) {
            try {
                Thread.sleep(50);
            } catch (final InterruptedException e) {
            }
        }
    }
}
