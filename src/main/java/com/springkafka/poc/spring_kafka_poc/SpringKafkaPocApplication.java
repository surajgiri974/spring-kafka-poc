package com.springkafka.poc.spring_kafka_poc;

import com.springkafka.poc.spring_kafka_poc.service.KsqlStreamReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringKafkaPocApplication {

	public static void main(String[] args) {
		KsqlStreamReader ksqlStreamReader = new KsqlStreamReader();
		ksqlStreamReader.process();
		SpringApplication.run(SpringKafkaPocApplication.class, args);
	}

}
