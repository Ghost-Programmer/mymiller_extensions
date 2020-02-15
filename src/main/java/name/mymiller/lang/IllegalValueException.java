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
package name.mymiller.lang;

/**
 * Exception throw when a value is seen as Illegal.
 *
 * @author jmiller
 */
public class IllegalValueException extends Exception {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = -5981624734067928969L;

    /**
     * Constructs a new Exception for an illegal value with null as it's detail
     * message
     */
    public IllegalValueException() {
    }

    /**
     * Constructs a new exception for an illegal value with a specificed detail
     * message
     *
     * @param message The detail message. The detail message is saved for later
     *                retrieval by the Throwable.getMessage() method.
     */
    public IllegalValueException(final String message) {
        super(message);
    }

    /**
     * @param message The detail message. The detail message is saved for later
     *                retrieval by the Throwable.getMessage() method.
     * @param cause   The cause (which is saved for later retrieval by the
     *                Throwable.getCause() method). (A null value is permitted, and
     *                indicates that the cause is nonexistent or unknown.)
     */
    public IllegalValueException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message            The detail message. The detail message is saved for
     *                           later retrieval by the Throwable.getMessage()
     *                           method.
     * @param cause              The cause (which is saved for later retrieval by
     *                           the Throwable.getCause() method). (A null value is
     *                           permitted, and indicates that the cause is
     *                           nonexistent or unknown.)
     * @param enableSuppression  Whether or not suppression is enabled or disabled
     * @param writableStackTrace Whether or not the stack trace should be writable
     */
    public IllegalValueException(final String message, final Throwable cause, final boolean enableSuppression,
                                 final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @param cause The cause (which is saved for later retrieval by the
     *              Throwable.getCause() method). (A null value is permitted, and
     *              indicates that the cause is nonexistent or unknown.)
     */
    public IllegalValueException(final Throwable cause) {
        super(cause);
    }

}
