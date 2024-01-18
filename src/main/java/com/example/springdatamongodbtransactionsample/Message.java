package com.example.springdatamongodbtransactionsample;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("message")
public record Message(@Id Integer id, String text) {

}
