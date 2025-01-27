package org.example.kihelp_back;

import org.example.kihelp_back.wallet.config.MonobankConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(MonobankConfig.class)
public class KihelpBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(KihelpBackApplication.class, args);
    }

}
