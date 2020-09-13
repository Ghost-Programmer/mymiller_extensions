package name.mymiller.query;

import java.util.*;

/**
 * Used to "And" a list of queries together.  All filters must pass in order for this one to return true.
 * Any Filter added that is "null" will be ignored.
 */
public class And<T> implements QueryFilter<T>{

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
    public Boolean process(T object) {
        return this.list.parallelStream().filter(Objects::nonNull).allMatch(filter -> filter.process(object));
    }
}
