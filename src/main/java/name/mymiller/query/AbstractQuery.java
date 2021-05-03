package name.mymiller.query;

/**
 *
 * @param <T>
 */
public abstract class AbstractQuery<T> implements QueryFilter<T> {
    /**
     *
     */
    private final Double weight;

    /**
     *
     * @param weight
     */
    public AbstractQuery(Double weight) {
        this.weight = weight;
    }

    /**
     *
     * @return
     */
    public Double getWeight() {
        return weight;
    }
}
