package name.mymiller.lang.concurrent;

import name.mymiller.task.RecursiveAction;
import name.mymiller.task.RecursiveFutureAction;
import name.mymiller.task.TaskManager;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public class BlockLock {
    private Object syncObject = new Object();

    public void lock(Procedure procedure) {
        synchronized (syncObject) {
            procedure.action();
        }
    }

    public <T> T lock(Function<T> function) {
        synchronized (syncObject) {
            return function.action();
        }
    }

    public Future lockFuture(Procedure procedure) {
        return TaskManager.getInstance().submit(new BlockLockRunnableProcedure(procedure));
    }

    public <T> Future<T> lockFuture(Function<T> function) {
        return TaskManager.getInstance().submit(new BlockLockRunnableFunction(function));
    }

    public void lockRecursiveAction(RecursiveAction recursiveAction) {
        TaskManager.getInstance().execute(new BlockLockRecursiveAction(recursiveAction));
    }

    public <T> Stream<T> lockRecursiveFutureAction(RecursiveFutureAction<T> recursiveFutureAction) {
        return TaskManager.getInstance().invoke(new BlockLockRecurseFutureAction<T>(recursiveFutureAction));
    }

    private class BlockLockRecurseFutureAction<T> extends java.util.concurrent.RecursiveTask<Stream<T>> {
        private RecursiveFutureAction<T> recursiveFutureAction;

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

    private class BlockLockRecursiveAction extends java.util.concurrent.RecursiveAction {
        private RecursiveAction recursiveAction;

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

    private class BlockLockRunnableProcedure  implements Runnable{
        private Procedure procedure;

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

    private class BlockLockRunnableFunction<T>  implements Callable {
        private Function<T> function;

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
