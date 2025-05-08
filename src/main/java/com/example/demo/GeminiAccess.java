import com.google.cloud.aiplatform.v1.EndpointName;
import com.google.cloud.aiplatform.v1.PredictResponse;
import com.google.cloud.aiplatform.v1.PredictionServiceClient;
import com.google.cloud.aiplatform.v1.PredictionServiceSettings;
import com.google.auth.oauth2.GoogleCredentials;
import java.util.ArrayList;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import java.util.List;
import java.util.Map;
import java.io.IOException;

public class GeminiAccess {
    public static String askGemini(String project, String location, String endpointId, String prompt) {
        try {

            // Set the endpoint address
            String apiEndpoint = String.format("%s-aiplatform.googleapis.com:443", location);

            PredictionServiceSettings.Builder predictionServiceSettingsBuilder =
                    PredictionServiceSettings.newBuilder()
                            .setEndpoint(apiEndpoint);
            PredictionServiceSettings predictionServiceSettings = predictionServiceSettingsBuilder.build();

            try (PredictionServiceClient predictionServiceClient =
                         PredictionServiceClient.create(predictionServiceSettings)) {

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
            } catch (InvalidProtocolBufferException e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error";
    }

    public static void main(String[] args) {
        String project = "192261268349"; 
        String location = "europe-west3";
        String endpointId = "4156941203318767616"; // Replace with your actual endpoint ID. You need to have a model deployed with the gemini-2.0-flash model, and use the id of that endpoint.
        String prompt = "Write a short poem about nature.";
        String response = askGemini(project, location, endpointId, prompt);
        System.out.println(response);
    }
}