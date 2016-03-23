package hello;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SenderConfiguration {

    final static String queueName1 = "spring-boot-1";

    @Bean
    Queue queue1() {
        return new Queue(queueName1, false);
    }


    @Bean
    TopicExchange exchange() {
        return new TopicExchange("spring-boot-exchange1");
    }

    @Bean
    public DirectExchange directexchange() {
        return new DirectExchange("exchange.rpc");
    }


    @Bean
    Binding binding1(Queue queue1, TopicExchange exchange) {

        return BindingBuilder.bind(queue1).to(exchange).with(queueName1);
    }
}
