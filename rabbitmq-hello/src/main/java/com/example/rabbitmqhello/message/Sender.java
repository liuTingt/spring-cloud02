package com.example.rabbitmqhello.message;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 消息生产者，通过注入Amqptemplate接口的实例来实现消息的发送
 */
@Component
public class Sender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(){
        String context = "hello " + new Date();
        System.out.println("sender: " + context);
        this.amqpTemplate.convertAndSend("hello", context);
    }
}
