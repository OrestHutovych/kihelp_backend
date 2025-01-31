package org.example.kihelp_back.global.api.idencoder;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.sqids.Sqids;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class IdEncoderApiRepository {
    private Map<String, Sqids> encoderMap = new ConcurrentHashMap<>();
    private final IdEncoderConfigurationProperties idEncoderConfigurationProperties;

    public IdEncoderApiRepository(IdEncoderConfigurationProperties idEncoderConfigurationProperties) {
        this.idEncoderConfigurationProperties = idEncoderConfigurationProperties;
    }

    public Sqids findEncoderByName(String name) {
        return encoderMap.get(name);
    }

    @PostConstruct
    private void postConstruct() {
        idEncoderConfigurationProperties.getEncoder().forEach((s, encoder) ->
                encoderMap.put(s, Sqids.builder().alphabet(encoder.getAlphabet()).minLength(encoder.getMinLength()).build()));
    }
}
