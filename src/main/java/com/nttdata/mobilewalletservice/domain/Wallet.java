package com.nttdata.mobilewalletservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Wallet implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private String typeDcoument;
    private String numberDocument;
    private String numberPhone;
    private String imeiPhone;
    private String email;
    private Double balance;
    private LocalDateTime createAt;
}
