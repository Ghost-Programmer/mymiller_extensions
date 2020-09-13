package name.mymiller.query;

public class GreaterThan<T> implements QueryFilter<Comparable<T>> {

    private T value;

    public GreaterThan(T value) {
        this.value = value;
    }

    /**
     * Must return true in order for this filter to agree to inclusion.
     *
     * @param object Object the filter should check
     * @return Boolean indicating if agree to inclusion.
     */
    @Override
    public Boolean process(Comparable<T> object) {
        if(object != null && this.value != null) {
            return object.compareTo(this.value) > 0;
        }

        return this.value == null && object == null;
    }
}
