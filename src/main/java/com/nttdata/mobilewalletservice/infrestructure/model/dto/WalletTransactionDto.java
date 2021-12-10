package com.nttdata.mobilewalletservice.infrestructure.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper=false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class WalletTransactionDto {

    private String id;
    private String originNumberPhone;
    private String destinyNumberPhone;
    private Double amount;
    private State state;
    private LocalDateTime createAt;

    public enum State {
        PENDING,
        REJECTED,
        SUCCESSFUL
    }

}
