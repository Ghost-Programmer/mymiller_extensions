/*******************************************************************************
 * Copyright 2018 MyMiller Consulting LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package name.mymiller.utils.streams;

import java.util.*;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.*;
import java.util.stream.*;

public class AdvancedStream<T> {
    private Stream<T> delegate;
    private SubmissionPublisher<T> publisher;

    /**
     * @param delegate
     */
    public AdvancedStream(final Stream<T> delegate) {
        super();
        this.delegate = delegate;
    }

    public AdvancedStream(T[] array) {
        super();
        this.delegate = Arrays.stream(array);
    }

    static <T> AdvancedStream<T> concat(AdvancedStream<? extends T> a, AdvancedStream<? extends T> b) {
        return new AdvancedStream<>(Stream.concat(a.getDelegate(), b.getDelegate()));
    }

    static <T> AdvancedStream<T> concat(Stream<? extends T> a, Stream<? extends T> b) {
        return new AdvancedStream<>(Stream.concat(a, b));
    }

    static <T> AdvancedStream<T> empty() {
        return new AdvancedStream<>(Stream.empty());
    }

    static <T> AdvancedStream<T> generate(Supplier<T> s) {
        return new AdvancedStream<>(Stream.generate(s));
    }

    static <T> AdvancedStream<T> iterate(T seed, UnaryOperator<T> f) {
        return new AdvancedStream<>(Stream.iterate(seed, f));
    }

    @SafeVarargs
    static <T> AdvancedStream<T> of(T... values) {
        return new AdvancedStream<>(Stream.of(values));
    }

    static <T> AdvancedStream<T> of(T t) {
        return new AdvancedStream<>(Stream.of(t));
    }

    /**
     * @param predicate
     * @return
     * @see Stream#allMatch(Predicate)
     */
    public boolean allMatch(final Predicate<? super T> predicate) {
        return this.getDelegate().allMatch(predicate);
    }

    /**
     * @param predicate
     * @return
     * @see Stream#anyMatch(Predicate)
     */
    public boolean anyMatch(final Predicate<? super T> predicate) {
        return this.getDelegate().anyMatch(predicate);
    }

    /**
     * @see java.util.stream.BaseStream#close()
     */
    public void close() {
        this.getDelegate().close();
    }

    public AdvancedStream<T> closeSubscriptions() {
        if (this.getPublisher() != null) {
            this.getPublisher().close();
            this.setPublisher(null);
        }

        return this;
    }

    /**
     * @param collector
     * @return
     * @see Stream#collect(Collector)
     */
    public <R, A> R collect(final Collector<? super T, A, R> collector) {
        return this.getDelegate().collect(collector);
    }

    /**
     * @param supplier
     * @param accumulator
     * @param combiner
     * @return
     * @see Stream#collect(Supplier, BiConsumer, BiConsumer)
     */
    public <R> R collect(final Supplier<R> supplier, final BiConsumer<R, ? super T> accumulator,
                         final BiConsumer<R, R> combiner) {
        return this.getDelegate().collect(supplier, accumulator, combiner);
    }

    public AdvancedStream<T> connect(AdvancedStream<T> add) {
        this.setDelegate(Stream.concat(this.getDelegate(), add.getDelegate()));

        return this;
    }

    /**
     * @return
     * @see Stream#count()
     */
    public long count() {
        return this.getDelegate().count();
    }

    /**
     * @return
     * @see Stream#distinct()
     */
    public AdvancedStream<T> distinct() {
        return new AdvancedStream<>(this.getDelegate().distinct());
    }

    /**
     * @param predicate
     * @return
     * @see java.util.stream.Stream#dropWhile(java.util.function.Predicate)
     */
    public AdvancedStream<T> dropWhile(Predicate<? super T> predicate) {
        return new AdvancedStream<>(this.delegate.dropWhile(predicate));
    }

    public AdvancedStream<T> duplicate(Stream.Builder<T> builder) {

        this.getDelegate().forEach(element -> builder.add(element));

        return this;
    }

    /**
     * @param predicate
     * @return
     * @see Stream#filter(Predicate)
     */
    public AdvancedStream<T> filter(final Predicate<? super T> predicate) {
        return new AdvancedStream<>(this.getDelegate().filter(predicate));
    }

    /**
     * @return
     * @see Stream#findAny()
     */
    public Optional<T> findAny() {
        return this.getDelegate().findAny();
    }

    /**
     * @return
     * @see Stream#findFirst()
     */
    public Optional<T> findFirst() {
        return this.getDelegate().findFirst();
    }

    /**
     * @param mapper
     * @return
     * @see Stream#flatMap(Function)
     */
    public <R> AdvancedStream<R> flatMap(final Function<? super T, ? extends Stream<? extends R>> mapper) {
        return new AdvancedStream<>(this.getDelegate().flatMap(mapper));
    }

    /**
     * @param mapper
     * @return
     * @see Stream#flatMapToDouble(Function)
     */
    public AdvancedDoubleStream flatMapToDouble(final Function<? super T, ? extends DoubleStream> mapper) {
        return new AdvancedDoubleStream(this.getDelegate().flatMapToDouble(mapper));
    }

    /**
     * @param mapper
     * @return
     * @see Stream#flatMapToInt(Function)
     */
    public AdvancedIntStream flatMapToInt(final Function<? super T, ? extends IntStream> mapper) {
        return new AdvancedIntStream(this.getDelegate().flatMapToInt(mapper));
    }

    /**
     * @param mapper
     * @return
     * @see Stream#flatMapToLong(Function)
     */
    public AdvancedLongStream flatMapToLong(final Function<? super T, ? extends LongStream> mapper) {
        return new AdvancedLongStream(this.getDelegate().flatMapToLong(mapper));
    }

    /**
     * @param action
     * @see Stream#forEach(Consumer)
     */
    public void forEach(final Consumer<? super T> action) {
        this.getDelegate().forEach(action);
    }

    /**
     * @param action
     * @see Stream#forEachOrdered(Consumer)
     */
    public void forEachOrdered(final Consumer<? super T> action) {
        this.getDelegate().forEachOrdered(action);
    }

    /**
     * @return the delegate
     */
    protected synchronized Stream<T> getDelegate() {
        return this.getDelegate();
    }

    /**
     * @param getDelegate() the delegate to set
     */
    protected synchronized void setDelegate(Stream<T> delegate) {
        this.delegate = delegate;
    }

    /**
     * @return the publisher
     */
    protected synchronized SubmissionPublisher<T> getPublisher() {
        return this.publisher;
    }

    /**
     * @param publisher the publisher to set
     */
    protected synchronized void setPublisher(SubmissionPublisher<T> publisher) {
        this.publisher = publisher;
    }

    /**
     * @return
     * @see java.util.stream.BaseStream#isParallel()
     */
    public boolean isParallel() {
        return this.getDelegate().isParallel();
    }

    /**
     * @return
     * @see java.util.stream.BaseStream#iterator()
     */
    public Iterator<T> iterator() {
        return this.getDelegate().iterator();
    }

    /**
     * @param maxSize
     * @return
     * @see Stream#limit(long)
     */
    public AdvancedStream<T> limit(final long maxSize) {
        return new AdvancedStream<>(this.getDelegate().limit(maxSize));
    }

    /**
     * @param mapper
     * @return
     * @see Stream#map(Function)
     */
    public <R> AdvancedStream<R> map(final Function<? super T, ? extends R> mapper) {
        return new AdvancedStream<>(this.getDelegate().map(mapper));
    }

    /**
     * @param mapper
     * @return
     * @see Stream#mapToDouble(ToDoubleFunction)
     */
    public AdvancedDoubleStream mapToDouble(final ToDoubleFunction<? super T> mapper) {
        return new AdvancedDoubleStream(this.getDelegate().mapToDouble(mapper));
    }

    /**
     * @param mapper
     * @return
     * @see Stream#mapToInt(ToIntFunction)
     */
    public AdvancedIntStream mapToInt(final ToIntFunction<? super T> mapper) {
        return new AdvancedIntStream(this.getDelegate().mapToInt(mapper));
    }

    /**
     * @param mapper
     * @return
     * @see Stream#mapToLong(ToLongFunction)
     */
    public AdvancedLongStream mapToLong(final ToLongFunction<? super T> mapper) {
        return new AdvancedLongStream(this.getDelegate().mapToLong(mapper));
    }

    /**
     * @param comparator
     * @return
     * @see Stream#max(Comparator)
     */
    public Optional<T> max(final Comparator<? super T> comparator) {
        return this.getDelegate().max(comparator);
    }

    public AdvancedStream<T> maxBy(T max, Comparator<? super T> comparator) {
        return new AdvancedStream<>(this.getDelegate().filter(element -> {
            return comparator.compare(element, max) < 1;
        }));
    }

    /**
     * @param comparator
     * @return
     * @see Stream#min(Comparator)
     */
    public Optional<T> min(final Comparator<? super T> comparator) {
        return this.getDelegate().min(comparator);
    }

    public AdvancedStream<T> minBy(T min, Comparator<? super T> comparator) {
        return new AdvancedStream<>(this.getDelegate().filter(element -> {
            return comparator.compare(min, element) > -1;
        }));
    }

    /**
     * @param predicate
     * @return
     * @see Stream#noneMatch(Predicate)
     */
    public boolean noneMatch(final Predicate<? super T> predicate) {
        return this.getDelegate().noneMatch(predicate);
    }

    /**
     * @param closeHandler
     * @return
     * @see java.util.stream.BaseStream#onClose(Runnable)
     */
    public AdvancedStream<T> onClose(final Runnable closeHandler) {
        return new AdvancedStream<>(this.getDelegate().onClose(closeHandler));
    }

    /**
     * @return
     * @see java.util.stream.BaseStream#parallel()
     */
    public AdvancedStream<T> parallel() {
        return new AdvancedStream<>(this.getDelegate().parallel());
    }

    /**
     * @param action
     * @return
     * @see Stream#peek(Consumer)
     */
    public AdvancedStream<T> peek(final Consumer<? super T> action) {
        return new AdvancedStream<>(this.getDelegate().peek(action));
    }

    /**
     * @param accumulator
     * @return
     * @see Stream#reduce(BinaryOperator)
     */
    public Optional<T> reduce(final BinaryOperator<T> accumulator) {
        return this.getDelegate().reduce(accumulator);
    }

    /**
     * @param identity
     * @param accumulator
     * @return
     * @see Stream#reduce(Object, BinaryOperator)
     */
    public T reduce(final T identity, final BinaryOperator<T> accumulator) {
        return this.getDelegate().reduce(identity, accumulator);
    }

    /**
     * @param identity
     * @param accumulator
     * @param combiner
     * @return
     * @see Stream#reduce(Object, BiFunction, BinaryOperator)
     */
    public <U> U reduce(final U identity, final BiFunction<U, ? super T, U> accumulator,
                        final BinaryOperator<U> combiner) {
        return this.getDelegate().reduce(identity, accumulator, combiner);
    }

    /**
     * @return
     * @see java.util.stream.BaseStream#sequential()
     */
    public AdvancedStream<T> sequential() {
        return new AdvancedStream<>(this.getDelegate().sequential());
    }

    /**
     * @param n
     * @return
     * @see Stream#skip(long)
     */
    public AdvancedStream<T> skip(final long n) {
        return new AdvancedStream<>(this.getDelegate().skip(n));
    }

    /**
     * @return
     * @see Stream#sorted()
     */
    public AdvancedStream<T> sorted() {
        return new AdvancedStream<>(this.getDelegate().sorted());
    }

    /**
     * @param comparator
     * @return
     * @see Stream#sorted(Comparator)
     */
    public AdvancedStream<T> sorted(final Comparator<? super T> comparator) {
        return new AdvancedStream<>(this.getDelegate().sorted(comparator));
    }

    /**
     * @return
     * @see java.util.stream.BaseStream#spliterator()
     */
    public Spliterator<T> spliterator() {
        return this.getDelegate().spliterator();
    }

    public AdvancedStream<T> submit() {
        if (this.getPublisher() != null) {
            this.getDelegate().forEach(element -> this.getPublisher().submit(element));
        }

        return this;
    }

    @SafeVarargs
    public final AdvancedStream<T> subscribe(Subscriber<T>... subscribers) {
        if (this.getPublisher() == null) {
            this.setPublisher(new SubmissionPublisher<>());
        }

        for (final Subscriber<T> subscriber : subscribers) {
            this.getPublisher().subscribe(subscriber);
        }

        return this;
    }

    /**
     * @param predicate
     * @return
     * @see java.util.stream.Stream#takeWhile(java.util.function.Predicate)
     */
    public AdvancedStream<T> takeWhile(Predicate<? super T> predicate) {
        return new AdvancedStream<>(this.delegate.takeWhile(predicate));
    }

    /**
     * @return
     * @see Stream#toArray()
     */
    public Object[] toArray() {
        return this.getDelegate().toArray();
    }

    /**
     * @param generator
     * @return
     * @see Stream#toArray(IntFunction)
     */
    public <A> A[] toArray(final IntFunction<A[]> generator) {
        return this.getDelegate().toArray(generator);
    }

    /**
     * @return
     * @see java.util.stream.BaseStream#unordered()
     */
    public AdvancedStream<T> unordered() {
        return new AdvancedStream<>(this.getDelegate().unordered());
    }
}
