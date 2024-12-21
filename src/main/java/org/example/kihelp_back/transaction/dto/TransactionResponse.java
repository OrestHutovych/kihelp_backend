package org.example.kihelp_back.transaction.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

public record TransactionResponse (
        Long id,
        String transactionId,
        String initials,
        BigDecimal amount,
        Instant createdTimeStamp,
        Long userId
) implements Serializable {
}
