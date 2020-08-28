/*
  Copyright 2018 MyMiller Consulting LLC.
  <p>
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License.  You may obtain a copy
  of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
  License for the specific language governing permissions and limitations under
  the License.
 */
/*

 */
package name.mymiller.io;

import java.io.*;
import java.nio.CharBuffer;

/**
 * File Reader for reading an active file that is being written to.
 *
 * @author jmiller
 */
public class TailFileReader extends FileReader {
    /**
     * Default wait time in ms.
     */
    private int waitTime = 500;

    /**
     * @param file File to reader
     * @throws FileNotFoundException Unable to find file.
     */
    public TailFileReader(final File file) throws FileNotFoundException {
        super(file);
    }

    /**
     * @param file File to read.
     * @param wait Time to wait.
     * @throws FileNotFoundException File not found
     */
    public TailFileReader(final File file, final int wait) throws FileNotFoundException {
        super(file);
        this.waitTime = wait;
    }

    /**
     * @param fd File Descriptor of the file to read.
     */
    public TailFileReader(final FileDescriptor fd) {
        super(fd);
    }

    /**
     * @param fd   File Descriptor of the file to read.
     * @param wait Time to wait.
     */
    public TailFileReader(final FileDescriptor fd, final int wait) {
        super(fd);
        this.waitTime = wait;
    }

    /**
     * @param filename String containing the filename of file to read
     * @throws FileNotFoundException File not Found
     */
    public TailFileReader(final String filename) throws FileNotFoundException {
        super(filename);
    }

    /**
     * @param filename String containing the file of file to read
     * @param wait     Time ot wait
     * @throws FileNotFoundException File not Found
     */
    public TailFileReader(final String filename, final int wait) throws FileNotFoundException {
        super(filename);
        this.waitTime = wait;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.io.InputStreamReader#read()
     */
    @Override
    public int read() throws IOException {
        int singleChar = 0;

        while ((singleChar = super.read()) == -1) {
            try {
                Thread.sleep(this.waitTime);
            } catch (final InterruptedException e) {

            }
        }
        return singleChar;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.io.Reader#read(char[])
     */
    @Override
    public int read(final char[] cbuf) throws IOException {
        int charsRead = 0;
        while ((charsRead = super.read(cbuf)) == -1) {
            try {
                Thread.sleep(this.waitTime);
            } catch (final InterruptedException e) {
            }
        }

        return charsRead;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.io.InputStreamReader#read(char[], int, int)
     */
    @Override
    public int read(final char[] cbuf, final int offset, final int length) throws IOException {
        int charsRead = 0;
        while ((charsRead = super.read(cbuf, offset, length)) == -1) {
            try {
                Thread.sleep(this.waitTime);
            } catch (final InterruptedException e) {
            }
        }

        return charsRead;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.io.Reader#read(java.nio.CharBuffer)
     */
    @Override
    public int read(final CharBuffer target) throws IOException {
        int charsRead = 0;
        while ((charsRead = super.read(target)) == -1) {
            try {
                Thread.sleep(this.waitTime);
            } catch (final InterruptedException e) {
            }
        }

        return charsRead;
    }

}
