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

import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.PrimitiveIterator.OfInt;
import java.util.function.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AdvancedIntStream {
    private final IntStream delegate;

    /**
     * @param delegate
     */
    public AdvancedIntStream(IntStream delegate) {
        super();
        this.delegate = delegate;
    }

    /**
     * @param predicate
     * @return
     * @see IntStream#allMatch(IntPredicate)
     */
    public boolean allMatch(IntPredicate predicate) {
        return this.delegate.allMatch(predicate);
    }

    /**
     * @param predicate
     * @return
     * @see IntStream#anyMatch(IntPredicate)
     */
    public boolean anyMatch(IntPredicate predicate) {
        return this.delegate.anyMatch(predicate);
    }

    /**
     * @return
     * @see IntStream#asDoubleStream()
     */
    public AdvancedDoubleStream asDoubleStream() {
        return new AdvancedDoubleStream(this.delegate.asDoubleStream());
    }

    /**
     * @return
     * @see IntStream#asLongStream()
     */
    public AdvancedLongStream asLongStream() {
        return new AdvancedLongStream(this.delegate.asLongStream());
    }

    /**
     * @return
     * @see IntStream#average()
     */
    public OptionalDouble average() {
        return this.delegate.average();
    }

    /**
     * @return
     * @see IntStream#boxed()
     */
    public Stream<Integer> boxed() {
        return this.delegate.boxed();
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
     * @see IntStream#collect(Supplier, ObjIntConsumer, BiConsumer)
     */
    public <R> R collect(Supplier<R> supplier, ObjIntConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        return this.delegate.collect(supplier, accumulator, combiner);
    }

    /**
     * @return
     * @see IntStream#count()
     */
    public long count() {
        return this.delegate.count();
    }

    /**
     * @return
     * @see IntStream#distinct()
     */
    public AdvancedIntStream distinct() {
        return new AdvancedIntStream(this.delegate.distinct());
    }

    /**
     * @param predicate
     * @return
     * @see IntStream#filter(IntPredicate)
     */
    public AdvancedIntStream filter(IntPredicate predicate) {
        return new AdvancedIntStream(this.delegate.filter(predicate));
    }

    /**
     * @return
     * @see IntStream#findAny()
     */
    public OptionalInt findAny() {
        return this.delegate.findAny();
    }

    /**
     * @return
     * @see IntStream#findFirst()
     */
    public OptionalInt findFirst() {
        return this.delegate.findFirst();
    }

    /**
     * @param mapper
     * @return
     * @see IntStream#flatMap(IntFunction)
     */
    public AdvancedIntStream flatMap(IntFunction<? extends IntStream> mapper) {
        return new AdvancedIntStream(this.delegate.flatMap(mapper));
    }

    /**
     * @param action
     * @see IntStream#forEach(IntConsumer)
     */
    public void forEach(IntConsumer action) {
        this.delegate.forEach(action);
    }

    /**
     * @param action
     * @see IntStream#forEachOrdered(IntConsumer)
     */
    public void forEachOrdered(IntConsumer action) {
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
     * @see IntStream#iterator()
     */
    public OfInt iterator() {
        return this.delegate.iterator();
    }

    /**
     * @param maxSize
     * @return
     * @see IntStream#limit(long)
     */
    public AdvancedIntStream limit(long maxSize) {
        return new AdvancedIntStream(this.delegate.limit(maxSize));
    }

    /**
     * @param mapper
     * @return
     * @see IntStream#map(IntUnaryOperator)
     */
    public AdvancedIntStream map(IntUnaryOperator mapper) {
        return new AdvancedIntStream(this.delegate.map(mapper));
    }

    /**
     * @param mapper
     * @return
     * @see IntStream#mapToDouble(IntToDoubleFunction)
     */
    public AdvancedDoubleStream mapToDouble(IntToDoubleFunction mapper) {
        return new AdvancedDoubleStream(this.delegate.mapToDouble(mapper));
    }

    /**
     * @param mapper
     * @return
     * @see IntStream#mapToLong(IntToLongFunction)
     */
    public AdvancedLongStream mapToLong(IntToLongFunction mapper) {
        return new AdvancedLongStream(this.delegate.mapToLong(mapper));
    }

    /**
     * @param mapper
     * @return
     * @see IntStream#mapToObj(IntFunction)
     */
    public <U> Stream<U> mapToObj(IntFunction<? extends U> mapper) {
        return this.delegate.mapToObj(mapper);
    }

    /**
     * @return
     * @see IntStream#max()
     */
    public OptionalInt max() {
        return this.delegate.max();
    }

    /**
     * @return
     * @see IntStream#min()
     */
    public OptionalInt min() {
        return this.delegate.min();
    }

    /**
     * @param predicate
     * @return
     * @see IntStream#noneMatch(IntPredicate)
     */
    public boolean noneMatch(IntPredicate predicate) {
        return this.delegate.noneMatch(predicate);
    }

    /**
     * @param closeHandler
     * @return
     * @see java.util.stream.BaseStream#onClose(Runnable)
     */
    public AdvancedIntStream onClose(Runnable closeHandler) {
        return new AdvancedIntStream(this.delegate.onClose(closeHandler));
    }

    /**
     * @return
     * @see IntStream#parallel()
     */
    public AdvancedIntStream parallel() {
        return new AdvancedIntStream(this.delegate.parallel());
    }

    /**
     * @param action
     * @return
     * @see IntStream#peek(IntConsumer)
     */
    public AdvancedIntStream peek(IntConsumer action) {
        return new AdvancedIntStream(this.delegate.peek(action));
    }

    /**
     * @param identity
     * @param op
     * @return
     * @see IntStream#reduce(int, IntBinaryOperator)
     */
    public int reduce(int identity, IntBinaryOperator op) {
        return this.delegate.reduce(identity, op);
    }

    /**
     * @param op
     * @return
     * @see IntStream#reduce(IntBinaryOperator)
     */
    public OptionalInt reduce(IntBinaryOperator op) {
        return this.delegate.reduce(op);
    }

    /**
     * @return
     * @see IntStream#sequential()
     */
    public AdvancedIntStream sequential() {
        return new AdvancedIntStream(this.delegate.sequential());
    }

    /**
     * @param n
     * @return
     * @see IntStream#skip(long)
     */
    public AdvancedIntStream skip(long n) {
        return new AdvancedIntStream(this.delegate.skip(n));
    }

    /**
     * @return
     * @see IntStream#sorted()
     */
    public AdvancedIntStream sorted() {
        return new AdvancedIntStream(this.delegate.sorted());
    }

    /**
     * @return
     * @see IntStream#spliterator()
     */
    public java.util.Spliterator.OfInt spliterator() {
        return this.delegate.spliterator();
    }

    /**
     * @return
     * @see IntStream#sum()
     */
    public int sum() {
        return this.delegate.sum();
    }

    /**
     * @return
     * @see IntStream#summaryStatistics()
     */
    public IntSummaryStatistics summaryStatistics() {
        return this.delegate.summaryStatistics();
    }

    /**
     * @return
     * @see IntStream#toArray()
     */
    public int[] toArray() {
        return this.delegate.toArray();
    }

    /**
     * @return
     * @see java.util.stream.BaseStream#unordered()
     */
    public AdvancedIntStream unordered() {
        return new AdvancedIntStream(this.delegate.unordered());
    }
}
