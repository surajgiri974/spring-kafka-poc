package com.springkafka.poc.spring_kafka_poc.service;

import com.google.gson.Gson;
import com.springkafka.poc.spring_kafka_poc.model.Transaction;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.Random;

@Service
public class TransactionProducerService {

    private final KafkaProducer<String, String> producer;
    private static final String TOPIC = "transactions";
    private final Random random = new Random();

    public TransactionProducerService() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        this.producer = new KafkaProducer<>(props);
    }

    public void sendTransaction() {
        Transaction transaction = new Transaction(
                String.valueOf(random.nextInt(10000)),
                String.valueOf(random.nextInt(100)),
                random.nextDouble() * 1000,
                "New York",
                System.currentTimeMillis()
        );

        String transactionJson = new Gson().toJson(transaction);
        producer.send(new ProducerRecord<>(TOPIC, transaction.getTransactionId(), transactionJson));
        System.out.println("Produced Transaction: " + transactionJson);
    }

}

