package com.hhj.quizCraft.config;


import com.hhj.quizCraft.constant.KeyConstant;
import com.zhipu.oapi.ClientV4;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ai")
@Data
public class AiConfig {
    /**
     * apiKey
     */

    private String apiKey;

    @Bean
    public ClientV4 getClientV4() {
        return new ClientV4.Builder(apiKey).build();
    }
}
