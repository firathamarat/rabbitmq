package com.ef.chat.rabbitmq.service.rabbit;

import com.ef.chat.rabbitmq.domain.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    final Logger log = LoggerFactory.getLogger(ConsumerService.class);


    public void handleMessage(Notification notification) {
        log.info("✉ < Receiver Notification [{} - {}]: {}", notification.getExchange(), notification.getRoutingKey(), notification);
    }

    /**
     @Value("${rabbitmq.queueName}") public String queueName;

     @Value("${rabbitmq.exchangeName}") public String exchangeName;

     @Value("${rabbitmq.routingKey}") public String routingKey;

     @Autowired private ConnectionFactory connectionFactory;

     @RabbitListener(queues = "${rabbitmq.queueName}")
     public void handleNotification(Notification notification) {
     System.out.println(notification.toString());
     }*/

}
