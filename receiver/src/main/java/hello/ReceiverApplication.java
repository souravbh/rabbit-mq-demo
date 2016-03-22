
package hello;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReceiverApplication {

    final static String queueName1 = "spring-boot-1";
    final static String queueName2 = "spring-boot-2";

    @Autowired
    AnnotationConfigApplicationContext context;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Bean
    Queue queue1() {
        return new Queue(queueName1, false);
    }

    @Bean
    Queue queue2() {
        return new Queue(queueName2, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("spring-boot-exchange");
    }

    @Bean
    Binding binding1(Queue queue1, TopicExchange exchange) {
        return BindingBuilder.bind(queue1).to(exchange).with(queueName1);
    }

    @Bean
    Binding binding2(Queue queue2, TopicExchange exchange) {
        return BindingBuilder.bind(queue2).to(exchange).with(queueName2);
    }


    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(new String[]{queueName1});
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    Receiver receiver() {
        return new Receiver();
    }


    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }



    @Bean
    SimpleMessageListenerContainer container1(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter1) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(new String[]{ queueName2});
        container.setMessageListener(listenerAdapter1);
        return container;
    }

    @Bean
    Receiver1 receiver1() {
        return new Receiver1();
    }


    @Bean
    MessageListenerAdapter listenerAdapter1(Receiver1 receiver1) {
        return new MessageListenerAdapter(receiver1, "receiveMessage");
    }




    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ReceiverApplication.class, args);
    }
}
