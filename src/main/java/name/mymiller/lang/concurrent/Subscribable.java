/**
 *
 */
package name.mymiller.lang.concurrent;

import java.util.function.Consumer;

/**
 * @author jmiller An interface identifying an object as being subscribed to.
 *         Allowing you to act upon the returned object in a functional
 *         procedure.
 * @param <T> Type that shall be returned in the subscribe method.
 *
 */
public interface Subscribable<T> {

    /**
     * Method called to subscribe to the object.
     *
     * @param consumer Consumer functional procedure to handle the return.
     */
    void subscribe(Consumer<? super T> consumer);
}
