package com.bhuvan.hiringapp1.Config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EmailConfig {
    public static final String QUEUE = "exp_queue" ;
    public static final String EXCHANGE = "exp_exchange" ;
    public static final String ROUTING_KEY = "exp_routing_key" ;

    public static final String DEAD_QUEUE = "dead_queue" ;
    private static final String DEAD_EXCHANGE = "dead_exchange";
    private static final String DEAD_ROUTING_KEY = "dead_routing_key";

    @Bean
    @Qualifier("main_queue")
    public org.springframework.amqp.core.Queue main_queue() {
        return new Queue(QUEUE,true);
    }

    @Bean
    @Qualifier("dead_queue")
    public Queue dead_queue(){
        return new Queue(DEAD_QUEUE) ;
    }

    @Bean
    @Qualifier("main_exchange")
    public TopicExchange main_exchange(){
        return new TopicExchange(EXCHANGE) ;
    }

    @Bean
    @Qualifier("dead_exchange")
    public TopicExchange dead_exchange(){
        return new TopicExchange(DEAD_EXCHANGE) ;
    }

    @Bean
    public Binding binding(@Qualifier("main_queue") Queue queue, @Qualifier("main_exchange") TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public Binding dead_binding(@Qualifier("dead_queue") Queue dead_queue, @Qualifier("dead_exchange") TopicExchange dead_exchange){
        return BindingBuilder.bind(dead_queue).to(dead_exchange).with(DEAD_ROUTING_KEY);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter() ;
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory){
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory) ;
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate ;
    }
}
