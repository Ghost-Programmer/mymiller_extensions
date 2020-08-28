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
package name.mymiller.lang.validation;

import name.mymiller.lang.AdvancedString;

import java.util.regex.Pattern;

/**
 * Class used to validate a phone number.
 *
 * @author jmiller
 */
public class PhoneNumber {
    /**
     * Regex for matching a phone numbers
     */
    private final static String PHONE_NUMBER_PATTERN = "^([\\+(]?(\\d){2,}[)]?[- \\.]?(\\d){2,}[- \\.]?(\\d){2,}[- \\.]?(\\d){2,}[- \\.]?(\\d){2,})|([\\+(]?(\\d){2,}[)]?[- \\.]?(\\d){2,}[- \\.]?(\\d){2,}[- \\.]?(\\d){2,})|([\\+(]?(\\d){2,}[)]?[- \\.]?(\\d){2,}[- \\.]?(\\d){2,})$";
    /**
     * Pattern for phone number validation
     */
    private final static Pattern phonePattern = Pattern.compile(PhoneNumber.PHONE_NUMBER_PATTERN);

    /**
     * Method used to validate a phone number.
     *
     * @param phoneNumber AdvancedString containing the phone number to validate
     * @return true if the string is a phone number
     */
    public static boolean isPhoneNumber(final AdvancedString phoneNumber) {
        return PhoneNumber.phonePattern.matcher(phoneNumber.toString()).matches();
    }

}
