package org.example.kihelp_back.task.usecase.impl;

import org.example.kihelp_back.global.api.idencoder.IdEncoderApiRepository;
import org.example.kihelp_back.history.model.History;
import org.example.kihelp_back.history.model.HistoryStatus;
import org.example.kihelp_back.history.service.HistoryService;
import org.example.kihelp_back.task.dto.TaskGenerateDto;
import org.example.kihelp_back.task.dto.TaskProcessCreateDto;
import org.example.kihelp_back.task.model.Task;
import org.example.kihelp_back.task.service.TaskService;
import org.example.kihelp_back.task.usecase.TaskProcessUseCase;
import org.example.kihelp_back.user.exception.UserIsBannedException;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.service.UserService;
import org.example.kihelp_back.wallet.service.WalletService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.example.kihelp_back.task.util.TaskErrorMessage.USER_BANNED_BY_RESELLER_ACTIVITY;
import static org.example.kihelp_back.user.util.UserErrorMessage.USER_IS_BANNED;

@Component
public class TaskProcessUseCaseFacade implements TaskProcessUseCase {
    private final TaskService taskService;
    private final HistoryService historyService;
    private final UserService userService;
    private final WalletService walletService;
    private final IdEncoderApiRepository idEncoderApiRepository;

    public TaskProcessUseCaseFacade(TaskService taskService,
                                    HistoryService historyService,
                                    UserService userService,
                                    WalletService walletService, IdEncoderApiRepository idEncoderApiRepository) {
        this.taskService = taskService;
        this.historyService = historyService;
        this.userService = userService;
        this.walletService = walletService;
        this.idEncoderApiRepository = idEncoderApiRepository;
    }

    @Override
    @Transactional
    public Map<String, String> processTask(TaskProcessCreateDto request) {
        User targetUser = userService.findByJwt();
        Long decodeTaskId = idEncoderApiRepository.findEncoderByName("task").decode(request.taskId()).get(0);
        Task task = taskService.getTaskById(decodeTaskId);
        BigDecimal price = task.getPrice();
        Map<String, String> processResponse = new HashMap<>();

        if (targetUser.isBanned()) {
            throw new UserIsBannedException(String.format(USER_IS_BANNED, targetUser.getTelegramId()));
        }

        boolean isDetected = historyService.detectResellerActivity(targetUser.getTelegramId(), decodeTaskId);

        if(isDetected) {
            userService.changeBan(targetUser.getTelegramId(), true);
            throw new UserIsBannedException(String.format(USER_BANNED_BY_RESELLER_ACTIVITY, targetUser.getTelegramId()));
        }

        if (targetUser.getRoles().stream().noneMatch(role -> "ROLE_ADMIN".equals(role.getName()))) {
            boolean isDeveloper = task.getDeveloper().getTelegramId().equals(targetUser.getTelegramId()) &&
                    targetUser.getRoles().stream().anyMatch(role -> "ROLE_DEVELOPER".equals(role.getName()));

            if (!isDeveloper) {
                if (task.getDiscount().compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal discount = task.getDiscount().divide(BigDecimal.valueOf(100), MathContext.DECIMAL64);
                    price = price.subtract(price.multiply(discount));
                }

                walletService.withdrawAmountFromWalletByUserId(targetUser.getId(), price, true);
                walletService.depositAmountToWalletByUserId(task.getDeveloper().getId(), price, false);
            }
        }

        if (task.isAutoGenerate()) {
            processAutoGeneratedTask(targetUser, task, request, processResponse);
        } else {
            processManualTask(targetUser, task, request, processResponse);
        }

        return processResponse;
    }

    private void processAutoGeneratedTask(User targetUser, Task task, TaskProcessCreateDto request, Map<String, String> processResponse) {
        TaskGenerateDto taskProcessResponse = taskService.process(targetUser, task, request);

        History historyToSave = History
                .builder()
                .name(taskProcessResponse.fileName())
                .link(taskProcessResponse.link())
                .arguments(request.arguments().toString())
                .status(HistoryStatus.COMPLETED)
                .createdAt(taskProcessResponse.createdAt())
                .user(targetUser)
                .task(task)
                .build();

        historyService.save(historyToSave);

        processResponse.put("taskTitle", task.getTitle());
        processResponse.put("subjectTitle", task.getTeacher().getSubject().getName());
        processResponse.put("developerName", task.getDeveloper().getUsername());
        processResponse.put("fileName", taskProcessResponse.fileName());
        processResponse.put("link", taskProcessResponse.link());
        processResponse.put("createdAt", taskProcessResponse.createdAt().toString());
    }

    private void processManualTask(User targetUser, Task task, TaskProcessCreateDto request, Map<String, String> processResponse) {
        History historyToSave = History
                .builder()
                .arguments(request.arguments().toString())
                .status(HistoryStatus.IN_PROGRESS)
                .createdAt(Instant.now())
                .user(targetUser)
                .task(task)
                .build();

        historyService.save(historyToSave);

        processResponse.put("taskTitle", task.getTitle());
        processResponse.put("subjectTitle", task.getTeacher().getSubject().getName());
        processResponse.put("developerName", task.getDeveloper().getUsername());
        processResponse.put("message", "Протягом 1 години розробник відпише вам, " +
                "щоб уточнити деталі для подальшої розробки" +
                "завдання відносно вашого запиту.");
        processResponse.put("createdAt", Instant.now().toString());
    }
}
