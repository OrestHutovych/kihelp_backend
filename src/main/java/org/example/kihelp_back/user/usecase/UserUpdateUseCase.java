package org.example.kihelp_back.user.usecase;

import jakarta.validation.Valid;
import org.example.kihelp_back.user.dto.UserCourseDto;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UserUpdateUseCase {
    void changeBanValueByUser(String telegramId, boolean value);
    void toggleRole(String telegramId, String roleName);
    void enterCourseNumber(@Valid UserCourseDto userCourseDto);
}
