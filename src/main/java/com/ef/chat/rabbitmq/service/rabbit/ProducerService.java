package com.ef.chat.rabbitmq.service.rabbit;

import com.ef.chat.rabbitmq.domain.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// Spring tarafından otomatik instance oluşturulur (@Bean)
@Service
public class ProducerService {

    private final Logger log = LoggerFactory.getLogger(ProducerService.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.routingKey}")
    public String routingKey;

    @Value("${rabbitmq.exchangeName}")
    public String exchangeName;

    public void sendDirectExchange(Notification notification) {
        // Exchange - routingKeu - Object
        log.info("✉ > Send [DirectExchange] Notification: {}", notification);
        rabbitTemplate.convertAndSend(notification.getExchange(), notification.getRoutingKey(), notification);
    }

    public void sendFanoutExchange(Notification notification) {
        log.info("✉ > Send [FanoutExchange] Notification: {}", notification);
        rabbitTemplate.convertAndSend(notification.getExchange(), notification.getRoutingKey(), notification);
    }

    public void sendTopicExchange(Notification notification) {
        log.info("✉ > Send [TopicExchange] Notification: {}", notification);
        rabbitTemplate.convertAndSend(notification.getExchange(), notification.getRoutingKey(), notification);
    }

    public void sendHeaderExchange(Notification notification) {
        log.info("✉ > Send [HeaderExchange] Notification: {}", notification);
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("department", notification.getDepartment());
        MessageConverter messageConverter = new SimpleMessageConverter();
        Message message = messageConverter.toMessage(notification, messageProperties);
        amqpTemplate.send(notification.getExchange(), "", message);
        log.info("HeaderExchange Notification: {}", message);
    }

    /**
     @PostConstruct public void init() {
     Notification notification = new Notification();
     notification.setCreateDate(LocalDateTime.now());
     notification.setMessage("Hey naber!");
     sendToQueue(notification);
     }

     */

}
