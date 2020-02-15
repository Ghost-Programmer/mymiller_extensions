package name.mymiller.pipelines;

import java.util.List;

/**
 * Interface for a Pipe object to implement for compatiablity with the Pipeline.
 *
 * @author jmiller
 */
public interface PipeInterface<S, T> {

    /**
     * @return List of any futures from sub-pipelines
     */
    default List<PipeFuture<?>> getFutures() {
        return null;
    }

    /**
     * Method to process the object pushed to the pipe.
     *
     * @param data    Object being pushed to the pipe
     * @param futures list of futures associated with this data as possible
     *                terminate points.
     * @param pipelineName  containing the name of this pipeline.
     * @return Object after processing has occurred.
     * @throws Throwable TODO
     */
    T process(final S data, List<PipeFuture<?>> futures, String pipelineName, boolean isParallel)
            throws Throwable;

}
