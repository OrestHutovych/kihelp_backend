package org.example.kihelp_back.wallet.dto;

import java.util.List;
import java.util.Map;

public record MonobankJarInfoDto(
        List<Map<String, String>> jars
) {
}
