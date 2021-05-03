package name.mymiller.io.message;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Message Container sent between systems for the exchange of data. UUID makes
 * them unique, message type determines who is listening for the message, and an
 * Object containing the data.
 *
 * @author jmiller
 */
public class Message implements Comparable<Message>, Cloneable, Serializable {
    /**
     * Generated Serial Version ID
     */
    public static final long serialVersionUID = -85962065619107195L;

    /**
     * Message type this container is carrying.
     */
    private final String messageType;

    /**
     * Unique ID for each message
     */
    private final UUID uuid;

    /**
     * Data contained in this message
     */
    private final Object data;

    /**
     * Copy Constructor to create new copy, with a new UUID.
     *
     * @param copy
     */
    public Message(Message copy) {
        super();
        this.messageType = copy.getMessageType();
        this.uuid = UUID.randomUUID();
        this.data = copy.getData();
    }

    /**
     * Constructor to generate a new message. UUID is generated automatically.
     *
     * @param messageType Type of message in this container.
     * @param data        Data of the for the message.
     */
    public Message(String messageType, Object data) {
        super();
        this.messageType = messageType;
        this.uuid = UUID.randomUUID();
        this.data = data;
    }

    /**
     * Private Constructor for cloning.
     *
     * @param messageType Type of message
     * @param data        Data of message
     * @param uuid        UUID of this message
     */
    private Message(String messageType, Object data, UUID uuid) {
        super();
        this.messageType = messageType;
        this.uuid = uuid;
        this.data = data;
    }

    @Override
    protected Object clone() {
        return new Message(this.getMessageType(), this.getData(), this.getUuid());
    }

    @Override
    public int compareTo(Message o) {
        if (this.uuid.equals(o.uuid)) {
            return 0;
        }

        return this.messageType.compareTo(o.messageType);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Message)) {
            return false;
        }
        final Message other = (Message) obj;
        return Objects.equals(this.data, other.data) && (this.messageType == other.messageType)
                && Objects.equals(this.uuid, other.uuid);
    }

    /**
     * @return Data this message contains
     */
    public final Object getData() {
        return this.data;
    }

    /**
     * @return MessageType this container has.
     */
    public final String getMessageType() {
        return this.messageType;
    }

    /**
     * @return UUID of this message.
     */
    public final UUID getUuid() {
        return this.uuid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.data, this.messageType, this.uuid);
    }

    @Override
    public String toString() {
        return "Message [messageType=" + this.messageType + ", uuid=" + this.uuid + ", data=" + this.data + "]";
    }

}
