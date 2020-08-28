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
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Primary UI Thread running for JAVAFX
 *
 * @author jmiller
 */
public class ProcessingApplication extends Application {

    /**
     * Primary Stage for this Display.
     */
    private Stage primaryStage = null;

    /**
     * @return int of the number of displays available
     */
    public int getDisplayCount() {
        return Screen.getScreens().size();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Platform.setImplicitExit(true);
        DisplayManager.getInstance().init(Screen.getScreens().size());
        final VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        final Scene scene = new Scene(root, 10, 10);
        primaryStage.setTitle("ProcessingApplication");
        primaryStage.setScene(scene);
         primaryStage.show();
    }

    /*
     * (non-Javadoc)
     *
     * @see javafx.application.Application#stop()
     */
    @Override
    public void stop() throws Exception {
        this.primaryStage.close();
        super.stop();
    }
}
