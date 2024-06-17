package ru.teymurosman.javacodetest.service;

import ru.teymurosman.javacodetest.dto.OperationDto;
import ru.teymurosman.javacodetest.dto.WalletDto;

import java.util.UUID;

public interface WalletService {

    WalletDto transfer(OperationDto operationDto);

    WalletDto getBalance(UUID walletId);
}
