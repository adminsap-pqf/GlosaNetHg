package ryndem.mx.glosanet.messagebroker.datastage.vucem.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ryndem.mx.glosanet.messagebroker.VucemDocuments;

import static ryndem.mx.glosanet.messagebroker.config.Protocols.*;

@Component(VUCEM_RABBIT_CONFIG_NAME)
public class RabbitConfig {
    private ConnectionFactory connectionFactory;

    @Autowired
    @Qualifier(RABBIT_CONN_FACTORY)
    private void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public RabbitTemplate fetchRabbitTemplate(VucemDocuments document) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        switch (document) {
            case AcuseCove: //Factura
                template.setRoutingKey(VUCEM_ACUSE_COVE_QUEUE_NAME);
                break;
            case Edoc:
                template.setRoutingKey(VUCEM_EDOC_QUEUE_NAME);
                break;
            case AcuseEdoc:
                template.setRoutingKey(VUCEM_ACUSE_EDOC_QUEUE_NAME);
                break;
        }

        return template;
    }
}
