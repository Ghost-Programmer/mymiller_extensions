package name.mymiller.lang.concurrent;

import name.mymiller.task.RecursiveAction;
import name.mymiller.task.RecursiveFutureAction;
import name.mymiller.task.TaskManager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public class NamedLock {

    private Object syncObject = new Object();
    private String currentLock = null;
    private Integer count ;
    private Long waitTime;
    private Long maxWait;
    private Map<Thread, Long> threadWait;
    private Boolean forceRelease = false;

    public NamedLock(Long waitTime, Long maxWait) {
        this.count = 0;
        this.waitTime = waitTime;
        this.threadWait = new HashMap<>();
        this.maxWait = maxWait;
    }

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
