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
