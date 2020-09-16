package name.mymiller.query;

public class Between<T> implements QueryFilter<T> {

    private And and;

    public Between(T low, T max) {
        this.and = new And(new LessThan(max), new GreaterThan(low));
    }

    /**
     * Must return true in order for this filter to agree to inclusion.
     *
     * @param object Object the filter should check
     * @return Boolean indicating if agree to inclusion.
     */
    @Override
    public Boolean process(T object) {
        return and.process(object);
    }
}
