package com.springkafka.poc.spring_kafka_poc.service;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class KsqlStreamReader {

    private static final Logger logger = LoggerFactory.getLogger(KsqlStreamReader.class);
    private static final String PROCESSED_MARKER = "[PROCESSED]";

    public void process() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "MyTopicProcessor");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, 3);

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> inputStream = builder.stream("MyTopics");

        // Log incoming messages
        inputStream.peek((key, value) -> logger.info("Consumed Message -> Key: {}, Value: {}", key, value));

        // Filter messages that are already processed
        KStream<String, String> filteredStream = inputStream.filter((key, value) ->
                value != null && !value.startsWith(PROCESSED_MARKER)
        );

        // Mark messages as processed and produce to a new topic
        KStream<String, String> processedStream = filteredStream.mapValues(value -> PROCESSED_MARKER + value);

        // Send the processed messages to a new topic
        processedStream.to("NewMyTopics", Produced.with(Serdes.String(), Serdes.String()));

        KafkaStreams streams = new KafkaStreams(builder.build(), props);

        // Clean up previous state (Only do this if its not storing any state or want a fresh run)
        logger.info("Cleaning up local state directory for application ID: {}", "MyTopicProcessors");
        streams.cleanUp(); // Delete previous state directory
        logger.info("Cleanup completed!");

        streams.start();
        logger.info("Kafka Streams State: {}", streams.state());

        // Shutdown hook for graceful shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutting down Kafka Streams...");
            streams.close();
            logger.info("Kafka Streams shutdown complete.");
        }));
    }
}
