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
package name.mymiller.httpserver.handlers;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import name.mymiller.httpserver.HttpConstants;
import name.mymiller.httpserver.filters.ParameterFilter;
import java.util.logging.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * @author jmiller File handler for requesting files from the file system. Also
 * monitors for groovy files and executes groovy scripts to generate
 * output.
 */
@HttpHandlerAnnotation
public class FileHandler extends AbstractContextHandler {
    /**
     * Error text for file not found
     */
    private static final String FILE_NOT_FOUND = "File Not Found";
    private static String WWW_ROOT = "./wwwroot";

    /**
     * Default Constructor that loads the property from the filehandler.properties
     * file. Also instantiates the logging.
     */
    public FileHandler() {
        Logger.getLogger(this.getClass().getName()).info("WWWRoot: " + FileHandler.getWwwRoot());
    }

    /**
     * @return String containing tthe WWW Root directory.
     */
    public static String getWwwRoot() {
        return FileHandler.WWW_ROOT;
    }

    /**
     * Sets the WWW Root Directory
     *
     * @param wwwRoot String containing the WWW Root Directory.
     */
    public static void setWwwRoot(String wwwRoot) {
        FileHandler.WWW_ROOT = wwwRoot;
    }

    @Override
    public void doGet(final HttpExchange exchange, final String pathInfo, final Map<String, Object> parameters)
            throws IOException {
        boolean success = false;
        boolean found = false;
        InputStream inputStream = Class.class.getResourceAsStream(FileHandler.getWwwRoot() + "/" + pathInfo);
        final File file = new File(FileHandler.getWwwRoot() + File.separator + pathInfo);

        if (inputStream != null) {
            found = true;
            Logger.getLogger(this.getClass().getName()).info("Opening resource: " + pathInfo);
        } else if (file.exists()) {
            found = true;
            Logger.getLogger(this.getClass().getName()).info("Opening file: " + pathInfo);
            inputStream = new FileInputStream(file);
        }

        if (found) {
            try {
                this.sendResponse(exchange, HttpConstants.HTTP_OK_STATUS, inputStream);

                success = true;

            } catch (final IOException ie) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Failed to read file: " + pathInfo, ie);
            }
        }
        if (!success) {
            Logger.getLogger(this.getClass().getName()).info("File Not Found: " + pathInfo);
            this.sendResponse(exchange, HttpConstants.HTTP_NOT_FOUND_STATUS, FileHandler.FILE_NOT_FOUND);
        }

        if (inputStream != null) {
            inputStream.close();
        }
    }

    @Override
    public void doHead(final HttpExchange exchange, final String pathInfo, final Map<String, Object> parameters)
            throws IOException {
        boolean found = false;
        final InputStream inputStream = Class.class.getResourceAsStream(FileHandler.getWwwRoot() + "/" + pathInfo);
        final File file = new File(FileHandler.getWwwRoot() + File.separator + pathInfo);

        if (inputStream != null) {
            found = true;
            Logger.getLogger(this.getClass().getName()).info("Opening resource: " + pathInfo);
            this.sendResponse(exchange, HttpConstants.HTTP_OK_STATUS, inputStream.available());
        } else if (file.exists()) {
            found = true;
            Logger.getLogger(this.getClass().getName()).info("Opening file: " + pathInfo);
            this.sendResponse(exchange, HttpConstants.HTTP_OK_STATUS, file.length());
        }

        if (!found) {
            Logger.getLogger(this.getClass().getName()).info("File Not Found: " + pathInfo);
            this.sendResponse(exchange, HttpConstants.HTTP_NOT_FOUND_STATUS, FileHandler.FILE_NOT_FOUND);
        }
    }

    @Override
    public List<Filter> getFilters() {
        final List<Filter> filters = new ArrayList<>();
        filters.add(new ParameterFilter());
        return filters;
    }
}
