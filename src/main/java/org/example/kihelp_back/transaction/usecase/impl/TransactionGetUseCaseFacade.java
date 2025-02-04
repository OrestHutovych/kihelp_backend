package org.example.kihelp_back.transaction.usecase.impl;

import org.example.kihelp_back.transaction.dto.TransactionDto;
import org.example.kihelp_back.transaction.dto.TransactionPageDto;
import org.example.kihelp_back.transaction.exception.TransactionNotFoundException;
import org.example.kihelp_back.transaction.mapper.TransactionMapper;
import org.example.kihelp_back.transaction.model.Transaction;
import org.example.kihelp_back.transaction.service.TransactionService;
import org.example.kihelp_back.transaction.usecase.TransactionGetUseCase;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static org.example.kihelp_back.transaction.util.TransactionErrorMessage.TRANSACTIONS_NOT_BY_USER;

@Component
public class TransactionGetUseCaseFacade implements TransactionGetUseCase {
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;
    private final UserService userService;

    public TransactionGetUseCaseFacade(TransactionService transactionService,
                                       TransactionMapper transactionMapper,
                                       UserService userService) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
        this.userService = userService;
    }

    @Override
    public Collection<TransactionDto> getAllTransactionsByUserTelegramId(String telegramId, TransactionPageDto pageDto) {
        User targetUser = userService.findByTelegramId(telegramId);
        User sender = userService.findByJwt();

        if(!hasRole(sender, "ROLE_ADMIN") || !sender.equals(targetUser)) {
            Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
            Pageable pageable = PageRequest.of(pageDto.page(), pageDto.limit(), sort);
            Page<Transaction> transactions = transactionService.findCompletedTransactionsByUserId(targetUser.getId(), pageable);

            return transactions.stream()
                    .map(transactionMapper::toTransactionDto)
                    .toList();
        } else{
            throw new TransactionNotFoundException(TRANSACTIONS_NOT_BY_USER);
        }
    }

    private boolean hasRole(User user, String roleName) {
        return user.getRoles().stream().anyMatch(role -> roleName.equals(role.getName()));
    }
}
