
package name.mymiller.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class used for interfacing with the user and providing input
 *
 * @author jmiller
 */
public class Console {

    /**
     * Provide a prompt to request input from a user.
     *
     * @param format Format of the prompt
     * @param args   accompanying arguments for the format
     * @return String with the user input
     * @throws IOException Error in reading the user input.
     */
    public static String readLine(String format, Object... args) throws IOException {
        if (System.console() != null) {
            return System.console().readLine(format, args);
        }
        System.out.printf(format, args);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

    /**
     * Provide a prompt to request a password from a user. If the System WebConsole
     * is not available password will be in the clear.
     *
     * @param format Format of the prompt
     * @param args   accompanying arguments for the format
     * @return String with the password
     * @throws IOException Error in reading the user input.
     */

    public static char[] readPassword(String format, Object... args) throws IOException {
        if (System.console() != null) {
            return System.console().readPassword(format, args);
        }
        return Console.readLine(format, args).toCharArray();
    }
}
