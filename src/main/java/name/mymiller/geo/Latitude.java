/*******************************************************************************
 * Copyright 2018 MyMiller Consulting LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
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
     * @throws IllegalValueException Value out of range
     */
    public Latitude(final Double decimal) throws IllegalValueException {
        this.setDecimal(decimal);
    }

    @Override
    protected int getDegreeLimit() {
        return 90;
    }

}
