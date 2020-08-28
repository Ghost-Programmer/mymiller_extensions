/******************************************************************************
 Copyright 2018 MyMiller Consulting LLC.

 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License.  You may obtain a copy
 of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 License for the specific language governing permissions and limitations under
 the License.
 */
package name.mymiller.io;

import java.util.logging.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;

/**
 * Class that executes a process and captures the output, runs as a thread.
 *
 * @author jmiller
 */
public class AdvancedProcess extends Thread {
    /**
     * Internal list of the arguments.
     */
    private String[] args = null;

    /**
     * Captured Output from process
     */
    private String[] output = null;

    /**
     * @return the args
     */
    public synchronized String[] getArgs() {
        return this.args;
    }

    /**
     * @param args the args to set
     */
    public synchronized void setArgs(final String[] args) {
        this.args = args;
    }

    /**
     * @return Output from the process
     */
    public synchronized String[] getOutput() {
        return this.output;
    }

    /**
     * Sets the output from the process
     *
     * @param output String array containing the output
     */
    private synchronized void setOutput(final String[] output) {
        this.output = output;
    }

    @Override
    public void run() {
        try {
            if (this.args != null) {
                final StringBuilder processOutput = new StringBuilder();
                final Process process = Runtime.getRuntime().exec(this.args);

                try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String inputLine = null;
                    while ((inputLine = input.readLine()) != null) {
                        processOutput.append(inputLine);
                        processOutput.append('\n');
                    }

                    process.waitFor();
                }

                final String[] lines = processOutput.toString().split("\n");
                this.setOutput(lines);
            }
        } catch (final InterruptedException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Advanced Process Interrupted", e);
        } catch (final IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Advanced Process I/O Exception", e);
        }
    }
}
