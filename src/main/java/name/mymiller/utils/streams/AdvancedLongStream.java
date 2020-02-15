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

import java.util.LongSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.PrimitiveIterator.OfLong;
import java.util.function.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class AdvancedLongStream {
    private final LongStream delegate;

    /**
     * @param delegate
     */
    public AdvancedLongStream(LongStream delegate) {
        super();
        this.delegate = delegate;
    }

    /**
     * @param predicate
     * @return
     * @see LongStream#allMatch(LongPredicate)
     */
    public boolean allMatch(LongPredicate predicate) {
        return this.delegate.allMatch(predicate);
    }

    /**
     * @param predicate
     * @return
     * @see LongStream#anyMatch(LongPredicate)
     */
    public boolean anyMatch(LongPredicate predicate) {
        return this.delegate.anyMatch(predicate);
    }

    /**
     * @return
     * @see LongStream#asDoubleStream()
     */
    public AdvancedDoubleStream asDoubleStream() {
        return new AdvancedDoubleStream(this.delegate.asDoubleStream());
    }

    /**
     * @return
     * @see LongStream#average()
     */
    public OptionalDouble average() {
        return this.delegate.average();
    }

    /**
     * @return
     * @see LongStream#boxed()
     */
    public AdvancedStream<Long> boxed() {
        return new AdvancedStream<>(this.delegate.boxed());
    }

    /**
     * @see java.util.stream.BaseStream#close()
     */
    public void close() {
        this.delegate.close();
    }

    /**
     * @param supplier
     * @param accumulator
     * @param combiner
     * @return
     * @see LongStream#collect(Supplier, ObjLongConsumer, BiConsumer)
     */
    public <R> R collect(Supplier<R> supplier, ObjLongConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        return this.delegate.collect(supplier, accumulator, combiner);
    }

    /**
     * @return
     * @see LongStream#count()
     */
    public long count() {
        return this.delegate.count();
    }

    /**
     * @return
     * @see LongStream#distinct()
     */
    public AdvancedLongStream distinct() {
        return new AdvancedLongStream(this.delegate.distinct());
    }

    /**
     * @param predicate
     * @return
     * @see LongStream#filter(LongPredicate)
     */
    public AdvancedLongStream filter(LongPredicate predicate) {
        return new AdvancedLongStream(this.delegate.filter(predicate));
    }

    /**
     * @return
     * @see LongStream#findAny()
     */
    public OptionalLong findAny() {
        return this.delegate.findAny();
    }

    /**
     * @return
     * @see LongStream#findFirst()
     */
    public OptionalLong findFirst() {
        return this.delegate.findFirst();
    }

    /**
     * @param mapper
     * @return
     * @see LongStream#flatMap(LongFunction)
     */
    public AdvancedLongStream flatMap(LongFunction<? extends LongStream> mapper) {
        return new AdvancedLongStream(this.delegate.flatMap(mapper));
    }

    /**
     * @param action
     * @see LongStream#forEach(LongConsumer)
     */
    public void forEach(LongConsumer action) {
        this.delegate.forEach(action);
    }

    /**
     * @param action
     * @see LongStream#forEachOrdered(LongConsumer)
     */
    public void forEachOrdered(LongConsumer action) {
        this.delegate.forEachOrdered(action);
    }

    /**
     * @return
     * @see java.util.stream.BaseStream#isParallel()
     */
    public boolean isParallel() {
        return this.delegate.isParallel();
    }

    /**
     * @return
     * @see LongStream#iterator()
     */
    public OfLong iterator() {
        return this.delegate.iterator();
    }

    /**
     * @param maxSize
     * @return
     * @see LongStream#limit(long)
     */
    public AdvancedLongStream limit(long maxSize) {
        return new AdvancedLongStream(this.delegate.limit(maxSize));
    }

    /**
     * @param mapper
     * @return
     * @see LongStream#map(LongUnaryOperator)
     */
    public AdvancedLongStream map(LongUnaryOperator mapper) {
        return new AdvancedLongStream(this.delegate.map(mapper));
    }

    /**
     * @param mapper
     * @return
     * @see LongStream#mapToDouble(LongToDoubleFunction)
     */
    public AdvancedDoubleStream mapToDouble(LongToDoubleFunction mapper) {
        return new AdvancedDoubleStream(this.delegate.mapToDouble(mapper));
    }

    /**
     * @param mapper
     * @return
     * @see LongStream#mapToInt(LongToIntFunction)
     */
    public IntStream mapToInt(LongToIntFunction mapper) {
        return this.delegate.mapToInt(mapper);
    }

    /**
     * @param mapper
     * @return
     * @see LongStream#mapToObj(LongFunction)
     */
    public <U> Stream<U> mapToObj(LongFunction<? extends U> mapper) {
        return this.delegate.mapToObj(mapper);
    }

    /**
     * @return
     * @see LongStream#max()
     */
    public OptionalLong max() {
        return this.delegate.max();
    }

    /**
     * @return
     * @see LongStream#min()
     */
    public OptionalLong min() {
        return this.delegate.min();
    }

    /**
     * @param predicate
     * @return
     * @see LongStream#noneMatch(LongPredicate)
     */
    public boolean noneMatch(LongPredicate predicate) {
        return this.delegate.noneMatch(predicate);
    }

    /**
     * @param closeHandler
     * @return
     * @see java.util.stream.BaseStream#onClose(Runnable)
     */
    public AdvancedLongStream onClose(Runnable closeHandler) {
        return new AdvancedLongStream(this.delegate.onClose(closeHandler));
    }

    /**
     * @return
     * @see LongStream#parallel()
     */
    public AdvancedLongStream parallel() {
        return new AdvancedLongStream(this.delegate.parallel());
    }

    /**
     * @param action
     * @return
     * @see LongStream#peek(LongConsumer)
     */
    public AdvancedLongStream peek(LongConsumer action) {
        return new AdvancedLongStream(this.delegate.peek(action));
    }

    /**
     * @param identity
     * @param op
     * @return
     * @see LongStream#reduce(long, LongBinaryOperator)
     */
    public long reduce(long identity, LongBinaryOperator op) {
        return this.delegate.reduce(identity, op);
    }

    /**
     * @param op
     * @return
     * @see LongStream#reduce(LongBinaryOperator)
     */
    public OptionalLong reduce(LongBinaryOperator op) {
        return this.delegate.reduce(op);
    }

    /**
     * @return
     * @see LongStream#sequential()
     */
    public AdvancedLongStream sequential() {
        return new AdvancedLongStream(this.delegate.sequential());
    }

    /**
     * @param n
     * @return
     * @see LongStream#skip(long)
     */
    public AdvancedLongStream skip(long n) {
        return new AdvancedLongStream(this.delegate.skip(n));
    }

    /**
     * @return
     * @see LongStream#sorted()
     */
    public AdvancedLongStream sorted() {
        return new AdvancedLongStream(this.delegate.sorted());
    }

    /**
     * @return
     * @see LongStream#spliterator()
     */
    public java.util.Spliterator.OfLong spliterator() {
        return this.delegate.spliterator();
    }

    /**
     * @return
     * @see LongStream#sum()
     */
    public long sum() {
        return this.delegate.sum();
    }

    /**
     * @return
     * @see LongStream#summaryStatistics()
     */
    public LongSummaryStatistics summaryStatistics() {
        return this.delegate.summaryStatistics();
    }

    /**
     * @return
     * @see LongStream#toArray()
     */
    public long[] toArray() {
        return this.delegate.toArray();
    }

    /**
     * @return
     * @see java.util.stream.BaseStream#unordered()
     */
    public AdvancedLongStream unordered() {
        return new AdvancedLongStream(this.delegate.unordered());
    }
}
