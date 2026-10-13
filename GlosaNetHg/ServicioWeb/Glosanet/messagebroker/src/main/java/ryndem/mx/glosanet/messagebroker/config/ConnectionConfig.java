package ryndem.mx.glosanet.messagebroker.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static ryndem.mx.glosanet.messagebroker.config.Protocols.RABBIT_CONN_FACTORY;

@Configuration
public class ConnectionConfig {

    @Bean(name = RABBIT_CONN_FACTORY)
    public ConnectionFactory connectionFactory() {
        /*LOCALHOST*/
        //CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost", 5672);
        //connectionFactory.setUsername("guest");
        //connectionFactory.setPassword("guest");

        /*GLOSADEV SERVER*/
      //  CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        //connectionFactory.setUri("amqp://glosa:glosarabbit20@172.24.:5672");

        /*GLOSAPROD SERVER*/
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUri("amqp://glosa:grabbit20@172.24.24.10:5672");

        return connectionFactory;
    }
}
