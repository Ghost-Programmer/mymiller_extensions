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
package name.mymiller.job;

import name.mymiller.lang.singleton.SingletonInterface;
import java.util.logging.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

/**
 * Manager to handle processing of Jobs
 *
 * @author jmiller
 */
public class JobManager implements SingletonInterface<JobManager> {

    /**
     * Job Manager Global Instance
     */
    private static JobManager global_instance = null;
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

    /**
     * Constructor protected to limit instantiation.
     */
    protected JobManager(int reserveProcessors, int processorMultiplier) {
        this.namedThreads = new HashMap<>();
        int processors = Runtime.getRuntime().availableProcessors();

        if (reserveProcessors >= processors) {
            processors = processorMultiplier;
            Logger.getLogger(this.getClass().getName()).info("Job Manager Processors: " + processors);
            Logger.getLogger(this.getClass().getName())
                    .info("Job Manager Reserved Processors: " + (Runtime.getRuntime().availableProcessors() - 1));
        } else {
            processors = (processors - reserveProcessors) * processorMultiplier;
            Logger.getLogger(this.getClass().getName()).info("Job Manager Processors: " + processors);
            Logger.getLogger(this.getClass().getName()).info("Job Manager Reserved Processors: " + reserveProcessors);
        }

        this.pool = Executors.newFixedThreadPool(processors);

        this.scheduledPool = Executors.newScheduledThreadPool(1);
    }

    /**
     * @return Global Instance of the Job Manager
     */
    public static JobManager getInstance() {
        if (JobManager.global_instance == null) {
            JobManager.global_instance = new JobManager(1, 2);
        }
        return JobManager.global_instance;
    }

    public static JobManager getInstance(int reserveProcessors, int processorMultiplier) {
        if (JobManager.global_instance == null) {
            JobManager.global_instance = new JobManager(reserveProcessors, processorMultiplier);
        }
        return JobManager.global_instance;
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
     * Create a new thread to execute the name.mymiller.job.
     *
     * @param name    Name of the thread to execute the name.mymiller.job
     * @param service Job to execute.
     */
    public void createService(final String name, final AbstractService service) {
        final Thread thread = new Thread(service, name);

        this.namedThreads.put(name, thread);

        thread.start();
    }

    /**
     * Executes the name.mymiller.job asynchronously.
     *
     * @param job Job to process
     */
    public void executeJob(final Job job) {
        this.pool.execute(job);
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
     * Creates and executes a one-shot name.mymiller.job that becomes
     * enabled after the given delay.
     *
     * @param job   the task to execute
     * @param delay the time from now to delay execution
     * @param unit  the time unit of the delay parameter
     * @return a ScheduledFuture that can be used to extract result or cancel
     * @see ScheduledExecutorService#schedule(Runnable, long, TimeUnit)
     */
    public ScheduledFuture<?> schedule(Job job, long delay, TimeUnit unit) {
        return this.scheduledPool.schedule(job, delay, unit);
    }

    /**
     * Creates and executes a periodic name.mymiller.job that becomes
     * enabled first after the given initial delay, and subsequently with the given
     * period; that is executions will commence after initialDelay then
     * initialDelay+period, then initialDelay + 2 * period, and so on. If any
     * execution of the name.mymiller.job encounters an exception,
     * subsequent executions are suppressed. Otherwise, the task will only terminate
     * via cancellation or termination of the executor. If any execution of this
     * task takes longer than its period, then subsequent executions may start late,
     * but will not concurrently execute.
     *
     * @param job          the task to execute
     * @param initialDelay the time to delay first execution
     * @param period       the period between successive executions
     * @param unit         the time unit of the initialDelay and period parameters
     * @return a ScheduledFuture representing pending completion of the task, and
     * whose get() method will throw an exception upon cancellation
     * @see ScheduledExecutorService#scheduleAtFixedRate(Runnable, long, long,
     * TimeUnit)
     */
    public ScheduledFuture<?> scheduleAtFixedRate(Job job, long initialDelay, long period, TimeUnit unit) {
        return this.scheduledPool.scheduleAtFixedRate(job, initialDelay, period, unit);
    }

    public <T> Future<T> scheduleWithFixedDelay(Callable<T> callable, long delay, TimeUnit unit) {
        return this.scheduledPool.schedule(callable, delay, unit);
    }

    /**
     * Creates and executes a periodic name.mymiller.job that becomes
     * enabled first after the given initial delay, and subsequently with the given
     * delay between the termination of one execution and the commencement of the
     * next. If any execution of the name.mymiller.job encounters an
     * exception, subsequent executions are suppressed. Otherwise, the task will
     * only terminate via cancellation or termination of the executor.
     *
     * @param job          the task to execute
     * @param initialDelay the time to delay first execution
     * @param delay        the delay between the termination of one execution and
     *                     the commencement of the next
     * @param unit         the time unit of the initialDelay and delay parameters
     * @return a ScheduledFuture representing pending completion of the task, and
     * whose get() method will throw an exception upon cancellation
     * @see ScheduledExecutorService#scheduleWithFixedDelay(Runnable, long, long,
     * TimeUnit)
     */
    public ScheduledFuture<?> scheduleWithFixedDelay(Job job, long initialDelay, long delay, TimeUnit unit) {
        return this.scheduledPool.scheduleWithFixedDelay(job, initialDelay, delay, unit);
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
     * Submits a name.mymiller.job for execution
     *
     * @param job Job to process
     * @return Future representing the completion of the
     * name.mymiller.job.
     */
    public Future<?> submit(final Job job) {
        return this.pool.submit(job);
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
}
