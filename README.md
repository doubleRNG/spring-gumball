# CMPE 172 - Lab #10 Notes

## CI Workflow Steps (Part 1)
![](screenshots/gradle-yml.png)

Here is the gradle.yml file that was created in the repository.

![](screenshots/build-in-progress.png)

Here the .\gradlew build command is shown to be in progress.

![](screenshots/build-with-gradle-1.png)
![](screenshots/build-with-gradle-2.png)

Here the .\gradlew build command has completed and shows a BUILD SUCCESSFUL message at the end.

![](screenshots/build-result-and-artefact.png)

Here are the the successfully built jar is shown and a message is output communicating that an artefact was uploaded for pull requests.


## CD Workflow Steps (Part 2)
![](screenshots/cluster-1.png)
![](screenshots/cluster-2.png)
![](screenshots/cluster-3.png)

Here the cmpe172 cluster creation  in the cmpe172 project is shown.

![](screenshots/api-library.png)
![](screenshots/api-enabled.png)

Here shows that the desired APIs are already enabled.

![](screenshots/cloud-dashboard.png)
![](screenshots/project-id.png)

Here the navigation to find the project id is shown.

![](screenshots/service-account-1.png)
![](screenshots/service-account-2.png)

Here the spring-gumball service account creation is shown.

![](screenshots/IAM.png)
![](screenshots/grant-access-1.png)
![](screenshots/grant-access-2.png)

Here the Kubernetes Engine Develope and Storage Admin permission creation is shown.

![](screenshots/create-key-1.png)
![](screenshots/create-key-2.png)
![](screenshots/key-saved.png)

Here the JSON service account key creation is shown.

![](screenshots/actions-secrets-1.png)
![](screenshots/actions-secrets-2.png)
![](screenshots/actions-secrets-3.png)

Here the GKE_PROJECT repository secret creation is shown.

![](screenshots/service-account-json.png)
![](screenshots/actions-secrets-4.png)
![](screenshots/actions-secrets-5.png)

Here the GKE_SA_KEY repository secret creation is shown.

![](screenshots/release-1.png)
![](screenshots/release-2.png)

Here the release setup is shown.

![](screenshots/release-workflow-1.png)
![](screenshots/release-workflow-2.png)
![](screenshots/release-workflow-3.png)
![](screenshots/release-workflow-4.png)
![](screenshots/release-workflow-5.png)

Here the building and deployment of the spring-gumball project to GKE is shown.

![](screenshots/workloads.png)

Here the successfull creation of the spring-gumball-deployment in GKE is shown.

![](screenshots/service.png)

Here the successfull creation of the spring-gumball-service in GKE is shown.

![](screenshots/ingress-1.png)
![](screenshots/ingress-2.png)

Here the creation of the spring-gumball load balancer is shown.

![](screenshots/web-app.png)

Here the web app running on the exposed frontend ip (34.149.217.251) is shown.
