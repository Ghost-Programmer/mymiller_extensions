package name.mymiller.task;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class RecursiveFutureAction<T> extends RecursiveTask<Stream<T>> {
    /**
     * Constructor allowing direct subclassing
     */
    protected RecursiveFutureAction() {
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
    protected Collection<RecursiveFutureAction<T>> createSubTasks() {
        return Collections.EMPTY_LIST;
    }

    /**
     * Method to implement to perform the actions necessary for the Job. Only called if split() returns false;
     */
    protected abstract T process();

    @Override
    protected Stream<T> compute() {

        Stream<T> stream = null;

        this.preProcess();
        if(this.split()) {
            Stream<RecursiveFutureAction<T>> stream1 = this.invokeAll(this.createSubTasks()).stream();

        } else {
            stream = Stream.of(this.process());
        }
        this.postProcess();

        return stream;
    }
}
