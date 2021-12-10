package com.nttdata.mobilewalletservice.infrestructure.config;

import java.util.HashMap;
import java.util.Map;

import com.nttdata.mobilewalletservice.infrestructure.model.dto.WalletTransactionDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class KafkaConsumerConfig {

    @Value("${custom.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${custom.kafka.group-id}")
    private String groupId;

    public ConsumerFactory<String, WalletTransactionDto> walletTransactionDtoConsumerFactory() {

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(WalletTransactionDto.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, WalletTransactionDto> walletConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, WalletTransactionDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(walletTransactionDtoConsumerFactory());
        return factory;
    }
}
