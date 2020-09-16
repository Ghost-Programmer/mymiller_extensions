package name.mymiller.query;

import name.mymiller.utils.ObjectUtils;

public class GreaterThan<T> extends AbstractQuery<T> {

    private T value;

    public GreaterThan(T value) {
        super(.1D);
        if(value != null) {
            ObjectUtils.throwIfNotInstance(Comparable.class, value,"Object must be Comparable.");
        }
        this.value = value;
    }

    public GreaterThan(T value, Double weight) {
        super(weight);
        if(value != null) {
            ObjectUtils.throwIfNotInstance(Comparable.class, value,"Object must be Comparable.");
        }
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
            ObjectUtils.throwIfNotInstance(Comparable.class, object,"Object must be Comparable.");
            if(((Comparable<T>)object).compareTo(this.value) > 0) {
                return this.getWeight();
            }
        }

        return 0D;
    }
}
