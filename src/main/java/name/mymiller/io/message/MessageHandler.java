package name.mymiller.io.message;

import name.mymiller.lang.singleton.SingletonInterface;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Receives and distributes messages between listeners.
 *
 * @author jmiller
 */
public class MessageHandler implements SingletonInterface<MessageHandler> {
    /**
     * Job Manager Global Instance
     */
    private static MessageHandler global_instance = null;
    /**
     * Map containing the listeners by type
     */
    private final Map<String, List<MessageListenerInterface>> listenerMap;

    /**
     * Constructor to create global instance
     */
    protected MessageHandler() {
        this.listenerMap = new ConcurrentHashMap<>();
    }

    /**
     * @return Global Instance of the Job Manager
     */
    public static MessageHandler getInstance() {
        if (MessageHandler.global_instance == null) {
            MessageHandler.global_instance = new MessageHandler();
        }
        return MessageHandler.global_instance;
    }

    /**
     * Add a listener for the specific message type.
     *
     * @param type     Message Type to listen
     * @param listener Listener object to receive the message
     * @return Boolean indicating success of adding listener.
     */
    public boolean addListener(String type, MessageListenerInterface listener) {
        this.addMessageType(type);

        final List<MessageListenerInterface> list = this.listenerMap.get(type);

        return list.add(listener);
    }

    public void addMessageType(String messageType) {
        final List<MessageListenerInterface> list = this.listenerMap.get(messageType);

        if (list == null) {
            this.listenerMap.put(messageType, new CopyOnWriteArrayList<>());
        }
    }

    public void addMessageType(String[] messageTypes) {
        Arrays.stream(messageTypes).forEach(type -> {
            this.addMessageType(type);
        });
    }

    /**
     * Determine if a Listener is registered for a Message Type
     *
     * @param type     Message Type to check for existing listener.
     * @param listener Listener to see if is registered
     * @return boolean indicating if registered
     */
    public boolean isListening(String type, MessageListenerInterface listener) {
        final List<MessageListenerInterface> list = this.listenerMap.get(type);

        if (list == null) {
            return false;
        }

        return list.contains(listener);
    }

    /**
     * Remove the listener from message type
     *
     * @param type     Type to remove the listener.
     * @param listener Listener to remove.
     * @return boolean indicating success.
     */
    public boolean removeListener(String type, MessageListenerInterface listener) {
        final List<MessageListenerInterface> list = this.listenerMap.get(type);

        if (list == null) {
            return false;
        }

        return list.remove(listener);
    }

    public boolean sendMessage(Message message) {
        final List<MessageListenerInterface> list = this.listenerMap.get(message.getMessageType());

        if (list == null) {
            return false;
        }
        list.forEach(listener -> listener.receiveMessage(message));

        return true;
    }
}
