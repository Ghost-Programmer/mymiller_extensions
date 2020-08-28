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
        System.out.print(String.format(format, args));
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
