package com.ef.chat.rabbitmq.config;

import com.ef.chat.rabbitmq.service.rabbit.ConsumerService;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitmqConfiguration {

    /**
     * Queues
     */
    @Value("${rabbitmq.queue.marketing}")
    public String marketingQ;

    @Value("${rabbitmq.queue.finance}")
    public String financeQ;

    @Value("${rabbitmq.queue.admin}")
    public String adminQ;

    /**
     * Routing Keys
     */
    @Value("${rabbitmq.routing-key.marketing}")
    public String marketingR;

    @Value("${rabbitmq.routing-key.finance}")
    public String financeR;

    @Value("${rabbitmq.routing-key.admin}")
    public String adminR;

    /**
     * Exchange
     */
    @Value("${rabbitmq.exchange.direct}")
    public String directE;

    @Value("${rabbitmq.exchange.fanout}")
    public String fanoutE;

    @Value("${rabbitmq.exchange.topic}")
    public String topicE;

    @Value("${rabbitmq.exchange.header}")
    public String headerE;

    /**
     * Bean Queue
     */
    @Bean
    Queue marketingQueue() {
        return new Queue(marketingQ, false);
    }

    @Bean
    Queue financeQueue() {
        return new Queue(financeQ, false);
    }

    @Bean
    Queue adminQueue() {
        return new Queue(adminQ, false);
    }

    /**
     * Bean Exchange
     */
    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(directE);
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(fanoutE);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(topicE);
    }

    @Bean
    HeadersExchange headersExchange() {
        return new HeadersExchange(headerE);
    }

    /**
     * BindingBuilder <DirectExchange>
     */
    @Bean
    Binding marketingBindingDE(Queue marketingQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(marketingQueue).to(directExchange).with(marketingR);
    }

    @Bean
    Binding financeBindingDE(Queue financeQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(financeQueue).to(directExchange).with(financeR);
    }

    @Bean
    Binding adminBindingDE(Queue adminQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(adminQueue).to(directExchange).with(adminR);
    }

    /**
     * BindingBuilder <FanoutExchange>
     */
    @Bean
    Binding marketingBindingFE(Queue marketingQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(marketingQueue).to(fanoutExchange);
    }

    @Bean
    Binding financeBindingFE(Queue financeQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(financeQueue).to(fanoutExchange);
    }

    @Bean
    Binding adminBindingFE(Queue adminQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(adminQueue).to(fanoutExchange);
    }

    /**
     * BindingBuilder <TopicExchange>
     */
    @Bean
    Binding marketingBindingTE(Queue marketingQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(marketingQueue).to(topicExchange).with("queue.marketing");
    }

    @Bean
    Binding financeBindingTE(Queue financeQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(financeQueue).to(topicExchange).with("queue.finance");
    }

    @Bean
    Binding adminBindingTE(Queue adminQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(adminQueue).to(topicExchange).with("queue.admin");
    }

    @Bean
    Binding allMessageBindingTE(Queue adminQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(adminQueue).to(topicExchange).with("queue.*");
    }

    /**
     * BindingBuilder <HeaderExchange>
     */
    @Bean
    Binding marketingBindingHE(Queue marketingQueue, HeadersExchange headersExchange) {
        return BindingBuilder.bind(marketingQueue).to(headersExchange).where("department").matches("marketing");
    }

    @Bean
    Binding financeBindingHE(Queue financeQueue, HeadersExchange headersExchange) {
        return BindingBuilder.bind(financeQueue).to(headersExchange).where("department").matches("finance");
    }

    @Bean
    Binding adminBindingHE(Queue adminQueue, HeadersExchange headersExchange) {
        return BindingBuilder.bind(adminQueue).to(headersExchange).where("department").matches("admin");
    }

    /**
     * Consumer Bean
     */
    @Bean
    ConsumerService receiver() {
        return new ConsumerService();
    }

    @Bean
    MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(receiver(), "handleMessage");
    }

    @Bean
    SimpleMessageListenerContainer simpleMessageListenerContainer(final ConnectionFactory connectionFactory,
                                                                  final MessageListenerAdapter messageListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(marketingQ, financeQ, adminQ);
        container.setMessageListener(messageListenerAdapter);
        return container;
    }

}
