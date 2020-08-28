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
 * Class used to validate an email address
 *
 * @author jmiller
 */
public class EmailAddress {
    /**
     * Regex for matching Email Addresses
     */
    private final static String EMAIL_PATTERN = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$";

    /**
     * Pattern for email validation
     */
    private final static Pattern emailPattern = Pattern.compile(EmailAddress.EMAIL_PATTERN);

    /**
     * Method to validate an email address.
     *
     * @param emailAddress AdvancedString containing the email address that needs to
     *                     be validated
     * @return true if the string is an email address
     */
    public static boolean isEmailAddres(final AdvancedString emailAddress) {
        return EmailAddress.emailPattern.matcher(emailAddress.toString()).matches();
    }
}
