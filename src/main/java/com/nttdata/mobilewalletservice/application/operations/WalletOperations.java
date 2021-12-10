package com.nttdata.mobilewalletservice.application.operations;

import com.nttdata.mobilewalletservice.domain.Wallet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WalletOperations {
    Flux<Wallet> findAll();

    Mono<Wallet> findById(String id);

    Mono<Wallet> save(Wallet wallet);

    Mono<Wallet> update(String id, Wallet wallet);

    Mono<Void> delete(String id);

    Mono<Wallet> findByNumberPhone(String numberPhone);

}
