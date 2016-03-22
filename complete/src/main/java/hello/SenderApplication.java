
package hello;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SenderApplication implements CommandLineRunner {

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




    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(SenderApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(queueName1, "Hello from RabbitMQ - 0 !");
		rabbitTemplate.convertAndSend(queueName2, "Hello from RabbitMQ - 1!");
		/*rabbitTemplate.convertAndSend(queueName, "Hello from RabbitMQ - 2!");
		rabbitTemplate.convertAndSend(queueName, "Hello from RabbitMQ - 3!");
		rabbitTemplate.convertAndSend(queueName, "Hello from RabbitMQ - 4!");
		rabbitTemplate.convertAndSend(queueName, "Hello from RabbitMQ - 5!");*/
        context.close();
    }
}
