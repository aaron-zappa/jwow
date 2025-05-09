import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class AiAccessor {

    public String getAiResult(String prompt) {
        try {
            ProcessBuilder pb = new ProcessBuilder("./vertex_ai_caller", prompt);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\\n");
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                // Handle error: Go process exited with a non-zero code
                System.err.println("Go process exited with code: " + exitCode);
            }
            return output.toString().trim();
        } catch (IOException | InterruptedException e) {
            return "Error calling AI service: " + e.getMessage();
        }
    }
}