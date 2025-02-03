package org.example.kihelp_back.discount.dto;

import java.math.BigDecimal;

public record DiscountCreateDto(
        String type,
        BigDecimal discountValue,
        Integer availableValue,
        String telegramId,
        String taskId
) {
}
