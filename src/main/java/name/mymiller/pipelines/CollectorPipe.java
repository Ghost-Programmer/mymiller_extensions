/*

 */
package name.mymiller.pipelines;

import java.util.List;
import java.util.stream.Collector;

/**
 * /** Pipe used for collecting the data coming down the pipe. Data is passed on
 * after it is is added to the collection
 *
 * @author jmiller
 *
 * @param <S> Data Source Type
 * @param <A> Accumulator
 * @param <R> Collection Type
 */
public class CollectorPipe<S, A, R> implements CollectorInterface<S, R> {

    private final Collector<S, A, R> collector;
    private final A supplier;

    public CollectorPipe(Collector<S, A, R> collector) {
        this.collector = collector;
        this.supplier = this.collector.supplier().get();
    }

    @Override
    public R getCollection() {
        return this.collector.finisher().apply(this.supplier);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * name.mymiller.utils.pipelines.PipeInterface#process(java.lang.
     * Object)
     */
    @Override
    public S process(S data, List<PipeFuture<?>> futures, String pipelineName, boolean isParallel) throws Throwable {
        this.collector.accumulator().accept(this.supplier, data);
        return data;
    }

}
