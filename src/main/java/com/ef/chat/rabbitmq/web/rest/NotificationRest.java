package com.ef.chat.rabbitmq.web.rest;

import com.ef.chat.rabbitmq.domain.Notification;
import com.ef.chat.rabbitmq.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationRest {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public Notification send(@RequestBody Notification notification) throws Exception {
        return notificationService.send(notification);
    }

}
