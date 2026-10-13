package ryndem.mx.glosanet.messagebroker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.logging.Logger;

public class Message {
    private static final Logger log = Logger.getLogger(Message.class.getName());

    public String toRawJson() {
        ObjectMapper mapper = new ObjectMapper();
        String json_message = null;
        try {
            json_message = mapper.writeValueAsString(this);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            log.info("Error al generar el RAW Json");
            e.printStackTrace();
            return null;
        }

        return json_message;
    }

    public String toPrettyJson() {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String pretty_json_message = null;
        try {
            pretty_json_message = ow.writeValueAsString(this);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            log.info("Error al darle formato al Json");
            e.printStackTrace();
            return null;
        }

        return pretty_json_message;
    }
}

