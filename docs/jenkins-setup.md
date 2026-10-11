# Jenkins CI/CD Setup Guide

## 1. Project Overview

This project uses a declarative Jenkins pipeline to automate the
build and quality checks for the Library Management System.

The pipeline includes eight stages:
1. Checkout and environment audit
2. Static code analysis and Checkstyle
3. Automated unit testing
4. JUnit test reporting
5. JaCoCo coverage enforcement
6. OWASP dependency security scanning
7. Maven packaging and JAR archiving
8. Docker image build and tagging

## 2. Prerequisites

Install and configure:
- Java JDK 17
- Apache Maven 3
- Git
- Docker Engine or Docker Desktop
- Jenkins with a suitable build agent

The build agent must be able to access the repository and the
Docker daemon.

## 3. Required Jenkins Plugins

Install these plugins using Manage Jenkins > Plugins:
- Pipeline
- Git
- GitHub Branch Source
- JUnit
- JaCoCo
- Docker Pipeline
- Credentials Binding
- OWASP Dependency-Check
- Email Extension (optional)

## 4. Configure Java and Maven

Open Manage Jenkins > Tools.

Configure a JDK installation named:
JDK17

Configure a Maven installation named:
Maven3

These names must match the tools section in the Jenkinsfile.

Ensure Docker is available to the agent running the pipeline.

## 5. Configure Credentials

Open Manage Jenkins > Credentials.

GitHub credential ID:
github-cred

Use a GitHub Personal Access Token when private repository
access requires authentication.

Docker Hub credential ID:
dockerhub-cred

Use a Docker Hub username and access token.

The current pipeline builds a local Docker image but does not
push it to Docker Hub. Therefore, the Docker Hub credentials
are documented for future use and are not consumed yet.

Never store passwords or tokens in source code.

## 6. Configure the Pipeline Job

Create a Multibranch Pipeline job in Jenkins.

Repository URL:
https://github.com/diksha78dev/library-management-system

Configure the GitHub Branch Source and credentials if needed.
Ensure the feature/jenkins branch is discovered and contains
the Jenkinsfile.

Run Scan Multibranch Pipeline Now to discover branches.

## 7. Configure GitHub Webhook

In GitHub, open repository Settings > Webhooks > Add webhook.

Payload URL:
http://<jenkins-url>/github-webhook/

Content type:
application/json

Select push events and any additional events required by the
configured Jenkins branch source.

Replace <jenkins-url> with the real, reachable Jenkins URL.
GitHub cannot reach a Jenkins service running only on localhost.
Use HTTPS and secure access for a publicly accessible Jenkins server.

## 8. Quality Gates and Reports

Unit tests use Maven Surefire.

JUnit report location:
target/surefire-reports/*.xml

JaCoCo HTML report location:
target/site/jacoco/

The configured JaCoCo quality gate requires at least 70% line
coverage and 70% branch coverage.

The OWASP Dependency-Check stage scans project dependencies
and fails the build for vulnerabilities at the configured
CVSS threshold of 9.0 or higher.

The first security scan can take additional time while its
vulnerability database is downloaded. Network access is required.
An NVD API key can be configured securely in Jenkins to help
with vulnerability database updates.

## 9. Environment Parameter

The Jenkinsfile provides an ENVIRONMENT dropdown:
- dev
- staging
- prod

The parameter labels the selected environment. It does not
deploy the application. Add and review deployment stages and
production approval controls before enabling real deployments.

## 10. Docker Image

The pipeline builds an image tagged as:
library-management-system:<BUILD_NUMBER>

The Dockerfile runs the packaged Spring Boot application on
port 8080.

## 11. Build Notifications

The pipeline logs the build status, commit author, commit hash,
selected environment and Jenkins build URL.

Email notifications require the Email Extension plugin and
working email configuration. Set the Jenkins environment variable
NOTIFICATION_EMAIL to the intended recipient address.

## 12. Local Verification

Run these commands from the repository root:

mvn clean verify

mvn -B "-Dcheckstyle.config.location=config/checkstyle/checkstyle.xml" checkstyle:check

mvn -B package -DskipTests

Build the Docker image after Maven packaging:

docker build -t library-management-system:local .

Run the container:

docker run --rm -p 8080:8080 library-management-system:local

Test the health endpoint:

http://localhost:8080/health
