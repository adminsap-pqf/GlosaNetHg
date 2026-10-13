package ryndem.mx.glosanet.messagebroker;

import java.util.UUID;

public class Utils {

    public static String generateGUID() {

        return UUID.randomUUID().toString();
    }
}
