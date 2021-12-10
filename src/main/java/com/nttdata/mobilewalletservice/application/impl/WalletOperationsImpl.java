package com.nttdata.mobilewalletservice.application.impl;

import com.nttdata.mobilewalletservice.application.model.WalletRepository;
import com.nttdata.mobilewalletservice.application.operations.WalletOperations;
import com.nttdata.mobilewalletservice.domain.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class WalletOperationsImpl implements WalletOperations {

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public Flux<Wallet> findAll() {
        return walletRepository.findAll();
    }

    @Override
    public Mono<Wallet> findById(String id) {
        return walletRepository.findById(id);
    }

    @Override
    public Mono<Wallet> save(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    @Override
    public Mono<Wallet> update(String id, Wallet wallet) {
        return walletRepository.update(id, wallet);
    }

    @Override
    public Mono<Void> delete(String id) {
        return walletRepository.delete(id);
    }

    @Override
    public Mono<Wallet> findByNumberPhone(String numberPhone) {
        return walletRepository.findByNumberPhone(numberPhone);
    }

}
