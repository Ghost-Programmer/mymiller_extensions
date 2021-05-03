/*

 */
package name.mymiller.pipelines;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Future representing the final data for a pipeline.
 *
 * @author jmiller
 * @param <V> Type of final data
 *
 */
public class PipeFuture<V> implements Future<V> {

    /**
     * Object used for synchronization between threads
     */
    private Object syncObject = null;

    /**
     * The final result from processing
     */
    private V futureObject = null;

    /**
     * String identifier for the pipeline. Will match the name of the pipeline
     */
    private String identifier = null;

    private UUID pipelineId = null;

    /**
     * Boolean indicating if the future is complete.
     */
    private AtomicBoolean done = null;

    /**
     * Constructor that takes the identifier for the future.
     *
     * @param identifier String indicating the pipeline this ia future too.
     */
    public PipeFuture(String identifier, UUID pipelineId) {
        this.syncObject = new Object();
        this.done = new AtomicBoolean(false);
        this.identifier = identifier;
        this.pipelineId = pipelineId;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public V get() {
        return this.futureObject;
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException {
        unit.timedWait(this.syncObject, timeout);
        return this.futureObject;
    }

    /**
     * @return the identifier
     */
    public String getIdentifier() {
        return this.identifier;
    }

    /**
     * @return the pipelineId
     */
    public UUID getPipelineId() {
        return this.pipelineId;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return this.done.get();
    }

    /**
     * @param done the done to set
     */
    public synchronized void setDone(boolean done) {
        this.done.set(done);
    }

    /**
     * @param futureObject the futureObject to set
     */
    public synchronized void setFutureObject(V futureObject) {
        this.futureObject = futureObject;
        this.syncObject.notifyAll();
    }

}
