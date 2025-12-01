package com.escola.eventservice.sender;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import io.cloudevents.core.format.EventFormat;
import io.cloudevents.core.provider.EventFormatProvider;
import io.cloudevents.jackson.JsonFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.UUID;

import static java.util.Objects.requireNonNull;


@RequiredArgsConstructor
@Component
@Slf4j
public class ASBPublishAdapterImpl implements ASBPublishAdapter {

  private final ServiceBusSenderClient senderQueueClient;

  private final ServiceBusSenderClient senderTopicClient;

  private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";

  private static final String ERROR_MESSAGE = "Error sending message";

  @Override
  public void sendMessageToQueue(String message) {

    String messageId = UUID.randomUUID().toString();

    CloudEvent cloudEvent = CloudEventBuilder.v1()
      .withId(messageId)
      .withSource(URI.create("/order-email"))
      .withSubject("med-shop-mail-connector")
      .withType("order-confirmation")
      .withExtension("scope","med-shop")
      .withTime(OffsetDateTime.now())
      .withData(message.getBytes())
      .withDataContentType(JsonFormat.CONTENT_TYPE)
      .build();

    EventFormat format = EventFormatProvider.getInstance().resolveFormat(JsonFormat.CONTENT_TYPE);
    ServiceBusMessage serviceBusMessage = new ServiceBusMessage(requireNonNull(format).serialize(cloudEvent));
    serviceBusMessage.getApplicationProperties().put("type", "order-confirmation");
    serviceBusMessage.getApplicationProperties().put("scope", "med-shop");
    serviceBusMessage.getApplicationProperties().put("project-key", "zeiss_med_shop_dev");

    log.info("Message : {} transfer to ASB subscription having id as :{}", MDC.get(CORRELATION_ID_HEADER), messageId);
    try {
      senderQueueClient.sendMessage(serviceBusMessage);
      log.info("Message sent to order created email queue successfully");
    } catch (Exception e) {
      log.error(ERROR_MESSAGE, e);
    }
  }

  @Override
  public void sendMessageToTopic(String message) {
    String newMessageId = UUID.randomUUID().toString();
    log.info("Message : {} transfer to ASB subscription having id as :{}", MDC.get(CORRELATION_ID_HEADER), newMessageId);
    try {
      ServiceBusMessage serviceBusMessage = new ServiceBusMessage(message);

     log.info("Message : {} transfer to new subscription having id as :{}", MDC.get(CORRELATION_ID_HEADER), newMessageId);
      serviceBusMessage.setMessageId(newMessageId);
      serviceBusMessage.getApplicationProperties().put("type", "order-template-trigger");
      senderTopicClient.sendMessage(serviceBusMessage);
      log.info("Message sent to Order Template Topic successfully");
    } catch (Exception e) {
      log.error(ERROR_MESSAGE, e);
    }
  }
}
