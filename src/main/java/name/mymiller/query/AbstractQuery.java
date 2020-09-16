package name.mymiller.query;

public abstract class AbstractQuery<T> implements QueryFilter<T> {
    private Double weight;

    public AbstractQuery(Double weight) {
        this.weight = weight;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
