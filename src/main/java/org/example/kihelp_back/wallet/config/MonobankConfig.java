package org.example.kihelp_back.wallet.config;

import lombok.Getter;
import lombok.Setter;
import org.example.kihelp_back.wallet.dto.JarConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "monobank")
public class MonobankConfig {
    private List<JarConfig> jars;
}
