package name.mymiller.query;

import java.util.function.Function;

public class NotEmpty<T,R> implements QueryFilter<T> {
    private Not not;

    public NotEmpty() {
        this.not = new Not(new IsEmpty());
    }

    public NotEmpty(Double weight) {
        this.not = new Not(new IsEmpty(weight));
    }

    public NotEmpty(Function<T, R> getter) {
        this.not = new Not(new IsEmpty(getter));
    }

    public NotEmpty(Function<T, R> getter, Double weight) {
        this.not = new Not(new IsEmpty(getter,weight));
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
