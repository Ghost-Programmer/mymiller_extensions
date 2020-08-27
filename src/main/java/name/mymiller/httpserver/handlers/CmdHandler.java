/**
 * Copyright 2018 MyMiller Consulting LLC.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
/**
 *
 */
package name.mymiller.httpserver.handlers;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import name.mymiller.httpserver.HttpConstants;
import name.mymiller.httpserver.HttpSystem;
import java.util.logging.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author jmiller
 *
 */
@HttpHandlerAnnotation(context = "/cmd")
public class CmdHandler extends AbstractContextHandler {
    /**
     * Error Text for unknown command
     */
    private static final String UNKNOWN_COMMAND = "Unkown Command";

    @Override
    public void doGet(final HttpExchange exchange, final String pathInfo, final Map<String, Object> parameters)
            throws IOException {
        switch (pathInfo) {
            case "/status":
                final String responseBody = "Listen Port: " + HttpSystem.getInstance().getAddress().getPort()
                        + "\nListen Address: " + HttpSystem.getInstance().getAddress().getHostString()
                        + "\nActive Thread Count: " + HttpSystem.getInstance().getActiveCount()
                        + "\nLargest Thread Pool Size: " + HttpSystem.getInstance().getLargestPoolSize()
                        + "\nMaximum Thread Pool Size: " + HttpSystem.getInstance().getMaximumPoolSize()
                        + "\nCurrent Task Count: " + HttpSystem.getInstance().getTaskCount()
                        + "\nCompleted Task Count: " + HttpSystem.getInstance().getCompletedTaskCount() + "\n";

                Logger.getLogger(this.getClass().getName()).info("Response: " + responseBody);

                this.sendResponse(exchange, HttpConstants.HTTP_OK_STATUS, responseBody);
                break;
            default:
                Logger.getLogger(this.getClass().getName()).severe("Unknown GET Command: " + pathInfo);

                this.sendResponse(exchange, HttpConstants.HTTP_NOT_ACCEPTABLE_STATUS, CmdHandler.UNKNOWN_COMMAND);
                break;
        }
    }

    @Override
    public void doPost(final HttpExchange exchange, final String pathInfo, final Map<String, Object> parameters)
            throws IOException {
        switch (pathInfo) {
            case "/stop":
                final String stopResponse = "Stopping Server on Port:"
                        + HttpSystem.getInstance().getAddress().getPort();
                Logger.getLogger(this.getClass().getName()).info("Response: " + stopResponse);
                // Set the response header status and length

                this.sendResponse(exchange, HttpConstants.HTTP_OK_STATUS, stopResponse);

                HttpSystem.getInstance().shutdown(HttpConstants.DELAY_TIME);
                break;
            case "/restart":
                final String restartResponse = "Restarting Server on Port:"
                        + HttpSystem.getInstance().getAddress().getPort();
                Logger.getLogger(this.getClass().getName()).info("Response: " + restartResponse);

                this.sendResponse(exchange, HttpConstants.HTTP_OK_STATUS, restartResponse);

                HttpSystem.getInstance().setRestart(true);
                break;
            default:
                Logger.getLogger(this.getClass().getName()).severe("Unknown POST Command: " + pathInfo);

                this.sendResponse(exchange, HttpConstants.HTTP_NOT_ACCEPTABLE_STATUS, CmdHandler.UNKNOWN_COMMAND);
                break;
        }

    }

    @Override
    public List<Filter> getFilters() {
        return null;
    }
}
