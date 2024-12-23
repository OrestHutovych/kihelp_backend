package org.example.kihelp_back.transaction.controller;

import jakarta.validation.Valid;
import org.example.kihelp_back.transaction.dto.TransactionDepositDto;
import org.example.kihelp_back.transaction.dto.TransactionResponse;
import org.example.kihelp_back.transaction.usecase.TransactionUpdateUseCase;
import org.example.kihelp_back.transaction.usecase.TransactionGetUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    private final TransactionGetUseCase transactionGetUseCase;
    private final TransactionUpdateUseCase transactionCreateUseCase;

    public TransactionController(TransactionGetUseCase transactionGetUseCase,
                                 TransactionUpdateUseCase transactionCreateUseCase) {
        this.transactionGetUseCase = transactionGetUseCase;
        this.transactionCreateUseCase = transactionCreateUseCase;
    }

    @PostMapping("/deposit")
    public void createTransaction(@RequestBody @Valid TransactionDepositDto request){
        transactionCreateUseCase.depositWallet(request);
    }


    @GetMapping("/{telegram_id}")
    public List<TransactionResponse> getTransactions(@PathVariable("telegram_id") String telegramId) {
        return transactionGetUseCase.getAllTransactionsByUserTelegramId(telegramId);
    }
}
