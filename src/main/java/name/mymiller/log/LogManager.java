/*******************************************************************************
 * Copyright 2018 MyMiller Consulting LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package name.mymiller.log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * @author jmiller Log Manager for controlling Java Logging
 */
public class LogManager {
    /**
     * Indicate if Logging Setup has completed
     */
    private static boolean setup = false;
    private static String configURL = "";

    public static String getConfigURL() {
        return LogManager.configURL;
    }

    public static void setConfigURL(String configURL) {
        LogManager.configURL = configURL;
        LogManager.setup();
    }

    /**
     * Get the GLOBAL Logger for the system
     *
     * @return Logger to use for Logging.
     */
    static public Logger getLogger() {
        if (LogManager.setup == false) {
            LogManager.setup();
        }
        return LogManager.getLogger(Logger.GLOBAL_LOGGER_NAME);
    }

    /**
     * Retrieve the Logger associated with a specific class.
     *
     * @param name Class to retrieve logger
     * @return Logger for the requested class.
     */
    static public Logger getLogger(@SuppressWarnings("rawtypes") final Class name) {
        if (LogManager.setup == false) {
            LogManager.setup();
        }
        return LogManager.getLogger(name.getName());
    }

    /**
     * Get the Logger for the system
     *
     * @param name Resource Name for the Logger
     * @return Logger to use for logging.
     */
    static public Logger getLogger(final String name) {
        if (LogManager.setup == false) {
            LogManager.setup();
        }
        return Logger.getLogger(name);
    }

    /**
     * Load the name.mymiller.name.mymiller.log.properties
     * file
     */
    static public void setup() {
        final java.util.logging.LogManager logManager = java.util.logging.LogManager.getLogManager();

        try {

            try (InputStream is = LogManager.class.getClassLoader().getResourceAsStream("log.properties")) {
                if(is != null) {
                    logManager.readConfiguration(is);
                    LogManager.setup = true;
                }else {
                    // Programmatic configuration
                    System.setProperty("java.util.logging.SimpleFormatter.format",
                            "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL %4$-7s [%3$s] %5$s %6$s%n");

                    final ConsoleHandler consoleHandler = new ConsoleHandler();
                    consoleHandler.setLevel(Level.FINEST);
                    consoleHandler.setFormatter(new SimpleFormatter());

                    final Logger app = Logger.getLogger("app");
                    app.setLevel(Level.FINEST);
                    app.addHandler(consoleHandler);
                }
            }
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }
}
