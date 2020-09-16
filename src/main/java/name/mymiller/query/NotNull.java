package name.mymiller.query;

import java.util.function.Function;

public class NotNull<T,R> implements QueryFilter<T> {
    private Not not;

    public NotNull() {
        this.not = new Not(new IsNull());
    }

    public NotNull(Double weight) {
        this.not = new Not(new IsNull(weight));
    }

    public NotNull(Function<T, R> getter) {
        this.not = new Not(new IsNull(getter));
    }

    public NotNull(Function<T, R> getter, Double weight) {
        this.not = new Not(new IsNull(getter,weight));
    }

    /**
     * Must return true in order for this filter to agree to inclusion.
     *
     * @param object Object the filter should check
     * @return Double indicating 0 if not include, of Double > 0 indicating weight.
     */
    @Override
    public Double process(T object) {
        return this.not.process(object);
    }
}
