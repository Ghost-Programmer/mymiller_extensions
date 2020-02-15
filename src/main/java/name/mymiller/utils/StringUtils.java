package name.mymiller.utils;

import java.math.BigDecimal;

public class StringUtils extends ObjectUtils {
    /**
     * ObjectUtil a String against another string checking for NULL
     *
     * @param object Object to compare
     * @param value  Value to to check
     * @return True if equals()
     */
    static public boolean compare(String object, String value) {
        if ((object == null) && (value == null)) {
            return true;
        }
        if (object != null) {
            return object.equals(value);
        }

        return false;
    }

    /**
     * Convert a BigDecimal to String if not null
     *
     * @param big       BigDecimal to convert
     * @param nullValue String to output if BigDecimal is null
     * @return String value of big
     */
    static public String StringNotNull(BigDecimal big, String nullValue) {
        if (big == null) {
            return nullValue;
        }
        return big.toPlainString();
    }

    /**
     * Return a String if not null
     *
     * @param value     Long.toString() to return
     * @param nullValue String to return if null
     * @return String to use for value.
     */
    static public String StringNotNull(Long value, String nullValue) {
        if (value == null) {
            return nullValue;
        }
        return value.toString();
    }

    /**
     * Return a String if not null
     *
     * @param string    String to return
     * @param nullValue String to return if null
     * @return String to use for value.
     */
    static public String StringNotNull(String string, String nullValue) {
        if (string == null) {
            return nullValue;
        }
        return string;
    }
}
