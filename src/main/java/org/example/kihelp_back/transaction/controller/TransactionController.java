package org.example.kihelp_back.transaction.controller;

import jakarta.validation.Valid;
import org.example.kihelp_back.transaction.dto.TransactionCreateDto;
import org.example.kihelp_back.transaction.dto.TransactionResponse;
import org.example.kihelp_back.transaction.usecase.TransactionCreateUseCase;
import org.example.kihelp_back.transaction.usecase.TransactionGetUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionGetUseCase transactionGetUseCase;
    private final TransactionCreateUseCase transactionCreateUseCase;

    public TransactionController(TransactionGetUseCase transactionGetUseCase,
                                 TransactionCreateUseCase transactionCreateUseCase) {
        this.transactionGetUseCase = transactionGetUseCase;
        this.transactionCreateUseCase = transactionCreateUseCase;
    }

    @PostMapping("/transaction")
    public void createTransaction(@RequestBody @Valid TransactionCreateDto request){
        transactionCreateUseCase.createTransaction(request);
    }

    @GetMapping("/transaction/{telegram_id}")
    public List<TransactionResponse> getTransactions(@PathVariable("telegram_id") String telegramId) {
        return transactionGetUseCase.getAllTransactionsByUserTelegramId(telegramId);
    }
}
