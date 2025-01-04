package org.example.kihelp_back.support.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.example.kihelp_back.support.util.ErrorMessage.MESSAGE_NO_BLANK;

public record SupportDto(
        @NotBlank(message = MESSAGE_NO_BLANK)
        String message,
        List<MultipartFile> files
) {
}
