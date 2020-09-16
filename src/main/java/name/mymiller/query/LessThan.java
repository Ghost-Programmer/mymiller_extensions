package name.mymiller.query;

import name.mymiller.utils.ObjectUtils;

import java.util.function.Function;

public class LessThan<T> implements QueryFilter<T> {

    private T value;

    public LessThan(T value) {
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
    public Boolean process(T object) {
        if(object != null && this.value != null) {
            ObjectUtils.throwIfNotInstance(Comparable.class, object,"Object must be Comparable.");
            return ((Comparable<T>)object).compareTo(this.value) < 0;
        }

        return this.value == null && object == null;
    }
}
