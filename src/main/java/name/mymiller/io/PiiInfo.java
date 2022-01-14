package name.mymiller.io;

import java.util.function.BooleanSupplier;


/**
 * Class to use to protect Private Information. Takes a string and checks against a lambda to see
 * if the PII information should be displayed, otherwise display [PII INFORMATION] in its place.
 * Defaults to hide PII Information.
 */
public class PiiInfo {
    static Object syncObject = new Object();
    static String PII_LABEL = "[PII INFORMATION]";
    static BooleanSupplier booleanSupplier = () -> {return false;};

    /**
     * Static method to return a PiiInfo object set to display the correct text.
     * @param information String that is the PII information that might be displayed in certain conditions.
     * @return PiiInfo object to correctly display the information
     */
    static PiiInfo protect(String information) {
        if(booleanSupplier.getAsBoolean()) {
            return new PiiInfo(information);
        }
        return new PiiInfo(PiiInfo.PII_LABEL);
    }

    /**
     * Set the label to use for PII when hidden.
     * @param label String containing the new label to replace [PII INFORMATION]
     */
    public static void setPiiLabel(String label) {
        synchronized (syncObject) {
            PiiInfo.PII_LABEL = label;
        }
    }

    /**
     * Setup a BooleanSupplier that will check conditiions to determine if PII information maybe displayed.
     * Examples might to check if this is a Production environment and to hide all PII information and to
     * display in test, development environments.
     * @param booleanSupplier  returns TRUE to display the PII Information and FALSE to hide.
     */
    public static void setDecisionMaker(BooleanSupplier booleanSupplier) {
        synchronized (syncObject) {
            PiiInfo.booleanSupplier = booleanSupplier;
        }
    }

    /**
     * Local variable to contain the text to display, defaults to the PII Label.
     */
    private String display = PiiInfo.PII_LABEL;

    /**
     * Priviate Constructor to create the PiiINfo object
     * @param display Data to display.
     */
    private PiiInfo(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return this.display;
    }
}
