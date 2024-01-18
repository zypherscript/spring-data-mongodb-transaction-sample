package com.example.springdatamongodbtransactionsample;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(properties = {"spring.data.mongodb.database=test"})
@Testcontainers
class SpringDataMongodbTransactionSampleApplicationTests {

  @Autowired
  private MessageService messageService;

  //  @Container vs Prematurely reached end of stream
  static MongoDBContainer mongoDbContainer = new MongoDBContainer(
      DockerImageName.parse("mongo:latest"))
      .withEnv("MONGO_INITDB_DATABASE", "test");

  static {
    mongoDbContainer.start();
  }

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.mongodb.uri", mongoDbContainer::getReplicaSetUrl);
  }

  @Test
  void testTransactional() {
    assertThrows(RuntimeException.class, () -> messageService.saveTestMessage());
    assertTrue(messageService.findAllMessages().isEmpty());
  }

}
