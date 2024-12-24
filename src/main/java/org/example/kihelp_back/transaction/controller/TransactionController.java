package org.example.kihelp_back.transaction.controller;

import jakarta.validation.Valid;
import org.example.kihelp_back.transaction.dto.TransactionDepositDto;
import org.example.kihelp_back.transaction.dto.TransactionResponse;
import org.example.kihelp_back.transaction.dto.TransactionWithdrawDto;
import org.example.kihelp_back.transaction.dto.TransactionWithdrawStatusDto;
import org.example.kihelp_back.transaction.usecase.TransactionCreateUseCase;
import org.example.kihelp_back.transaction.usecase.TransactionUpdateUseCase;
import org.example.kihelp_back.transaction.usecase.TransactionGetUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    private final TransactionCreateUseCase transactionCreateUseCase;
    private final TransactionGetUseCase transactionGetUseCase;
    private final TransactionUpdateUseCase transactionUpdateUseCase;

    public TransactionController(TransactionCreateUseCase transactionCreateUseCase,
                                 TransactionGetUseCase transactionGetUseCase,
                                 TransactionUpdateUseCase transactionUpdateUseCase) {
        this.transactionCreateUseCase = transactionCreateUseCase;
        this.transactionGetUseCase = transactionGetUseCase;
        this.transactionUpdateUseCase = transactionUpdateUseCase;
    }

    @PostMapping("/deposit/{telegram_id}")
    public void deposit(@PathVariable("telegram_id") String telegramId,
                                  @RequestBody @Valid TransactionDepositDto request){
        transactionCreateUseCase.depositWallet(telegramId, request);
    }

    @PostMapping("/withdraw/{telegram_id}")
    public void withdraw(@PathVariable("telegram_id") String telegramId,
                         @RequestBody TransactionWithdrawDto request){
        transactionCreateUseCase.withdrawWallet(telegramId, request);
    }

    @GetMapping("/{telegram_id}")
    public List<TransactionResponse> getTransactions(@PathVariable("telegram_id") String telegramId) {
        return transactionGetUseCase.getAllTransactionsByUserTelegramId(telegramId);
    }

    @PutMapping("/toggle_withdraw_status")
    public void toggleWithdrawStatus(@RequestBody TransactionWithdrawStatusDto request){
        transactionUpdateUseCase.toggleWithdrawStatus(request);
    }
}
