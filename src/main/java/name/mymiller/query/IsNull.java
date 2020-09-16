package name.mymiller.query;

import java.util.function.Function;

public class IsNull<T, R> extends AbstractQuery<T>  {
    private Function<T, R> getter;

    public IsNull() {
        super(.1D);
    }

    public IsNull(Double weight) {
        super(weight);
    }

    public IsNull(Function<T, R> getter) {
        super(.1D);
        this.getter = getter;
    }

    public IsNull(Function<T, R> getter,Double weight) {
        super(weight);
        this.getter = getter;
    }

    /**
     * Must return true in order for this filter to agree to inclusion.
     *
     * @param object Object the filter should check
     * @return Double indicating 0 if not include, of Double > 0 indicating weight.
     */
    @Override
    public Double process(T object) {
        if(this.getter != null) {
            Object obj = this.getter.apply(object);
            if(object == null) {
                return this.getWeight();
            }
        } else if(object == null) {
            return this.getWeight();
        }
        return 0D;
    }
}
