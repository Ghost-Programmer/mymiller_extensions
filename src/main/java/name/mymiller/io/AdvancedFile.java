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
package name.mymiller.io;

import java.io.*;
import java.net.URI;
import java.nio.channels.FileChannel;

/**
 * @author jmiller Implement advanced features onto the existing File object
 */
public class AdvancedFile extends File {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -1030829156540504068L;

    /**
     * Creates a new File instance from a parent abstract pathname and a child
     * pathname string. If parent is null then the new File instance is created as
     * if by invoking the single-argument File constructor on the given child
     * pathname string.
     * <p>
     * Otherwise the parent abstract pathname is taken to denote a directory, and
     * the child pathname string is taken to denote either a directory or a file. If
     * the child pathname string is absolute then it is converted into a relative
     * pathname in a system-dependent way. If parent is the empty abstract pathname
     * then the new File instance is created by converting child into an abstract
     * pathname and resolving the result against a system-dependent default
     * directory. Otherwise each pathname string is converted into an abstract
     * pathname and the child abstract pathname is resolved against the parent.
     *
     * @param parent The parent abstract pathname
     * @param child  The child pathname string
     */
    public AdvancedFile(final File parent, final String child) {
        super(parent, child);
    }

    /**
     * Creates a new File instance by converting the given pathname string into an
     * abstract pathname. If the given string is the empty string, then the result
     * is the empty abstract pathname.
     *
     * @param pathname A pathname string
     */
    public AdvancedFile(final String pathname) {
        super(pathname);
    }

    /**
     * Creates a new File instance from a parent pathname string and a child
     * pathname string. If parent is null then the new File instance is created as
     * if by invoking the single-argument File constructor on the given child
     * pathname string.
     * <p>
     * Otherwise the parent pathname string is taken to denote a directory, and the
     * child pathname string is taken to denote either a directory or a file. If the
     * child pathname string is absolute then it is converted into a relative
     * pathname in a system-dependent way. If parent is the empty string then the
     * new File instance is created by converting child into an abstract pathname
     * and resolving the result against a system-dependent default directory.
     * Otherwise each pathname string is converted into an abstract pathname and the
     * child abstract pathname is resolved against the parent.
     *
     * @param parent The parent abstract pathname
     * @param child  The child pathname string
     */
    public AdvancedFile(final String parent, final String child) {
        super(parent, child);
    }

    /**
     * Creates a new File instance by converting the given file: URI into an
     * abstract pathname. The exact form of a file: URI is system-dependent, hence
     * the transformation performed by this constructor is also system-dependent.
     * <p>
     * For a given abstract pathname f it is guaranteed that
     * <p>
     * new File( f.toURI()).equals( f.getAbsoluteFile()) so long as the original
     * abstract pathname, the URI, and the new abstract pathname are all created in
     * (possibly different invocations of) the same Java virtual machine. This
     * relationship typically does not hold, however, when a file: URI that is
     * created in a virtual machine on one operating system is converted into an
     * abstract pathname in a virtual machine on a different operating system.
     *
     * @param uri An absolute, hierarchical URI with a scheme equal to "file", a
     *            non-empty path component, and undefined authority, query, and
     *            fragment components Throws:
     */
    public AdvancedFile(final URI uri) {
        super(uri);
    }

    /**
     * Copy the file to the destination
     *
     * @param dst Destination file to copy to
     * @throws IOException Exception occurred during file copy.
     */
    public void copy(final File dst) throws IOException {
        if (!dst.exists()) {
            final File dir = dst.getParentFile();
            dir.mkdirs();
            dst.createNewFile();
        }

        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(this);
            fos = new FileOutputStream(dst);
            this.copy(fis, fos);
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * Copy the file from source to destination
     *
     * @param in  Source
     * @param out Destination
     * @throws IOException Error copying file.
     */
    private void copy(final FileInputStream in, final FileOutputStream out) throws IOException {
        final FileChannel sourceChannel = in.getChannel();
        final FileChannel destinationChannel = out.getChannel();

        destinationChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
    }

    /**
     * Constructor to create a File Copier to copy a file from one place to another
     *
     * @param dst Destination file to copy to
     * @throws IOException Exception occurred during file copy.
     */
    public void copy(final String dst) throws IOException {
        this.copy(new File(dst));
    }

    /**
     * Delete the file
     *
     * @return boolean indicating success
     */
    @Override
    public boolean delete() {
        if (this.isDirectory()) {
            return this.deleteDirectory(this);
        }
        return super.delete();
    }

    /**
     * Delete a Directory and all files in it.
     *
     * @param directory File for the directory to delete
     * @return boolean indicating success
     */
    private boolean deleteDirectory(final File directory) {
        for (final File file : directory.listFiles()) {
            if (file.isDirectory()) {
                this.deleteDirectory(file);
            } else {
                file.delete();
            }
        }
        return directory.delete();
    }

    /**
     * Method to initiate split.
     *
     * @param count     Lines to split on.
     * @param prefix    Prefix for the split files
     * @param postfix   Postfix for the split files.
     * @param directory Directory to split into
     * @throws IOException Error reading files.
     */
    public void splitByLines(final int count, final String prefix, final String postfix, final String directory)
            throws IOException {
        int fileCount = 1;
        final BufferedReader reader = new BufferedReader(new FileReader(this));
        BufferedWriter writer = new BufferedWriter(
                new FileWriter(new File(directory + File.separator + prefix + fileCount + postfix)));

        int lineCount = 0;

        while (true) {
            final String line = reader.readLine();
            if (line != null) {
                lineCount++;
            } else {
                writer.flush();
                writer.close();
                writer = null;
                break;
            }

            writer.write(line);
            writer.newLine();

            if (lineCount == count) {
                writer.flush();
                writer.close();
                lineCount = 0;
                fileCount++;
                writer = new BufferedWriter(
                        new FileWriter(new File(directory + File.separator + prefix + fileCount + postfix)));
            }
        }
        reader.close();
    }
}
