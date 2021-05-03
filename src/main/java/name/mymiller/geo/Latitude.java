package name.mymiller.geo;

import name.mymiller.lang.IllegalValueException;

import java.io.Serializable;

/**
 * Object for representing Latitude
 *
 * @author jmiller
 */
public class Latitude extends Coordinate implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5927609321794514712L;

    /**
     * Constructor for creating the Latitude from a decimal
     *
     * @param decimal Decimal Degree to set this latitude to.
     */
    public Latitude(final Double decimal) {
        this.setDecimal(decimal);
    }

    @Override
    protected int getDegreeLimit() {
        return 90;
    }

}
