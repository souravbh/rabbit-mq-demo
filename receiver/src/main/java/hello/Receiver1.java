package hello;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;

public class Receiver1 {

	private CountDownLatch latch = new CountDownLatch(1);

	@Autowired
	RabbitTemplate template;

	public void receiveMessage(String value) throws InterruptedException {
		System.out.println("Receiver 1 received :: <" + value + ">");

		boolean received = this.template.receiveAndReply("spring-boot-2", response -> value + "hello");
		if (received) {
			System.out.println("**************  hello");
		}


		//Thread.sleep(1000);
		//latch.countDown();
	}

	public CountDownLatch getLatch() {
		return latch;
	}

}
