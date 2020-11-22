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

    /**
     * Once the lock is achieved will execute the Procedure and then release the lock.
     * @param name String contain the name of this instance
     * @param procedure Procedure interface to execute once locked.
     */
    public void lock(String name, Procedure procedure) {
        this.lock(name);
        procedure.action();
        this.unlock();
    }

    /**
     * Once the lock is achieved will execute the Function, release the lock, and return the value
     * @param name String containing the name of this instance.
     * @param function Function interface to execute once locked.
     * @param <T> Type of return
     * @return Value returned from the Function.
     */
    public <T> T lock(String name, Function<T> function) {
        this.lock(name);
        try {
            return function.action();
        } finally {
            this.unlock();
        }
    }

    /**
     * Lock based on the name instance.
     * @param name Name of instance to lock
     */
    public void lock(String name) {
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

    /**
     * Call to unlock for a previous call to lock()
     */
    public void unlock() {
        synchronized (syncObject) {
            this.count--;
            if(this.count <= 0) {
                this.count = 0;
                this.currentLock = null;
            }
        }
    }
    /**
     * Continue process but process this Procedure in the future, results can be checked with a Future that is returned
     * @param name String containing the instance to lock
     * @param procedure Procedure interface to execute once locked
     * @return Future returned to check status
     */
    public Future lockFuture(String name, Procedure procedure) {
        return TaskManager.getInstance().submit(new NamedLockRunnableProcedure(name, procedure));
    }
    /**
     * Continue processing but process this Function in the future, results can be check with a Future that is returned.
     * @param name String containing the instance to lock
     * @param function Function interface to execute once locked.
     * @param <T> Type of return
     * @return Future returned to check status and value
     */
    public <T> Future<T> lockFuture(String name, Function<T> function) {
        return TaskManager.getInstance().submit(new NamedLockRunnableFunction(name, function));
    }
    /**
     * Using the ForkJoinPool of the TaskManager, execute this task one acquire lock.
     * @param name String containing the instance to lock
     * @param recursiveAction RecursiveAction to execute once locked.
     */
    public void lockRecursiveAction(String name, RecursiveAction recursiveAction) {
        TaskManager.getInstance().execute(new NamedLockRecursiveAction(name, recursiveAction));
    }
    /**
     * Using the ForkJoinPoool of the TaskManager, execute this task once the acquire is locked.
     * @param name String containing the instance to lock
     * @param recursiveFutureAction RecursiveFutureAction to execute once locked.
     * @param <T> Type of return
     * @return Future returned to check status and value
     */
    public <T> Stream<T> lockRecursiveFutureAction(String name, RecursiveFutureAction<T> recursiveFutureAction) {
        return TaskManager.getInstance().invoke(new NamedLockRecurseFutureAction<T>(name, recursiveFutureAction));
    }

    /**
     * Internal class used for the RecursiveFutureAction
     * @param <T> Type of Return
     */
    private class NamedLockRecurseFutureAction<T> extends java.util.concurrent.RecursiveTask<Stream<T>> {
        /**
         * name of instance to lock
         */
        private String name;
        /**
         * RecursiveFutureAction to execute
         */
        private RecursiveFutureAction<T> recursiveFutureAction;
        /**
         * Constructor to generate internal RecursiveTask for processing
         * @param recursiveFutureAction
         */
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
                lock(name);
                try {
                    return TaskManager.getInstance().invoke(recursiveFutureAction);
                } finally {
                    unlock();
                }
            }
        }
    }
    /**
     * Internal class used for RecursiveAction
     */
    private class NamedLockRecursiveAction extends java.util.concurrent.RecursiveAction {
        /**
         * name of instance to lock
         */
        private String name;
        /**
         * RecursiveAction to execute
         */
        private RecursiveAction recursiveAction;
        /**
         * Constructor to generate internal Recursive Task for processing
         * @param recursiveAction
         */
        public NamedLockRecursiveAction(String name, RecursiveAction recursiveAction) {
            this.name = name;
            this.recursiveAction = recursiveAction;
        }

        /**
         * The main computation performed by this task.
         */
        @Override
        protected void compute() {
            lock(name);
            try {
                invokeAll(this.recursiveAction);
            } finally {
                unlock();
            }
        }
    }
    /**
     * Internal Class used to schedule a Procedure
     */
    private class NamedLockRunnableProcedure  implements Runnable{
        /**
         * name of instance to lock
         */
        private String name;
        /**
         * Procedure to run
         */
        private Procedure procedure;
        /**
         * Constructor with Procedure to run
         * @param procedure Procedure to run
         */
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
            lock(name);
            try {
                procedure.action();
            } finally {
                unlock();
            }
        }
    }
    /**
     * Internal Class used to schedule a Function
     * @param <T> Type of Return
     */
    private class NamedLockRunnableFunction<T>  implements Callable {
        /**
         * name of instance to lock
         */
        private String name;
        /**
         * Function to run
         */
        private Function<T> function;
        /**
         * Constructor with Function to run
         * @param function Function to run
         */
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
            lock(name);
            try {
                return function.action();
            } finally {
                unlock();
            }
        }
    }

}
