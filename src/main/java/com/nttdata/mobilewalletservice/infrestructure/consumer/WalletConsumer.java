package com.nttdata.mobilewalletservice.infrestructure.consumer;

import com.nttdata.mobilewalletservice.infrestructure.model.dto.WalletTransactionDto;
import com.nttdata.mobilewalletservice.infrestructure.producer.WalletProducer;
import com.nttdata.mobilewalletservice.infrestructure.rest.service.WalletCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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

    public void consumer(WalletTransactionDto walletTransactionDto) {
        log.info("Mensaje consumido [{}]", walletTransactionDto);
        this.validarBalance(walletTransactionDto);
    }

    private void validarBalance(WalletTransactionDto walletTransactionDto) {

        walletCrudService.findByNumberPhone(walletTransactionDto.getOriginNumberPhone())
                .flatMap(wallet -> {

                    if (wallet.getBalance() >= walletTransactionDto.getAmount()) {

                        Double nuevoBalance = wallet.getBalance() - walletTransactionDto.getAmount();
                        wallet.setBalance(nuevoBalance);

                        walletCrudService.save(wallet)
                                .flatMap(walletSaved -> walletCrudService.findByNumberPhone(walletTransactionDto.getDestinyNumberPhone()))
                                .flatMap(walletD -> {
                                    walletD.setBalance(walletD.getBalance() + walletTransactionDto.getAmount());
                                    return walletCrudService.save(wallet);

                                }).subscribe(c -> log.info("ACTUALIZADO TODO"));

                        walletTransactionDto.setState(WalletTransactionDto.State.SUCCESSFUL);

                    } else {

                        //REVISAR SI TIENE DEBITO ASOCIADA

                        // SI NO TIENE -> RECHAZAR PETICION
                        walletTransactionDto.setState(WalletTransactionDto.State.REJECTED);
                    }
                    this.walletProducer.producer(walletTransactionDto);

                    return Mono.just(wallet);
                }).subscribe();
    }
}