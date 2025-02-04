package org.example.kihelp_back.history.dto;

import org.springframework.web.multipart.MultipartFile;

public record HistorySaveFileDto(
        MultipartFile file
) {
}
