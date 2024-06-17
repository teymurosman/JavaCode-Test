package ru.teymurosman.javacodetest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.teymurosman.javacodetest.dto.OperationDto;
import ru.teymurosman.javacodetest.dto.OperationType;
import ru.teymurosman.javacodetest.exception.EntityNotFoundException;
import ru.teymurosman.javacodetest.exception.InsufficientFundsException;
import ru.teymurosman.javacodetest.mapper.OperationMapper;
import ru.teymurosman.javacodetest.mapper.WalletMapper;
import ru.teymurosman.javacodetest.model.Operation;
import ru.teymurosman.javacodetest.model.Wallet;
import ru.teymurosman.javacodetest.repository.OperationRepository;
import ru.teymurosman.javacodetest.repository.WalletRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WalletServiceImplTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private OperationRepository operationRepository;

    @InjectMocks
    private WalletServiceImpl walletService;

    @Spy
    WalletMapper walletMapper = Mappers.getMapper(WalletMapper.class);

    @Spy
    OperationMapper operationMapper = Mappers.getMapper(OperationMapper.class);

    private Wallet wallet;
    private UUID walletId;
    private OperationDto operationDto;

    @BeforeEach
    void setUp() {
        walletId = UUID.randomUUID();
        wallet = new Wallet();
        wallet.setId(walletId);
        wallet.setBalance(BigDecimal.valueOf(1000));

        operationDto = new OperationDto();
        operationDto.setWalletId(walletId);
    }

    @Test
    void testDeposit() {
        operationDto.setOperationType(OperationType.DEPOSIT);
        operationDto.setAmount(BigDecimal.valueOf(500));

        when(walletRepository.findByIdForTransfer(walletId))
                .thenReturn(Optional.of(wallet));

        var updatedWallet = walletService.transfer(operationDto);

        assertEquals(BigDecimal.valueOf(1500), updatedWallet.getBalance());
        verify(walletRepository, times(1)).save(wallet);
        verify(operationRepository, times(1)).save(any(Operation.class));
    }

    @Test
    void testWithdraw() {
        operationDto.setOperationType(OperationType.WITHDRAW);
        operationDto.setAmount(BigDecimal.valueOf(500));

        when(walletRepository.findByIdForTransfer(walletId))
                .thenReturn(Optional.of(wallet));

        var updatedWallet = walletService.transfer(operationDto);

        assertEquals(BigDecimal.valueOf(500), updatedWallet.getBalance());
        verify(walletRepository, times(1)).save(wallet);
        verify(operationRepository, times(1)).save(any(Operation.class));
    }

    @Test
    void testWithdrawInsufficientFunds() {
        operationDto.setOperationType(OperationType.WITHDRAW);
        operationDto.setAmount(BigDecimal.valueOf(1500));

        when(walletRepository.findByIdForTransfer(walletId))
                .thenReturn(Optional.of(wallet));

        assertThrows(InsufficientFundsException.class, () -> walletService.transfer(operationDto));
        verify(walletRepository, never()).save(wallet);
        verify(operationRepository, never()).save(any(Operation.class));
    }

    @Test
    void testWalletNotFound() {
        operationDto.setOperationType(OperationType.DEPOSIT);
        operationDto.setAmount(BigDecimal.valueOf(500));

        when(walletRepository.findByIdForTransfer(walletId))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> walletService.transfer(operationDto));
    }

    @Test
    void testGetBalance() {
        when(walletRepository.findById(walletId))
                .thenReturn(Optional.of(wallet));

        var result = walletService.getBalance(walletId);

        assertEquals(wallet.getId(), result.getId());
        assertEquals(wallet.getBalance(), result.getBalance());
        verify(walletRepository, times(1)).findById(walletId);
    }

    @Test
    void testGetBalanceNotFound() {
        when(walletRepository.findById(walletId))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> walletService.getBalance(walletId));
    }
}
