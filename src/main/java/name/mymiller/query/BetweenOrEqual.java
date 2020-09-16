package name.mymiller.query;

public class BetweenOrEqual<T> implements QueryFilter<T> {

    private Or or;

    public BetweenOrEqual(T low, T max) {
        this.or = new Or(new And(new LessThan(max), new GreaterThan(low)), new Equals(low),new Equals(max));
    }

    /**
     * Must return true in order for this filter to agree to inclusion.
     *
     * @param object Object the filter should check
     * @return Boolean indicating if agree to inclusion.
     */
    @Override
    public Boolean process(T object) {
        return or.process(object);
    }
}
