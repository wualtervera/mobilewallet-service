package com.nttdata.mobilewalletservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MobilewalletServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobilewalletServiceApplication.class, args);
	}

}
