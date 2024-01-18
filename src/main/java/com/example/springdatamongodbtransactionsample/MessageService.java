package com.example.springdatamongodbtransactionsample;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageService {

  private final MessageRepository messageRepository;

  public MessageService(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  @Transactional
  public void saveTestMessage() {
    messageRepository.save(new Message(1, "test1"));
    fail();
    messageRepository.save(new Message(2, "test2"));
  }

  private void fail() {
    throw new RuntimeException("mongo err");
  }

  public List<Message> findAllMessages() {
    return messageRepository.findAll().stream().toList();
  }
}
