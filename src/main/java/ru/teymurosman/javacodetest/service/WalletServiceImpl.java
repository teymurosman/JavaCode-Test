package ru.teymurosman.javacodetest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.teymurosman.javacodetest.dto.OperationDto;
import ru.teymurosman.javacodetest.dto.WalletDto;
import ru.teymurosman.javacodetest.exception.EntityNotFoundException;
import ru.teymurosman.javacodetest.exception.InsufficientFundsException;
import ru.teymurosman.javacodetest.mapper.OperationMapper;
import ru.teymurosman.javacodetest.mapper.WalletMapper;
import ru.teymurosman.javacodetest.model.Wallet;
import ru.teymurosman.javacodetest.repository.OperationRepository;
import ru.teymurosman.javacodetest.repository.WalletRepository;

import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final OperationRepository operationRepository;
    private final WalletMapper walletMapper;
    private final OperationMapper operationMapper;

    @Transactional
    @Override
    public WalletDto transfer(OperationDto operationDto) {
        log.debug("Transfer operationDto: {}", operationDto);

        var walletId = operationDto.getWalletId();
        Wallet wallet = walletRepository.findByIdForTransfer(walletId)
                .orElseThrow(() -> new EntityNotFoundException("Wallet with id: " + walletId + " not found"));

        switch (operationDto.getOperationType()) {
            case DEPOSIT:
                wallet.setBalance(wallet.getBalance().add(operationDto.getAmount()));
                break;
            case WITHDRAW:
                if (wallet.getBalance().compareTo(operationDto.getAmount()) < 0) {
                    throw new InsufficientFundsException("Withdraw amount exceeds balance");
                }
                wallet.setBalance(wallet.getBalance().subtract(operationDto.getAmount()));
                break;
        }
        walletRepository.save(wallet);
        operationRepository.save(operationMapper.toOperation(operationDto));

        return walletMapper.toWalletDto(wallet);
    }

    @Override
    public WalletDto getBalance(UUID walletId) {
        log.debug("Get balance of wallet {}", walletId);

        Wallet wallet = findWalletOrThrow(walletId);
        return walletMapper.toWalletDto(wallet);
    }

    private Wallet findWalletOrThrow(UUID walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new EntityNotFoundException("Wallet with id " + walletId + " not found"));
    }
}
