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
package name.mymiller.task;

import name.mymiller.lang.singleton.SingletonInterface;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * Manager to handle processing of Jobs
 *
 * @author jmiller
 */
public class TaskManager implements SingletonInterface<TaskManager> {

    /**
     * Action Manager Global Instance
     */
    private static TaskManager global_instance = null;
    /**
     * Thread pool to perform the processing
     */
    protected ExecutorService pool = null;
    /**
     * Thread pool for scheduled jobs
     */
    protected ScheduledExecutorService scheduledPool = null;
    /**
     * Map of named threads created.
     */
    private HashMap<String, Thread> namedThreads = null;

    private ForkJoinPool forkJoinPool = null;


    /**
     * Constructor protected to limit instantiation.
     */
    protected TaskManager(int reserveProcessors, int processorMultiplier) {
        this.namedThreads = new HashMap<>();
        int processors = Runtime.getRuntime().availableProcessors();

        if (reserveProcessors >= processors) {
            processors = processorMultiplier;
            Logger.getLogger(this.getClass().getName()).info("Action Manager Processors: " + processors);
            Logger.getLogger(this.getClass().getName())
                    .info("Action Manager Reserved Processors: " + (Runtime.getRuntime().availableProcessors() - 1));
        } else {
            processors = (processors - reserveProcessors) * processorMultiplier;
            Logger.getLogger(this.getClass().getName()).info("Action Manager Processors: " + processors);
            Logger.getLogger(this.getClass().getName()).info("Action Manager Reserved Processors: " + reserveProcessors);
        }

        this.pool = Executors.newFixedThreadPool(processors);

        this.forkJoinPool = ForkJoinPool.commonPool();

        this.scheduledPool = Executors.newScheduledThreadPool(1);
    }

    /**
     * @return Global Instance of the Action Manager
     */
    public static TaskManager getInstance() {
        if (TaskManager.global_instance == null) {
            TaskManager.global_instance = new TaskManager(1, 2);
        }
        return TaskManager.global_instance;
    }

    public static TaskManager getInstance(int reserveProcessors, int processorMultiplier) {
        if (TaskManager.global_instance == null) {
            TaskManager.global_instance = new TaskManager(reserveProcessors, processorMultiplier);
        }
        return TaskManager.global_instance;
    }

    /**
     * Blocks until all tasks have completed execution after a shutdown request, or
     * the timeout occurs, or the current thread is interrupted, whichever happens
     * first.
     *
     * @param timeout the maximum time to wait
     * @param unit    the time unit of the timeout argument
     * @return true if this executor terminated and false if the timeout elapsed
     * before termination
     * @throws InterruptedException if interrupted while waiting
     * @see ExecutorService#awaitTermination(long, TimeUnit)
     */
    public boolean awaitTermination(final long timeout, final TimeUnit unit) throws InterruptedException {
        return this.pool.awaitTermination(timeout, unit);
    }

    /**
     * Create a new thread to execute the name.mymiller.action.
     *
     * @param name    Name of the thread to execute the name.mymiller.action
     * @param service Action to execute.
     */
    public void createService(final String name, final AbstractService service) {
        final Thread thread = new Thread(service, name);

        this.namedThreads.put(name, thread);

        thread.start();
    }

    /**
     * Executes the name.mymiller.action asynchronously.
     *
     * @param action Action to process
     */
    public void executeJob(final Action action) {
        this.pool.execute(action);
    }

    /**
     * Returns true if this executor has been shut down.
     *
     * @return true if this executor has been shut down
     * @see ExecutorService#isShutdown()
     */
    public boolean isShutdown() {
        return this.pool.isShutdown();
    }

    /**
     * Returns true if all tasks have completed following shut down. Note that
     * isTerminated is never true unless either shutdown or shutdownNow was called
     * first.
     *
     * @return true if all tasks have completed following shut down
     * @see ExecutorService#isTerminated()
     */
    public boolean isTerminated() {
        return this.pool.isTerminated();
    }

    /**
     * Creates and executes a one-shot name.mymiller.action that becomes
     * enabled after the given delay.
     *
     * @param action   the task to execute
     * @param delay the time from now to delay execution
     * @param unit  the time unit of the delay parameter
     * @return a ScheduledFuture that can be used to extract result or cancel
     * @see ScheduledExecutorService#schedule(Runnable, long, TimeUnit)
     */
    public ScheduledFuture<?> schedule(Action action, long delay, TimeUnit unit) {
        return this.scheduledPool.schedule(action, delay, unit);
    }

    /**
     * Creates and executes a periodic name.mymiller.action that becomes
     * enabled first after the given initial delay, and subsequently with the given
     * period; that is executions will commence after initialDelay then
     * initialDelay+period, then initialDelay + 2 * period, and so on. If any
     * execution of the name.mymiller.action encounters an exception,
     * subsequent executions are suppressed. Otherwise, the task will only terminate
     * via cancellation or termination of the executor. If any execution of this
     * task takes longer than its period, then subsequent executions may start late,
     * but will not concurrently execute.
     *
     * @param action          the task to execute
     * @param initialDelay the time to delay first execution
     * @param period       the period between successive executions
     * @param unit         the time unit of the initialDelay and period parameters
     * @return a ScheduledFuture representing pending completion of the task, and
     * whose get() method will throw an exception upon cancellation
     * @see ScheduledExecutorService#scheduleAtFixedRate(Runnable, long, long,
     * TimeUnit)
     */
    public ScheduledFuture<?> scheduleAtFixedRate(Action action, long initialDelay, long period, TimeUnit unit) {
        return this.scheduledPool.scheduleAtFixedRate(action, initialDelay, period, unit);
    }

    public <T> Future<T> scheduleWithFixedDelay(Callable<T> callable, long delay, TimeUnit unit) {
        return this.scheduledPool.schedule(callable, delay, unit);
    }

    /**
     * Creates and executes a periodic name.mymiller.action that becomes
     * enabled first after the given initial delay, and subsequently with the given
     * delay between the termination of one execution and the commencement of the
     * next. If any execution of the name.mymiller.action encounters an
     * exception, subsequent executions are suppressed. Otherwise, the task will
     * only terminate via cancellation or termination of the executor.
     *
     * @param action          the task to execute
     * @param initialDelay the time to delay first execution
     * @param delay        the delay between the termination of one execution and
     *                     the commencement of the next
     * @param unit         the time unit of the initialDelay and delay parameters
     * @return a ScheduledFuture representing pending completion of the task, and
     * whose get() method will throw an exception upon cancellation
     * @see ScheduledExecutorService#scheduleWithFixedDelay(Runnable, long, long,
     * TimeUnit)
     */
    public ScheduledFuture<?> scheduleWithFixedDelay(Action action, long initialDelay, long delay, TimeUnit unit) {
        return this.scheduledPool.scheduleWithFixedDelay(action, initialDelay, delay, unit);
    }

    /**
     * Stops all execution once all jobs complete.
     */
    public void shutdown() {
        this.pool.shutdown();
        this.scheduledPool.shutdown();
    }

    /**
     * Attempts to stop all actively executing tasks, halts the processing of
     * waiting tasks, and returns a list of the tasks that were awaiting execution.
     * This method does not wait for actively executing tasks to terminate. Use
     * awaitTermination to do that.
     * <p>
     * There are no guarantees beyond best-effort attempts to stop processing
     * actively executing tasks. For example, typical implementations will cancel
     * via Thread.interrupt(), so any task that fails to respond to interrupts may
     * never terminate.
     *
     * @return list of tasks that never commenced execution
     * @see ExecutorService#shutdownNow()
     */
    public List<Runnable> shutdownNow() {
        this.scheduledPool.shutdownNow();
        return this.pool.shutdownNow();
    }

    /**
     * Submits a callable to to the pool for processing
     *
     * @param callable Executable unit to process
     * @return Future for the callable
     */
    public <T> Future<T> submit(Callable<T> callable) {
        return this.pool.submit(callable);
    }

    /**
     * Submits a name.mymiller.action for execution
     *
     * @param action Action to process
     * @return Future representing the completion of the
     * name.mymiller.action.
     */
    public Future<?> submit(final Action action) {
        return this.pool.submit(action);
    }

    /**
     * Sumits a Runnable to the processing pool
     *
     * @param runnable Runnable to process
     * @return Future for the Runnable.
     */
    public Future<?> submit(Runnable runnable) {
        return this.pool.submit(runnable);
    }

    /**
     *
     * @return Pool Executor
     */
    public ExecutorService getPool() {
        return pool;
    }

    /**
     *
     * @return ScheduledExecuteService
     */
    public ScheduledExecutorService getScheduledPool() {
        return scheduledPool;
    }

    /**
     * Performs the given task, returning its result upon completion.
     * If the computation encounters an unchecked Exception or Error,
     * it is rethrown as the outcome of this invocation.  Rethrown
     * exceptions behave in the same way as regular exceptions, but,
     * when possible, contain stack traces (as displayed for example
     * using {@code ex.printStackTrace()}) of both the current thread
     * as well as the thread actually encountering the exception;
     * minimally only the latter.
     *
     * @param action the action
     * @return the task's result
     * @throws NullPointerException if the task is null
     * @throws java.util.concurrent.RejectedExecutionException if the task cannot be
     *         scheduled for execution
     */
    public void invoke(Action action) {
        forkJoinPool.invoke(action);
    }

    /**
     * Arranges for (asynchronous) execution of the given task.
     *
     * @param action the task
     * @throws NullPointerException if the task is null
     * @throws java.util.concurrent.RejectedExecutionException if the task cannot be
     *         scheduled for execution
     */
    public void execute(RecursiveAction action) {
        forkJoinPool.execute(action);
    }

    /**
     * Arranges for (asynchronous) execution of the given task.
     *
     * @param task the task
     * @throws NullPointerException if the task is null
     * @throws java.util.concurrent.RejectedExecutionException if the task cannot be
     *         scheduled for execution
     */
    public void execute(ForkJoinTask<?> task) {
        forkJoinPool.execute(task);
    }

    /**
     * Performs the given task, returning its result upon completion.
     * If the computation encounters an unchecked Exception or Error,
     * it is rethrown as the outcome of this invocation.  Rethrown
     * exceptions behave in the same way as regular exceptions, but,
     * when possible, contain stack traces (as displayed for example
     * using {@code ex.printStackTrace()}) of both the current thread
     * as well as the thread actually encountering the exception;
     * minimally only the latter.
     *
     * @param task the task
     * @return the task's result
     * @throws NullPointerException if the task is null
     * @throws java.util.concurrent.RejectedExecutionException if the task cannot be
     *         scheduled for execution
     */
    public <T> T invoke(ForkJoinTask<T> task) {
        return forkJoinPool.invoke(task);
    }

    /**
     * Submits a ForkJoinTask for execution.
     *
     * @param task the task to submit
     * @return the task
     * @throws NullPointerException if the task is null
     * @throws java.util.concurrent.RejectedExecutionException if the task cannot be
     *         scheduled for execution
     */
    public <T> ForkJoinTask<T> submit(ForkJoinTask<T> task) {
        return forkJoinPool.submit(task);
    }
}
