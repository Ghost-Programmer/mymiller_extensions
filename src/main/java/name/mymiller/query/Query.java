package name.mymiller.query;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;

/**
 *
 */
public class Query {

    public static class QueryFilterStream<T> implements Predicate<T> {

        private QueryFilter<T> filter;

        public QueryFilterStream(QueryFilter<T> filter) {
            this.filter = filter;
        }

        /**
         * Evaluates this predicate on the given argument.
         *
         * @param t the input argument
         * @return {@code true} if the input argument matches the predicate,
         * otherwise {@code false}
         */
        @Override
        public boolean test(T t) {
            if(filter.process(t) > 0D) {
                return true;
            }

            return false;
        }
    }

    /**
     * Given a QueryFilter to use, returns a Predicate suited for a Java Stream, or Pipeline filter() call.
     * @param filter QueryFilter structure defining the query to perform
     * @param <T> Type of data that will be passed in.
     * @return Predicate suitable for a filter() call.
     */
    public static <T> Predicate<T> filter(QueryFilter<T> filter) {
        return new QueryFilterStream<>(filter);
    }

    public static <T> And<T> and(QueryFilter<T> ... filters) {
        return new And<>(filters);
    }

    public static <T> And<T> and(List<QueryFilter<T>> list) {
        return new And<>(list);
    }

    public static <T> And<T> and() {
        return new And<>();
    }

    public static <T,R> Between<T,R> between(T low, T max) {
        return new Between<>(low,max);
    }

    public static <T,R> Between<T,R> btween(Function<T, R> getter, T low, T max) {
        return new Between<>(getter,low,max);
    }

    public static <T,R> BetweenOrEqual<T,R> betweenOrEqual(T low, T max) {
        return new BetweenOrEqual<>(low,max);
    }

    public static <T,R> BetweenOrEqual<T,R> betweenOrEqual(Function<T, R> getter, T low, T max) {
        return new BetweenOrEqual<>(getter,low,max);
    }

    public static <T> Contains<T> contains(String value) {
        return new Contains<T>(value);
    }
    public static <T> Contains<T> contains(String value, Function<T, String> getter) {
        return new Contains<T>(value, getter);
    }
    public static <T> Contains<T> contains(String value, Double weigth, Integer multiplier) {
        return new Contains<T>(value, weigth, multiplier);
    }
    public static <T> Contains<T> contains(String value, Function<T, String> getter, Double weight, Integer multiplier) {
        return new Contains<T>(value, getter, weight, multiplier);
    }

    public static <T,R> Match<T,R> match(T value){
        return new Match<>(value);
    }
    public static <T,R> Match<T,R> match(Function<T, R> getter, T value){
        return new Match<>(getter, value);
    }
    public static <T,R> Match<T,R> match(T value, Double weight){
        return new Match<>(value, weight);
    }
    public static <T,R> Match<T,R> match(Function<T, R> getter, T value, Double weight){
        return new Match<>(getter, value, weight);
    }

    public static <T,R> GreaterThan<T,R> greaterThan(T value){
        return new GreaterThan<>(value);
    }
    public static <T,R> GreaterThan<T,R> greaterThan(T value, Double weight){
        return new GreaterThan<>(value, weight);
    }
    public static <T,R> GreaterThan<T,R> greaterThan(Function<T, R> getter, T value){
        return new GreaterThan<>(getter, value);
    }
    public static <T,R> GreaterThan<T,R> greaterThan(Function<T, R> getter, T value, Double weight){
        return new GreaterThan<>(getter, value, weight);
    }

    public static <T,R> IsEmpty<T,R> isEmpty(){
        return new IsEmpty<>();
    }
    public static <T,R> IsEmpty<T,R> isEmpty(Double weight){
        return new IsEmpty<>(weight);
    }
    public static <T,R> IsEmpty<T,R> isEmpty(Function<T, R> getter){
        return new IsEmpty<>(getter);
    }
    public static <T,R> IsEmpty<T,R> isEmpty(Function<T, R> getter,Double weight){
        return new IsEmpty<>(getter,weight);
    }

    public static <T,R> IsNull<T,R> isNull(){
        return new IsNull<>();
    }
    public static <T,R> IsNull<T,R> isNull(Double weight){
        return new IsNull<>(weight);
    }
    public static <T,R> IsNull<T,R> isNull(Function<T, R> getter){
        return new IsNull<>(getter);
    }
    public static <T,R> IsNull<T,R> isNull(Function<T, R> getter,Double weight){
        return new IsNull<>(getter,weight);
    }

    public static <T,R> LessThan<T,R> lessThan(T value){
        return new LessThan<>(value);
    }
    public static <T,R> LessThan<T,R> lessThan(T value, Double weight){
        return new LessThan<>(value, weight);
    }
    public static <T,R> LessThan<T,R> lessThan(Function<T, R> getter,T value){
        return new LessThan<>(getter, value);
    }
    public static <T,R> LessThan<T,R> lessThan(Function<T, R> getter,T value, Double weight){
        return new LessThan<>(getter, value, weight);
    }

    public static <T> Not<T> not(QueryFilter<T> filter){
        return new Not<>(filter);
    }
    public static <T> Not<T> not(QueryFilter<T> filter, Double weight){
        return new Not<>(filter, weight);
    }

    public static <T,R> NotEmpty<T,R> notEmpty(){
        return new NotEmpty<>();
    }
    public static <T,R> NotEmpty<T,R> notEmpty(Double weight){
        return new NotEmpty<>(weight);
    }
    public static <T,R> NotEmpty<T,R> notEmpty(Function<T, R> getter){
        return new NotEmpty<>(getter);
    }
    public static <T,R> NotEmpty<T,R> notEmpty(Function<T, R> getter, Double weight){
        return new NotEmpty<>(getter,weight);
    }

    public static <T,R> NotNull<T,R> notNull() {
        return new NotNull<>();
    }
    public static <T,R> NotNull<T,R> notNull(Double weight) {
        return new NotNull<>(weight);
    }
    public static <T,R> NotNull<T,R> notNull(Function<T, R> getter) {
        return new NotNull<>(getter);
    }
    public static <T,R> NotNull<T,R> notNull(Function<T, R> getter, Double weight) {
        return new NotNull<>(getter,weight);
    }

    public static <T> Or<T> or(List<QueryFilter<T>> list){
        return new Or<>(list);
    }
    public static <T> Or<T> or(QueryFilter<T> ... filters){
        return new Or<>(filters);
    }
    public static <T> Or<T> or(){
        return new Or<>();
    }

    public static <T> Xor<T> xor(){
        return new Xor<>();
    }
    public static <T> Xor<T> xor(List<QueryFilter<T>> list){
        return new Xor<>(list);
    }
    public static <T> Xor<T> xor(QueryFilter<T> ... filters){
        return new Xor<>(filters);
    }
}
