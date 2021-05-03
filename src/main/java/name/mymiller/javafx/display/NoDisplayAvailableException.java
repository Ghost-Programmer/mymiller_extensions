package name.mymiller.javafx.display;

/**
 * Exception thrown when the DisplayManager has no open displays available.
 *
 * @author jmiller
 */
public class NoDisplayAvailableException extends Exception {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 2814362903846338687L;

    /**
     * Exception indicating all Displays are in use.
     *
     * @param totalDisplay Total number of displays detected.
     */
    public NoDisplayAvailableException(Integer totalDisplay) {
        super("All Displays use. Total: " + totalDisplay);
    }
}
