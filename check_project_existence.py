import argparse
from googleapiclient import discovery
from googleapiclient.errors import HttpError
import google.auth

def project_exists(project_id):
    """Checks if a Google Cloud project exists."""
    try:
        credentials, project = google.auth.default()
        service = discovery.build("cloudresourcemanager", "v1", credentials=credentials)
        service.projects().get(projectId=project_id).execute()
        return True
    except HttpError as e:
        if e.resp.status == 404:
            return False
        else:
            print(f"An unexpected error occurred: {e}")
            return False
    except google.auth.exceptions.DefaultCredentialsError:
        print("Google Cloud authentication failed. Ensure you have configured your credentials.")
        print("Try running 'gcloud auth application-default login'")
        return False
    except Exception as e:
        print(f"An error occurred: {e}")
        return False

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Check if a Google Cloud project ID exists.")
    parser.add_argument("project_id", help="The Google Cloud project ID to check.")
    args = parser.parse_args()

    if project_exists(args.project_id):
        print(f"Project '{args.project_id}' exists.")
    else:
        print(f"Project '{args.project_id}' does not exist or an error occurred.")