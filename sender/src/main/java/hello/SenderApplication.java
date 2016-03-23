
package hello;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class SenderApplication implements CommandLineRunner {



	@Autowired
	AnnotationConfigApplicationContext context;

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Autowired
    DirectExchange exchange;


    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(SenderApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");

        String jsonMessage = "{\"message\":\"hello\", \"email\":\"a@a.com\"}";

        rabbitTemplate.convertAndSend("spring-boot-1", jsonMessage);

        rabbitTemplate.setReceiveTimeout(3000);
        String response = (String) rabbitTemplate.convertSendAndReceive(exchange.getName(), "demo.rpc", "hello madhan ");
        System.out.println("val:"+ response);

		/*rabbitTemplate.convertAndSend(queueName, "Hello from RabbitMQ - 2!");
		rabbitTemplate.convertAndSend(queueName, "Hello from RabbitMQ - 3!");
		rabbitTemplate.convertAndSend(queueName, "Hello from RabbitMQ - 4!");
		rabbitTemplate.convertAndSend(queueName, "Hello from RabbitMQ - 5!");*/
        context.close();
    }
}
