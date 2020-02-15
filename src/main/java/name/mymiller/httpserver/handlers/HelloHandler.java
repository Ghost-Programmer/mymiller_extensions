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
import name.mymiller.httpserver.HttpSystem;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author jmiller Simple test Handler to verify HTTP Server is running and
 * processing.
 */
@HttpHandlerAnnotation(context = "/hello")
public class HelloHandler extends AbstractContextHandler {

    @Override
    public void doGet(final HttpExchange exchange, final String pathInfo, final Map<String, Object> parameters)
            throws IOException {
        this.sendResponse(exchange, HttpConstants.HTTP_OK_STATUS,
                "AWAKE Listening on Port:" + HttpSystem.getInstance().getAddress().getPort());
    }

    @Override
    public List<Filter> getFilters() {
        return null;
    }

}
