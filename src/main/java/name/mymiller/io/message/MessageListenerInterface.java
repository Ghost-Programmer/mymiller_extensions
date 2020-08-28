/*

 */
package name.mymiller.io.message;

/**
 * Interface to receive messages.
 *
 * @author jmiller
 *
 */
public interface MessageListenerInterface {

    /**
     * Listener interface to receive messages
     *
     * @param message Message to receive.
     */
    void receiveMessage(final Message message);
}
