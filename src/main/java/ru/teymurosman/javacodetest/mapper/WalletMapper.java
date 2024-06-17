package ru.teymurosman.javacodetest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.teymurosman.javacodetest.dto.WalletDto;
import ru.teymurosman.javacodetest.model.Wallet;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WalletMapper {

    Wallet toWallet(WalletDto walletDto);

    WalletDto toWalletDto(Wallet wallet);
}
