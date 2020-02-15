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
package name.mymiller.algorithms;

/**
 * Exception throw to indicate that the expected object or data flow is out of
 * expected order.
 *
 * @author jmiller
 */
public class OutOfOrderException extends Exception {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 7223695263340467610L;

    /**
     * Exception throw to indicate that the expected object or data flow is out of
     * expected order.
     */
    public OutOfOrderException() {
        super("Out of order detected");
    }

    /**
     * Constructs a new exception with the specified detail message. The cause is
     * not initialized, and may subsequently be initialized by a call to
     * Throwable.initCause(java.lang.Throwable).
     *
     * @param message The detail message. The detail message is saved for later
     *                retrieval by the Throwable.getMessage() method.
     */
    public OutOfOrderException(final String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause. Note
     * that the detail message associated with cause is not automatically
     * incorporated in this exception's detail message.
     *
     * @param message The detail message. The detail message is saved for later
     *                retrieval by the Throwable.getMessage() method.
     * @param cause   The cause (which is saved for later retrieval by the
     *                Throwable.getCause() method). (A null value is permitted, and
     *                indicates that the cause is nonexistent or unknown.)
     */
    public OutOfOrderException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified detail message, cause,
     * suppression enabled or disabled, and writable stack trace enabled or
     * disabled.
     *
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
    public OutOfOrderException(final String message, final Throwable cause, final boolean enableSuppression,
                               final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Constructs a new exception with the specified cause and a detail message of
     * (cause==null ? null : cause.toString()) (which typically contains the class
     * and detail message of cause). This constructor is useful for exceptions that
     * are little more than wrappers for other throwables (for example,
     * PrivilegedActionException).
     *
     * @param cause The cause (which is saved for later retrieval by the
     *              Throwable.getCause() method). (A null value is permitted, and
     *              indicates that the cause is nonexistent or unknown.)
     */
    public OutOfOrderException(final Throwable cause) {
        super(cause);
    }

}
