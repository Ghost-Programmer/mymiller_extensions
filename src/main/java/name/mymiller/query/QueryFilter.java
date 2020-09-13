package name.mymiller.query;

/**
 * Base filter for queries
 */
public interface QueryFilter<T> {
    /**
     * Must return true in order for this filter to agree to inclusion.
     * @param object Object the filter should check
     * @return Boolean indicating if agree to inclusion.
     */
    Boolean process(T object);
}
