package com.example.demo;

import com.google.api.gax.rpc.ApiException;
import com.google.cloud.resourcemanager.v3.ProjectsClient;
import com.google.cloud.resourcemanager.v3.Project;
import com.google.cloud.resourcemanager.v3.ProjectName;

/**
 * This class is for checking Google Cloud project existence using the
 * Resource Manager API.
 */
public class CheckProjectExistence {

    /**
     * Checks if a Google Cloud project with the given ID exists.
     *
     * @param projectId The ID of the Google Cloud project to check.
     * @return true if the project exists, false otherwise.
     */
    public boolean projectExists(String projectId) {
        try (ProjectsClient projectsClient = ProjectsClient.create()) {
            ProjectName projectName = ProjectName.of(projectId);
            projectsClient.getProject(projectName);
            return true; // Project found
        } catch (ApiException e) {
            // Handle the case where the project is not found (HTTP 404)
            if (e.getStatusCode().getCode() == com.google.api.gax.rpc.StatusCode.Code.NOT_FOUND) {
                return false; // Project not found
            } else {
                // Handle other potential API exceptions (e.g., authentication issues)
                System.err.println("Error checking project existence: " + e.getMessage());
                e.printStackTrace();
                return false; // An error occurred
            }
        } catch (Exception e) {
            // Handle other potential exceptions
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
            return false; // An unexpected error occurred
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java CheckProjectExistence <project-id>");
            System.exit(1);
        }
        String projectIdToCheck = args[0];
        CheckProjectExistence checker = new CheckProjectExistence();
        boolean exists = checker.projectExists(projectIdToCheck);

        if (exists) {
            System.out.println("Project '" + projectIdToCheck + "' exists.");
        } else {
            System.out.println("Project '" + projectIdToCheck + "' does not exist or an error occurred.");
        }
    }
}