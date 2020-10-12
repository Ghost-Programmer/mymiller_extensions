package name.mymiller.query;

import name.mymiller.utils.ObjectUtils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Query builder for creating queries to be used in Stream filters, or other uses. Calculates a weight on each passed item.
 */
public class Query {

    /**
     * Given a QueryFilter to use, returns a Predicate suited for a Java Stream, or Pipeline filter() call.
     *
     * @param filter QueryFilter structure defining the query to perform
     * @param <T>    Type of data that will be passed in.
     * @return Predicate suitable for a filter() call.
     */
    public static <T> Predicate<T> filter(QueryFilter<T> filter) {
        return new QueryFilterStream<>(filter);
    }

    /**
     * Returns a comparator that will act be based on the how well the objects match the query.
     * @param filter Query to use to compare the objects
     * @param <T> Type of data that will be passed in.
     * @return Comparator suitable for a sort() or sorted() call.
     */
    public static <T> Comparator<T> comparator(QueryFilter<T> filter) { return new QueryComparator<>(filter);}

    /**
     * Wraps a number of queries in an And filter.  All queries must return a weight > 0 in order of this to pass.
     * @param filters Array of filters to be wrapped in the And.
     * @param <T> Type of object to filter
     * @return And QueryFilter with the filters added to it
     */
    public static <T> And<T> and(QueryFilter<T>... filters) {
        return new And<>(filters);
    }

    /**
     * Wraps a number of queries in an And filter.  All queries must return a weight > 0 in order of this to pass.
     * @param list List of QueryFilters for this And.
     * @param <T> Type of object to filter
     * @return And QueryFilter with the list of filters added to it
     */
    public static <T> And<T> and(List<QueryFilter<T>> list) {
        return new And<>(list);
    }

    /**
     * Wraps a number of queries in an And filter.  All queries must return a weight > 0 in order of this to pass.
     * @param <T> Type of object to filter
     * @return And QueryFilter ready to add queries to.
     */
    public static <T> And<T> and() {
        return new And<>();
    }

    /**
     * Creates a QueryFilter that will pass the filter if the object is between low and max.
     * @param low the low value to compare on.
     * @param max the hight value to compare on.
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return Between QueryFilter to check if the value is between low/max
     */
    public static <T, R> Between<T, R> between(T low, T max) {
        return new Between<>(low, max);
    }

    /**
     * Creates a QueryFilter that will pass the filter if the object is between low and max.
     * @param getter the function used to extract the Comparable sort key
     * @param low the low value to compare on.
     * @param max the hight value to compare on.
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return Between QueryFilter to check if the value is between low/max
     */
    public static <T, R> Between<T, R> between(Function<T, R> getter, T low, T max) {
        return new Between<>(getter, low, max);
    }

    /**
     *
     * @param low the low value to compare on.
     * @param max the hight value to compare on.
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return BetweenOrEuals QueryFilter to check if the value is between low/max or equal
     */
    public static <T, R> BetweenOrEqual<T, R> betweenOrEqual(T low, T max) {
        return new BetweenOrEqual<>(low, max);
    }

    /**
     *
     * @param getter the function used to extract the Comparable sort key
     * @param low the low value to compare on.
     * @param max the hight value to compare on.
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return BetweenOrEuals QueryFilter to check if the value is between low/max or equal
     */
    public static <T, R> BetweenOrEqual<T, R> betweenOrEqual(Function<T, R> getter, T low, T max) {
        return new BetweenOrEqual<>(getter, low, max);
    }

    /**
     *
     * @param value  the value to compe on the value to compare on.
     * @param <T> Type of object to filter
     * @return Contains QueryFilter to check if the object contains the value.
     */
    public static <T> Contains<T> contains(String value) {
        return new Contains<T>(value);
    }

    /**
     *
     * @param value  the value to compe on
     * @param getter the function used to extract the Comparable sort key
     * @param <T> Type of object to filter
     * @return Contains QueryFilter to check if the object contains the value.
     */
    public static <T> Contains<T> contains(String value, Function<T, String> getter) {
        return new Contains<T>(value, getter);
    }

    /**
     *
     * @param value  the value to compe on
     * @param weigth the weight this should return if QueryFilter return matches.
     * @param multiplier In the case of an exact match, this is applied to the weight
     * @param <T> Type of object to filter
     * @return Contains QueryFilter to check if the object contains the value.
     */
    public static <T> Contains<T> contains(String value, Double weigth, Integer multiplier) {
        return new Contains<T>(value, weigth, multiplier);
    }

    /**
     *
     * @param value  the value to compe on
     * @param getter the function used to extract the Comparable sort key
     * @param weight the weight this should return if QueryFilter return matches.
     * @param multiplier In the case of an exact match, this is applied to the weight
     * @param <T> Type of object to filter
     * @return Contains QueryFilter to check if the object contains the value.
     */
    public static <T> Contains<T> contains(String value, Function<T, String> getter, Double weight, Integer multiplier) {
        return new Contains<T>(value, getter, weight, multiplier);
    }

    /**
     *
     * @param value  the value to compe on
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return Match QueryFilter to check if the object equalss the value.
     */
    public static <T, R> Match<T, R> match(T value) {
        return new Match<>(value);
    }

    /**
     *
     * @param getter the function used to extract the Comparable sort key
     * @param value  the value to compe on
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return Match QueryFilter to check if the object equalss the value.
     */
    public static <T, R> Match<T, R> match(Function<T, R> getter, T value) {
        return new Match<>(getter, value);
    }

    /**
     *
     * @param value  the value to compe on
     * @param weight the weight this should return if QueryFilter return matches.
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return Match QueryFilter to check if the object equalss the value.
     */
    public static <T, R> Match<T, R> match(T value, Double weight) {
        return new Match<>(value, weight);
    }

    /**
     *
     * @param getter the function used to extract the Comparable sort key
     * @param value  the value to compe on
     * @param weight the weight this should return if QueryFilter return matches.
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return Match QueryFilter to check if the object equalss the value.
     */
    public static <T, R> Match<T, R> match(Function<T, R> getter, T value, Double weight) {
        return new Match<>(getter, value, weight);
    }

    /**
     *
     * @param value  the value to compe on
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return GreaterThan QueryFilter to check if the object is greater than the value when compared.
     */
    public static <T, R> GreaterThan<T, R> greaterThan(T value) {
        return new GreaterThan<>(value);
    }

    /**
     *
     * @param value  the value to compe on
     * @param weight the weight this should return if QueryFilter return matches.
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return GreaterThan QueryFilter to check if the object is greater than the value when compared.
     */
    public static <T, R> GreaterThan<T, R> greaterThan(T value, Double weight) {
        return new GreaterThan<>(value, weight);
    }

    /**
     *
     * @param getter the function used to extract the Comparable sort key
     * @param value  the value to compe on
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return GreaterThan QueryFilter to check if the object is greater than the value when compared.
     */
    public static <T, R> GreaterThan<T, R> greaterThan(Function<T, R> getter, T value) {
        return new GreaterThan<>(getter, value);
    }

    /**
     *
     * @param getter the function used to extract the Comparable sort key
     * @param value  the value to compe on
     * @param weight the weight this should return if QueryFilter return matches.
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return GreaterThan QueryFilter to check if the object is greater than the value when compared.
     */
    public static <T, R> GreaterThan<T, R> greaterThan(Function<T, R> getter, T value, Double weight) {
        return new GreaterThan<>(getter, value, weight);
    }

    /**
     *
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return IsEmpty QueryFilter to check if the object is empty
     */
    public static <T, R> IsEmpty<T, R> isEmpty() {
        return new IsEmpty<>();
    }

    /**
     *
     * @param weight the weight this should return if QueryFilter return matches.
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return IsEmpty QueryFilter to check if the object is empty
     */
    public static <T, R> IsEmpty<T, R> isEmpty(Double weight) {
        return new IsEmpty<>(weight);
    }

    /**
     *
     * @param getter the function used to extract the Comparable sort key
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return IsEmpty QueryFilter to check if the object is empty
     */
    public static <T, R> IsEmpty<T, R> isEmpty(Function<T, R> getter) {
        return new IsEmpty<>(getter);
    }

    /**
     *
     * @param getter the function used to extract the Comparable sort key
     * @param weight the weight this should return if QueryFilter return matches.
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return IsEmpty QueryFilter to check if the object is empty
     */
    public static <T, R> IsEmpty<T, R> isEmpty(Function<T, R> getter, Double weight) {
        return new IsEmpty<>(getter, weight);
    }

    /**
     *
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return IsNull QueryFilter to check if the object is null
     */
    public static <T, R> IsNull<T, R> isNull() {
        return new IsNull<>();
    }

    /**
     *
     * @param weight the weight this should return if QueryFilter return matches.
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return IsNull QueryFilter to check if the object is null
     */
    public static <T, R> IsNull<T, R> isNull(Double weight) {
        return new IsNull<>(weight);
    }

    /**
     *
     * @param getter the function used to extract the Comparable sort key
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return IsNull QueryFilter to check if the object is null
     */
    public static <T, R> IsNull<T, R> isNull(Function<T, R> getter) {
        return new IsNull<>(getter);
    }

    /**
     *
     * @param getter the function used to extract the Comparable sort key
     * @param weight the weight this should return if QueryFilter return matches.
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return IsNull QueryFilter to check if the object is null
     */
    public static <T, R> IsNull<T, R> isNull(Function<T, R> getter, Double weight) {
        return new IsNull<>(getter, weight);
    }

    /**
     *
     * @param value  the value to compe on
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return LessThan QueryFilter to check if the object is less than the value when compared.
     */
    public static <T, R> LessThan<T, R> lessThan(T value) {
        return new LessThan<>(value);
    }

    /**
     *
     * @param value  the value to compe on
     * @param weight the weight this should return if QueryFilter return matches.
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return LessThan QueryFilter to check if the object is less than the value when compared.
     */
    public static <T, R> LessThan<T, R> lessThan(T value, Double weight) {
        return new LessThan<>(value, weight);
    }

    /**
     *
     * @param getter the function used to extract the Comparable sort key
     * @param value  the value to compe on
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return LessThan QueryFilter to check if the object is less than the value when compared.
     */
    public static <T, R> LessThan<T, R> lessThan(Function<T, R> getter, T value) {
        return new LessThan<>(getter, value);
    }

    /**
     *
     * @param getter the function used to extract the Comparable sort key
     * @param value  the value to compe on
     * @param weight the weight this should return if QueryFilter return matches.
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return LessThan QueryFilter to check if the object is less than the value when compared.
     */
    public static <T, R> LessThan<T, R> lessThan(Function<T, R> getter, T value, Double weight) {
        return new LessThan<>(getter, value, weight);
    }

    /**
     *
     * @param filter QueryFilter to flip the value on
     * @param <T> Type of object to filter
     * @return Not QueryFilter flips the value of QueryFilter
     */
    public static <T> Not<T> not(QueryFilter<T> filter) {
        return new Not<>(filter);
    }

    /**
     *
     * @param filter QueryFilter to flip the value on
     * @param weight the weight this should return if QueryFilter return matches.
     * @param <T> Type of object to filter
     * @return Not QueryFilter flips the value of QueryFilter
     */
    public static <T> Not<T> not(QueryFilter<T> filter, Double weight) {
        return new Not<>(filter, weight);
    }

    /**
     *
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return NotEmpty QueryFilter to check if the object is not empty
     */
    public static <T, R> NotEmpty<T, R> notEmpty() {
        return new NotEmpty<>();
    }

    /**
     *
     * @param weight the weight this should return if QueryFilter return matches.
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return NotEmpty QueryFilter to check if the object is not empty
     */
    public static <T, R> NotEmpty<T, R> notEmpty(Double weight) {
        return new NotEmpty<>(weight);
    }

    /**
     *
     * @param getter the function used to extract the Comparable sort key
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return NotEmpty QueryFilter to check if the object is not empty
     */
    public static <T, R> NotEmpty<T, R> notEmpty(Function<T, R> getter) {
        return new NotEmpty<>(getter);
    }

    /**
     *
     * @param getter the function used to extract the Comparable sort key
     * @param weight the weight this should return if QueryFilter return matches.
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return NotEmpty QueryFilter to check if the object is not empty
     */
     public static <T, R> NotEmpty<T, R> notEmpty(Function<T, R> getter, Double weight) {
     return new NotEmpty<>(getter, weight);
     }

     /**
     *
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return NotNull QueryFilter to check if the object is not null
     */
    public static <T, R> NotNull<T, R> notNull() {
        return new NotNull<>();
    }

    /**
     *
     * @param weight the weight this should return if QueryFilter return matches.
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return NotNull QueryFilter to check if the object is not null
     */
    public static <T, R> NotNull<T, R> notNull(Double weight) {
        return new NotNull<>(weight);
    }

    /**
     *
     * @param getter the function used to extract the Comparable sort key
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return NotNull QueryFilter to check if the object is not null
     */
    public static <T, R> NotNull<T, R> notNull(Function<T, R> getter) {
        return new NotNull<>(getter);
    }

    /**
     *
     * @param getter the function used to extract the Comparable sort key
     * @param weight the weight this should return if QueryFilter return matches.
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     * @return NotNull QueryFilter to check if the object is not null
     */
    public static <T, R> NotNull<T, R> notNull(Function<T, R> getter, Double weight) {
        return new NotNull<>(getter, weight);
    }

    /**
     *
     * @param list List of QueryFilters to include in the Or
     * @param <T> Type of object to filter
     * @return Or queryFilter to check if at least one QueryFilter in the Or is positive.
     */
    public static <T> Or<T> or(List<QueryFilter<T>> list) {
        return new Or<>(list);
    }

    /**
     *
     * @param filters Array of QueryFilters to include in the Or
     * @param <T> Type of object to filter
     * @return Or queryFilter to check if at least one QueryFilter in the Or is positive.
     */
    public static <T> Or<T> or(QueryFilter<T>... filters) {
        return new Or<>(filters);
    }

    /**
     *
     * @param <T> Type of object to filter
     * @return Or queryFilter to check if at least one QueryFilter in the Or is positive.
     */
    public static <T> Or<T> or() {
        return new Or<>();
    }

    /**
     *
     * @param <T> Type of object to filter
     * @return Or queryFilter to check if only one QueryFilter in the Or is positive.
     */
    public static <T> Xor<T> xor() {
        return new Xor<>();
    }

    /**
     *
     * @param list List of QueryFilters to include in the Xor
     * @param <T> Type of object to filter
     * @return Or queryFilter to check if only one QueryFilter in the Or is positive.
     */
    public static <T> Xor<T> xor(List<QueryFilter<T>> list) {
        return new Xor<>(list);
    }

    /**
     *
     * @param filters Array of QueryFilters to include in the Xor
     * @param <T> Type of object to filter
     * @return Or queryFilter to check if only one QueryFilter in the Or is positive.
     */
    public static <T> Xor<T> xor(QueryFilter<T>... filters) {
        return new Xor<>(filters);
    }

    /**
     * Comparator that accepts a QueryFilter for processing weights to determine order.
     * @param <T> Type of object in filter
     */
    public static class QueryComparator<T> implements Comparator<T> {
        /**
         *
         */
        private QueryFilter<T> filter;

        /**
         * Constructor taking in the QueryFilter to use for processing
         * @param filter QueryFilter to use for processing.
         */
        public QueryComparator(QueryFilter<T> filter) {
            this.filter = filter;
        }

        /**
         * Compares its two arguments for order.  Returns a negative integer,
         * zero, or a positive integer as the first argument is less than, equal
         * to, or greater than the second.<p>
         * <p>
         * The implementor must ensure that {@code sgn(compare(x, y)) ==
         * -sgn(compare(y, x))} for all {@code x} and {@code y}.  (This
         * implies that {@code compare(x, y)} must throw an exception if and only
         * if {@code compare(y, x)} throws an exception.)<p>
         * <p>
         * The implementor must also ensure that the relation is transitive:
         * {@code ((compare(x, y)>0) && (compare(y, z)>0))} implies
         * {@code compare(x, z)>0}.<p>
         * <p>
         * Finally, the implementor must ensure that {@code compare(x, y)==0}
         * implies that {@code sgn(compare(x, z))==sgn(compare(y, z))} for all
         * {@code z}.<p>
         * <p>
         * It is generally the case, but <i>not</i> strictly required that
         * {@code (compare(x, y)==0) == (x.equals(y))}.  Generally speaking,
         * any comparator that violates this condition should clearly indicate
         * this fact.  The recommended language is "Note: this comparator
         * imposes orderings that are inconsistent with equals."<p>
         * <p>
         * In the foregoing description, the notation
         * {@code sgn(}<i>expression</i>{@code )} designates the mathematical
         * <i>signum</i> function, which is defined to return one of {@code -1},
         * {@code 0}, or {@code 1} according to whether the value of
         * <i>expression</i> is negative, zero, or positive, respectively.
         *
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         * @return a negative integer, zero, or a positive integer as the
         * first argument is less than, equal to, or greater than the
         * second.
         * @throws NullPointerException if an argument is null and this
         *                              comparator does not permit null arguments
         * @throws ClassCastException   if the arguments' types prevent them from
         *                              being compared by this comparator.
         */
        @Override
        public int compare(T o1, T o2) {
            Double value1 = this.filter.process(o1);
            Double value2 = this.filter.process(o2);

            return value1.compareTo(value2);
        }
    }

    /**
     * QueryFilter Predicate to use in a filter method for determining whether to filter the item.
     * @param <T> Type of object to filter
     */
    public static class QueryFilterStream<T> implements Predicate<T> {
        /**
         * QueryFilter to use in the processing
         */
        private QueryFilter<T> filter;

        /**
         * Constructor for creating the QueryFilter for filter processing
         * @param filter QueryFilter to use for processing.
         */
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
            if (filter.process(t) > 0D) {
                return true;
            }

            return false;
        }
    }

    /**
     * Used to "And" a list of queries together.  All filters must pass in order for this one to return true.
     * Any Filter added that is "null" will be ignored.
     */
    public static class And<T> implements QueryFilter<T> {

        /**
         *
         */
        private final List<QueryFilter<T>> list;

        /**
         * Generate an And Filter with a list.
         *
         * @param list List of QueryFilter to require to be true.
         */
        public And(List<QueryFilter<T>> list) {
            this.list = list;
        }

        /**
         * Variable list of filters to require to be true.
         *
         * @param filters Variable argument list of filters to process
         */
        public And(QueryFilter<T>... filters) {
            this.list = Arrays.asList(filters);
        }

        /**
         * Create a blank And Filter
         */
        public And() {
            this.list = new ArrayList<>();
        }

        /**
         * Add a QueryFilter to the AndFilter
         *
         * @param queryFilter QueryFilter to add
         * @return boolean indicating if successfully added
         */
        public boolean add(QueryFilter<T> queryFilter) {
            return list.add(queryFilter);
        }

        @Override
        public Double process(T object) {
            if (this.list.parallelStream().filter(Objects::nonNull).allMatch(filter -> filter.process(object) > 0)) {
                return this.list.parallelStream().filter(Objects::nonNull).mapToDouble(filter -> filter.process(object)).sum();
            }
            return 0D;
        }
    }

    /**
     *
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     */
    public static class Between<T, R> implements QueryFilter<T> {
        /**
         *
         */
        private And and;

        /**
         *
         * @param low
         * @param max
         */
        public Between(T low, T max) {
            this.and = new And(new LessThan(max), new GreaterThan(low));
        }

        /**
         *
         * @param getter the function used to extract the Comparable sort key
         * @param low
         * @param max
         */
        public Between(Function<T, R> getter, T low, T max) {
            this.and = new And(new LessThan(getter, max), new GreaterThan(getter, low));
        }

        /**
         * Must return true in order for this filter to agree to inclusion.
         *
         * @param object Object the filter should check
         * @return Boolean indicating if agree to inclusion.
         */
        @Override
        public Double process(T object) {
            return and.process(object);
        }
    }

    /**
     *
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     */
    public static class BetweenOrEqual<T, R> implements QueryFilter<T> {
        /**
         *
         */
        private Or or;

        /**
         *
         * @param low
         * @param max
         */
        public BetweenOrEqual(T low, T max) {
            this.or = new Or(new And(new LessThan(max), new GreaterThan(low)), new Match(low), new Match(max));
        }

        /**
         *
         * @param getter the function used to extract the Comparable sort key
         * @param low
         * @param max
         */
        public BetweenOrEqual(Function<T, R> getter, T low, T max) {
            this.or = new Or(new And(new LessThan(getter, max), new GreaterThan(getter, low)), new Match(getter, low), new Match(getter, max));
        }

        /**
         * Must return true in order for this filter to agree to inclusion.
         *
         * @param object Object the filter should check
         * @return Boolean indicating if agree to inclusion.
         */
        @Override
        public Double process(T object) {
            return or.process(object);
        }
    }

    /**
     * Check if the values associated with key contains the text in the value.
     */
    public static class Contains<T> extends AbstractQuery<T> {

        /**
         *
         */
        private final String value;
        /**
         *
         */
        private final Integer multiplier;
        /**
         *
         */
        private Function<T, String> getter;

        /**
         *
         * @param value  the value to compe on
         */
        public Contains(String value) {
            super(1D);
            this.multiplier = 2;
            this.value = value;
            this.getter = null;
        }

        /**
         *
         * @param value  the value to compe on
         * @param getter the function used to extract the Comparable sort key
         */
        public Contains(String value, Function<T, String> getter) {
            super(1D);
            this.multiplier = 2;
            this.value = value;
            this.getter = getter;
        }

        /**
         *
         * @param value  the value to compe on
         * @param weigth the weight this should return if QueryFilter return matches.
         * @param multiplier In the case of an exact match, this is applied to the weight
         */
        public Contains(String value, Double weigth, Integer multiplier) {
            super(weigth);
            this.multiplier = multiplier;
            this.value = value;
            this.getter = null;
        }

        /**
         *
         * @param value  the value to compe on
         * @param getter the function used to extract the Comparable sort key
         * @param weight the weight this should return if QueryFilter return matches.
         * @param multiplier In the case of an exact match, this is applied to the weight
         */
        public Contains(String value, Function<T, String> getter, Double weight, Integer multiplier) {
            super(weight);
            this.multiplier = multiplier;
            this.value = value;
            this.getter = getter;
        }


        /**
         * Must return true in order for this filter to agree to inclusion.
         *
         * @param object String the filter should check
         * @return Boolean indicating if agree to inclusion.
         */
        @Override
        public Double process(T object) {
            if (object != null && value != null) {
                if (getter == null) {
                    if (object.toString().contains(value)) {
                        if (object.toString().equals(value)) {
                            return this.getWeight() * multiplier;
                        }
                        return this.getWeight();
                    }
                } else if (getter != null) {
                    String content = this.getter.apply(object);
                    if (content != null) {
                        if (content.contains(value)) ;
                        {
                            if (content.equals(value)) {
                                return this.getWeight() * multiplier;
                            }
                            return this.getWeight();
                        }
                    }
                }
            }
            if (object == null && value == null) {
                return this.getWeight();
            }
            return 0D;
        }
    }

    /**
     *
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     */
    public static class GreaterThan<T, R> extends AbstractQuery<T> {
        /**
         *
         */
        private Function<T, R> getter;

        /**
         *
         */
        private T value;

        /**
         *
         * @param value  the value to compe on
         */
        public GreaterThan(T value) {
            super(.1D);
            if (value != null) {
                ObjectUtils.throwIfNotInstance(Comparable.class, value, "Object must be Comparable.");
            }
            this.value = value;
        }

        /**
         *
         * @param value  the value to compe on
         * @param weight the weight this should return if QueryFilter return matches.
         */
        public GreaterThan(T value, Double weight) {
            super(weight);
            if (value != null) {
                ObjectUtils.throwIfNotInstance(Comparable.class, value, "Object must be Comparable.");
            }
            this.value = value;
        }

        /**
         *
         * @param getter the function used to extract the Comparable sort key
         * @param value  the value to compe on
         */
        public GreaterThan(Function<T, R> getter, T value) {
            super(.1D);
            if (value != null) {
                ObjectUtils.throwIfNotInstance(Comparable.class, value, "Object must be Comparable.");
            }
            this.getter = getter;
            this.value = value;
        }

        /**
         *
         * @param getter the function used to extract the Comparable sort key
         * @param value  the value to compe on
         * @param weight the weight this should return if QueryFilter return matches.
         */
        public GreaterThan(Function<T, R> getter, T value, Double weight) {
            super(weight);
            if (value != null) {
                ObjectUtils.throwIfNotInstance(Comparable.class, value, "Object must be Comparable.");
            }
            this.getter = getter;
            this.value = value;
        }

        /**
         * Must return true in order for this filter to agree to inclusion.
         *
         * @param object Object the filter should check
         * @return Boolean indicating if agree to inclusion.
         */
        @Override
        public Double process(T object) {
            if (object != null && this.value != null) {
                if (this.getter == null) {
                    ObjectUtils.throwIfNotInstance(Comparable.class, object, "Object must be Comparable.");
                    if (((Comparable<T>) object).compareTo(this.value) > 0) {
                        return this.getWeight();
                    }
                } else {
                    Object obj = this.getter.apply(object);
                    ObjectUtils.throwIfNotInstance(Comparable.class, obj, "Value from getter must be Comparable.");
                    if (((Comparable<T>) obj).compareTo(this.value) > 0) {
                        return this.getWeight();
                    }
                }
            }

            return 0D;
        }
    }

    /**
     *
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     */
    public static class IsEmpty<T, R> extends AbstractQuery<T> {

        /**
         *
         */
        private Function<T, R> getter;

        /**
         *
         */
        public IsEmpty() {
            super(.1D);
        }

        /**
         *
         * @param weight the weight this should return if QueryFilter return matches.
         */
        public IsEmpty(Double weight) {
            super(weight);
        }

        /**
         *
         * @param getter the function used to extract the Comparable sort key
         */
        public IsEmpty(Function<T, R> getter) {
            super(.1D);
            this.getter = getter;
        }

        /**
         *
         * @param getter the function used to extract the Comparable sort key
         * @param weight the weight this should return if QueryFilter return matches.
         */
        public IsEmpty(Function<T, R> getter, Double weight) {
            super(weight);
            this.getter = getter;
        }

        /**
         * Must return true in order for this filter to agree to inclusion.
         *
         * @param object Object the filter should check
         * @return Double indicating 0 if not include, of Double > 0 indicating weight.
         */
        @Override
        public Double process(T object) {
            if (object != null) {
                if (this.getter != null) {
                    Object obj = this.getter.apply(object);
                    ObjectUtils.throwIfNotInstance(String.class, obj, "Value from getter must be of type String");
                    if (object.toString().isEmpty()) {
                        return this.getWeight();
                    }
                } else {
                    ObjectUtils.throwIfNotInstance(String.class, object, "Object must be of type String");
                    if (object.toString().isEmpty()) {
                        return this.getWeight();
                    }
                }
            }

            return 0D;
        }
    }

    /**
     *
     * @param <T> Type of object to filter
     * @param <R>
     */
    public static class IsNull<T, R> extends AbstractQuery<T> {
        /**
         *
         */
        private Function<T, R> getter;

        /**
         *
         */
        public IsNull() {
            super(.1D);
        }

        /**
         *
         * @param weight the weight this should return if QueryFilter return matches.
         */
        public IsNull(Double weight) {
            super(weight);
        }

        /**
         *
         * @param getter the function used to extract the Comparable sort key
         */
        public IsNull(Function<T, R> getter) {
            super(.1D);
            this.getter = getter;
        }

        /**
         *
         * @param getter the function used to extract the Comparable sort key
         * @param weight the weight this should return if QueryFilter return matches.
         */
        public IsNull(Function<T, R> getter, Double weight) {
            super(weight);
            this.getter = getter;
        }

        /**
         * Must return true in order for this filter to agree to inclusion.
         *
         * @param object Object the filter should check
         * @return Double indicating 0 if not include, of Double > 0 indicating weight.
         */
        @Override
        public Double process(T object) {
            if (this.getter != null) {
                Object obj = this.getter.apply(object);
                if (object == null) {
                    return this.getWeight();
                }
            } else if (object == null) {
                return this.getWeight();
            }
            return 0D;
        }
    }

    /**
     *
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     */
    public static class LessThan<T, R> extends AbstractQuery<T> {

        /**
         *
         */
        private T value;
        /**
         *
         */
        private Function<T, R> getter;

        /**
         *
         * @param value  the value to compe on
         */
        public LessThan(T value) {
            super(.1D);
            if (value != null) {
                ObjectUtils.throwIfNotInstance(Comparable.class, value, "Object must be Comparable.");
            }
            this.value = value;
        }

        /**
         *
         * @param value  the value to compe on
         * @param weight the weight this should return if QueryFilter return matches.
         */
        public LessThan(T value, Double weight) {
            super(weight);
            if (value != null) {
                ObjectUtils.throwIfNotInstance(Comparable.class, value, "Object must be Comparable.");
            }
            this.value = value;
        }

        /**
         *
         * @param getter the function used to extract the Comparable sort key
         * @param value  the value to compe on
         */
        public LessThan(Function<T, R> getter, T value) {
            super(.1D);
            if (value != null) {
                ObjectUtils.throwIfNotInstance(Comparable.class, value, "Object must be Comparable.");
            }
            this.getter = getter;
            this.value = value;
        }

        /**
         *
         * @param getter the function used to extract the Comparable sort key
         * @param value  the value to compe on
         * @param weight the weight this should return if QueryFilter return matches.
         */
        public LessThan(Function<T, R> getter, T value, Double weight) {
            super(weight);
            if (value != null) {
                ObjectUtils.throwIfNotInstance(Comparable.class, value, "Object must be Comparable.");
            }
            this.getter = getter;
            this.value = value;
        }

        /**
         * Must return true in order for this filter to agree to inclusion.
         *
         * @param object Object the filter should check
         * @return Boolean indicating if agree to inclusion.
         */
        @Override
        public Double process(T object) {
            if (object != null && this.value != null) {
                if (this.getter == null) {
                    ObjectUtils.throwIfNotInstance(Comparable.class, object, "Object must be Comparable.");
                    if (((Comparable<T>) object).compareTo(this.value) < 0) {
                        return this.getWeight();
                    }
                } else {
                    Object obj = this.getter.apply(object);
                    ObjectUtils.throwIfNotInstance(Comparable.class, obj, "Value from getter must be Comparable.");
                    if (((Comparable<T>) obj).compareTo(this.value) < 0) {
                        return this.getWeight();
                    }
                }
            }

            return 0D;
        }
    }

    /**
     * Compares the toString() value of an object the value in the field.
     */
    public static class Match<T, R> extends AbstractQuery<T> {

        /**
         *
         */
        private final T value;
        /**
         *
         */
        private Function<T, R> getter;

        /**
         *
         * @param value  the value to compe on
         */
        public Match(T value) {
            super(1D);
            if (value == null) {
                throw new NullPointerException("value may not be null");
            }
            this.value = value;
            this.getter = null;
        }

        /**
         *
         * @param getter the function used to extract the Comparable sort key
         * @param value  the value to compe on
         */
        public Match(Function<T, R> getter, T value) {
            super(1D);
            this.value = value;
            this.getter = getter;
        }

        /**
         *
         * @param value  the value to compe on
         * @param weight the weight this should return if QueryFilter return matches.
         */
        public Match(T value, Double weight) {
            super(weight);
            if (value == null) {
                throw new NullPointerException("value may not be null");
            }
            this.value = value;
            this.getter = null;
        }

        /**
         *
         * @param getter the function used to extract the Comparable sort key
         * @param value  the value to compe on
         * @param weight the weight this should return if QueryFilter return matches.
         */
        public Match(Function<T, R> getter, T value, Double weight) {
            super(weight);
            this.value = value;
            this.getter = getter;
        }

        @Override
        public Double process(T object) {
            if (getter == null) {
                if (value.equals(object)) {
                    return this.getWeight();
                }
            } else if (value.equals(this.getter.apply(object))) {
                return this.getWeight();
            }

            return 0D;
        }
    }

    /**
     * Used to flip the value of a filter.
     */
    public static class Not<T> extends AbstractQuery<T> {
        /**
         *
         */
        private final QueryFilter<T> filter;

        /**
         * Create a NotFilter to flip the value of a filter
         *
         * @param filter filter to flip the value on.
         */
        public Not(QueryFilter<T> filter) {
            super(.1D);
            if (filter == null) {
                throw new NullPointerException("Filter may not be null");
            }
            this.filter = filter;
        }

        /**
         *
         * @param filter
         * @param weight the weight this should return if QueryFilter return matches.
         */
        public Not(QueryFilter<T> filter, Double weight) {
            super(weight);
            if (filter == null) {
                throw new NullPointerException("Filter may not be null");
            }
            this.filter = filter;
        }

        @Override
        public Double process(T object) {
            if (this.filter.process(object) == 0D) {
                return this.getWeight();
            }

            return 0D;
        }
    }

    /**
     *
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Getter
     */
    public static class NotEmpty<T, R> implements QueryFilter<T> {
        /**
         *
         */
        private Not not;

        /**
         *
         */
        public NotEmpty() {
            this.not = new Not(new IsEmpty());
        }

        /**
         *
         * @param weight the weight this should return if QueryFilter return matches.
         */
        public NotEmpty(Double weight) {
            this.not = new Not(new IsEmpty(weight));
        }

        /**
         *
         * @param getter the function used to extract the Comparable sort key
         */
        public NotEmpty(Function<T, R> getter) {
            this.not = new Not(new IsEmpty(getter));
        }

        /**
         *
         * @param getter the function used to extract the Comparable sort key
         * @param weight the weight this should return if QueryFilter return matches.
         */
        public NotEmpty(Function<T, R> getter, Double weight) {
            this.not = new Not(new IsEmpty(getter, weight));
        }

        /**
         * Must return true in order for this filter to agree to inclusion.
         *
         * @param object Object the filter should check
         * @return Double indicating 0 if not include, of Double > 0 indicating weight.
         */
        @Override
        public Double process(T object) {
            return this.not.process(object);
        }
    }

    /**
     *
     * @param <T> Type of object to filter
     * @param <R> Type returned from the Gettervvv
     */
    public static class NotNull<T, R> implements QueryFilter<T> {
        /**
         *
          */
        private Not not;

        /**
         *
         */
        public NotNull() {
            this.not = new Not(new IsNull());
        }

        /**
         *
         * @param weight the weight this should return if QueryFilter return matches.
         */
        public NotNull(Double weight) {
            this.not = new Not(new IsNull(weight));
        }

        /**
         *
         * @param getter the function used to extract the Comparable sort key
         */
        public NotNull(Function<T, R> getter) {
            this.not = new Not(new IsNull(getter));
        }

        /**
         *
         * @param getter the function used to extract the Comparable sort key
         * @param weight the weight this should return if QueryFilter return matches.
         */
        public NotNull(Function<T, R> getter, Double weight) {
            this.not = new Not(new IsNull(getter, weight));
        }

        /**
         * Must return true in order for this filter to agree to inclusion.
         *
         * @param object Object the filter should check
         * @return Double indicating 0 if not include, of Double > 0 indicating weight.
         */
        @Override
        public Double process(T object) {
            return this.not.process(object);
        }
    }

    /**
     * Used to "Or" a list of queries together.  Any filter returning true for this one to return true.
     * Any Filter added that is "null" will be ignored.
     */
    public static class Or<T> implements QueryFilter<T> {
        private final List<QueryFilter<T>> list;

        /**
         * Generate an Or Filter with a list.
         *
         * @param list List of QueryFilter to require to be true.
         */
        public Or(List<QueryFilter<T>> list) {
            this.list = list;
        }

        /**
         * Variable list of filters to check if one is true.
         *
         * @param filters Variable argument list of filters to process
         */
        public Or(QueryFilter<T>... filters) {
            this.list = Arrays.asList(filters);
        }

        /**
         * Create a blank Or Filter
         */
        public Or() {
            this.list = new ArrayList<>();
        }

        /**
         * Add a QueryFilter to the OrFilter
         *
         * @param queryFilter QueryFilter to add
         * @return boolean indicating if successfully added
         */
        public boolean add(QueryFilter<T> queryFilter) {
            return list.add(queryFilter);
        }

        @Override
        public Double process(T object) {
            return this.list.parallelStream().filter(Objects::nonNull).map(filter -> filter.process(object)).filter(value -> value > 0).mapToDouble(value -> value).sum();
        }
    }

    /**
     * Used to "Xor a list of queries together.  Only one filter may return true for this one to return true.
     * Any Filter added that is "null" will be ignored.
     */
    public static class Xor<T> implements QueryFilter<T> {
        private final List<QueryFilter<T>> list;

        /**
         * Generate an Or Filter with a list.
         *
         * @param list List of QueryFilter to process.
         */
        public Xor(List<QueryFilter<T>> list) {
            this.list = list;
        }

        /**
         * Variable list of filters to check if one is true.
         *
         * @param filters Variable argument list of filters to process
         */
        public Xor(QueryFilter<T>... filters) {
            this.list = Arrays.asList(filters);
        }

        /**
         * Create a blank Or Filter
         */
        public Xor() {
            this.list = new ArrayList<>();
        }

        /**
         * Add a QueryFilter to the OrFilter
         *
         * @param queryFilter QueryFilter to add
         * @return boolean indicating if successfully added
         */
        public boolean add(QueryFilter<T> queryFilter) {
            return list.add(queryFilter);
        }

        @Override
        public Double process(T object) {
            List<Double> values = this.list.parallelStream().filter(Objects::nonNull).map(filter -> filter.process(object)).collect(Collectors.toList());
            values = values.stream().filter(value -> value != 0D).collect(Collectors.toList());
            if (values.size() == 1) {
                return values.get(0);
            }

            return 0D;
        }
    }
}
