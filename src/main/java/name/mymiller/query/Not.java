package name.mymiller.query;

/**
 * Used to flip the value of a filter.
 */
public class Not<T> implements QueryFilter<T>{

    private final QueryFilter<T> filter;

    /**
     * Create a NotFilter to flip the value of a filter
     * @param filter filter to flip the value on.
     */
    public Not(QueryFilter<T> filter) {
        if(filter == null) {
            throw new NullPointerException("Filter may not be null");
        }
        this.filter = filter;
    }

    @Override
    public Boolean process(T object) {
        return !this.filter.process(object);
    }
}
