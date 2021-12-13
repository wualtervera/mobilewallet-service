package com.nttdata.mobilewalletservice.infrestructure.rest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.mobilewalletservice.application.model.WalletRepository;
import com.nttdata.mobilewalletservice.domain.Wallet;
import com.nttdata.mobilewalletservice.infrestructure.model.dao.WalletDao;
import com.nttdata.mobilewalletservice.infrestructure.rest.repository.WalletCrudRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Component
public class WalletCrudService implements WalletRepository {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WalletCrudRepository walletCrudRepository;

    @Override
    public Flux<Wallet> findAll() {
        return walletCrudRepository.findAll().map(this::toWallet);
    }

    @Override
    public Mono<Wallet> findById(String id) {
        return walletCrudRepository.findById(id).map(this::toWallet);
    }

    @Override
    public Mono<Wallet> save(Wallet wallet) {
        if (wallet.getCreateAt() == null)
            wallet.setCreateAt(LocalDateTime.now());
        return walletCrudRepository.save(this.toWalletDao(wallet)).map(this::toWallet);
    }

    @Override
    public Mono<Wallet> update(String id, Wallet wallet) {
        return walletCrudRepository.findById(id)
                .switchIfEmpty(Mono.empty())
                .flatMap(walletDao -> {
                    wallet.setId(walletDao.getId());
                    wallet.setCreateAt(walletDao.getCreateAt());
                    return walletCrudRepository.save(this.toWalletDao(wallet)).map(this::toWallet);
                });
    }

    @Override
    public Mono<Void> delete(String id) {
        return walletCrudRepository.deleteById(id);
    }

    @Override
    public Mono<Wallet> findByNumberPhone(String numberPhone) {
        return walletCrudRepository.findByNumberPhone(numberPhone);
    }


    public Wallet toWallet(WalletDao walletDao) {
        /*Wallet wallet = new Wallet();
        BeanUtils.copyProperties(walletDao, wallet);
        return wallet;*/
        return objectMapper.convertValue(walletDao, Wallet.class);
    }

    public WalletDao toWalletDao(Wallet wallet) {
        /*WalletDao walletDao = new WalletDao();
        BeanUtils.copyProperties(wallet, walletDao);
        return walletDao;*/
        return objectMapper.convertValue(wallet, WalletDao.class);
    }



}
