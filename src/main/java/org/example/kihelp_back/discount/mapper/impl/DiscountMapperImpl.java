package org.example.kihelp_back.discount.mapper.impl;

import org.example.kihelp_back.discount.dto.DiscountCreateDto;
import org.example.kihelp_back.discount.mapper.DiscountMapper;
import org.example.kihelp_back.discount.model.Discount;
import org.example.kihelp_back.discount.model.DiscountType;
import org.example.kihelp_back.global.api.idencoder.IdEncoderApiRepository;
import org.example.kihelp_back.task.exception.TypeNotValidException;
import org.example.kihelp_back.task.model.Task;
import org.example.kihelp_back.task.model.TaskType;
import org.example.kihelp_back.task.service.TaskService;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.service.UserService;
import org.springframework.stereotype.Component;

import static org.example.kihelp_back.task.util.TaskErrorMessage.TYPE_NOT_VALID;

@Component
public class DiscountMapperImpl implements DiscountMapper {
    private final UserService userService;
    private final TaskService taskService;
    private final IdEncoderApiRepository idEncoderApiRepository;

    public DiscountMapperImpl(UserService userService,
                              TaskService taskService, IdEncoderApiRepository idEncoderApiRepository) {
        this.userService = userService;
        this.taskService = taskService;
        this.idEncoderApiRepository = idEncoderApiRepository;
    }

    @Override
    public Discount toEntity(DiscountCreateDto createDto) {
        if (createDto == null) {
            return null;
        }

        Discount discount = new Discount();
        System.out.println(createDto);

        discount.setDiscountValue(createDto.discountValue());
        discount.setType(resolveType(createDto.type()));

        if(createDto.telegramId() != null) {
            User user = userService.findByTelegramId(createDto.telegramId());

            discount.setUser(user);
            discount.setAvailableValue(createDto.availableValue());
        }

        Task task = taskService.getTaskById(decodeTaskId(createDto.taskId()));
        discount.setTask(task);

        return discount;
    }

    private DiscountType resolveType(String type) {
        try {
            return DiscountType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format(TYPE_NOT_VALID, type));
        }
    }

    private Long decodeTaskId(String taskId) {
        return idEncoderApiRepository.findEncoderByName("task").decode(taskId).get(0);
    }
}
