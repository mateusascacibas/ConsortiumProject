package com.educationProject.consortium.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.educationProject.consortium.config.RabbitMQConfig.QUEUE_NAME;

@Component
public class ConsortiumGroupConsumer {
    @RabbitListener(queues = QUEUE_NAME)
    public void consumerMessage(String message){
        System.out.println("Message received from RabbitMQ: " + message);
    }
}
