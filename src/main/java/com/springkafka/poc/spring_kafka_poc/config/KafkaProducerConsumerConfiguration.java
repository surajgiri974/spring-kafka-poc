package com.springkafka.poc.spring_kafka_poc.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaProducerConsumerConfiguration {

        public NewTopic creaNewTopic(String topicName, int paritionNumber){
            try{
                return new NewTopic(topicName, paritionNumber,(short)1);
            }catch(Exception e){
                System.err.println("Error Creating Topic: "+e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
        }

}
