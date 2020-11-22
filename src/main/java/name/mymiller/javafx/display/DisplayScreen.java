/******************************************************************************
 Copyright 2018 MyMiller Consulting LLC.

 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License.  You may obtain a copy
 of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 License for the specific language governing permissions and limitations under
 the License.
 */
package name.mymiller.javafx.display;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract class to aid in the creation of Displays on monitors
 *
 * @author jmiller
 */
public abstract class DisplayScreen extends Application implements Runnable {
    private String displayName;

    /**
     * Zero index display of the screens
     */
    private int display = 0;

    /**
     * Maximize the Display Window
     */
    private boolean maximized = false;

    /**
     * This instance of the displayStage.
     */
    private Stage displayStage = null;

    /**
     * This instance is always on top
     */
    private boolean onTop = false;

    /**
     * Full Screen for the Display Window
     */
    private boolean fullScreen = false;

    /**
     * Sets the display name;
     */
    public DisplayScreen(String displayName) {
        super();
        this.displayName = displayName;
    }

    /**
     * @return the display
     */
    public int getDisplay() {
        return this.display;
    }

    /**
     * @param display the display to set
     */
    public void setDisplay(int display) {
        this.display = display;

        if (this.displayStage != null) {
            final ObservableList<Screen> screens = Screen.getScreens();
            final Rectangle2D bounds = screens.get(this.getDisplay()).getVisualBounds();

            this.displayStage.setX(bounds.getMinX());
            this.displayStage.setY(bounds.getMinY());
            this.displayStage.setWidth(bounds.getWidth());
            this.displayStage.setHeight(bounds.getHeight());
        }
    }

    /**
     * @return the displayName
     */
    public synchronized String getDisplayName() {
        return this.displayName;
    }

    /**
     * @param displayName the displayName to set
     */
    protected synchronized void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Hide the given DisplayScreen
     */
    public void hide() {
        this.displayStage.hide();
    }

    /**
     * @return True if the DisplayScreen is FullScreen
     */
    public boolean isFullScreen() {
        return this.fullScreen;
    }

    /**
     * Set if the DisplayScreen is Full Screen
     *
     * @param fullScreen True if it should be Full Screen
     */
    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;

        if (this.displayStage != null) {
            this.displayStage.setFullScreen(fullScreen);
        }
    }

    /**
     * Set if the DisplayScreen is Always on Top
     * @param onTop True if always on top
     */
    public void setOnTop(boolean onTop) {
        this.onTop = onTop;

        if(this.displayStage != null) {
            this.displayStage.setAlwaysOnTop(onTop);
        }
    }

    /**
     *
     * @return Boolean indicating if always on top
     */
    public boolean isOnTop() {
        return this.onTop;
    }

    /**
     * @return the maximized
     */
    public boolean isMaximized() {
        return this.maximized;
    }

    /**
     * @param maximized the maximized to set
     */
    public void setMaximized(boolean maximized) {
        this.maximized = maximized;

        if (this.displayStage != null) {
            this.displayStage.setMaximized(this.maximized);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        if (this.displayStage == null) {
            this.displayStage = new Stage();
            try {
                Logger.getLogger(DisplayScreen.class.getName()).info("Creating Display Stage");
                this.start(this.displayStage);
            } catch (final Exception e) {
                Logger.getLogger(DisplayScreen.class.getName()).log(Level.SEVERE, "Failed to open Display", e);
            }
        } else {
            this.show();
        }
    }

    /**
     * Show the DisplayScreen
     */
    public void show() {
        this.displayStage.show();
    }

    /*
     * (non-Javadoc)
     *
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage stage) throws Exception {
        stage.setMaximized(this.isMaximized());
        stage.setFullScreen(this.isFullScreen());
        stage.setAlwaysOnTop(this.onTop);

        final ObservableList<Screen> screens = Screen.getScreens();
        final Rectangle2D bounds = screens.get(this.getDisplay()).getVisualBounds();

        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());

        this.startDisplay(stage, bounds.getHeight(), bounds.getWidth());
    }

    /**
     * Method to construct the Stage. Stage will be set
     *
     * @param stage  Stage to use in your Display
     * @param height the Height of this display
     * @param width  the Width of this display.
     * @throws Exception Exception thrown while starting the Display.
     */
    abstract protected void startDisplay(Stage stage, double height, double width) throws Exception;

    /*
     * (non-Javadoc)
     *
     * @see javafx.application.Application#stop()
     */
    @Override
    public void stop() throws Exception {
        this.displayStage.close();
        super.stop();
    }

}
