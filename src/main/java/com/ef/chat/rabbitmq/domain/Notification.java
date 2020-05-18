package com.ef.chat.rabbitmq.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class Notification implements Serializable {

    private LocalDateTime createDate;
    private String exchange;
    private String routingKey;
    private String department;
    private String message;

    @Override
    public String toString() {
        return "Notification{" +
                "createDate=" + createDate +
                ", exchange='" + exchange + '\'' +
                ", routingKey='" + routingKey + '\'' +
                ", department='" + department + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
