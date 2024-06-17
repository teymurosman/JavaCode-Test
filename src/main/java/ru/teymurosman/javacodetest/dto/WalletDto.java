package ru.teymurosman.javacodetest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
public class WalletDto {

    private UUID id;

    private BigDecimal balance;
}
