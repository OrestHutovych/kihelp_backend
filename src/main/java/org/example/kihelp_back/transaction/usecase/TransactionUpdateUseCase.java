package org.example.kihelp_back.transaction.usecase;


import org.example.kihelp_back.transaction.dto.TransactionWithdrawStatusDto;

public interface TransactionUpdateUseCase {
    void toggleWithdrawStatus(TransactionWithdrawStatusDto request);
}
