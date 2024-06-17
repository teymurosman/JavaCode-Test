package ru.teymurosman.javacodetest.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class OperationDto {

    @NotNull(message = "walletId must not be null.")
    private UUID walletId;

    @NotNull(message = "operation type must not be null.")
    private OperationType operationType;

    @Positive(message = "amount of transfer must be positive.")
    private BigDecimal amount;
}
