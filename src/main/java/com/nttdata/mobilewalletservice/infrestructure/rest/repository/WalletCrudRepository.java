package com.nttdata.mobilewalletservice.infrestructure.rest.repository;


import com.nttdata.mobilewalletservice.domain.Wallet;
import com.nttdata.mobilewalletservice.infrestructure.model.dao.WalletDao;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface WalletCrudRepository extends ReactiveCrudRepository<WalletDao, String> {
    Mono<Wallet> findByNumberPhone(String numberPhone);
}
