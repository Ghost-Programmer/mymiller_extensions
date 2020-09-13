package name.mymiller.query;
import java.util.function.Function;

/**
 * Check if the values associated with key contains the text in the value.
 */
public class Contains<T> implements QueryFilter<T>{

    private final String value;
    private Function<T, String> getter;

    public Contains(String value) {
        this.value = value;
        this.getter = null;
    }

    public Contains(String value, Function<T, String> getter) {
        this.value = value;
        this.getter = getter;
    }


    /**
     * Must return true in order for this filter to agree to inclusion.
     *
     * @param object String the filter should check
     * @return Boolean indicating if agree to inclusion.
     */
    @Override
    public Boolean process(T object) {
        if(object != null && value != null) {
            if(getter == null) {
                return object.toString().contains(value);
            } else if(getter != null) {
                String content = this.getter.apply(object);
                if(content != null) {
                    return content.contains(value);
                }
            }
        }
        return object == null && value == null;
    }
}
