package name.mymiller.utils;

/**
 * Determine what operating system the VM is executing on.
 *
 * @author jmiller
 */
public class OperatingSystemCheck {
    /**
     * os.name propery value.
     */
    private static final String OS = System.getProperty("os.name").toLowerCase();

    /**
     * @return String indicating the OS type
     */
    public static String getOS() {
        if (OperatingSystemCheck.isWindows()) {
            return "Windows";
        } else if (OperatingSystemCheck.isOSX()) {
            return "OSX";
        } else if (OperatingSystemCheck.isUnix()) {
            return "Unix";
        } else if (OperatingSystemCheck.isSolaris()) {
            return "Solaris";
        } else {
            return "UNKNOWN";
        }
    }

    /**
     * @return True if the OS is OSX
     */
    public static boolean isOSX() {
        return (OperatingSystemCheck.OS.contains("mac"));
    }

    /**
     * @return True if the OS is Solaris
     */
    public static boolean isSolaris() {
        return (OperatingSystemCheck.OS.contains("sunos"));
    }

    /**
     * @return True if the OS ia Unix, AIX, or Linux derivative
     */
    public static boolean isUnix() {
        return ((OperatingSystemCheck.OS.contains("nix")) || (OperatingSystemCheck.OS.contains("nux"))
                || (OperatingSystemCheck.OS.indexOf("aix") > 0));
    }

    /**
     * @return True if the OS is Windows.
     */
    public static boolean isWindows() {
        return (OperatingSystemCheck.OS.contains("win"));
    }
}
