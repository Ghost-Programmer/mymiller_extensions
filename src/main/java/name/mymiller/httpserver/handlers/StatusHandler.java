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
import name.mymiller.httpserver.filters.ParameterFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jmiller
 *
 */
public class StatusHandler extends AbstractContextHandler {

    @Override
    /**
     *
     */
    public void doDelete(final HttpExchange exchange, final String pathInfo, final Map<String, Object> parameters) {
    }

    @Override
    public void doGet(final HttpExchange exchange, final String pathInfo, final Map<String, Object> parameters) {
    }

    @Override
    public void doHead(final HttpExchange exchange, final String pathInfo, final Map<String, Object> parameters) {
    }

    @Override
    public void doPost(final HttpExchange exchange, final String pathInfo, final Map<String, Object> parameters) {
    }

    @Override
    public void doPut(final HttpExchange exchange, final String pathInfo, final Map<String, Object> parameters) {
    }

    @Override
    public List<Filter> getFilters() {
        final List<Filter> filters = new ArrayList<>();
        filters.add(new ParameterFilter());
        return filters;
    }

}
