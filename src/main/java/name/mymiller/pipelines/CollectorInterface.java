/*

 */
package name.mymiller.pipelines;

/**
 * Interface that extends the PipeInterface to all for retrieving a collection.
 *
 * @author jmiller
 *
 * @param <R> Type of collection to be returned.
 */
public interface CollectorInterface<S, R> extends PipeInterface<S, S> {
    /**
     * Get the current collection that should be returned.
     *
     * @return Collection returned.
     */
    R getCollection();
}
