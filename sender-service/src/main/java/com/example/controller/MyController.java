package com.example.controller;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @Autowired
    RabbitTemplate template;

    @Autowired
    DirectExchange exchange;

    @RequestMapping("/notifications")
    public String notification(@RequestParam String message, @RequestParam String email){


        String jsonMessage = "{\"message\":\"" + message + "\",\"email\":\"" + email + "\" }";
        try{
            template.convertAndSend("spring-boot-1", jsonMessage);
        }catch(Exception e){

            return "message did not go through";
        }

        return "message sent" + message;
    }
}
