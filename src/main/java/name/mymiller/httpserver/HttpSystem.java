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
package name.mymiller.httpserver;

import com.sun.net.httpserver.HttpServer;
import name.mymiller.httpserver.handlers.ContextHandlerInterface;
import name.mymiller.httpserver.handlers.HttpHandlerAnnotation;
import name.mymiller.job.AbstractService;
import name.mymiller.job.JobManager;
import name.mymiller.lang.singleton.SingletonInterface;
import java.util.logging.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * @author jmiller HTTP System to start an HTTP Server and load handlers
 */

public class HttpSystem extends AbstractService implements SingletonInterface<HttpSystem> {
    /**
     * Thread Pool size for handling requests
     */
    private static int POOL_SIZE = 10;
    /**
     * Max Thread Pool size
     */
    private static int MAX_POOL_SIZE = 25;
    /**
     * Thread Keep Alive Time
     */
    private static int KEEP_ALIVE_TIME = 30;

    /**
     * Global Instance for the HTTP System
     */
    private static HttpSystem globalInstance = null;

    /**
     * Port to list on
     */
    private static int LISTEN_PORT = 8080;

    /**
     * Backlog for HTTP System
     */
    private static int BACK_LOG = 10;
    /**
     * HTTP Server reference
     */
    private HttpServer httpServer = null;
    /**
     * Queue to hold incoming requests
     */
    private ArrayBlockingQueue<Runnable> queue = null;
    /**
     * Thread Pool Executor for the HTTP Serever
     */
    private ThreadPoolExecutor threadPool = null;

    /**
     * Instantiates a new simple http server.
     */
    private HttpSystem() {
    }

    public static int getBackLog() {
        return HttpSystem.BACK_LOG;
    }

    public static void setBackLog(int backLog) {
        HttpSystem.BACK_LOG = backLog;
    }

    /**
     * @return Global Instance of the HTTPS System
     */
    public static HttpSystem getInstance() {
        if (HttpSystem.globalInstance == null) {
            HttpSystem.globalInstance = new HttpSystem();
        }
        return HttpSystem.globalInstance;
    }

    public static int getListenPort() {
        return HttpSystem.LISTEN_PORT;
    }

    public static void setListenPort(int listenPort) {
        HttpSystem.LISTEN_PORT = listenPort;
    }

    public static int getMaxPoolSize() {
        return HttpSystem.MAX_POOL_SIZE;
    }

    public static void setMaxPoolSize(int maxPoolSize) {
        HttpSystem.MAX_POOL_SIZE = maxPoolSize;
    }

    /**
     * @return Active Thread Count
     */
    public int getActiveCount() {
        return this.threadPool.getActiveCount();
    }

    /**
     * @return InetSocketAddress of the HTTP Server
     */
    public InetSocketAddress getAddress() {
        return this.httpServer.getAddress();
    }

    /**
     * @return Count of Completed Tasks by the Thread Pool
     */
    public long getCompletedTaskCount() {
        return this.threadPool.getCompletedTaskCount();
    }

    /**
     * @return Maximum size of the Thread Pool has reached
     */
    public int getLargestPoolSize() {
        return this.threadPool.getLargestPoolSize();
    }

    /**
     * @return Maximum size the thread pool can reach
     */
    public int getMaximumPoolSize() {
        return this.threadPool.getMaximumPoolSize();
    }

    /**
     * @return Current size of the thread pool
     */
    public int getPoolSize() {
        return this.threadPool.getPoolSize();
    }

    /**
     * @return Current Task Count for the Thread Pool
     */
    public long getTaskCount() {
        return this.threadPool.getTaskCount();
    }

    @Override
    protected void service() {
        final HttpSystem system = HttpSystem.getInstance();

        synchronized (system) {
            while (!system.isShutdown()) {
                try {
                    system.wait(1000);
                } catch (final InterruptedException e) {
                    Logger.getLogger(HttpSystem.class.getName()).info("Http System Thread Interupted:" + e.getMessage());
                }
                if (system.isRestart()) {
                    Logger.getLogger(HttpSystem.class.getName()).info("Restarting HTTP Server System");
                    system.stop(HttpConstants.DELAY_TIME);
                    system.start();
                    system.setRestart(false);
                }
            }
        }
    }

    /**
     * Start.
     */
    @Override
    public void start() {
        this.start(null);
    }

    /**
     * Start method accpeing handlers to use for this HTTP Server.
     *
     * @param handlers
     */
    public void start(ContextHandlerInterface[] handlers) {
        try {
            Logger.getLogger(HttpSystem.class.getName()).info("Starting HTTP Server System");

            if (this.threadPool != null) {
                this.threadPool.shutdown();
            }
            if (this.queue != null) {
                this.queue.clear();
            }
            // Create a default executor
            this.queue = new ArrayBlockingQueue<>(HttpSystem.MAX_POOL_SIZE);
            this.threadPool = new ThreadPoolExecutor(HttpSystem.POOL_SIZE, HttpSystem.MAX_POOL_SIZE,
                    HttpSystem.KEEP_ALIVE_TIME, TimeUnit.SECONDS, this.queue);

            // Create HttpServer which is listening on the given port
            this.httpServer = HttpServer.create(new InetSocketAddress(HttpSystem.getListenPort()),
                    HttpSystem.getBackLog());
            this.httpServer.setExecutor(this.threadPool);

            for (final ContextHandlerInterface handler : handlers) {
                Logger.getLogger(HttpSystem.class.getName())
                        .info("Loading Handler: " + handler.getClass().getCanonicalName());

                final String context = handler.getClass().getAnnotation(HttpHandlerAnnotation.class).context();
                Logger.getLogger(HttpSystem.class.getName()).info("Adding Handler: " + handler + " Context: " + context);

                this.httpServer.createContext(context, handler);
            }
            Logger.getLogger(HttpSystem.class.getName())
                    .info("Server is started and listening on port " + this.httpServer.getAddress().getPort());
            this.httpServer.start();

            JobManager.getInstance().createService("HTTP System", HttpSystem.getInstance());
        } catch (final IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "IOException Error in starting HTTPSystem", e);
        } catch (final SecurityException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "SecurityException", e);
        } catch (final IllegalArgumentException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "IllegalArgumentException", e);
        }
    }

    /**
     * Force a stop of the server
     *
     * @param delay the maximum time in seconds to wait until exchanges have
     *              finished.
     */
    @Override
    protected void stop(final int delay) {
        this.httpServer.stop(delay);
        Logger.getLogger(HttpSystem.class.getName()).info("Server is stopped.");
    }
}
