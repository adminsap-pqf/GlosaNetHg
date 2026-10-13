package ryndem.mx.glosanet.messagebroker;

public interface Consumer {

    public static final String CONSUMER_LISTENER_NAME = "consumeMessage";

    /**
     *
     * @param message
     * @return
     */
    void consumeMessage(String message);
}
