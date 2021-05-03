package name.mymiller.lang.validation;

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
    public static boolean isEmailAddres(final String emailAddress) {
        return EmailAddress.emailPattern.matcher(emailAddress).matches();
    }
}
