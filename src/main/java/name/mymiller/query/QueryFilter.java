package name.mymiller.query;

/**
 * Base filter for queries
 */
public interface QueryFilter<T> {
    /**
     * Must return true in order for this filter to agree to inclusion.
     * @param object Object the filter should check
     * @return Double indicating 0 if not to include, or Double > 0 indicating weight of matching
     */
    Double process(T object);
}
