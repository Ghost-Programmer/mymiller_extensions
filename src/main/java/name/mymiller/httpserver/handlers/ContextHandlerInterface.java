
package name.mymiller.httpserver.handlers;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpHandler;

import java.util.List;

/**
 * @author jmiller Interface to create ContextHandler for the HTTP Server
 */
public interface ContextHandlerInterface extends HttpHandler {
    /**
     * @return List of Filter's this handler needs.
     */
    List<Filter> getFilters();
}
