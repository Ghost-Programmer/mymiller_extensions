/*

 */
package name.mymiller.lang.concurrent;

import java.util.function.Consumer;

/**
 * @author jmiller User to return immediately while processing may take place in
 *         other threads or processes.
 */
public class FutureSubscription<T> implements Subscribable<T> {

    private Consumer<? super T> consumer;

    /**
     * Method to publish your result to a subscriber.
     */
    public void publish(T item) {
        this.consumer.accept(item);
    }

    @Override
    public void subscribe(Consumer<? super T> consumer) {
        this.consumer = consumer;
    }

}
