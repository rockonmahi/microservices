package com.escola.eventservice.service;

import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.escola.eventservice.sender.ASBPublishAdapter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
@Slf4j
public class ConnectorServiceImpl implements ConnectorService {

  private final ASBPublishAdapter asbPublishAdapter;

  @Override
  public void processCreatedMessage(ServiceBusReceivedMessage message) {

    ObjectMapper mapper = new ObjectMapper();

    Map<String, Object> jsonMap = Map.of(
      "projectKey", "zeiss_mach_stg",
      "storeKey", "zeiss_med_shop_ch",
      "email", "testmachconnectors+machines011@gmail.com",
      "customerId", "9148d405-4d73-41d2-bb5b-3a7d9a958433",
      "sapCustomerNumber", "0000551543"
    );

    try {
      String jsonString = mapper.writeValueAsString(jsonMap);
      asbPublishAdapter.sendMessageToTopic(jsonString);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void processOrderTemplateImportMessage(ServiceBusReceivedMessage message) {

    ObjectMapper mapper = new ObjectMapper();

    Map<String, Object> jsonMap = Map.of();

    try {
      String jsonString = mapper.writeValueAsString(jsonMap);
      asbPublishAdapter.sendMessageToQueue(jsonString);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
