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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jmiller
 *
 */
public class RandomLineFile {
    /**
     * Random Access File object for reading the file
     */
    private RandomAccessFile raf = null;
    /**
     * Position of the Last GeoLine
     */
    private long lastLinePosition = -1;

    /**
     * @param file File object of file to read
     * @throws FileNotFoundException File not Found
     */
    public RandomLineFile(final File file) throws FileNotFoundException {
        this.raf = new RandomAccessFile(file, "r");
        this.raf.getChannel();
    }

    /**
     * @param filename Filename of file to read
     * @throws FileNotFoundException File not Found
     */
    public RandomLineFile(final String filename) throws FileNotFoundException {
        this(new File(filename));
    }

    /**
     * @return the position of the last line
     */
    public synchronized long getLinePosition() {
        return this.lastLinePosition;
    }

    /**
     * Read the next line in the file
     *
     * @return String containing the next line in the file.
     */
    public String nextLine() {
        try {
            this.lastLinePosition = this.raf.getFilePointer();
            return this.raf.readLine();
        } catch (final IOException e) {
            return null;
        }
    }

    /**
     * @return String containing the previous line.
     */
    public String previousLine() {

        try {
            long pos = this.getLinePosition();
            if (pos == 0) {
                return null;
            }

            if (pos > 0) {
                if (pos > 2) {
                    pos -= 2;
                } else {
                    pos--;
                }
            }

            this.raf.seek(pos);
            if (pos != 0) {
                while (pos > 0) {

                    pos = this.raf.getFilePointer();
                    final byte c = this.raf.readByte();
                    if (c == 0xA) {
                        pos++;
                        this.raf.seek(pos);
                        break;
                    }
                    pos--;
                    this.raf.seek(pos);
                }
            }
            this.lastLinePosition = this.raf.getFilePointer();
            return this.raf.readLine();
        } catch (final IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Failed to read previous line", e);
        }
        return null;
    }

    /**
     * Move to the end of the file
     *
     * @throws IOException Failure to move to end of file.
     */
    public void seekEnd() throws IOException {
        this.seekPosition(this.raf.length() - 1);
    }

    /**
     * Move to the specified position in the file
     *
     * @param position Position in the file to move to.
     * @throws IOException Failure to move to indicated position
     */
    public void seekPosition(final long position) throws IOException {
        this.raf.seek(position);
        this.lastLinePosition = this.raf.getFilePointer();
    }

    /**
     * Move to the start of the file
     *
     * @throws IOException Failoure to move to start of file
     */
    public void seekStart() throws IOException {
        this.seekPosition(0);
    }
}
