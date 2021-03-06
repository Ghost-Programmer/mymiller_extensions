package name.mymiller.pipelines.pipes;

import name.mymiller.pipelines.PipeFuture;
import name.mymiller.pipelines.PipeInterface;

import java.io.ObjectOutputStream;
import java.util.List;

public class ObjectOutputStreamPipe<S> implements PipeInterface<S, S> {

    private final ObjectOutputStream oos;

    /**
     * @param oos
     */
    public ObjectOutputStreamPipe(ObjectOutputStream oos) {
        super();
        this.oos = oos;
    }

    @Override
    public S process(S data, List<PipeFuture<?>> futures, String pipelineName, boolean isParallel) throws Throwable {

        if (this.oos != null) {
            this.oos.writeObject(data);
        }
        return data;
    }

}
