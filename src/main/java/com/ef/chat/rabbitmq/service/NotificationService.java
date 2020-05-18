package com.ef.chat.rabbitmq.service;

import com.ef.chat.rabbitmq.domain.Notification;
import com.ef.chat.rabbitmq.service.rabbit.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    private final Logger log = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private ProducerService producerService;

    public Notification send(Notification notification) throws Exception {

        try {
            notification.setCreateDate(LocalDateTime.now());

            if (notification.getExchange().equals("directExchange")) {
                producerService.sendDirectExchange(notification);
            } else if (notification.getExchange().equals("fanoutExchange")) {
                producerService.sendFanoutExchange(notification);
            } else if (notification.getExchange().equals("topicExchange")) {
                producerService.sendTopicExchange(notification);
            } else if (notification.getExchange().equals("headerExchange")) {
                producerService.sendHeaderExchange(notification);
            }
            return notification;
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
        return null;
    }

}
