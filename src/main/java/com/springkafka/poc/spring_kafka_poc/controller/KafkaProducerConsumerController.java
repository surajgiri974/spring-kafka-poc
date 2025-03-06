package com.springkafka.poc.spring_kafka_poc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springkafka.poc.spring_kafka_poc.config.KafkaProducerConsumerConfiguration;
import com.springkafka.poc.spring_kafka_poc.service.KafkaProducerConsumerService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/producer")
public class KafkaProducerConsumerController {

    @Autowired
    private KafkaProducerConsumerConfiguration configuration;

    @Autowired
    private KafkaProducerConsumerService kafkaProducerConsumerService ;

    @PostMapping("/send/{message}")
    public ResponseEntity<?> publishMessage(@PathVariable String message) {
        try{
            kafkaProducerConsumerService.sendMessageToTopic(message);
            return ResponseEntity.ok("Message Send...");
        }catch(Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/create-topic/{name}/{partition}")
    public ResponseEntity<String> createNewTopic(@PathVariable String name,@PathVariable int partition) {
        try{
            configuration.creaNewTopic(name, partition);
            return ResponseEntity.ok("Topic Created");
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
    

}
