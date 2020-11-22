package name.mymiller.lang.concurrent;

import name.mymiller.task.RecursiveAction;
import name.mymiller.task.RecursiveFutureAction;
import name.mymiller.task.TaskManager;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.stream.Stream;

/**
 * Provides Thread Synchronization on Functional or Task related code.  Provides thread synchronization across
 * Procecure/Function interfaces, and RecursiveAction / RecursiveFutureAction tasks.
 */
public class BlockLock {
    /**
     * Used for synchronization
     */
    private Object syncObject = new Object();

    /**
     * Once the lock is achieved will execute the Procedure and then release the lock.
     * @param procedure Procedure interface to execute once locked.
     */
    public void lock(Procedure procedure) {
        synchronized (syncObject) {
            procedure.action();
        }
    }

    /**
     * Once the lock is achieved will execute the Function, release the lock and return the value.
     * @param function Function interface to execute once locked
     * @param <T> Type of return
     * @return Value returned from the Function.
     */
    public <T> T lock(Function<T> function) {
        synchronized (syncObject) {
            return function.action();
        }
    }

    /**
     * Continue process but process this Procedure in the future, results can be checked with a Future that is returned
     * @param procedure Procedure interface to execute once locked
     * @return Future returned to check status
     */
    public Future lockFuture(Procedure procedure) {
        return TaskManager.getInstance().submit(new BlockLockRunnableProcedure(procedure));
    }

    /**
     * Continue processing but process this Function in the future, results can be check with a Future that is returned.
     * @param function Function interface to execute once locked.
     * @param <T> Type of return
     * @return Future returned to check status and value
     */
    public <T> Future<T> lockFuture(Function<T> function) {
        return TaskManager.getInstance().submit(new BlockLockRunnableFunction(function));
    }

    /**
     * Using the ForkJoinPool of the TaskManager, execute this task one acquire lock.
     * @param recursiveAction RecursiveAction to execute once locked.
     */
    public void lockRecursiveAction(RecursiveAction recursiveAction) {
        TaskManager.getInstance().execute(new BlockLockRecursiveAction(recursiveAction));
    }

    /**
     * Using the ForkJoinPoool of the TaskManager, execute this task once the acquire is locked.
     * @param recursiveFutureAction RecursiveFutureAction to execute once locked.
     * @param <T> Type of return
     * @return Future returned to check status and value
     */
    public <T> Stream<T> lockRecursiveFutureAction(RecursiveFutureAction<T> recursiveFutureAction) {
        return TaskManager.getInstance().invoke(new BlockLockRecurseFutureAction<T>(recursiveFutureAction));
    }

    /**
     * Internal class used for the RecursiveFutureAction
     * @param <T> Type of Return
     */
    private class BlockLockRecurseFutureAction<T> extends java.util.concurrent.RecursiveTask<Stream<T>> {
        /**
         * RecursiveFutureAction to execute
         */
        private RecursiveFutureAction<T> recursiveFutureAction;

        /**
         * Constructor to generate internal RecursiveTask for processing
         * @param recursiveFutureAction
         */
        public BlockLockRecurseFutureAction(RecursiveFutureAction<T> recursiveFutureAction) {
            this.recursiveFutureAction = recursiveFutureAction;
        }

        /**
         * The main computation performed by this task.
         *
         * @return the result of the computation
         */
        @Override
        protected Stream<T> compute() {
            synchronized (syncObject) {
                return TaskManager.getInstance().invoke(recursiveFutureAction);
            }
        }
    }

    /**
     * Internal class used for RecursiveAction
     */
    private class BlockLockRecursiveAction extends java.util.concurrent.RecursiveAction {
        /**
         * RecursiveAction to execute
         */
        private RecursiveAction recursiveAction;

        /**
         * Constructor to generate internal Recursive Task for processing
         * @param recursiveAction
         */
        public BlockLockRecursiveAction(RecursiveAction recursiveAction) {
            this.recursiveAction = recursiveAction;
        }

        /**
         * The main computation performed by this task.
         */
        @Override
        protected void compute() {
            synchronized (syncObject) {
                invokeAll(this.recursiveAction);
            }
        }
    }

    /**
     * Internal Class used to schedule a Procedure
     */
    private class BlockLockRunnableProcedure  implements Runnable{
        /**
         * Procedure to run
         */
        private Procedure procedure;

        /**
         * Constructor with Procedure to run
         * @param procedure Procedure to run
         */
        public BlockLockRunnableProcedure(Procedure procedure) {
            this.procedure = procedure;
        }

        /**
         * When an object implementing interface {@code Runnable} is used
         * to create a thread, starting the thread causes the object's
         * {@code run} method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method {@code run} is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            synchronized (syncObject) {
                procedure.action();
            }
        }
    }

    /**
     * Internal Class used to schedule a Function
     * @param <T> Type of Return
     */
    private class BlockLockRunnableFunction<T>  implements Callable {
        /**
         * Function to run
         */
        private Function<T> function;

        /**
         * Constructor with Function to run
         * @param function Function to run
         */
        public BlockLockRunnableFunction(Function<T> function) {
            this.function = function;
        }

        /**
         * Computes a result, or throws an exception if unable to do so.
         *
         * @return computed result
         * @throws Exception if unable to compute a result
         */
        @Override
        public T call() throws Exception {
            synchronized (syncObject) {
                return function.action();
            }
        }
    }
}
