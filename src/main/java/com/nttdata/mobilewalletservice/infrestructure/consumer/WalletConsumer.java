package com.nttdata.mobilewalletservice.infrestructure.consumer;

import com.nttdata.mobilewalletservice.infrestructure.model.dto.WalletTransactionDto;
import com.nttdata.mobilewalletservice.infrestructure.producer.WalletProducer;
import com.nttdata.mobilewalletservice.infrestructure.rest.service.WalletCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Slf4j
@Service
public class WalletConsumer {

    @Autowired
    private WalletCrudService walletCrudService;

    @Autowired
    private WalletProducer walletProducer;


    @KafkaListener(
            topics = "${custom.kafka.topic-name}",
            groupId = "${custom.kafka.group-id}",
            containerFactory = "walletConcurrentKafkaListenerContainerFactory")
    public void consumer(@Payload WalletTransactionDto walletTransactionDto, @Headers MessageHeaders headers) {
        log.info("message received [{}]", walletTransactionDto);
        this.validData(walletTransactionDto);
        System.out.println("Sent message!");
    }

    public void validData(WalletTransactionDto walletTransactionDto) {
        walletCrudService.findByNumberPhone(walletTransactionDto.getOriginNumberPhone())
                .flatMap(walletOrigin -> {
                    if (walletOrigin.getBalance() >= walletTransactionDto.getAmount()) {
                        walletOrigin.setBalance((walletOrigin.getBalance() - walletTransactionDto.getAmount()));
                        walletCrudService.save(walletOrigin) //Update wallet origin
                                .flatMap(walletUpdate -> walletCrudService.findByNumberPhone(walletTransactionDto.getDestinyNumberPhone()))
                                .flatMap(walletDestiny -> {
                                    walletDestiny.setBalance(walletDestiny.getBalance() + walletTransactionDto.getAmount());
                                    return walletCrudService.save(walletDestiny); //Update wallet destiny
                                }).subscribe(w -> log.info("Updated All"));
                        walletTransactionDto.setState(WalletTransactionDto.State.SUCCESSFUL);
                    } else {
                        //Transaccion rechazada
                        walletTransactionDto.setState(WalletTransactionDto.State.REJECTED);
                    }
                    this.walletProducer.producer(walletTransactionDto);

                    return Mono.just(walletOrigin);
                }).subscribe();
    }

}