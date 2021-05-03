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
    public void start(Stage primaryStage) {
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
