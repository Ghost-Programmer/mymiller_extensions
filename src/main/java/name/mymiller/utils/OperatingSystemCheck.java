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
        return (OperatingSystemCheck.OS.indexOf("mac") >= 0);
    }

    /**
     * @return True if the OS is Solaris
     */
    public static boolean isSolaris() {
        return (OperatingSystemCheck.OS.indexOf("sunos") >= 0);
    }

    /**
     * @return True if the OS ia Unix, AIX, or Linux derivative
     */
    public static boolean isUnix() {
        return ((OperatingSystemCheck.OS.indexOf("nix") >= 0) || (OperatingSystemCheck.OS.indexOf("nux") >= 0)
                || (OperatingSystemCheck.OS.indexOf("aix") > 0));
    }

    /**
     * @return True if the OS is Windows.
     */
    public static boolean isWindows() {
        return (OperatingSystemCheck.OS.indexOf("win") >= 0);
    }
}
