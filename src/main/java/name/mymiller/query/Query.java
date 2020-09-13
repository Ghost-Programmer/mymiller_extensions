package name.mymiller.query;

import java.util.function.Predicate;

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
            return filter.process(t);
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

}
