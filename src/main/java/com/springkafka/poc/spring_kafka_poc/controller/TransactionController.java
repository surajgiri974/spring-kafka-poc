package com.springkafka.poc.spring_kafka_poc.controller;

import com.springkafka.poc.spring_kafka_poc.service.TransactionProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private final TransactionProducerService producer;

    public TransactionController(TransactionProducerService producer) {
        this.producer = producer;
    }

    @GetMapping("/send")
    public String sendTransaction() {
        producer.sendTransaction();
        return "Transaction sent!";
    }
}
