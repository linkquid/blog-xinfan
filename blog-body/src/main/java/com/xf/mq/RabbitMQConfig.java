package com.xf.mq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */

@Configuration
public class RabbitMQConfig {

    public static final String EMAIL_QUEUE = "emailQueue";
    public static final String EMAIL_EXCHANGER = "emailExchanger";

    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE);
    }

    @Bean
    public TopicExchange emailExchanger() {
        return new TopicExchange(EMAIL_EXCHANGER);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(emailQueue()).to(emailExchanger()).with("email.#");
    }

}
