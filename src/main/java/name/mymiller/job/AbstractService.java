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
package name.mymiller.job;

import name.mymiller.log.LogManager;

/**
 * @author jmiller Basic service functions
 */
public abstract class AbstractService extends Job {

    /**
     * Boolean indicating if the Service should shutdown
     */
    private boolean shutdown = false;

    /**
     * Boolean indicating if the Service should restart after shutdown
     */
    private boolean restart = false;

    /**
     *
     * @return Indicating if the Service should restart have shutdown
     */
    public boolean isRestart() {
        return this.restart;
    }

    /**
     * Set whether the Service should Restart
     *
     * @param restart Boolean indicating if it should restart
     */
    public void setRestart(final boolean restart) {
        this.restart = restart;
    }

    /**
     *
     * @return Indicating if the Service should shutdown
     */
    public boolean isShutdown() {
        return this.shutdown;
    }

    /**
     * Method to set if the service is shutdown
     *
     * @param shutdown boolean indicating if the service is shutdown.
     */
    protected void setShutdown(boolean shutdown) {
        this.shutdown = shutdown;
    }

    /**
     *
     * @return Indicating if the service is up or shutdown
     */
    public boolean notShutdown() {
        return !this.isShutdown();
    }

    @Override
    protected void process() {
        LogManager.getLogger(AbstractService.class).info(this.getClass().getSimpleName() + " Service Running");
        this.setShutdown(false);
        this.service();
        this.setShutdown(true);
        LogManager.getLogger(AbstractService.class).info(this.getClass().getSimpleName() + " Service Exiting");
    }

    /**
     * Method that performs the processing for a service
     */
    abstract protected void service();

    /**
     * Method to shutdown the HTTP Server
     *
     * @param delay ms. to wait before shutting down.
     */
    public void shutdown(int delay) {
        LogManager.getLogger(AbstractService.class).info(this.getClass().getSimpleName() + " Service Shutdown called");
        this.stop(delay);
        this.shutdown = true;
    }

    /**
     * Start.
     */
    abstract public void start();

    /**
     * Force a stop of the server
     *
     * @param delay the maximum time in seconds to wait until exchanges have
     *              finished.
     */
    abstract protected void stop(final int delay);

}
