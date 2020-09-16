package name.mymiller.query;

import java.util.function.Function;

/**
 * Compares the toString() value of an object the value in the field.
 */
public class Equals<T,R> extends AbstractQuery<T>{

    private final T value;
    private Function<T, R> getter;

    public Equals(T value) {
        super(1D);
        if(value == null) {
            throw new NullPointerException("value may not be null");
        }
        this.value = value;
        this.getter = null;
    }

    public Equals(Function<T, R> getter,T value) {
        super(1D);
        this.value = value;
        this.getter = getter;
    }

    public Equals(T value, Double weight) {
        super(weight);
        if(value == null) {
            throw new NullPointerException("value may not be null");
        }
        this.value = value;
        this.getter = null;
    }

    public Equals(Function<T, R> getter, T value, Double weight) {
        super(weight);
        this.value = value;
        this.getter = getter;
    }

    @Override
    public Double process(T object) {
        if(getter == null) {
            if(value.equals(object)) {
                return this.getWeight();
            }
        } else if(value.equals(this.getter.apply(object))) {
            return this.getWeight();
        }

        return 0D;
    }
}
