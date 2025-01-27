package org.example.kihelp_back.transaction.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record TransactionPageDto(
        @Min(0)
        int page,
        @Min(0) @Max(10)
        int limit
) {
}
