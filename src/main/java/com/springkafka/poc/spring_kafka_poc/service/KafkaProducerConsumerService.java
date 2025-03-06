package com.springkafka.poc.spring_kafka_poc.service;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerConsumerService {

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;
    Logger logger = LoggerFactory.getLogger(KafkaProducerConsumerService.class);

    public void sendMessageToTopic(String message){
        
        CompletableFuture<SendResult<String,Object>> completableFuture = kafkaTemplate.send("MyTopics",message);
       completableFuture.whenComplete((result,ex)->{
           // result.getRecordMetadata().partition();
            if(ex == null){
                System.out.println(
                    "Send Message=[ "+ message +" ] with offset no=[ "+result.getRecordMetadata().offset() +"]"
                );
            }else{
                System.out.println(
                    "Unable to Send Message=[ "+ message +" ] with error=[ "+ex.getMessage() +"]"
                );
            }
        });

    }

    @KafkaListener(topics = "MyTopics",groupId = "my-kafaka")
    public void consumer(String message){
        logger.info("Consumer Message: {} ",message);
    } 

}
