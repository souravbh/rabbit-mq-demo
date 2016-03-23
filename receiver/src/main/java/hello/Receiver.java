package hello;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class Receiver {

	private CountDownLatch latch = new CountDownLatch(1);

	public void receiveMessage(String message) throws InterruptedException, IOException {

		ObjectMapper mapper = new ObjectMapper(new JsonFactory());
		EmailMessage emailMessage = mapper.readValue(message, EmailMessage.class);
		System.out.println("Receiver 0 received :: <" + emailMessage.getMessage() + ">");
		Thread.sleep(1000);
		latch.countDown();
	}

	public CountDownLatch getLatch() {
		return latch;
	}


	public static class EmailMessage{

		String email;

		String message;

		@JsonCreator
		public EmailMessage(@JsonProperty("email") String email, @JsonProperty("message") String message) {
			this.message = message;
			this.email= email;
		}

		public String getEmail() {
			return email;
		}

		public String getMessage() {
			return message;
		}
	}

}
