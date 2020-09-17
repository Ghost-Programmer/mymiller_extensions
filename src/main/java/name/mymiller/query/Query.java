package name.mymiller.query;

import name.mymiller.utils.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
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
     * Used to "And" a list of queries together.  All filters must pass in order for this one to return true.
     * Any Filter added that is "null" will be ignored.
     */
    public static class And<T> implements QueryFilter<T>{

        private final List<QueryFilter<T>> list;

        /**
         * Generate an And Filter with a list.
         * @param list List of QueryFilter to require to be true.
         */
        public And(List<QueryFilter<T>> list) {
            this.list = list;
        }

        /**
         * Variable list of filters to require to be true.
         * @param filters Variable argument list of filters to process
         */
        public And(QueryFilter<T> ... filters) {
            this.list = Arrays.asList(filters);
        }

        /**
         * Create a blank And Filter
         */
        public And() {
            this.list =  new ArrayList<>();
        }

        /**
         * Add a QueryFilter to the AndFilter
         * @param queryFilter QueryFilter to add
         * @return boolean indicating if successfully added
         */
        public boolean add(QueryFilter<T> queryFilter) {
            return list.add(queryFilter);
        }

        @Override
        public Double process(T object) {
            if(this.list.parallelStream().filter(Objects::nonNull).allMatch(filter -> filter.process(object) > 0)) {
                return this.list.parallelStream().filter(Objects::nonNull).mapToDouble(filter -> filter.process(object)).sum();
            }
            return 0D;
        }
    }

    public static class Between<T,R> implements QueryFilter<T> {
        private And and;

        public Between(T low, T max) {
            this.and = new And(new LessThan(max), new GreaterThan(low));
        }
        public Between(Function<T, R> getter, T low, T max) {
            this.and = new And(new LessThan(getter,max), new GreaterThan(getter,low));
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
    public static class BetweenOrEqual<T,R> implements QueryFilter<T> {

        private Or or;

        public BetweenOrEqual(T low, T max) {
            this.or = new Or(new And(new LessThan(max), new GreaterThan(low)), new Match(low),new Match(max));
        }
        public BetweenOrEqual(Function<T, R> getter, T low, T max) {
            this.or = new Or(new And(new LessThan(getter,max), new GreaterThan(getter,low)), new Match(getter,low),new Match(getter,max));
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
    public static class Contains<T> extends AbstractQuery<T>{

        private final String value;
        private final Integer multiplier;
        private Function<T, String> getter;

        public Contains(String value) {
            super(1D);
            this.multiplier = 2;
            this.value = value;
            this.getter = null;
        }

        public Contains(String value, Function<T, String> getter) {
            super(1D);
            this.multiplier = 2;
            this.value = value;
            this.getter = getter;
        }

        public Contains(String value, Double weigth, Integer multiplier) {
            super(weigth);
            this.multiplier = multiplier;
            this.value = value;
            this.getter = null;
        }

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
            if(object != null && value != null) {
                if(getter == null) {
                    if(object.toString().contains(value)) {
                        if(object.toString().equals(value)) {
                            return this.getWeight() * multiplier;
                        }
                        return this.getWeight();
                    }
                } else if(getter != null) {
                    String content = this.getter.apply(object);
                    if(content != null) {
                        if(content.contains(value)); {
                            if(content.equals(value)) {
                                return this.getWeight() * multiplier;
                            }
                            return this.getWeight();
                        }
                    }
                }
            }
            if(object == null && value == null) {
                return this.getWeight();
            }
            return 0D;
        }
    }

    public static class GreaterThan<T,R> extends AbstractQuery<T> {
        private Function<T, R> getter;

        private T value;

        public GreaterThan(T value) {
            super(.1D);
            if(value != null) {
                ObjectUtils.throwIfNotInstance(Comparable.class, value,"Object must be Comparable.");
            }
            this.value = value;
        }

        public GreaterThan(T value, Double weight) {
            super(weight);
            if(value != null) {
                ObjectUtils.throwIfNotInstance(Comparable.class, value,"Object must be Comparable.");
            }
            this.value = value;
        }
        public GreaterThan(Function<T, R> getter, T value) {
            super(.1D);
            if(value != null) {
                ObjectUtils.throwIfNotInstance(Comparable.class, value,"Object must be Comparable.");
            }
            this.getter = getter;
            this.value = value;
        }

        public GreaterThan(Function<T, R> getter, T value, Double weight) {
            super(weight);
            if(value != null) {
                ObjectUtils.throwIfNotInstance(Comparable.class, value,"Object must be Comparable.");
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
            if(object != null && this.value != null) {
                if(this.getter == null) {
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
    public static class IsEmpty<T,R> extends AbstractQuery<T> {

        private Function<T, R> getter;

        public IsEmpty() {
            super(.1D);
        }

        public IsEmpty(Double weight) {
            super(weight);
        }

        public IsEmpty(Function<T, R> getter) {
            super(.1D);
            this.getter = getter;
        }

        public IsEmpty(Function<T, R> getter,Double weight) {
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
            if(object != null) {
                if(this.getter != null) {
                    Object obj = this.getter.apply(object);
                    ObjectUtils.throwIfNotInstance(String.class,obj,"Value from getter must be of type String");
                    if(object.toString().isEmpty()) {
                        return this.getWeight();
                    }
                } else {
                    ObjectUtils.throwIfNotInstance(String.class,object,"Object must be of type String");
                    if(object.toString().isEmpty()) {
                        return this.getWeight();
                    }
                }
            }

            return 0D;
        }
    }

    public static class IsNull<T, R> extends AbstractQuery<T>  {
        private Function<T, R> getter;

        public IsNull() {
            super(.1D);
        }

        public IsNull(Double weight) {
            super(weight);
        }

        public IsNull(Function<T, R> getter) {
            super(.1D);
            this.getter = getter;
        }

        public IsNull(Function<T, R> getter,Double weight) {
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
            if(this.getter != null) {
                Object obj = this.getter.apply(object);
                if(object == null) {
                    return this.getWeight();
                }
            } else if(object == null) {
                return this.getWeight();
            }
            return 0D;
        }
    }

    public static class LessThan<T,R> extends AbstractQuery<T> {

        private T value;
        private Function<T, R> getter;

        public LessThan(T value) {
            super(.1D);
            if(value != null) {
                ObjectUtils.throwIfNotInstance(Comparable.class, value,"Object must be Comparable.");
            }
            this.value = value;
        }

        public LessThan(T value, Double weight) {
            super(weight);
            if(value != null) {
                ObjectUtils.throwIfNotInstance(Comparable.class, value,"Object must be Comparable.");
            }
            this.value = value;
        }

        public LessThan(Function<T, R> getter,T value) {
            super(.1D);
            if(value != null) {
                ObjectUtils.throwIfNotInstance(Comparable.class, value,"Object must be Comparable.");
            }
            this.getter = getter;
            this.value = value;
        }

        public LessThan(Function<T, R> getter,T value, Double weight) {
            super(weight);
            if(value != null) {
                ObjectUtils.throwIfNotInstance(Comparable.class, value,"Object must be Comparable.");
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
            if(object != null && this.value != null) {
                if(this.getter == null) {
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
    public static class Match<T,R> extends AbstractQuery<T>{

        private final T value;
        private Function<T, R> getter;

        public Match(T value) {
            super(1D);
            if(value == null) {
                throw new NullPointerException("value may not be null");
            }
            this.value = value;
            this.getter = null;
        }

        public Match(Function<T, R> getter, T value) {
            super(1D);
            this.value = value;
            this.getter = getter;
        }

        public Match(T value, Double weight) {
            super(weight);
            if(value == null) {
                throw new NullPointerException("value may not be null");
            }
            this.value = value;
            this.getter = null;
        }

        public Match(Function<T, R> getter, T value, Double weight) {
            super(weight);
            this.value = value;
            this.getter = getter;
        }

        @Override
        public Double process(T object) {
            if(getter == null) {
                if(value.equals(object)) {
                    return this.getWeight();
                }
            } else if(value.equals(this.getter.apply(object))) {
                return this.getWeight();
            }

            return 0D;
        }
    }

    /**
     * Used to flip the value of a filter.
     */
    public static class Not<T> extends AbstractQuery<T>{

        private final QueryFilter<T> filter;

        /**
         * Create a NotFilter to flip the value of a filter
         * @param filter filter to flip the value on.
         */
        public Not(QueryFilter<T> filter) {
            super(.1D);
            if(filter == null) {
                throw new NullPointerException("Filter may not be null");
            }
            this.filter = filter;
        }

        public Not(QueryFilter<T> filter, Double weight) {
            super(weight);
            if(filter == null) {
                throw new NullPointerException("Filter may not be null");
            }
            this.filter = filter;
        }

        @Override
        public Double process(T object) {
            if(this.filter.process(object) == 0D) {
                return this.getWeight();
            }

            return 0D;
        }
    }

    public static class NotEmpty<T,R> implements QueryFilter<T> {
        private Not not;

        public NotEmpty() {
            this.not = new Not(new IsEmpty());
        }

        public NotEmpty(Double weight) {
            this.not = new Not(new IsEmpty(weight));
        }

        public NotEmpty(Function<T, R> getter) {
            this.not = new Not(new IsEmpty(getter));
        }

        public NotEmpty(Function<T, R> getter, Double weight) {
            this.not = new Not(new IsEmpty(getter,weight));
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

    public static class NotNull<T,R> implements QueryFilter<T> {
        private Not not;

        public NotNull() {
            this.not = new Not(new IsNull());
        }

        public NotNull(Double weight) {
            this.not = new Not(new IsNull(weight));
        }

        public NotNull(Function<T, R> getter) {
            this.not = new Not(new IsNull(getter));
        }

        public NotNull(Function<T, R> getter, Double weight) {
            this.not = new Not(new IsNull(getter,weight));
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
    public static class Or<T> implements QueryFilter<T>{
        private final List<QueryFilter<T>> list;

        /**
         * Generate an Or Filter with a list.
         * @param list List of QueryFilter to require to be true.
         */
        public Or(List<QueryFilter<T>> list) {
            this.list = list;
        }

        /**
         * Variable list of filters to check if one is true.
         * @param filters Variable argument list of filters to process
         */
        public Or(QueryFilter<T> ... filters) {
            this.list = Arrays.asList(filters);
        }

        /**
         * Create a blank Or Filter
         */
        public Or() {
            this.list =  new ArrayList<>();
        }

        /**
         * Add a QueryFilter to the OrFilter
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
    public static class Xor<T> implements QueryFilter<T>{
        private final List<QueryFilter<T>> list;

        /**
         * Generate an Or Filter with a list.
         * @param list List of QueryFilter to process.
         */
        public Xor(List<QueryFilter<T>> list) {
            this.list = list;
        }

        /**
         * Variable list of filters to check if one is true.
         * @param filters Variable argument list of filters to process
         */
        public Xor(QueryFilter<T> ... filters) {
            this.list = Arrays.asList(filters);
        }

        /**
         * Create a blank Or Filter
         */
        public Xor() {
            this.list =  new ArrayList<>();
        }

        /**
         * Add a QueryFilter to the OrFilter
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
            if(values.size()== 1) {
                return values.get(0);
            }

            return 0D;
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
