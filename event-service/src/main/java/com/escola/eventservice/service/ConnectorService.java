package com.escola.eventservice.service;

import com.azure.messaging.servicebus.ServiceBusReceivedMessage;

public interface ConnectorService {

  void processCreatedMessage(ServiceBusReceivedMessage message);

  void processOrderTemplateImportMessage(ServiceBusReceivedMessage message);
}
