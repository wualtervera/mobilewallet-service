package com.nttdata.mobilewalletservice.infrestructure.spring.config;

import com.nttdata.mobilewalletservice.application.model.WalletRepository;
import com.nttdata.mobilewalletservice.infrestructure.rest.service.WalletCrudService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {

    @Bean
    public WalletRepository walletRepository(){
        return new WalletCrudService();
    }
}
