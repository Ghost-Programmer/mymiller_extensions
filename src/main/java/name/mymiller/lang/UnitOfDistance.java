/******************************************************************************
 Copyright 2018 MyMiller Consulting LLC.

 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License.  You may obtain a copy
 of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 License for the specific language governing permissions and limitations under
 the License.
 */
package name.mymiller.lang;

import java.io.Serializable;

/**
 * @author jmiller Defined UnitOfMeasures
 */

public enum UnitOfDistance implements Serializable {
    // @formatter:off
    /**
     * Standard U.S. Miles
     */
    Miles("Miles", 1609.34, 0.000621371),
    /**
     * Feet
     */
    Feet("Feet", 3.28084, 0.3048),
    /**
     * Inches
     */
    Inches("Inches", 39.3701, 0.0254),
    /**
     * Nautical Miles as defined at the equator
     */
    Nautical_Miles("Nautical Miles", 1852.0, 0.000539957),
    /**
     * Metric Based
     */
    Kilometers("Kilometers", 1000.0, 0.001),
    /**
     * Metric Based
     */
    Meters("Meters", 1.0, 1.0),
    /**
     * Metric Based
     */
    Centimeters("Centimeters", 100.0, 0.01),
    /**
     * Metric Based
     */
    Millimeters("Millimeters", 1000.0, 0.001);
    // @formatter:on

    /**
     * Field to hold the conversion rate of unit to meters
     */
    private double conversionRateToMeters = 0.0;
    /**
     * Field to hold the conversion rate from meters to unit
     */
    private double conversionRateFromMeters = 0.0;
    /**
     * Label to use with unit of measure
     */
    private String label = null;

    /**
     * Constructor for this enum to set values.
     *
     * @param label                    Label to use with the unit of measure
     * @param conversionRateFromMeters Field to hold the conversion rate from meters
     *                                 to unit
     * @param conversionRateToMeters   Field to hold the conversion rate of unit to
     *                                 meters
     */
    UnitOfDistance(final String label, final Double conversionRateFromMeters, final double conversionRateToMeters) {
        this.label = label;
        this.conversionRateToMeters = conversionRateToMeters;
        this.conversionRateFromMeters = conversionRateFromMeters;
    }

    /**
     * Convert a measurement from one type to another
     *
     * @param convertTo   UnitOfDistance to convert measurement to.
     * @param convertFrom UnitOfDistance measurement is in.
     * @param distance    Distance to convert
     * @return Double indicating the distance in the new UnitOfDistance
     */
    public static double convert(UnitOfDistance convertTo, UnitOfDistance convertFrom, double distance) {
        return convertTo.getConversionRateFromMeters() * (distance * convertFrom.getConversionRateToMeters());
    }

    /**
     * @return Conversion Rate To Meters
     */
    public double getConversionRateFromMeters() {
        return this.conversionRateFromMeters;
    }

    /**
     * @return Conversion Rate from Meters
     */
    public double getConversionRateToMeters() {
        return this.conversionRateToMeters;
    }

    /**
     * @return Name given for this unit of measure.
     */
    public String getLabel() {
        return this.label;
    }
}
