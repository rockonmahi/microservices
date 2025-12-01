package com.escola.eventservice.listener;

import com.azure.messaging.servicebus.ServiceBusReceiverAsyncClient;
import com.escola.eventservice.service.ConnectorService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class ASBListenerAdapterImpl implements ASBListenerAdapter {

  private final ServiceBusReceiverAsyncClient receiverQueueClient;

  private final ServiceBusReceiverAsyncClient receiverTopicSubscriptionClient;

  private final ConnectorService connectorService;

  private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";

  @PostConstruct
  @Override
  public void listenerQueueClient() {
    receiverQueueClient.receiveMessages().doOnNext(message -> {
      log.info("Created Email Message Received message with id : {}", message.getMessageId());
      try {
        MDC.put(CORRELATION_ID_HEADER, message.getMessageId());
        try {
          connectorService.processCreatedMessage(message);
        } finally {
          MDC.clear();
        }
        receiverQueueClient.complete(message).subscribe(
                unused -> log.info("Message - {} completed !! ", message.getMessageId()),
                error -> log.error("Error completing the message -{}  due to- {}", message.getMessageId(), error.getMessage(), error));
      } catch (Exception e) {
        log.error("Message - {} processing error , due to", message.getMessageId(), e);
        receiverQueueClient.deadLetter(message).subscribe(
                unused -> log.info("Message - {} dead-lettered !! ", message.getMessageId()),
                error -> log.error("Error dead-lettered message -{} due to- {}", message.getMessageId(), error.getMessage(), error));
      }
    }).doOnError(error -> log.error("Error Receiving messages !!")).subscribe();
  }

  @PostConstruct
  @Override
  public void listenerTopicSubscriptionClient() {
    receiverTopicSubscriptionClient.receiveMessages().doOnNext(message -> {
      log.info("Order template import Received message with id : {}", message.getMessageId());
      try {
        MDC.put(CORRELATION_ID_HEADER, message.getMessageId());
        try {
          connectorService.processOrderTemplateImportMessage(message);
        } finally {
          MDC.clear();
        }
        receiverTopicSubscriptionClient.complete(message).subscribe(
                unused -> log.info("Message - {} completed !! ", message.getMessageId()),
                error -> log.error("Error completing the message -{}  due to- {}", message.getMessageId(), error.getMessage(), error));
      } catch (Exception e) {
        log.error("Message - {} processing error , due to", message.getMessageId(), e);
        receiverTopicSubscriptionClient.deadLetter(message).subscribe(
                unused -> log.info("Message - {} dead-lettered !! ", message.getMessageId()),
                error -> log.error("Error dead-lettered message -{} due to- {}", message.getMessageId(), error.getMessage(), error));
      }
    }).doOnError(
            error -> log.error("Error Receiving messages !!")).subscribe();
  }
}
