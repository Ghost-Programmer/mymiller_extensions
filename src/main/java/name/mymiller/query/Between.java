package name.mymiller.query;

import java.util.function.Function;

public class Between<T,R> implements QueryFilter<T> {
    private And and;

    public Between(T low, T max) {
        this.and = new And(new LessThan(max), new GreaterThan(low));
    }
    public Between(Function<T, R> getter, T low, T max) {
        this.and = new And(new LessThan(getter,max), new GreaterThan(getter,low));
    }

    /**
     * Must return true in order for this filter to agree to inclusion.
     *
     * @param object Object the filter should check
     * @return Boolean indicating if agree to inclusion.
     */
    @Override
    public Double process(T object) {
        return and.process(object);
    }
}
