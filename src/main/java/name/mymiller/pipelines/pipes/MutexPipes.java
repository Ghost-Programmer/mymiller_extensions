package name.mymiller.pipelines.pipes;

import name.mymiller.pipelines.PipeFuture;
import name.mymiller.pipelines.PipeInterface;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Generate Locking and Unlocking pipes for a pipeline.  Create an instance of MutexPipes and then use getLockPipe()
 * and getLockPipe() to get the pipes to add to pipeline.  Locking is on a thread. Based on a ReentrantLock.
 */
public class MutexPipes {

    /**
     * ReentrantLock to use behind the pipe.
     */
    private final ReentrantLock mutex = new ReentrantLock();

    /**
     * Locks the pipeline for all threads
     * @param <S> Type of data passing through
     * @return MutexLockPipe to perform locking.
     */
    public <S> MutexLockPipe<S> getLockPipe() {
        return new MutexLockPipe<>();
    }
    /**
     * Unlocks the pipeline for all threads
     * @param <S> Type of data passing through
     * @return MutexUnlockPipe to perform unlocking.
     */
    public <S> MutexUnlockPipe<S> getUnlockPipe() {
        return new MutexUnlockPipe<>();
    }

    public class MutexLockPipe<S> implements PipeInterface<S,S> {

        /**
         * Method to process the object pushed to the pipe.
         *
         * @param data         Object being pushed to the pipe
         * @param futures      list of futures associated with this data as possible
         *                     terminate points.
         * @param pipelineName containing the name of this pipeline.
         * @param isParallel
         * @return Object after processing has occurred.
         */
        @Override
        public S process(S data, List<PipeFuture<?>> futures, String pipelineName, boolean isParallel) {
            mutex.lock();
            return data;
        }
    }

    public class MutexUnlockPipe<S> implements PipeInterface<S,S> {

        /**
         * Method to process the object pushed to the pipe.
         *
         * @param data         Object being pushed to the pipe
         * @param futures      list of futures associated with this data as possible
         *                     terminate points.
         * @param pipelineName containing the name of this pipeline.
         * @param isParallel
         * @return Object after processing has occurred.
         */
        @Override
        public S process(S data, List<PipeFuture<?>> futures, String pipelineName, boolean isParallel) {
            mutex.unlock();
            return data;
        }
    }

}
