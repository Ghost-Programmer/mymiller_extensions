package name.mymiller.query;

import java.util.function.Function;

/**
 * Compares the toString() value of an object the value in the field.
 */
public class Equals<T,R> implements QueryFilter<T>{

    private final T value;
    private Function<T, R> getter;

    public Equals(T value) {
        if(value == null) {
            throw new NullPointerException("value may not be null");
        }
        this.value = value;
        this.getter = null;
    }

    public Equals(T value, Function<T,R> getter) {
        this.value = value;
        this.getter = getter;
    }

    @Override
    public Boolean process(T object) {
        if(getter == null) {
            return value.equals(object);
        }

        return value.equals(this.getter.apply(object));
    }
}
