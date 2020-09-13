package name.mymiller.query;

import java.util.*;

/**
 * Used to "Xor a list of queries together.  Only one filter may return true for this one to return true.
 * Any Filter added that is "null" will be ignored.
 */
public class Xor<T> implements QueryFilter<T>{
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
    public Boolean process(T object) {
        return this.list.parallelStream().filter(Objects::nonNull).map(filter -> filter.process(object)).filter(Boolean::booleanValue).count() == 1L;
    }
}
