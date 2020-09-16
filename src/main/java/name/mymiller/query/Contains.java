package name.mymiller.query;
import java.util.function.Function;

/**
 * Check if the values associated with key contains the text in the value.
 */
public class Contains<T> extends AbstractQuery<T>{

    private final String value;
    private final Integer multiplier;
    private Function<T, String> getter;

    public Contains(String value) {
        super(1D);
        this.multiplier = 2;
        this.value = value;
        this.getter = null;
    }

    public Contains(String value, Function<T, String> getter) {
        super(1D);
        this.multiplier = 2;
        this.value = value;
        this.getter = getter;
    }

    public Contains(String value, Double weigth, Integer multiplier) {
        super(weigth);
        this.multiplier = multiplier;
        this.value = value;
        this.getter = null;
    }

    public Contains(String value, Function<T, String> getter, Double weight, Integer multiplier) {
        super(weight);
        this.multiplier = multiplier;
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
    public Double process(T object) {
        if(object != null && value != null) {
            if(getter == null) {
                if(object.toString().contains(value)) {
                    if(object.toString().equals(value)) {
                        return this.getWeight() * multiplier;
                    }
                    return this.getWeight();
                }
            } else if(getter != null) {
                String content = this.getter.apply(object);
                if(content != null) {
                    if(content.contains(value)); {
                        if(content.equals(value)) {
                            return this.getWeight() * multiplier;
                        }
                        return this.getWeight();
                    }
                }
            }
        }
        if(object == null && value == null) {
            return this.getWeight();
        }
        return 0D;
    }
}
