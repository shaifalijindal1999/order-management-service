package com.ordermanagementservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

@SpringBootApplication
public class OrderManagementServiceApplication {

    private final Logger logger = LoggerFactory.getLogger(OrderManagementServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(OrderManagementServiceApplication.class, args);
    }

    @KafkaListener(id = "order-acceptance-group", topics = "order-topic")
    public void listen(String in, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.OFFSET) long offset) {

        this.logger.info("Received: {} from {} @ {}", in, topic, offset);
    }

//    @DltHandler
//    public void listenDlt(String in, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
//                          @Header(KafkaHeaders.OFFSET) long offset) {
//
//        this.logger.info("DLT Received: {} from {} @ {}", in, topic, offset);
//    }
}
