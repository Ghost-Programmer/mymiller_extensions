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
package name.mymiller.httpserver.filters;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import name.mymiller.httpserver.HttpConstants;
import name.mymiller.lang.AdvancedString;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jmiller Parses the requested URI for parameters and places them into
 * a map
 */
public class ParameterFilter extends Filter {

	/**
	 * Retrieve the Parameters Attribute from the Exchange
	 *
	 * @param exchange HTTPExchange containing the Parameters Attribute
	 * @return Map&lt;String, Object&gt; containing the parameters
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getParametersFromExchange(final HttpExchange exchange) {
		return ((Map<String, Object>) exchange.getAttribute("parameters"));
	}

	/**
	 * Method to add a parameter to the HttpExchange.
	 *
	 * @param exchange HttpExchange to add the parameter to.
	 * @param key      Key for the parameter
	 * @param value    Value of the parameter
	 */
	private void addParameter(final HttpExchange exchange, final String key, final String value) {
		final Map<String, Object> parameters = ParameterFilter.getParametersFromExchange(exchange);

		final AdvancedString advKey = new AdvancedString(key);
		final AdvancedString advValue = new AdvancedString(value);

		if (!advKey.containsHtml() && !advValue.containsHtml()) {
			if (parameters.containsKey(key)) {
				final Object obj = parameters.get(key);
				if (obj instanceof List<?>) {
					if (obj instanceof List) {
						@SuppressWarnings("unchecked") final List<String> values = (List<String>) obj;
						values.add(value);
					}
				} else if (obj instanceof String) {
					final List<String> values = new ArrayList<>();
					values.add((String) obj);
					values.add(value);
					parameters.put(key, values);
				}
			} else {
				parameters.put(key, advValue);
			}
		}
	}

	/**
	 * Create a Parameters attribute on the HTTP Exchange.
	 *
	 * @param exchange HttpExchange to contain the attribute
	 * @return copy of the attribute.
	 */
	private Map<String, Object> createParametersOnExchange(final HttpExchange exchange) {
		exchange.setAttribute("parameters", new HashMap<String, Object>());
		return ParameterFilter.getParametersFromExchange(exchange);
	}

	/**
	 * Decode the Key Parameter from the URL
	 *
	 * @param param Array of parameters from the Key/Value Pair
	 * @return Key
	 * @throws UnsupportedEncodingException Parameter 0 not in standard encoding.
	 */
	private String decodeKey(final String[] param) throws UnsupportedEncodingException {
		String key = null;
		if (param.length > 0) {
			key = URLDecoder.decode(param[0], System.getProperty("file.encoding"));
		}
		return key;
	}

	/**
	 * Decode the Value Parameter from the URL
	 *
	 * @param param Array of parameters from the Key/Value Pair
	 * @return Value
	 * @throws UnsupportedEncodingException Parameter 1 not in standard encoding.
	 */
	private String decodeValue(final String[] param) throws UnsupportedEncodingException {
		String value = null;
		if (param.length > 1) {
			value = URLDecoder.decode(param[1], System.getProperty("file.encoding"));
		}
		return value;
	}

	@Override
	public String description() {
		return "Parses the requested URI for parameters";
	}

	@Override
	public void doFilter(final HttpExchange exchange, final Chain chain) throws IOException {
		this.createParametersOnExchange(exchange);
		this.parseGetParameters(exchange);
		this.parsePostParameters(exchange);
		chain.doFilter(exchange);
	}

	/**
	 * parse the Get Parameters and add them to the HttpExchange as attribute
	 * parameters
	 *
	 * @param exchange HTTPExchange for this URI request
	 * @throws UnsupportedEncodingException Unable to decode
	 */
	private void parseGetParameters(final HttpExchange exchange) throws UnsupportedEncodingException {
		final URI requestedUri = exchange.getRequestURI();
		final String query = requestedUri.getRawQuery();
		this.parseQuery(query, exchange);
	}

	/**
	 * Parse the Post Parameter and add them to the HttpExcahnge as attribute
	 * parameters
	 *
	 * @param exchange HTTPExchange for this URI request
	 * @throws IOException Unable to parse request body
	 */
	private void parsePostParameters(final HttpExchange exchange) throws IOException {

		if ("post".equalsIgnoreCase(exchange.getRequestMethod())) {
			final InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
			final BufferedReader br = new BufferedReader(isr);
			final String query = br.readLine();
			this.parseQuery(query, exchange);
		}
	}

	/**
	 * Splits out the parameters from the URI
	 *
	 * @param query    URI Query for this request
	 * @param exchange HttpExchange containing the information on this request
	 * @throws UnsupportedEncodingException Unable to decode
	 */
	private void parseQuery(final String query, final HttpExchange exchange) throws UnsupportedEncodingException {
		if (query != null) {
			final String[] pairs = query.split("[" + HttpConstants.AND_DELIMITER + "]");

			for (final String pair : pairs) {
				this.parseQueryPair(exchange, pair);
			}
		}
	}

	/**
	 * Takes a Key / Value Pair from the Query for parsing.
	 *
	 * @param exchange HttpExchange containing this query
	 * @param pair     String for the Key/Value Pair
	 * @throws UnsupportedEncodingException Pair encoded incorrectly.
	 */
	private void parseQueryPair(final HttpExchange exchange, final String pair) throws UnsupportedEncodingException {
		final String[] param = pair.split("[" + HttpConstants.EQUAL_DELIMITER + "]");

		final String key = this.decodeKey(param);
		final String value = this.decodeValue(param);
		this.addParameter(exchange, key, value);
	}
}
