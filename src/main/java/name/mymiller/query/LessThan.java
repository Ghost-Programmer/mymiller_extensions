package name.mymiller.query;

import name.mymiller.utils.ObjectUtils;

import java.util.function.Function;

public class LessThan<T,R> extends AbstractQuery<T> {

    private T value;
    private Function<T, R> getter;

    public LessThan(T value) {
        super(.1D);
        if(value != null) {
            ObjectUtils.throwIfNotInstance(Comparable.class, value,"Object must be Comparable.");
        }
        this.value = value;
    }

    public LessThan(T value, Double weight) {
        super(weight);
        if(value != null) {
            ObjectUtils.throwIfNotInstance(Comparable.class, value,"Object must be Comparable.");
        }
        this.value = value;
    }

   public LessThan(Function<T, R> getter,T value) {
        super(.1D);
        if(value != null) {
            ObjectUtils.throwIfNotInstance(Comparable.class, value,"Object must be Comparable.");
        }
        this.getter = getter;
        this.value = value;
    }

    public LessThan(Function<T, R> getter,T value, Double weight) {
        super(weight);
        if(value != null) {
            ObjectUtils.throwIfNotInstance(Comparable.class, value,"Object must be Comparable.");
        }
        this.getter = getter;
        this.value = value;
    }

    /**
     * Must return true in order for this filter to agree to inclusion.
     *
     * @param object Object the filter should check
     * @return Boolean indicating if agree to inclusion.
     */
    @Override
    public Double process(T object) {
        if(object != null && this.value != null) {
            if(this.getter == null) {
                ObjectUtils.throwIfNotInstance(Comparable.class, object, "Object must be Comparable.");
                if (((Comparable<T>) object).compareTo(this.value) < 0) {
                    return this.getWeight();
                }
            } else {
                Object obj = this.getter.apply(object);
                ObjectUtils.throwIfNotInstance(Comparable.class, obj, "Value from getter must be Comparable.");
                if (((Comparable<T>) obj).compareTo(this.value) < 0) {
                    return this.getWeight();
                }
            }
        }

        return 0D;
    }
}
