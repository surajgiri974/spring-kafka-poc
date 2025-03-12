package com.springkafka.poc.spring_kafka_poc.service;

import com.springkafka.poc.spring_kafka_poc.model.Transaction;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;


import java.util.Properties;

@Service
public class FraudDetectionService {
    private static final String INPUT_TOPIC = "transactions";
    private static final String OUTPUT_TOPIC = "fraud-alerts";

    public FraudDetectionService() {

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "fraud-detection-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> transactions = builder.stream(INPUT_TOPIC);

        KStream<String, String> fraudulentTransactions = transactions.filter((key, value) -> {
            Transaction tx = new Gson().fromJson(value, Transaction.class);
            return tx.getAmount() > 500;  // Fraud rule: flag transactions > $500
        });

        fraudulentTransactions.to(OUTPUT_TOPIC);

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }

}
