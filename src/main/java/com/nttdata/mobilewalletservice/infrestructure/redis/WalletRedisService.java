package com.nttdata.mobilewalletservice.infrestructure.redis;

import com.nttdata.mobilewalletservice.domain.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WalletRedisService {
    public static final String KEY = "wallet";

    @Autowired
    private ReactiveRedisTemplate<String, Wallet> redisTemplate;

    public Mono<Long> save(Wallet wallet) {
        System.out.println("wallet save in redis = " + wallet);
        return redisTemplate.opsForList().rightPush(KEY, wallet);
    }

    public Flux<Wallet> getAll() {
        System.out.println("wallet GetAll");
        return redisTemplate.opsForList().range(KEY, 0, -1);
    }
}
