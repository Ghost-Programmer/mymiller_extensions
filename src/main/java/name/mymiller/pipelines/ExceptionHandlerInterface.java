package name.mymiller.pipelines;

import java.util.List;

/**
 * Interface for Exception handling in pipelines.
 *
 * @author jmiller
 */
public interface ExceptionHandlerInterface {

    /**
     * Process Exceptions to determine if processing should continue to occur
     *
     * @param throwable    Throwable caught.
     * @param pipe         PipeInterface that received the exception
     * @param data         Data passed to the pipe.
     * @param futures      Futures passed to the pipe.
     * @param pipelineName Name of the pipeline.
     * @param isParallel   Indicates if the processing is occurring in Parallel
     * @return Boolean true indicates attempt to process, false abort.
     */
    boolean process(Throwable throwable, PipeInterface<?, ?> pipe, final Object data,
                    List<PipeFuture<?>> futures, String pipelineName, boolean isParallel);
}
