package name.mymiller.query;

import name.mymiller.utils.ObjectUtils;

import java.util.function.Function;

public class IsEmpty<T,R> extends AbstractQuery<T> {

    private Function<T, R> getter;

    public IsEmpty() {
        super(.1D);
    }

    public IsEmpty(Double weight) {
        super(weight);
    }

    public IsEmpty(Function<T, R> getter) {
        super(.1D);
        this.getter = getter;
    }

    public IsEmpty(Function<T, R> getter,Double weight) {
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
        if(object != null) {
            if(this.getter != null) {
                Object obj = this.getter.apply(object);
                ObjectUtils.throwIfNotInstance(String.class,obj,"Value from getter must be of type String");
                if(object.toString().isEmpty()) {
                    return this.getWeight();
                }
            } else {
                ObjectUtils.throwIfNotInstance(String.class,object,"Object must be of type String");
                if(object.toString().isEmpty()) {
                    return this.getWeight();
                }
            }
        }

        return 0D;
    }
}
