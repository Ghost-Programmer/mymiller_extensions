/*

 */
package name.mymiller.pipelines.pipes;

import name.mymiller.pipelines.PipeFuture;
import name.mymiller.pipelines.PipeInterface;

import java.util.List;

/**
 * @author jmiller
 *
 */
public class ObjectInputStreamPIpe<S, T> implements PipeInterface<S, T> {

    @Override
    public T process(S data, List<PipeFuture<?>> futures, String pipelineName, boolean isParallel) throws Throwable {
        // TODO Auto-generated method stub
        return null;
    }

}
