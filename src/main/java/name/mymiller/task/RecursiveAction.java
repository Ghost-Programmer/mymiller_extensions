package name.mymiller.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class RecursiveAction extends java.util.concurrent.RecursiveAction {

    /**
     * Constructor allowing direct subclassing
     */
    protected RecursiveAction() {
    }

    /**
     * Override this method to implement post-processing, after all sub-jobs have
     * completed.
     */
    protected void postProcess() {

    }

    /**
     * Overrite this method to implement any pre-run processing, before the Run
     * method is called
     */
    protected void preProcess() {

    }

    /**
     * Overwrite this method to implement a check to split the work into smaller units.
     * @return default is false
     */
    protected boolean split() {
        return false;
    }

    /**
     * Called with split() return true to break this into sub-tasks
     * @return List of actions to break this action into.
     */
    protected List<RecursiveAction> createSubTasks() {
        return Collections.EMPTY_LIST;
    }

    /**
     * Method to implement to perform the actions necessary for the Job. Only called if split() returns false;
     */
    protected abstract void process();

    @Override
    protected void compute() {

        this.preProcess();
        if(this.split()) {
           this.invokeAll(this.createSubTasks());
        } else {
            this.process();
        }
        this.postProcess();
    }
}
