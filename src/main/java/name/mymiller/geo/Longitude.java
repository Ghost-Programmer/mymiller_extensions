package name.mymiller.geo;

import name.mymiller.lang.IllegalValueException;

import java.io.Serializable;

/**
 * Object for representing Longitude
 *
 * @author jmiller
 */
public class Longitude extends Coordinate implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2165340852811132537L;

    /**
     * Constructor for creating the Longitude from a decimal
     *
     * @param decimal Decimal Degree to set this latitude to.
     */
    public Longitude(final Double decimal) {
        this.setDecimal(decimal);
    }

    @Override
    protected int getDegreeLimit() {
        return 180;
    }
}
