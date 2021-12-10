package com.nttdata.mobilewalletservice.infrestructure.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("wallet")
public class WalletDao {
    @Id
    private String id;
    @NonNull
    private String typeDcoument;
    @NonNull
    private String numberDocument;
    @NonNull
    private String numberPhone;
    @NonNull
    private String imeiPhone;
    @NonNull
    private String email;
    @NonNull
    private Double balance;
    @NonNull
    private LocalDateTime createAt;
}
