package name.mymiller.javafx.job;

import javafx.application.Platform;
import name.mymiller.task.Action;
import name.mymiller.task.TaskManager;

/**
 * Class to wrap a JavaFX Job
 *
 * @author jmiller
 */
public abstract class JobFx extends Action {
    @Override
    public void run() {
        Platform.runLater(() -> {
            JobFx.this.preProcess();
            JobFx.this.process();
            for (final Action job : JobFx.this.subJobs) {
                TaskManager.getInstance().submit(job);
            }
            JobFx.this.postProcess();
        });
    }
}
