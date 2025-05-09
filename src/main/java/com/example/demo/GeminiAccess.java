package com.example.demo;

import com.google.cloud.aiplatform.v1.EndpointName;
import com.google.cloud.aiplatform.v1.PredictResponse;
import com.google.cloud.aiplatform.v1.PredictionServiceClient;
import com.google.cloud.aiplatform.v1.PredictionServiceSettings;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import java.util.List;

@Component
public class GeminiAccess implements CommandLineRunner {
    private final PredictionServiceClient predictionServiceClient;

    public GeminiAccess(PredictionServiceClient predictionServiceClient) {
        this.predictionServiceClient = predictionServiceClient;
    }

    public String askGemini(String project, String location, String endpointId, String prompt) {
        try {
                // Configure the Endpoint Name
                String endpoint = String.format(
                        "projects/%s/locations/%s/endpoints/%s", project, location, endpointId);

                // Prepare the instance (input data)
                List<Value> instancesList = new ArrayList<>();

                Struct.Builder structBuilder = Struct.newBuilder();
                structBuilder.putFields("content", Value.newBuilder().setStringValue(prompt).build());
                instancesList.add(Value.newBuilder().setStructValue(structBuilder.build()).build());

                // Prepare the parameters (model settings)
                Struct.Builder parametersBuilder = Struct.newBuilder();
                parametersBuilder.putFields("temperature", Value.newBuilder().setNumberValue(0.7).build());
                parametersBuilder.putFields("maxOutputTokens", Value.newBuilder().setNumberValue(150.0).build());

                Value parameters = Value.newBuilder().setStructValue(parametersBuilder).build();

                // Prepare the request and make the prediction
                PredictResponse response = predictionServiceClient.predict(endpoint, instancesList, parameters);

                // Process the response
                List<Value> predictions = response.getPredictionsList();
                if (!predictions.isEmpty()) {
                    Value firstPrediction = predictions.get(0);
                    if (firstPrediction.hasStructValue()) {
                        Struct struct = firstPrediction.getStructValue();
                        if (struct.containsFields("content")) {
                            // Handle potential errors or unexpected formats
                            return struct.getFields().get("content").getStringValue();
                        }
                    }
                }
        } catch (Exception e) {
            // This exception can be thrown by the predictionServiceClient.predict() method
 System.err.println("Error: " + e.getMessage());
 e.printStackTrace();
 }
 return "Error";
    }

 @Override
 public void run(String... args) throws Exception {
 String project = "192261268349";
 String location = "europe-west3";
 String endpointId = "4156941203318767616"; // Replace with your actual endpoint ID. You need to have a model deployed with the gemini-2.0-flash model, and use the id of that endpoint.

 String prompt = "Write a short poem about nature.";
 String response = askGemini(project, location, endpointId, prompt);
 System.out.println(response);
 }

    public static void main(String[] args) {
        SpringApplication.run(GeminiAccess.class, args);
    }
}