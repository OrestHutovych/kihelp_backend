package org.example.kihelp_back;

import org.example.kihelp_back.global.api.idencoder.IdEncoderConfigurationProperties;
import org.example.kihelp_back.user.service.RoleService;
import org.example.kihelp_back.wallet.config.MonobankConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({MonobankConfig.class, IdEncoderConfigurationProperties.class})
public class KihelpBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(KihelpBackApplication.class, args);
    }


    // todo delete it before deploy in product
    @Bean
    public CommandLineRunner initRoles(RoleService roleService) {
        return args -> {
            roleService.createRolesIfNotExists(List.of("ROLE_USER", "ROLE_ADMIN", "ROLE_DEVELOPER"));
        };
    }
}
