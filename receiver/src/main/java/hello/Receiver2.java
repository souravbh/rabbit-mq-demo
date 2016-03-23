package hello;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver2 {

    @RabbitListener(queues = "demo.rpc")
    public String flushMe(String value) {
        return "message"+ value;
    }

}
