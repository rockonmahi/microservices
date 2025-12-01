package com.escola.eventservice.sender;

public interface ASBPublishAdapter {

  void sendMessageToQueue(String message);

  void sendMessageToTopic(String message);
}
