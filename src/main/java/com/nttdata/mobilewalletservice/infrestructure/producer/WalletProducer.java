package com.nttdata.mobilewalletservice.infrestructure.producer;

import com.nttdata.mobilewalletservice.infrestructure.model.dto.WalletTransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class WalletProducer {

    @Value("${custom.kafka.topic-name-mobilewallet}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, WalletTransactionDto> walletTransactionDtoKafkaTemplate;

    public void producer(WalletTransactionDto walletTransactionDto) {
        walletTransactionDtoKafkaTemplate.send(topicName, walletTransactionDto);
    }

}