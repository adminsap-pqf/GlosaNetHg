package ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static ryndem.mx.glosanet.messagebroker.config.Protocols.*;

@Component(PHASE1_RABBIT_CONFIG_NAME)
public class RabbitConfig {

    private ConnectionFactory connectionFactory;

    @Autowired
    @Qualifier(RABBIT_CONN_FACTORY)
    private void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public RabbitTemplate fetchRabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        /* se omite su uso por el momento
        template.setExchange(PHASE1_DIRECT_EXCHANGE_NAME);*/
        template.setRoutingKey(PHASE1_QUEUE_NAME);
        return template;
    }

}
