package name.mymiller.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamPipe extends Thread {
    private InputStream inputStream = null;
    private OutputStream outputStream = null;
    private boolean process = false;

    /**
     * @param inputStream
     * @param outputStream
     */
    public StreamPipe(InputStream inputStream, OutputStream outputStream) {
        super();
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public boolean isProcess() {
        return this.process;
    }

    public void setProcess(boolean process) {
        this.process = process;
    }

    @Override
    public void run() {
        this.process = true;
        while (this.process) {
            try {
                this.inputStream.transferTo(this.outputStream);
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

}
