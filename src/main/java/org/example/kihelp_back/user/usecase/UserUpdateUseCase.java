package org.example.kihelp_back.user.usecase;

import jakarta.validation.Valid;
import org.example.kihelp_back.user.adapters.dto.RoleUpdateDto;
import org.example.kihelp_back.user.adapters.dto.UserCourseDto;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UserUpdateUseCase {
    void changeBanValueByUser(String telegramId, boolean value);
    void toggleRole(String telegramId, RoleUpdateDto roleUpdateDto);
    void enterCourseNumber(@Valid UserCourseDto userCourseDto);
}
