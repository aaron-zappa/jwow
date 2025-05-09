package main

import (
	"context"
	"fmt"
	"os"
	"log"

	aiplatform "cloud.google.com/go/aiplatform/apiv1"
	aiplatformpb "google.golang.org/genproto/googleapis/cloud/aiplatform/v1"
)

// GetVertexAIResult makes a prediction request to a Vertex AI endpoint.
// projectID: Your Google Cloud project ID.
// location: The location of your Vertex AI endpoint (e.g., "us-central1").
// endpointID: The ID of the deployed model endpoint.
// prompt: The input string to send to the AI model.
// Returns: The predicted result as a string, or an error if the request fails.
func GetVertexAIResult(projectID, location, endpointID, prompt string) (string, error) {
	// The parameters for projectID, location, and endpointID are no longer needed as they are hardcoded.
	ctx := context.Background()
	client, err := aiplatform.NewPredictionClient(ctx)
	if err != nil {
		return "", fmt.Errorf("failed to create prediction client: %w", err)
	}
	defer client.Close()

	endpoint := fmt.Sprintf("projects/%s/locations/%s/endpoints/%s", projectID, location, endpointID)

	// --- CUSTOMIZATION REQUIRED ---
	// The structure of 'instances' and 'parameters' in the PredictRequest
	// depends entirely on the specific AI model deployed to the endpoint.
	//
	// This is an example for a model that expects a single string input named "content"
	// and is expected to return a prediction that can be extracted as a string.
	// You MUST adjust this part based on your model's input/output schema.
	instances := []*aiplatformpb.Instance{
		{
			StructValue: &aiplatformpb.Struct{
				Fields: map[string]*aiplatformpb.Value{
					"content": {GetStringValue: prompt}, // Adjust the key "content" and value structure as needed
				},
			},
		},
	}

	// Add parameters if your model requires them. This is also model-specific.
	// Example: parameters := &aiplatformpb.Parameters{...}

	req := &aiplatformpb.PredictRequest{
		Endpoint:  endpoint,
		Instances: instances,
		// Parameters: parameters, // Uncomment and set parameters if your model needs them
	}

	resp, err := client.Predict(ctx, req)
	if err != nil {
		return "", fmt.Errorf("failed to get prediction from endpoint %s: %w", endpoint, err)
	}

	// --- CUSTOMIZATION REQUIRED ---
	// How you extract the prediction from the response also depends on your model's
	// output schema.
	//
	// This example assumes the model returns a single prediction in the first element
	// of the Predictions slice, and that this prediction is a struct with a field
	// named "content" whose value is a string.
	if len(resp.GetPredictions()) > 0 {
		// Access the first prediction
		firstPrediction := resp.GetPredictions()[0]

		// Attempt to extract the string value from a field named "content"
		if predictionStruct, ok := firstPrediction.GetStructValue(); ok {
			if predictionValue, ok := predictionStruct.Fields["content"]; ok {
				if predictionString, ok := predictionValue.GetStringValue(); ok {
					// Extract the string value from the "content" field.
					return predictionString, nil
				}
			}
		}
		// If extraction failed with the assumed structure
		// This error message is more specific now.
		return "", fmt.Errorf("could not extract string prediction from response based on assumed structure")
	}

	return "", fmt.Errorf("no predictions returned from endpoint %s", endpoint)
}

func main() {
	if len(os.Args) < 2 {
		fmt.Fprintln(os.Stderr, "Usage: vertex_ai_caller <prompt>")
		os.Exit(1)
	}

	projectID := "192261268349"
	location := "europe-west3"
	endpointID := "4156941203318767616"
	prompt := os.Args[1]

	// Initialize logger to write to stderr
	log.SetOutput(os.Stderr)
	log.SetPrefix("VertexAIAccessor: ")
	log.SetFlags(log.Ldate | log.Ltime | log.Lshortfile)


	result, err := GetVertexAIResult(projectID, location, endpointID, prompt)
	if err != nil {
		log.Printf("Error getting Vertex AI result: %v", err)
		os.Exit(1)
	}
	fmt.Printf("Vertex AI Result: %s\n", result)
}