package name.mymiller.lang.concurrent;

import name.mymiller.task.RecursiveAction;
import name.mymiller.task.RecursiveFutureAction;
import name.mymiller.task.TaskManager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.stream.Stream;

/**
 * NamedLock provides locking access not on a thread, but on a String.  This string may be based on any value desired.
 * When Lock is called if the lock is not assigned it will be assigned to the name, and processing will continue.  If the
 * lock is assigned, the name is checked if the value matches, processing will immediately continue, unless a different value
 * has been waiting longer than maxWait, then this request will enter the queue.  If it does not match then the it will sleep
 * for waitTime and check the lock again.
 */
public class NamedLock {

    /**
     * Syncrhonization Object
     */
    private Object syncObject = new Object();

    /**
     * String containing the current lock
     */
    private String currentLock = null;

    /**
     * the number of threads currently processing
     */
    private Integer count ;

    /**
     * The number of milliseconds to wait before checking the lock again.
     */
    private Long waitTime;
    /**
     * The number of milliseconds to wait before forcing a relase of the lock.
     */
    private Long maxWait;

    /**
     * Map tracking the time a thread has waited.
     */
    private Map<Thread, Long> threadWait;

    /**
     * Indicates that a forceRelease is required.
     */
    private Boolean forceRelease = false;

    /**
     * Construct a NamedLock with the waitTime and maxWait values
     * @param waitTime maximum number of millieseconds to wait before checking the lock.
     * @param maxWait maximum number of milliseconds to wait before forcing a release.
     */
    public NamedLock(Long waitTime, Long maxWait) {
        this.count = 0;
        this.waitTime = waitTime;
        this.threadWait = new HashMap<>();
        this.maxWait = maxWait;
    }

    /**
     * Default constructor that generates a NamedLock with 100ms waitTime and 1000ms maxWait.
     */
    public NamedLock() {
        this.count = 0;
        this.waitTime = 100L;
        this.threadWait = new HashMap<>();
        this.maxWait = 1000L;
    }

    public void lock(String name, Procedure procedure) {
        this.internalLock(name);
        procedure.action();
        this.internalUnlock(name);
    }

    public <T> T lock(String name, Function<T> function) {
        this.internalLock(name);
        try {
            return function.action();
        } finally {
            this.internalUnlock(name);
        }
    }

    private void internalLock(String name) {
        do {
            synchronized (syncObject) {
                if(this.currentLock == null) {
                    this.currentLock = name;
                    this.count = 1;
                    this.threadWait.remove(Thread.currentThread());
                    this.forceRelease = false;
                    return;
                } else if (name.equals(currentLock)  && !this.forceRelease) {
                    this.count++;
                    this.threadWait.remove(Thread.currentThread());
                    return;
                } else if(this.threadWait.get(Thread.currentThread()) == null) {
                    this.threadWait.put(Thread.currentThread(),System.currentTimeMillis());
                } else if((this.threadWait.get(Thread.currentThread()) + this.maxWait) < System.currentTimeMillis()) {
                    this.forceRelease = true;
                }
            }
            try {
                Thread.sleep(this.waitTime);
            } catch (InterruptedException e) {
            }
        } while(true);
    }

    public void internalUnlock(String name) {
        synchronized (syncObject) {
            this.count--;
            if(this.count <= 0) {
                this.count = 0;
                this.currentLock = null;
            }
        }
    }

    public Future lockFuture(String name, Procedure procedure) {
        return TaskManager.getInstance().submit(new NamedLockRunnableProcedure(name, procedure));
    }

    public <T> Future<T> lockFuture(String name, Function<T> function) {
        return TaskManager.getInstance().submit(new NamedLockRunnableFunction(name, function));
    }

    public void lockRecursiveAction(String name, RecursiveAction recursiveAction) {
        TaskManager.getInstance().execute(new NamedLockRecursiveAction(name, recursiveAction));
    }

    public <T> Stream<T> lockRecursiveFutureAction(String name, RecursiveFutureAction<T> recursiveFutureAction) {
        return TaskManager.getInstance().invoke(new NamedLockRecurseFutureAction<T>(name, recursiveFutureAction));
    }


    private class NamedLockRecurseFutureAction<T> extends java.util.concurrent.RecursiveTask<Stream<T>> {
        private String name;
        private RecursiveFutureAction<T> recursiveFutureAction;

        public NamedLockRecurseFutureAction(String name, RecursiveFutureAction<T> recursiveFutureAction) {
            this.name = name;
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
                internalLock(name);
                try {
                    return TaskManager.getInstance().invoke(recursiveFutureAction);
                } finally {
                    internalUnlock(name);
                }
            }
        }
    }

    private class NamedLockRecursiveAction extends java.util.concurrent.RecursiveAction {
        private String name;
        private RecursiveAction recursiveAction;

        public NamedLockRecursiveAction(String name, RecursiveAction recursiveAction) {
            this.name = name;
            this.recursiveAction = recursiveAction;
        }

        /**
         * The main computation performed by this task.
         */
        @Override
        protected void compute() {
            internalLock(name);
            try {
                invokeAll(this.recursiveAction);
            } finally {
                internalUnlock(name);
            }
        }
    }

    private class NamedLockRunnableProcedure  implements Runnable{
        private String name;
        private Procedure procedure;

        public NamedLockRunnableProcedure(String name, Procedure procedure) {
            this.name = name;
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
            internalLock(name);
            try {
                procedure.action();
            } finally {
                internalUnlock(name);
            }
        }
    }

    private class NamedLockRunnableFunction<T>  implements Callable {
        private String name;
        private Function<T> function;

        public NamedLockRunnableFunction(String name, Function<T> function) {
            this.name = name;
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
            internalLock(name);
            try {
                return function.action();
            } finally {
                internalUnlock(name);
            }
        }
    }

}
