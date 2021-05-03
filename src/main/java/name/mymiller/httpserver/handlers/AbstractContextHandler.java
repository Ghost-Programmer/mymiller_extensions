package name.mymiller.httpserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import name.mymiller.httpserver.HttpConstants;
import name.mymiller.utils.StringUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author jmiller Abstract class to build handlers for URI Requests coming into
 * the HTTPS erver
 */
public abstract class AbstractContextHandler implements ContextHandlerInterface {
	/**
	 * Method to handle Delete URI Requests
	 *
	 * @param exchange   HttpExchange containing the URI information
	 * @param pathInfo   URI Path specified
	 * @param parameters Parameters parsed from the URI Quests
	 */
	public void doDelete(final HttpExchange exchange, final String pathInfo, final Map<String, Object> parameters) {

	}

	/**
	 * Method to handle Get URI Requests
	 *
	 * @param exchange   HttpExchange containing the URI information
	 * @param pathInfo   URI Path specified
	 * @param parameters Parameters parsed from the URI Quests
	 * @throws IOException Error responding to request
	 */

	public void doGet(final HttpExchange exchange, final String pathInfo, final Map<String, Object> parameters)
			throws IOException {

	}

	/**
	 * Method to handle Head URI Requests
	 *
	 * @param exchange   HttpExchange containing the URI information
	 * @param pathInfo   URI Path specified
	 * @param parameters Parameters parsed from the URI Quests
	 * @throws IOException Error responding to request
	 */

	public void doHead(final HttpExchange exchange, final String pathInfo, final Map<String, Object> parameters)
			throws IOException {

	}

	/**
	 * Method to handle Post URI Requests
	 *
	 * @param exchange   HttpExchange containing the URI information
	 * @param pathInfo   URI Path specified
	 * @param parameters Parameters parsed from the URI Quests
	 * @throws IOException Error responding to request
	 */

	public void doPost(final HttpExchange exchange, final String pathInfo, final Map<String, Object> parameters)
			throws IOException {

	}

	/**
	 * Method to handle Put URI Requests
	 *
	 * @param exchange   HttpExchange containing the URI information
	 * @param pathInfo   URI Path specified
	 * @param parameters Parameters parsed from the URI Quests
	 */

	public void doPut(final HttpExchange exchange, final String pathInfo, final Map<String, Object> parameters) {

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.
	 * HttpExchange)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void handle(final HttpExchange exchange) throws IOException {
		final String context = exchange.getHttpContext().getPath();
		final String pathInfo = exchange.getRequestURI().getRawPath().substring(context.length());
		final String advPathInfo = new String(pathInfo);
		if (StringUtils.containsHtml(advPathInfo)) {
			this.sendBadRequest(exchange);
		} else {
			Map<String, Object> parameters = null;
			if (exchange.getAttribute("parameters") instanceof Map<?, ?>) {
				parameters = (Map<String, Object>) exchange.getAttribute("parameters");
			}

			if (this.isDelete(exchange)) {
				this.doDelete(exchange, pathInfo, parameters);
			} else if (this.isGet(exchange)) {
				this.doGet(exchange, pathInfo, parameters);
			} else if (this.isHead(exchange)) {
				this.doHead(exchange, pathInfo, parameters);
			} else if (this.isPost(exchange)) {
				this.doPost(exchange, pathInfo, parameters);
			} else if (this.isPut(exchange)) {
				this.doPut(exchange, pathInfo, parameters);
			} else {
				this.sendBadRequest(exchange);
			}
		}
	}

	/**
	 * @param exchange HttpExchange containing the information on this request
	 * @return True if this is a Delete URI Request
	 */
	private boolean isDelete(final HttpExchange exchange) {
		return HttpConstants.DELETE_REQUEST.equalsIgnoreCase(exchange.getRequestMethod());
	}

	/**
	 * @param exchange HttpExchange containing the information on this request
	 * @return True if this is a Get URI Request
	 */

	private boolean isGet(final HttpExchange exchange) {
		return HttpConstants.GET_REQUEST.equalsIgnoreCase(exchange.getRequestMethod());
	}

	/**
	 * @param exchange HttpExchange containing the information on this request
	 * @return True if this is a Head URI Request
	 */

	private boolean isHead(final HttpExchange exchange) {
		return HttpConstants.HEAD_REQUEST.equalsIgnoreCase(exchange.getRequestMethod());
	}

	/**
	 * @param exchange HttpExchange containing the information on this request
	 * @return True if this is a Post URI Request
	 */

	private boolean isPost(final HttpExchange exchange) {
		return HttpConstants.POST_REQUEST.equalsIgnoreCase(exchange.getRequestMethod());
	}

	/**
	 * @param exchange HttpExchange containing the information on this request
	 * @return True if this is a Put URI Request
	 */

	private boolean isPut(final HttpExchange exchange) {
		return HttpConstants.PUT_REQUEST.equalsIgnoreCase(exchange.getRequestMethod());
	}

	/**
	 * Send an HTTP Error for Bad Request
	 *
	 * @param exchange HttpExchange containing the information on this request
	 * @throws IOException Error responding to request
	 */
	private void sendBadRequest(final HttpExchange exchange) throws IOException {
		Logger.getLogger(this.getClass().getName()).info("HTML found in PathInfo, rejecting");
		exchange.sendResponseHeaders(HttpConstants.HTTP_BAD_REQUEST_STATUS, 0);
		// Write the response string
		final OutputStream os = exchange.getResponseBody();
		os.write("Bad Request Status".getBytes());
		os.close();
	}

	/**
	 * Method to send the HTTP Response
	 *
	 * @param exchange     HttpExchange to send the response
	 * @param responseCode Response code, one of HttpConstants
	 * @param responseBody byte[] containing the response to send
	 * @throws IOException Error in sending response.
	 */
	protected void sendResponse(final HttpExchange exchange, final int responseCode, final byte[] responseBody)
			throws IOException {
		this.sendResponse(exchange, responseCode, responseBody.length);

		if (responseBody.length > 0) {
			final OutputStream os = exchange.getResponseBody();
			os.write(responseBody);
			os.close();
		}
	}

	/**
	 * Method to send the HTTP Response
	 *
	 * @param exchange     HttpExchange to send the response
	 * @param responseCode Response code, one of HttpConstants
	 * @param inputStream  inputStream containing the response to send
	 * @throws IOException Error in sending response.
	 */
	protected void sendResponse(final HttpExchange exchange, final int responseCode, final InputStream inputStream)
			throws IOException {
		final byte[] bytesToSend = new byte[inputStream.available()];

		final BufferedInputStream bis = new BufferedInputStream(inputStream);
		bis.read(bytesToSend, 0, bytesToSend.length);
		bis.close();

		this.sendResponse(exchange, responseCode, bytesToSend);
	}

	/**
	 * Method to send the HTTP Response Header with the size of the response,
	 *
	 * @param exchange     HttpExchange to send the response
	 * @param responseCode Response code, one of HttpConstants
	 * @param responseSize Int indicating the size of the response
	 * @throws IOException Error in sending response.
	 */
	protected void sendResponse(final HttpExchange exchange, final int responseCode, final long responseSize)
			throws IOException {
		exchange.sendResponseHeaders(responseCode, responseSize);
	}

	/**
	 * Method to send the HTTP Response
	 *
	 * @param exchange     HttpExchange to send the response
	 * @param responseCode Response code, one of HttpConstants
	 * @param responseBody String containing the response to send
	 * @throws IOException Error in sending response.
	 */
	protected void sendResponse(final HttpExchange exchange, final int responseCode, final String responseBody)
			throws IOException {
		if (responseBody != null) {
			this.sendResponse(exchange, responseCode, responseBody.getBytes());
		} else {
			this.sendResponse(exchange, responseCode, new byte[0]);
		}
	}
}
