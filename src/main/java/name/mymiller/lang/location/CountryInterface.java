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
package name.mymiller.lang.location;

import java.util.Optional;

/**
 * Interface for classes that provide Country identification.
 * https://countrycode.org/
 *
 * @author jmiller
 */
public interface CountryInterface {
    /**
     * @return the Enum for the Continent the location is on
     */
    ContinentEnum getContinent();

    /**
     * @return String with the Language name to display
     */
    Optional<String> getDisplayLanguage();

    /**
     * @return FIPS Country code
     */
    Optional<String> getFips();

    /**
     * @return the two letter digram representing this location, or null if none
     * assigned
     */
    Optional<String> getISO2();

    /**
     * @return the tree letter trigram representing this location, or null if none
     * assigned
     */
    Optional<String> getISO3();

    /**
     * @return ISO Numeric Country Code
     */
    Optional<Integer> getISONumeric();

    /**
     * @return Array of String with the Language Codes
     */
    Optional<String[]> getLanguageCodes();

    /**
     * @return String with the Country Name
     */
    String getName();

    /**
     * @return String with the International Dialing Prefix
     */
    Optional<String> getPhoneCode();

    /**
     * @return String with the Top Level Internat Domain.
     */
    Optional<String> getTopLevelInternetDomain();
}
