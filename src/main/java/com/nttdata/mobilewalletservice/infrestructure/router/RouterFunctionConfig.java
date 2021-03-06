package com.nttdata.mobilewalletservice.infrestructure.router;

import com.nttdata.mobilewalletservice.infrestructure.rest.handler.WalletHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterFunctionConfig {
    String uri = "api/v1/wallet";

    @Bean
    public RouterFunction<ServerResponse> routes(WalletHandler walletHandler) {
        return route(GET(uri), walletHandler::getall)
                .andRoute(GET(uri.concat("/phone/{number}")), walletHandler::getIdPhone)
                .andRoute(GET(uri.concat("/{id}")), walletHandler::getOne)
                .andRoute(POST(uri), walletHandler::save)
                .andRoute(PUT(uri.concat("/{id}")), walletHandler::update)
                .andRoute(DELETE(uri.concat("/{id}")), walletHandler::delete);
                //.andRoute(GET(uri.concat("/getAllRedis")), walletHandler::getAllRedis);


    }

}
