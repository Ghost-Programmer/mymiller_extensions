package name.mymiller.lang.validation;

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
    public static boolean isPhoneNumber(final String phoneNumber) {
        return PhoneNumber.phonePattern.matcher(phoneNumber).matches();
    }

}
