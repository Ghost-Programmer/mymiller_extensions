package name.mymiller.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that executes a process and captures the output, runs as a thread.
 *
 * @author jmiller
 */
public class Process extends Thread {
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
                final java.lang.Process process = Runtime.getRuntime().exec(this.args);

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
