package hello;

import java.util.concurrent.CountDownLatch;

public class Receiver1 {

	private CountDownLatch latch = new CountDownLatch(1);

	public void receiveMessage(String message) throws InterruptedException {
		System.out.println("Receiver 1 received :: <" + message + ">");
		Thread.sleep(1000);
		latch.countDown();
	}

	public CountDownLatch getLatch() {
		return latch;
	}

}
