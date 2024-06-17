package ru.teymurosman.javacodetest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.teymurosman.javacodetest.dto.OperationDto;
import ru.teymurosman.javacodetest.dto.WalletDto;
import ru.teymurosman.javacodetest.service.WalletService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/wallet")
    public ResponseEntity<WalletDto> transfer(@RequestBody @Valid OperationDto operationDto) {
        return new ResponseEntity<>(walletService.transfer(operationDto), HttpStatus.CREATED);
    }

    @GetMapping("/wallets/{WALLET_UUID}")
    public ResponseEntity<WalletDto> getBalance(@PathVariable(value = "WALLET_UUID") UUID walletId) {
        return new ResponseEntity<>(walletService.getBalance(walletId), HttpStatus.OK);
    }
}
