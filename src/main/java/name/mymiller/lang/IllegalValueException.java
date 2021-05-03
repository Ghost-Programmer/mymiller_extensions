package name.mymiller.lang;

/**
 * Exception throw when a value is seen as Illegal.
 *
 * @author jmiller
 */
public class IllegalValueException extends Exception {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = -5981624734067928969L;

    /**
     * Constructs a new Exception for an illegal value with null as it's detail
     * message
     */
    public IllegalValueException() {
    }

    /**
     * Constructs a new exception for an illegal value with a specificed detail
     * message
     *
     * @param message The detail message. The detail message is saved for later
     *                retrieval by the Throwable.getMessage() method.
     */
    public IllegalValueException(final String message) {
        super(message);
    }

    /**
     * @param message The detail message. The detail message is saved for later
     *                retrieval by the Throwable.getMessage() method.
     * @param cause   The cause (which is saved for later retrieval by the
     *                Throwable.getCause() method). (A null value is permitted, and
     *                indicates that the cause is nonexistent or unknown.)
     */
    public IllegalValueException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message            The detail message. The detail message is saved for
     *                           later retrieval by the Throwable.getMessage()
     *                           method.
     * @param cause              The cause (which is saved for later retrieval by
     *                           the Throwable.getCause() method). (A null value is
     *                           permitted, and indicates that the cause is
     *                           nonexistent or unknown.)
     * @param enableSuppression  Whether or not suppression is enabled or disabled
     * @param writableStackTrace Whether or not the stack trace should be writable
     */
    public IllegalValueException(final String message, final Throwable cause, final boolean enableSuppression,
                                 final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @param cause The cause (which is saved for later retrieval by the
     *              Throwable.getCause() method). (A null value is permitted, and
     *              indicates that the cause is nonexistent or unknown.)
     */
    public IllegalValueException(final Throwable cause) {
        super(cause);
    }

}
