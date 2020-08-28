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
package name.mymiller.javafx.display;

/**
 * Exception thrown when the DisplayManager has no open displays available.
 *
 * @author jmiller
 */
public class NoDisplayAvailableException extends Exception {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 2814362903846338687L;

    /**
     * Exception indicating all Displays are in use.
     *
     * @param totalDisplay Total number of displays detected.
     */
    public NoDisplayAvailableException(Integer totalDisplay) {
        super("All Displays use. Total: " + totalDisplay);
    }
}
