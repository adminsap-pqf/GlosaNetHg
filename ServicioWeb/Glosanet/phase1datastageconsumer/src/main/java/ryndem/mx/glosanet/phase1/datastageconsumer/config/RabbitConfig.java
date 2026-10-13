package ryndem.mx.glosanet.phase1.datastageconsumer.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ryndem.mx.glosanet.messagebroker.Consumer;

import static ryndem.mx.glosanet.messagebroker.config.Protocols.PHASE1_QUEUE_NAME;
import static ryndem.mx.glosanet.messagebroker.Consumer.CONSUMER_LISTENER_NAME;

@Configuration
@PropertySource("classpath:configuration.properties")
public class RabbitConfig {

    @Bean
    public ConnectionFactory connectionFactory() {

        /*LOCALHOST*/
        /*CachingConnectionFactory connectionFactory = new CachingConnectionFactory(
                "localhost", 5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");*/

        /*GLOSADEV SERVER*/
        /*CachingConnectionFactory connectionFactory = new CachingConnectionFactory(
                "172.24.32.19", 5672);
        connectionFactory.setUsername("glosa");
        connectionFactory.setPassword("glosarabbit20");*/


        /*GLOSAPROD SERVER*/
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(
                "172.24.24.10", 5672);
        connectionFactory.setUsername("glosa");
        connectionFactory.setPassword("grabbit20");

        return connectionFactory;
    }

    @Bean
    SimpleMessageListenerContainer container(MessageListenerAdapter listenerAdapter) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueueNames(PHASE1_QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Consumer consumer) {
        return new MessageListenerAdapter(consumer, CONSUMER_LISTENER_NAME);
    }
}
