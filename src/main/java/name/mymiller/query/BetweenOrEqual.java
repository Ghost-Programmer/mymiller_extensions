package name.mymiller.query;

import java.util.function.Function;

public class BetweenOrEqual<T,R> implements QueryFilter<T> {

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
