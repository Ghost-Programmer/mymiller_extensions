/******************************************************************************
 Copyright 2018 MyMiller Consulting LLC.

 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License.  You may obtain a copy
 of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 License for the specific language governing permissions and limitations under
 the License.
 */
package name.mymiller.utils.streams;

import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;
import java.util.PrimitiveIterator.OfDouble;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class AdvancedDoubleStream {

    private final DoubleStream delegate;

    /**
     * @param delegate
     */
    public AdvancedDoubleStream(DoubleStream delegate) {
        super();
        this.delegate = delegate;
    }

    /**
     * @param predicate
     * @return
     * @see DoubleStream#allMatch(DoublePredicate)
     */
    public boolean allMatch(DoublePredicate predicate) {
        return this.delegate.allMatch(predicate);
    }

    /**
     * @param predicate
     * @return
     * @see DoubleStream#anyMatch(DoublePredicate)
     */
    public boolean anyMatch(DoublePredicate predicate) {
        return this.delegate.anyMatch(predicate);
    }

    /**
     * @return
     * @see DoubleStream#average()
     */
    public OptionalDouble average() {
        return this.delegate.average();
    }

    /**
     * @return
     * @see DoubleStream#boxed()
     */
    public AdvancedStream<Double> boxed() {
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
     * @see DoubleStream#collect(Supplier, ObjDoubleConsumer, BiConsumer)
     */
    public <R> R collect(Supplier<R> supplier, ObjDoubleConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        return this.delegate.collect(supplier, accumulator, combiner);
    }

    /**
     * @return
     * @see DoubleStream#count()
     */
    public long count() {
        return this.delegate.count();
    }

    /**
     * @return
     * @see DoubleStream#distinct()
     */
    public AdvancedDoubleStream distinct() {
        return new AdvancedDoubleStream(this.delegate.distinct());
    }

    /**
     * @param predicate
     * @return
     * @see DoubleStream#filter(DoublePredicate)
     */
    public AdvancedDoubleStream filter(DoublePredicate predicate) {
        return new AdvancedDoubleStream(this.delegate.filter(predicate));
    }

    /**
     * @return
     * @see DoubleStream#findAny()
     */
    public OptionalDouble findAny() {
        return this.delegate.findAny();
    }

    /**
     * @return
     * @see DoubleStream#findFirst()
     */
    public OptionalDouble findFirst() {
        return this.delegate.findFirst();
    }

    /**
     * @param mapper
     * @return
     * @see DoubleStream#flatMap(DoubleFunction)
     */
    public AdvancedDoubleStream flatMap(DoubleFunction<? extends DoubleStream> mapper) {
        return new AdvancedDoubleStream(this.delegate.flatMap(mapper));
    }

    /**
     * @param action
     * @see DoubleStream#forEach(DoubleConsumer)
     */
    public void forEach(DoubleConsumer action) {
        this.delegate.forEach(action);
    }

    /**
     * @param action
     * @see DoubleStream#forEachOrdered(DoubleConsumer)
     */
    public void forEachOrdered(DoubleConsumer action) {
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
     * @see DoubleStream#iterator()
     */
    public OfDouble iterator() {
        return this.delegate.iterator();
    }

    /**
     * @param maxSize
     * @return
     * @see DoubleStream#limit(long)
     */
    public AdvancedDoubleStream limit(long maxSize) {
        return new AdvancedDoubleStream(this.delegate.limit(maxSize));
    }

    /**
     * @param mapper
     * @return
     * @see DoubleStream#map(DoubleUnaryOperator)
     */
    public AdvancedDoubleStream map(DoubleUnaryOperator mapper) {
        return new AdvancedDoubleStream(this.delegate.map(mapper));
    }

    /**
     * @param mapper
     * @return
     * @see DoubleStream#mapToInt(DoubleToIntFunction)
     */
    public IntStream mapToInt(DoubleToIntFunction mapper) {
        return this.delegate.mapToInt(mapper);
    }

    /**
     * @param mapper
     * @return
     * @see DoubleStream#mapToLong(DoubleToLongFunction)
     */
    public AdvancedLongStream mapToLong(DoubleToLongFunction mapper) {
        return new AdvancedLongStream(this.delegate.mapToLong(mapper));
    }

    /**
     * @param mapper
     * @return
     * @see DoubleStream#mapToObj(DoubleFunction)
     */
    public <U> AdvancedStream<U> mapToObj(DoubleFunction<? extends U> mapper) {
        return new AdvancedStream<>(this.delegate.mapToObj(mapper));
    }

    /**
     * @return
     * @see DoubleStream#max()
     */
    public OptionalDouble max() {
        return this.delegate.max();
    }

    /**
     * @return
     * @see DoubleStream#min()
     */
    public OptionalDouble min() {
        return this.delegate.min();
    }

    /**
     * @param predicate
     * @return
     * @see DoubleStream#noneMatch(DoublePredicate)
     */
    public boolean noneMatch(DoublePredicate predicate) {
        return this.delegate.noneMatch(predicate);
    }

    /**
     * @param closeHandler
     * @return
     * @see java.util.stream.BaseStream#onClose(Runnable)
     */
    public AdvancedDoubleStream onClose(Runnable closeHandler) {
        return new AdvancedDoubleStream(this.delegate.onClose(closeHandler));
    }

    /**
     * @return
     * @see DoubleStream#parallel()
     */
    public AdvancedDoubleStream parallel() {
        return new AdvancedDoubleStream(this.delegate.parallel());
    }

    /**
     * @param action
     * @return
     * @see DoubleStream#peek(DoubleConsumer)
     */
    public AdvancedDoubleStream peek(DoubleConsumer action) {
        return new AdvancedDoubleStream(this.delegate.peek(action));
    }

    /**
     * @param identity
     * @param op
     * @return
     * @see DoubleStream#reduce(double, DoubleBinaryOperator)
     */
    public double reduce(double identity, DoubleBinaryOperator op) {
        return this.delegate.reduce(identity, op);
    }

    /**
     * @param op
     * @return
     * @see DoubleStream#reduce(DoubleBinaryOperator)
     */
    public OptionalDouble reduce(DoubleBinaryOperator op) {
        return this.delegate.reduce(op);
    }

    /**
     * @return
     * @see DoubleStream#sequential()
     */
    public AdvancedDoubleStream sequential() {
        return new AdvancedDoubleStream(this.delegate.sequential());
    }

    /**
     * @param n
     * @return
     * @see DoubleStream#skip(long)
     */
    public AdvancedDoubleStream skip(long n) {
        return new AdvancedDoubleStream(this.delegate.skip(n));
    }

    /**
     * @return
     * @see DoubleStream#sorted()
     */
    public AdvancedDoubleStream sorted() {
        return new AdvancedDoubleStream(this.delegate.sorted());
    }

    /**
     * @return
     * @see DoubleStream#spliterator()
     */
    public java.util.Spliterator.OfDouble spliterator() {
        return this.delegate.spliterator();
    }

    /**
     * @return
     * @see DoubleStream#sum()
     */
    public double sum() {
        return this.delegate.sum();
    }

    /**
     * @return
     * @see DoubleStream#summaryStatistics()
     */
    public DoubleSummaryStatistics summaryStatistics() {
        return this.delegate.summaryStatistics();
    }

    /**
     * @return
     * @see DoubleStream#toArray()
     */
    public double[] toArray() {
        return this.delegate.toArray();
    }

    /**
     * @return
     * @see java.util.stream.BaseStream#unordered()
     */
    public AdvancedDoubleStream unordered() {
        return new AdvancedDoubleStream(this.delegate.unordered());
    }

}
