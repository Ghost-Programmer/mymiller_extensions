package name.mymiller.geo;

import java.io.Serializable;

/**
 * Abstract class that will form the basis of Latitude and Longitude. Basic
 * functionality is the same between the two types, just the range of values
 * differentiate.
 *
 * @author jmiller
 */
abstract public class Coordinate implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 5443490914016651058L;
    /**
     * The Degree of the coordinate
     */
    private Double decimal = 0D;

    /**
     * returns the entire coordinate as a decimal of the degree
     *
     * @return Double representing the decimal value of the degree, minute and
     * second.
     */
    public Double getDecimal() {
        return this.decimal;
    }

    /**
     * Converts a decimal degree into Degree, Minute and Second
     *
     * @param decimal Decimal value of the degree
     */
    public void setDecimal(final Double decimal) {
        this.decimal = decimal;
    }

    /**
     * @return the Maximum value of the Degree.
     */
    abstract protected int getDegreeLimit();

    @Override
    public String toString() {
        return "Coordinate [decimal=" + this.getDecimal() + "]";
    }
}
