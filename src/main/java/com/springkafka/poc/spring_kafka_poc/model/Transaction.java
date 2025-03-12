package com.springkafka.poc.spring_kafka_poc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    private String transactionId;
    private String userId;
    private double amount;
    private String location;
    private long timestamp;

}
