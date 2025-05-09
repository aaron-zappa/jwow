package com.example.demo;

import com.google.cloud.aiplatform.v1.PredictionServiceClient;
import com.google.cloud.aiplatform.v1.PredictionServiceSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class GoogleCloudConfig {

    @Bean
    public PredictionServiceClient predictionServiceClient() throws IOException {
        PredictionServiceSettings predictionServiceSettings = PredictionServiceSettings.newBuilder().build();
        return PredictionServiceClient.create(predictionServiceSettings);
    }
}