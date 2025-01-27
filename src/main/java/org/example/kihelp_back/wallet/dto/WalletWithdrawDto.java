package org.example.kihelp_back.wallet.dto;

import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.math.BigDecimal;

import static org.example.kihelp_back.wallet.util.WalletErrorMessage.CARD_NUMBER_NOT_VALID;

public record WalletWithdrawDto(
        @CreditCardNumber(message = CARD_NUMBER_NOT_VALID)
        String cardNumber,
        @Positive
        BigDecimal amount
) {
}
