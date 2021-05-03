package name.mymiller.pipelines;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;

/**
 * Pipeline object to manage your processing pipeline. Pipe segments are added
 * in the ordered they are connected to the Pipeline. They are called in
 * sequential order. Add or create a Collector at the end to collect a result if
 * needed.
 *
 * @author jmiller
 */
public class Pipeline<S, T> {

    private final DefaultExceptionHandler defaultExceptionHandler = new DefaultExceptionHandler();
    /**
     * Name of this pipeline.
     */
    private final String pipelineName;
    private final UUID pipelineId;
    /**
     * Executor to use for parallel processing
     */
    private ExecutorService executorService = null;
    /**
     * List of pipes to be processed.
     */
    private List<PipeInterface<?, ?>> pipes;
    /**
     * Exception Handler to use for processing
     */
    private ExceptionHandlerInterface exceptionHandler = this.defaultExceptionHandler;

    /**
     * Copy constructor used to create a duplicate pipeline.
     *
     * @param pipeline Pipeline to duplciate.
     */
    private Pipeline(Pipeline<?, ?> pipeline) {
        super();
        this.executorService = pipeline.executorService;
        this.pipes = new ArrayList<>(pipeline.pipes);
        this.pipelineName = pipeline.pipelineName;
        this.pipelineId = pipeline.pipelineId;
    }

    /**
     * Constructor that creates an empty pipeline with the specified name.
     *
     * @param pipelineName
     */
    private Pipeline(String pipelineName) {
        super();
        this.pipelineName = pipelineName;
        this.pipelineId = UUID.randomUUID();
        this.executorService = new ForkJoinPool(Runtime.getRuntime().availableProcessors(),
                ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);
    }

    /**
     * Static method to create a pipeline
     *
     * @param name Name to give the pipeline
     * @return Pipeline ready to received pipe segments
     */
    public static <K> Pipeline<K, K> start(String name) {
        final Pipeline<K, K> chain = new Pipeline<>(name);
        chain.pipes = new ArrayList<>();
        return chain;
    }

    /**
     * Creates an Action Segment for the pipeline with an action functional
     * interface.
     *
     * @param action Action Functional Interface that will be used.
     * @return Pipeline with the additional segment added.
     */
    @SuppressWarnings("unchecked")
    public <V> Pipeline<S, V> action(Function<? super T, ? extends V> action) {
        if (this.pipes.isEmpty()) {
            return (Pipeline<S, V>) this.connectFirstPipe(new ActionPipe<T, V>(action));
        }
        return this.connectInternalPipe(new ActionPipe<>(action));
    }

    /**
     * Create a Collector pipe segment by passing in the collector interface
     *
     * @param collector Collector Funcation interface that will be used.
     * @return Pipeline with the additional segment added.
     */
    @SuppressWarnings("unchecked")
    public <A, V> Pipeline<S, T> collect(Collector<T, A, V> collector) {
        if (this.pipes.isEmpty()) {
            return (Pipeline<S, T>) this.connectFirstPipe(new CollectorPipe<>(collector));
        }
        return this.connectInternalPipe(new CollectorPipe<>(collector));
    }

    /**
     * Create a Collector pipe segment by passing in the collector interface
     *
     * @param collector Pipe segment Collector Pipe that will be used.
     * @return Pipeline with the additional segment added.
     */
    @SuppressWarnings("unchecked")
    public <V> Pipeline<S, T> collect(CollectorInterface<T, V> collector) {
        if (this.pipes.isEmpty()) {
            return (Pipeline<S, T>) this.connectFirstPipe(collector);
        }
        return this.connectInternalPipe(collector);
    }

    /**
     * Method to add your own Pipe to the pipeline. Implement your pipe using the
     * PipeInterface
     *
     * @param pipe Pipe to add to the pipeline
     * @return Pipeline with the additional segment added.
     */
    @SuppressWarnings("unchecked")
    public <V> Pipeline<S, V> connect(PipeInterface<T, V> pipe) {
        if (this.pipes.isEmpty()) {
            return (Pipeline<S, V>) this.connectFirstPipe(pipe);
        }
        return this.connectInternalPipe(pipe);
    }

    /**
     * Internal method to connect the first pipe to the pipeline.
     *
     * @param pipe Pipe to add to the pipeline
     * @return Pipeline with the segment added.
     */
    private <K, L> Pipeline<K, L> connectFirstPipe(PipeInterface<K, L> pipe) {
        final Pipeline<K, L> pipeline = new Pipeline<>(this);
        pipeline.pipes.add(pipe);
        return pipeline;
    }

    /**
     * Internal call to added a pipe to a pipelien with existing pipes.
     *
     * @param pipe Pipe to add to the pipeline
     * @return Pipeline with the segment added.
     */
    private <V> Pipeline<S, V> connectInternalPipe(PipeInterface<T, V> pipe) {
        final Pipeline<S, V> pipeline = new Pipeline<>(this);
        pipeline.pipes.add(pipe);
        return pipeline;
    }

    /**
     * Add a pipe for limiting the data to be distinct. Identical data objects will
     * be blocked if the two data objects would match with .equals().
     *
     * @return Pipeline with the segment added.
     */
    @SuppressWarnings("unchecked")
    public Pipeline<S, T> distinct() {
        if (this.pipes.isEmpty()) {
            return (Pipeline<S, T>) this.connectFirstPipe(new DistinctPipe<T>());
        }
        return this.connectInternalPipe(new DistinctPipe<>());
    }

    /**
     * Add a pipe for limiting the data to be distinct based on a property.
     * Identical key objects will be blocked if the two data keys would match with
     * .equals().
     *
     * @return Pipeline with the segment added.
     */
    @SuppressWarnings("unchecked")
    public Pipeline<S, T> distinctBy(Function<? super T, Object> keyExtractor) {
        if (this.pipes.isEmpty()) {
            return (Pipeline<S, T>) this.connectFirstPipe(new DistinctByPipe<T>(keyExtractor));
        }
        return this.connectInternalPipe(new DistinctByPipe<>(keyExtractor));
    }

    /**
     * Add a pipe for limiting the data to duplicates only. Second instances of
     * identical data objects will be allowed if the two data objects would match
     * with .equals().
     *
     * @return Pipeline with the segment added.
     */
    @SuppressWarnings("unchecked")
    public Pipeline<S, T> duplicates() {
        if (this.pipes.isEmpty()) {
            return (Pipeline<S, T>) this.connectFirstPipe(new DistinctPipe<T>(false));
        }
        return this.connectInternalPipe(new DistinctPipe<>(false));
    }

    /**
     * Add a pipe for limiting the data to be duplicates only based on a property.
     * Second instances of identical key objects will be blocked if the two data
     * keys would match with .equals().
     *
     * @return Pipeline with the segment added.
     */
    @SuppressWarnings("unchecked")
    public Pipeline<S, T> duplicatesBy(Function<? super T, Object> keyExtractor) {
        if (this.pipes.isEmpty()) {
            return (Pipeline<S, T>) this.connectFirstPipe(new DistinctByPipe<T>(keyExtractor, false));
        }
        return this.connectInternalPipe(new DistinctByPipe<>(keyExtractor, false));
    }

    /**
     * Pipeline to add a filter based on a Predicate passed in.
     *
     * @param predicate Predicate providing the filtering logic.
     * @return Pipeline with the segment added.
     */
    @SuppressWarnings("unchecked")
    public Pipeline<S, T> filter(Predicate<? super T> predicate) {
        if (this.pipes.isEmpty()) {
            return (Pipeline<S, T>) this.connectFirstPipe(new FilterPipe<T>(predicate));
        }
        return this.connectInternalPipe(new FilterPipe<>(predicate));
    }

    /**
     * Pipe Fork, to split the data passing in, to multiple additional pipelines.
     *
     * @param pipelines List of Pipelines that this pipe segment forks into
     * @return Pipeline with the segment added.
     */
    @SuppressWarnings("unchecked")
    public Pipeline<S, T> fork(List<Pipeline<T, ?>> pipelines) {
        if (this.pipes.isEmpty()) {
            return (Pipeline<S, T>) this.connectFirstPipe(new ForkPipe<>(pipelines));
        }
        return this.connectInternalPipe(new ForkPipe<>(pipelines));
    }

    /**
     * @return the exceptionHandler
     */
    protected synchronized ExceptionHandlerInterface getExceptionHandler() {
        return this.exceptionHandler;
    }

    /**
     * @param exceptionHandler the exceptionHandler to set
     */
    protected synchronized void setExceptionHandler(ExceptionHandlerInterface exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
        if (this.exceptionHandler == null) {
            this.exceptionHandler = this.defaultExceptionHandler;
        }
    }

    /**
     * @return List of Futures for the next processing
     */
    public List<PipeFuture<?>> getFutures() {
        final ArrayList<PipeFuture<?>> pipeFutures = new ArrayList<>();

        pipeFutures.add(new PipeFuture<T>(this.pipelineName, this.pipelineId));
        for (final PipeInterface<?, ?> pipe : this.pipes) {
            pipeFutures.addAll(pipe.getFutures());
        }
        return pipeFutures;

    }

    /**
     * @return the pipelineId
     */
    public UUID getPipelineId() {
        return this.pipelineId;
    }

    /**
     * @return the pipelineName
     */
    public String getPipelineName() {
        return this.pipelineName;
    }

    /**
     * Internal method for running the code in Parallel
     *
     * @param source  Source data being passed
     * @param futures List of Futures that need to me marked as complete when their
     *                associated pipeline completes.
     */
    public void internalParallel(S source, List<PipeFuture<?>> futures) {
        this.executorService.submit(new PipeRun(this.pipes, source, futures, this.pipelineName, this.exceptionHandler));
    }

    /**
     * Max segment limiting data to a max, anything greater than max, is blocked
     * from passing.
     *
     * @param max        Max of the data being processed.
     * @param comparator Comparator to compare two instances of the data.
     * @return Pipeline with the segment added.
     */
    @SuppressWarnings("unchecked")
    public Pipeline<S, T> max(T max, Comparator<? super T> comparator) {
        if (this.pipes.isEmpty()) {
            return (Pipeline<S, T>) this.connectFirstPipe(new MaxPipe<>(max, comparator));
        }
        return this.connectInternalPipe(new MaxPipe<>(max, comparator));
    }

    /**
     * Min segment limiting data to a min, anything less than min, is blocked from
     * passing.
     *
     * @param min        Min of the data being processed.
     * @param comparator Comparator to compare two instances of the data.
     * @return Pipeline with the segment added.
     */
    @SuppressWarnings("unchecked")
    public Pipeline<S, T> min(T min, Comparator<? super T> comparator) {
        if (this.pipes.isEmpty()) {
            return (Pipeline<S, T>) this.connectFirstPipe(new MinPipe<>(min, comparator));
        }
        return this.connectInternalPipe(new MinPipe<>(min, comparator));
    }

    /**
     * Method to add a peek funcationality to allow the data to be seen by other
     * code outside of the pipeline
     *
     * @param consumer Consumer that shall peek at the data.
     * @return Pipeline with the segment added.
     */
    @SuppressWarnings("unchecked")
    public Pipeline<S, T> peek(Consumer<? super T> consumer) {
        if (this.pipes.isEmpty()) {
            return (Pipeline<S, T>) this.connectFirstPipe(new PeekPipe<T>(consumer));
        }
        return this.connectInternalPipe(new PeekPipe<>(consumer));
    }

    /**
     * Method to process an instance of data on the current thread.
     *
     * @param s S data type that shall start the data processing
     * @return Completed value after processing.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public T process(S s) {
        Object source = s;
        Object target = null;
        for (final PipeInterface pipe : this.pipes) {
            boolean processAgain = true;
            int processAttempt = 0;

            while (processAgain) {
                try {
                    processAttempt++;
                    target = pipe.process(source, null, this.pipelineName, false);
                    processAgain = false;
                } catch (final Throwable throwable) {
                    if (this.exceptionHandler != null) {
                        final boolean response = this.exceptionHandler.process(throwable, pipe, source, null,
                                this.getPipelineName(), false);
                        if (response && (processAttempt == 1)) {
                            processAgain = true;
                        } else {
                            processAgain = false;
                            target = null;
                        }
                    }
                }
            }
            if (target == null) {
                break;
            }
            source = target;
        }
        return (T) target;
    }

    /**
     * Method to process an instance of data in parallel
     *
     * @param source S data type that shall start the data processing
     * @return List of PipeFutures that represent all possible outcomes of the data.
     */
    public List<PipeFuture<?>> processParallel(S source) {
        final List<PipeFuture<?>> futures = this.getFutures();

        this.executorService.submit(new PipeRun(this.pipes, source, futures, this.pipelineName, this.exceptionHandler));

        return futures;
    }

    /**
     * Allow a data object to flow to another pipeline if the predicate matches.
     *
     * @param predicate Predicate to determine if the data is allowed to flow on the
     *                  alternate pipeline
     * @param pipeline  Alternate pipeline to send the data.
     * @return Pipeline with the segment added.
     */
    @SuppressWarnings("unchecked")
    public Pipeline<S, T> switchIf(Predicate<? super T> predicate, Pipeline<T, ?> pipeline) {
        if (this.pipes.isEmpty()) {
            return (Pipeline<S, T>) this.connectFirstPipe(new SwitchPipe<>(predicate, pipeline));
        }
        return this.connectInternalPipe(new SwitchPipe<>(predicate, pipeline));
    }

    /**
     * Method to wait for ALL PipeFutures in the list to complete
     *
     * @param futures List of PipeFutures to wait on.
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void waitForAll(List<PipeFuture<?>> futures) throws InterruptedException, ExecutionException {

        for (final PipeFuture<?> future : futures) {
            future.get();
        }
    }

    /**
     * Method to watch for a PipeFuture to complete once it does, it will return it.
     * Processing will yield after each cycle of checking futures.
     *
     * @param futures List of futures to watch.
     * @return PipeFuture that has completed.
     */
    public PipeFuture<?> waitForOne(List<PipeFuture<?>> futures) {
        while (true) {
            for (final PipeFuture<?> future : futures) {
                if (future.isDone()) {
                    return future;
                }
            }
            Thread.yield();
        }
    }

    /**
     * A pipe that will take in an object of type T and perform an action on the
     * data. This can transform the data to a new type, or update data on the pipe.
     *
     * @param <A> The type the Pipe will accept,
     * @param <B> The Type the Pipe will return.
     * @author jmiller
     */
    private class ActionPipe<A, B> implements PipeInterface<A, B> {

        /**
         * Action Functional interface
         */
        private final Function<? super A, ? extends B> action;

        /**
         * Constructor to provide the Class it will act on, and the functional interface
         *
         * @param action Functional interface to act on.
         */
        public ActionPipe(Function<? super A, ? extends B> action) {
            this.action = action;
        }

        /*
         * (non-Javadoc)
         *
         * @see
         * name.mymiller.utils.pipelines.PipeInterface#process(java.lang.
         * Object)
         */
        @Override
        public B process(final A data, List<PipeFuture<?>> futures, String pipelineName, boolean isParallel) {
            return this.action.apply(data);
        }
    }

    private class DefaultExceptionHandler implements ExceptionHandlerInterface {

        @Override
        public boolean process(Throwable throwable, PipeInterface<?, ?> pipe, Object data, List<PipeFuture<?>> futures,
                               String pipelineName, boolean isParallel) {
            System.out.println("Pipe: " + pipe.getClass().getName());
            System.out.println("Date: " + data.toString());
            System.out.println("Pipeline: " + pipelineName);
            System.out.println("Parallel: " + isParallel);
            System.out.println("Exception: " + throwable.getMessage());
            System.out.println("Stacktrace: ");
            throwable.printStackTrace(System.out);

            return false;
        }

    }

    /**
     * Pipe segment used to allow only distinct data blocks duplicate data blocks
     * from being further processed
     *
     * @param <A> Source Type
     * @author jmiller
     */
    private class DistinctByPipe<A> implements PipeInterface<A, A> {

        /**
         * List containing data blocks previously processed
         */
        private final Map<Object, Boolean> seen;
        private final Function<? super A, Object> keyExtractor;
        private boolean onDistinct = true;

        /**
         * Default contructor setting up the distinctList
         */
        public DistinctByPipe(Function<? super A, Object> keyExtractor) {
            this.seen = new ConcurrentHashMap<>();
            this.keyExtractor = keyExtractor;
        }

        /**
         * Default contructor setting up the distinctList
         */
        public DistinctByPipe(Function<? super A, Object> keyExtractor, boolean onDistinct) {
            this.seen = new ConcurrentHashMap<>();
            this.keyExtractor = keyExtractor;
            this.onDistinct = onDistinct;
        }

        @Override
        public A process(final A data, List<PipeFuture<?>> futures, String pipelineName, boolean isParallel) {
            if (this.seen.putIfAbsent(this.keyExtractor.apply(data), Boolean.TRUE) == null) {
                if (this.onDistinct) {
                    return data;
                } else {
                    return null;
                }
            }
            if (this.onDistinct) {
                return null;
            } else {
                return data;
            }
        }
    }

    /**
     * Pipe segment used to allow only distinct data blocks duplicate data blocks
     * from being further processed
     *
     * @param <A> Source Type
     * @author jmiller
     */
    private class DistinctPipe<A> implements PipeInterface<A, A> {

        /**
         * List containing data blocks previously processed
         */
        private final Set<A> distinctList;

        private boolean onDistinct = true;

        /**
         * Default contructor setting up the distinctList
         */
        public DistinctPipe() {
            this.distinctList = Collections.synchronizedSet(new HashSet<>());
        }

        /**
         * Default contructor setting up the distinctList
         */
        public DistinctPipe(boolean onDistinct) {
            this.distinctList = Collections.synchronizedSet(new HashSet<>());
            this.onDistinct = onDistinct;
        }

        @Override
        public A process(final A data, List<PipeFuture<?>> futures, String pipelineName, boolean isParallel) {
            if (!this.distinctList.contains(data)) {
                this.distinctList.add(data);
                if (this.onDistinct) {
                    return data;
                } else {
                    return null;
                }
            }
            if (this.onDistinct) {
                return null;
            } else {
                return data;
            }

        }
    }

    /**
     * Pipe segment allowing for filtering data. Allowing only certain data from
     * continuing processing.
     *
     * @param <A> Source Type
     * @author jmiller
     */
    private class FilterPipe<A> implements PipeInterface<A, A> {

        /**
         * Predicate functional interface proving the filtering algorithm.
         */
        private final Predicate<? super A> predicate;

        /**
         * Constructor to setup the FilterPipe
         *
         * @param predicate Predicate to be used for filtering data.
         */
        public FilterPipe(Predicate<? super A> predicate) {
            this.predicate = predicate;
        }

        @Override
        public A process(final A data, List<PipeFuture<?>> futures, String pipelineName, boolean isParallel) {
            if (this.predicate.test(data)) {
                return data;
            }
            return null;
        }

    }

    /**
     * Fork Pipe takes a list of Pipelines and will send the data to each pipeline.
     * If processing is occurring in parallel, this pipe will run in parallel as
     * well.
     *
     * @param <A> Source Type
     * @author jmiller
     */
    private class ForkPipe<A> implements PipeInterface<A, A> {

        /**
         * List of Pipelines data is to be forked into.
         */
        private List<Pipeline<A, ?>> pipelines = null;

        /**
         * Constructor for accepting a list of pipelines to fork data into.
         *
         * @param pipelines List of Pipelines.
         */
        public ForkPipe(List<Pipeline<A, ?>> pipelines) {
            this.pipelines = pipelines;
        }

        /*
         * (non-Javadoc)
         *
         * @see name.mymiller.utils.pipelines.PipeInterface#getFutures()
         */
        @SuppressWarnings({"rawtypes", "unchecked"})
        @Override
        public List<PipeFuture<?>> getFutures() {
            final ArrayList<PipeFuture<?>> pipeFutures = new ArrayList<>();

            for (final Pipeline pipeline : this.pipelines) {
                pipeFutures.addAll(pipeline.getFutures());
            }
            return pipeFutures;
        }

        @Override
        public A process(final A data, List<PipeFuture<?>> futures, String pipelineName, boolean isParallel) {
            for (final Pipeline<A, ?> pipeline : this.pipelines) {
                if (isParallel) {
                    pipeline.internalParallel(data, futures);
                } else {
                    pipeline.process(data);
                }
            }
            return null;
        }

    }

    /**
     * Pipe that places a Max filter on the data. Comparator is used to determine if
     * the data exceeds the max value.
     *
     * @param <A> Source Type
     * @author jmiller
     */
    private class MaxPipe<A> implements PipeInterface<A, A> {

        /**
         * Max value to allow through the pipe
         */
        private final A max;

        /**
         * Comparator to use to compare the data against the max value.
         */
        private final Comparator<? super A> comparator;

        /**
         * Constructor accepting the max value to allow and the comparator.
         *
         * @param max        Max value of type S to allow.
         * @param comparator Comparator to compare type S.
         */
        public MaxPipe(A max, Comparator<? super A> comparator) {
            this.max = max;
            this.comparator = comparator;
        }

        @Override
        public A process(final A data, List<PipeFuture<?>> futures, String pipelineName, boolean isParallel) {
            if (this.comparator.compare(data, this.max) <= 0) {
                return data;
            }
            return null;
        }

    }

    /**
     * Pipe that places a Min filter on the Data. Comaprator is used to determine if
     * the data exceeds the min value.
     *
     * @param <A> Source Type
     * @author jmiller
     */
    private class MinPipe<A> implements PipeInterface<A, A> {

        /**
         * Min value to allow thorugh the pipe.
         */
        private final A min;

        /**
         * Comparator to use to compare the data against the min value.
         */
        private final Comparator<? super A> comparator;

        /**
         * Constructor accepting the min value to allow and the comparator.
         *
         * @param min        Min value of type S to allow.
         * @param comparator Comparator to compare type S.
         */
        public MinPipe(A min, Comparator<? super A> comparator) {
            this.min = min;
            this.comparator = comparator;
        }

        @Override
        public A process(final A data, List<PipeFuture<?>> futures, String pipelineName, boolean isParallel) {
            if (this.comparator.compare(this.min, data) >= 0) {
                return data;
            }
            return null;
        }

    }

    /**
     * Pipe that allows you to see the data at this point in the processing an
     * action is called allowing the data to be passed to a Consumer. The data is
     * passed along in the pipe.
     *
     * @param <A> Source TYpe
     * @author jmiller
     */
    private class PeekPipe<A> implements PipeInterface<A, A> {

        /**
         * Consumer interface to allow 'peeking' at the data.
         */
        private Consumer<? super A> action = null;

        /**
         * Constructor for creating a peek at the data.
         *
         * @param action Consumer interface to peek at the data.
         */
        public PeekPipe(Consumer<? super A> action) {
            this.action = action;
        }

        @Override
        public A process(final A data, List<PipeFuture<?>> futures, String pipelineName, boolean isParallel) {
            this.action.accept(data);
            return data;
        }
    }

    /**
     * Runnable to perform processing in Parallel
     *
     * @author jmiller
     */
    private class PipeRun implements Runnable {
        /**
         * List of pipes at the time the processing was started that needs to be
         * executed.
         */
        private final List<PipeInterface<?, ?>> pipes;

        /**
         * Original data source
         */
        private final S source;

        /**
         * List of futures that will need to be responded to for complete processing.
         */
        private final List<PipeFuture<?>> futures;

        /**
         * Name givent to this pipeline.
         */
        private final String pipelineName;

        /**
         * Exception Handler to use for processing
         */
        private ExceptionHandlerInterface exceptionHandler = null;

        /**
         * Constructor for creating a paralell processing on the pipeline.
         *
         * @param pipes        List of pipes to rpcess
         * @param source       Original data to process
         * @param futures      List of futures that need to be completed.
         * @param pipelineName Name of this pipeline
         */
        protected PipeRun(List<PipeInterface<?, ?>> pipes, S source, List<PipeFuture<?>> futures, String pipelineName,
                          ExceptionHandlerInterface exceptionHandler) {
            this.pipes = pipes;
            this.source = source;
            this.futures = futures;
            this.pipelineName = pipelineName;
            this.exceptionHandler = exceptionHandler;
        }

        @SuppressWarnings({"unchecked", "rawtypes"})
        @Override
        public void run() {
            Object source = this.source;
            Object target = null;
            for (final PipeInterface pipe : this.pipes) {
                boolean processAgain = true;
                int processAttempt = 0;

                while (processAgain) {
                    try {
                        processAttempt++;
                        target = pipe.process(source, this.futures, this.pipelineName, true);
                        processAgain = false;
                    } catch (final Throwable throwable) {
                        if (this.exceptionHandler != null) {
                            final boolean response = this.exceptionHandler.process(throwable, pipe, source,
                                    this.futures, this.pipelineName, true);
                            if (response && (processAttempt == 1)) {
                                processAgain = true;
                            } else {
                                processAgain = false;
                                target = null;
                            }
                        }
                    }
                }
                if (target == null) {
                    break;
                }
                source = target;
            }

            for (final PipeFuture future : this.futures) {
                if (future.getIdentifier().equals(this.pipelineName)) {
                    future.setFutureObject(target);
                }
            }
        }
    }

    /**
     * Pipe Segment allowing for a copy of the data to flow to an additional
     * pipeline if the predicate is matched.
     *
     * @param <A> Source Type
     * @author jmiller
     */
    private class SwitchPipe<A> implements PipeInterface<A, A> {

        /**
         * Predicate functional interface proving the filtering algorithm.
         */
        private final Predicate<? super A> predicate;

        /**
         * Alternate Pipeline for data to flow.
         */
        private final Pipeline<A, ?> pipeline;

        /**
         * Constructor to setup the FilterPipe
         *
         * @param predicate Predicate to be used for filtering data.
         */
        public SwitchPipe(Predicate<? super A> predicate, Pipeline<A, ?> pipeline) {
            this.predicate = predicate;
            this.pipeline = pipeline;
        }

        /*
         * (non-Javadoc)
         *
         * @see name.mymiller.utils.pipelines.PipeInterface#getFutures()
         */
        @Override
        public List<PipeFuture<?>> getFutures() {
            return this.pipeline.getFutures();
        }

        @Override
        public A process(A data, List<PipeFuture<?>> futures, String pipelineName, boolean isParallel) {
            if (this.predicate.test(data)) {
                if (isParallel) {
                    this.pipeline.internalParallel(data, futures);
                } else {
                    this.pipeline.process(data);
                }
            } else if (futures != null) {
                final List<PipeFuture<?>> futureList = this.pipeline.getFutures();
                futures.parallelStream().forEach(future -> {
                    final PipeFuture<?> match = futureList.stream()
                            .filter(pipe -> pipe.getPipelineId().equals(future.getPipelineId())).findFirst()
                            .orElse(null);

                    if (match != null) {
                        future.setFutureObject(null);
                        future.setDone(true);
                    }
                });
            }
            return data;
        }

    }
}
