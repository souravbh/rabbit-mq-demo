package hello;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReceiverConfiguration {

    final static String queueName1 = "spring-boot-1";

    @Bean
    public Queue queue() {
        return new Queue("demo.rpc");
    }

    @Bean
    Queue queue1() {
        return new Queue(queueName1, false);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("spring-boot-exchange1");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("exchange.rpc");
    }

    @Bean
    Binding topicBinding(Queue queue1, TopicExchange exchange) {

        return BindingBuilder.bind(queue1).to(exchange).with(queueName1);
    }

    @Bean
    public Binding directBinding(DirectExchange exchange, Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with("demo.rpc");
    }

    @Bean
    Receiver receiver() {
        return new Receiver();
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }



}
