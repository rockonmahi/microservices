package com.escola.eventservice.config;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusReceiverAsyncClient;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ServiceBusConfig {

  @Value("${azure.services.connections.queue.listener.connection}")
  private String listenerQueueConnection;

  @Value("${azure.services.connections.queue.sender.connection}")
  private String senderQueueConnection;

  @Value("${azure.services.connections.queue.listener.queue}")
  private String listenerQueue;

  @Value("${azure.services.connections.queue.sender.queue}")
  private String senderQueue;

  //Topic

  @Value("${azure.servicebus.connections.topic.listener.connection}")
  private String listenerTopicConnection;

  @Value("${azure.servicebus.connections.topic.sender.connection}")
  private String senderTopicConnection;

  @Value("${azure.servicebus.connections.topic.listener.topic}")
  private String listenerTopic;

  @Value("${azure.servicebus.connections.topic.listener.subscriptionName}")
  private String subscriptionName;

  @Value("${azure.servicebus.connections.topic.sender.topic}")
  private String senderTopic;

  @Bean("receiverQueueClient")
  public ServiceBusReceiverAsyncClient serviceBusReceiverQueueAsyncClient() {
    return new ServiceBusClientBuilder()
      .connectionString(listenerQueueConnection)
      .receiver()
      .queueName(listenerQueue)
      .disableAutoComplete()
      .buildAsyncClient();
  }

  @Bean("senderQueueClient")
  ServiceBusSenderClient serviceBusSenderQueueClient() {
    return new ServiceBusClientBuilder()
      .connectionString(senderQueueConnection)
      .sender()
      .queueName(senderQueue)
      .buildClient();
  }

  @Bean("receiverTopicSubscriptionClient")
  public ServiceBusReceiverAsyncClient serviceBusReceiverTopicSubscriptionAsyncClient() {
    return new ServiceBusClientBuilder()
      .connectionString(listenerTopicConnection)
      .receiver()
      .topicName(listenerTopic)
      .subscriptionName(subscriptionName)
      .disableAutoComplete()
      .buildAsyncClient();
  }

  @Bean("senderTopicClient")
  ServiceBusSenderClient serviceBusSenderTopicClient() {
    return new ServiceBusClientBuilder()
      .connectionString(senderTopicConnection)
      .sender()
      .topicName(senderTopic)
      .buildClient();
  }
}
