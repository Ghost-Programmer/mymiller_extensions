package name.mymiller.query;

/**
 * Used to flip the value of a filter.
 */
public class Not<T> extends AbstractQuery<T>{

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
