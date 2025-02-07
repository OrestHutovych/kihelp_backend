package org.example.kihelp_back.transaction.controller;

import org.example.kihelp_back.transaction.dto.TransactionDto;
import org.example.kihelp_back.transaction.dto.TransactionPageDto;
import org.example.kihelp_back.transaction.dto.TransactionWithdrawStatusDto;
import org.example.kihelp_back.transaction.usecase.TransactionUpdateUseCase;
import org.example.kihelp_back.transaction.usecase.TransactionGetUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionGetUseCase transactionGetUseCase;
    private final TransactionUpdateUseCase transactionUpdateUseCase;

    public TransactionController(TransactionGetUseCase transactionGetUseCase,
                                 TransactionUpdateUseCase transactionUpdateUseCase) {
        this.transactionGetUseCase = transactionGetUseCase;
        this.transactionUpdateUseCase = transactionUpdateUseCase;
    }

    @GetMapping("/history/user/{telegram_id}")
    public Collection<TransactionDto> getTransactions(@PathVariable("telegram_id") String telegramId,
                                                      @RequestParam(name = "page") int page,
                                                      @RequestParam(name = "limit") int limit) {
        TransactionPageDto pageDto = new TransactionPageDto(page, limit);
        return transactionGetUseCase.getAllTransactionsByUserTelegramId(telegramId, pageDto);
    }

    @PatchMapping("/toggle_withdraw_status")
    public void toggleWithdrawStatus(@RequestBody TransactionWithdrawStatusDto request){
        transactionUpdateUseCase.toggleWithdrawStatus(request);
    }
}
