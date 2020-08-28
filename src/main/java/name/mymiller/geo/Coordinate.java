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
