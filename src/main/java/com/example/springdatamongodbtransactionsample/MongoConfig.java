package com.example.springdatamongodbtransactionsample;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

  private String databaseName;
  private String mongoUri;

  @Autowired
  public void setDatabaseName(@Value("${spring.data.mongodb.database}") String databaseName) {
    this.databaseName = databaseName;
  }

  @Autowired
  public void setMongoUri(@Value("${spring.data.mongodb.uri}") String mongoUri) {
    this.mongoUri = mongoUri;
  }

  @Bean
  MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
    return new MongoTransactionManager(dbFactory);
  }

  @Override
  protected String getDatabaseName() {
    return this.databaseName;
  }

  @Override
  public MongoClient mongoClient() {
    final ConnectionString connectionString = new ConnectionString(this.mongoUri);
    final MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
        .applyConnectionString(connectionString)
        .build();
    return MongoClients.create(mongoClientSettings);
  }
}
