package com.nttdata.mobilewalletservice.infrestructure.producer;

import com.nttdata.mobilewalletservice.infrestructure.model.dto.WalletTransactionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class WalletProducer {

    @Value("${custom.kafka.topic-name-mobilewallet}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, WalletTransactionDto> kafkaTemplate;

    public void producer(WalletTransactionDto walletTransactionDto) {
        log.info("Sending message....");
        Message<WalletTransactionDto> message = MessageBuilder
                .withPayload(walletTransactionDto)
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .build();
        this.kafkaTemplate.send(message);
        log.info("Sent message!");
    }



}