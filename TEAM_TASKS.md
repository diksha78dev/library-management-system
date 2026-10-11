# Team Member Task Allocation & Execution Guide

Welcome to the **Library Management System** DevOps project!  
This document defines the exact role, responsibilities, step-by-step commands, and ready-to-use prompts for each team member.

> 💡 **Recommended Tool:** Everyone should use **Antigravity IDE**. When you open this project in Antigravity IDE, you can directly copy-paste the provided prompts into the chat assistant to get your code generated and verified in seconds.

---

## 📋 General GitFlow Rules for All Members

1. **Never commit to `main` directly.** The `main` branch is protected and locked.
2. Before starting work, always pull the latest updates from `develop`:
   ```bash
   git checkout develop
   git pull origin develop
   ```
3. Switch to your allocated feature branch:
   ```bash
   git checkout <your-assigned-branch>
   ```
4. Build and test your code locally before pushing:
   ```bash
   mvn test
   ```
5. Push your branch and open a Pull Request (PR) on GitHub targeting **`develop`** (NOT `main`).
6. Request a review from your teammate and wait for **Diksha (Git/GitHub Engineer)** to review and merge it.

---

## 👥 Member-Wise Tasks and Instructions

---

### 1. Developer 1 — Girija Ashok Kadam (PRN: 2303074)
* **Assigned Branch:** `feature/login`
* **Focus Area:** User Authentication & Login Endpoint (Simple & Clean)

#### Steps to Complete:
1. Switch to your branch:
   ```bash
   git checkout feature/login
   ```
2. Open Antigravity IDE and paste this prompt:
   > **Antigravity IDE Prompt:**  
   > *"I am Developer 1 working on `feature/login`. Please create a clean Spring Boot REST controller in `src/main/java/com/rit/library/controller/AuthController.java` with a `POST /api/auth/login` endpoint that accepts JSON `LoginRequest` (username/email and password) and returns a JSON `AuthResponse` with a success status, message, and a dummy auth token. Also create a matching unit test in `src/test/java/com/rit/library/controller/AuthControllerTest.java` that tests successful login and invalid credentials using MockMvc."*
3. Verify tests pass:
   ```bash
   mvn test
   ```
4. Commit and push:
   ```bash
   git add .
   git commit -m "feat(auth): implement login endpoint and unit tests"
   git push origin feature/login
   ```
5. Go to GitHub and open a Pull Request into **`develop`**.

---

### 2. Developer 2 — Rajsi Rajesh Shinde (PRN: 2303076)
* **Assigned Branch:** `feature/registration`
* **Focus Area:** User Registration & Book Rating Endpoints (Simple & Clean)

#### Steps to Complete:
1. Switch to your branch:
   ```bash
   git checkout feature/registration
   ```
2. Open Antigravity IDE and paste this prompt:
   > **Antigravity IDE Prompt:**  
   > *"I am Developer 2 working on `feature/registration`. Please add:  
   > 1. A registration endpoint `POST /api/auth/register` taking `RegistrationRequest` (name, email, password) returning success status.  
   > 2. A book rating endpoint `POST /api/books/{id}/rate` taking a rating score (1-5) and review comment, returning status.  
   > 3. Corresponding unit tests in `src/test/java/com/rit/library/` using MockMvc to verify both endpoints."*
3. Verify tests pass:
   ```bash
   mvn test
   ```
4. Commit and push:
   ```bash
   git add .
   git commit -m "feat(api): add registration and book rating endpoints"
   git push origin feature/registration
   ```
5. Go to GitHub and open a Pull Request into **`develop`**.

---

### 3. QA Engineer — Awantika Sharadkumar Patil (PRN: 2303065)
* **Assigned Branch:** `feature/qa-tests` (or create off `develop`)
* **Focus Area:** Postman Test Suite & API Validation

#### Steps to Complete:
1. Check out develop and create your branch:
   ```bash
   git checkout develop
   git pull origin develop
   git checkout -b feature/qa-tests
   ```
2. Open Antigravity IDE and paste this prompt:
   > **Antigravity IDE Prompt:**  
   > *"I am the QA Engineer. Please generate a complete Postman collection JSON file (`postman/Library_Management_System.postman_collection.json`) and environment file (`postman/Local_Environment.postman_environment.json`) covering:  
   > 1. GET /health (Verify status UP)  
   > 2. POST /api/auth/login (Verify 200 OK and token returned)  
   > 3. POST /api/auth/register (Verify 201/200 created)  
   > 4. POST /api/books/1/rate (Verify rating submitted)  
   > Include Javascript test scripts inside the requests validating response status codes and schema."*
3. Commit and push:
   ```bash
   git add postman/
   git commit -m "test(qa): add Postman API collection and environment"
   git push -u origin feature/qa-tests
   ```
4. Open a Pull Request into **`develop`**.

---

### 4. Docker Engineer — Prajakta Keshav Suryawanshi (PRN: 2303069)
* **Assigned Branch:** `feature/docker`
* **Focus Area:** Containerization (Simple Multi-Stage Dockerfile)

#### Steps to Complete:
1. Switch to your branch:
   ```bash
   git checkout feature/docker
   ```
2. Open Antigravity IDE and paste this prompt:
   > **Antigravity IDE Prompt:**  
   > *"I am the Docker Engineer. Please create:  
   > 1. A clean, optimized multi-stage `Dockerfile` using `maven:3.9-eclipse-temurin-17` for building and `eclipse-temurin:17-jre-alpine` for running the JAR on port 8080.  
   > 2. A `.dockerignore` file ignoring target, .git, and IDE files.  
   > 3. A `docker-compose.yml` file to run the library application service locally with port mapping 8080:8080."*
3. Verify local build:
   ```bash
   docker build -t library-management-system:latest .
   ```
4. Commit and push:
   ```bash
   git add Dockerfile .dockerignore docker-compose.yml
   git commit -m "feat(docker): add multi-stage Dockerfile and docker-compose"
   git push origin feature/docker
   ```
5. Open a Pull Request into **`develop`**.

---

### 5. Kubernetes Engineer — Komal Ramesh Koli (PRN: 2303077)
* **Assigned Branch:** `feature/kubernetes`
* **Focus Area:** Kubernetes Manifests (Simple & Standard Deployment)

#### Steps to Complete:
1. Switch to your branch:
   ```bash
   git checkout feature/kubernetes
   ```
2. Open Antigravity IDE and paste this prompt:
   > **Antigravity IDE Prompt:**  
   > *"I am the Kubernetes Engineer. Please create standard Kubernetes manifests inside a `k8s/` directory:  
   > 1. `k8s/deployment.yaml`: Deployment named `library-app-deployment`, 2 replicas, container port 8080, liveness and readiness probes checking `/health`.  
   > 2. `k8s/service.yaml`: Service named `library-app-service`, Type NodePort (port 8080, targetPort 8080, nodePort 30080).  
   > 3. `k8s/README.md`: Quick instructions with `kubectl apply -f k8s/` and verification commands."*
3. Commit and push:
   ```bash
   git add k8s/
   git commit -m "feat(k8s): add deployment and service manifests"
   git push origin feature/kubernetes
   ```
5. Open a Pull Request into **`develop`**.

---

### 6. DevOps/SRE Engineer — Sakshi Sunil Mane (PRN: 2303080)
* **Assigned Branch:** `feature/monitoring` (or create off `develop`)
* **Focus Area:** Actuator, Logging & AWS CloudWatch Integration

#### Steps to Complete:
1. Create and switch to your branch:
   ```bash
   git checkout develop
   git pull origin develop
   git checkout -b feature/monitoring
   ```
2. Open Antigravity IDE and paste this prompt:
   > **Antigravity IDE Prompt:**  
   > *"I am the DevOps/SRE Engineer. Please configure monitoring and structured logging for our Spring Boot project:  
   > 1. Add `spring-boot-starter-actuator` and Micrometer dependencies to `pom.xml`.  
   > 2. Configure `application.properties` to expose health, info, and metrics endpoints (`/actuator`).  
   > 3. Create `src/main/resources/logback-spring.xml` with JSON-formatted console logging and AWS CloudWatch log formatting.  
   > 4. Create `docs/monitoring-guide.md` explaining how AWS CloudWatch and CloudWatch Agent collect these logs."*
3. Verify build:
   ```bash
   mvn test
   ```
4. Commit and push:
   ```bash
   git add .
   git commit -m "feat(monitoring): configure Spring Actuator, logback, and CloudWatch setup"
   git push -u origin feature/monitoring
   ```
5. Open a Pull Request into **`develop`**.

---

### 7. Jenkins Engineer — Rutuja Balu Kavilkar (PRN: 2303075)
* **Assigned Branch:** `feature/jenkins`
* **Focus Area:** Production-Grade, Multi-Stage Enterprise CI/CD Pipeline (High Complexity & Complete Rigor)

> ⚠️ **Important Requirement for Rutuja Kavilkar:**  
> A simple single-stage build script is **not acceptable** for the Jenkins role in this batch. To satisfy faculty evaluation and DevOps standards, you are responsible for an end-to-end, multi-stage automated Declarative CI/CD pipeline with strict Quality Gates, Code Coverage, Static Analysis, and Docker integration.

#### Detailed Deliverables Required:
1. **Multi-Stage Declarative `Jenkinsfile`:**
   - **Stage 1: Checkout & Environment Audit** (prints Java, Maven versions, git commit hash).
   - **Stage 2: Static Code Analysis & Linting** (Maven checkstyle / compiler lint checks).
   - **Stage 3: Automated Unit Testing** (`mvn test` execution).
   - **Stage 4: Test Reporting & Quality Gate** (publish JUnit XML test results with `junit 'target/surefire-reports/*.xml'`).
   - **Stage 5: JaCoCo Code Coverage Enforcement** (execute JaCoCo plugin; enforce minimum line & branch coverage threshold of 70%; archive HTML coverage report).
   - **Stage 6: Security Vulnerability Scan** (integrate OWASP Dependency-Check or Maven audit step; fail build on critical CVEs).
   - **Stage 7: Production Artifact Packaging** (`mvn package -DskipTests` to generate release JAR; archive artifacts in Jenkins).
   - **Stage 8: Docker Image Build & Tagging** (automate building `library-management-system:${BUILD_NUMBER}` using Docker pipeline plugin).
   - **Post-Build Actions:** Comprehensive notifications block (`always`, `success`, `failure`, `unstable`) sending status summary, commit author, and build URL.

2. **Maven Configuration (`pom.xml` modifications):**
   - Add and configure the `jacoco-maven-plugin` with execution goals for `prepare-agent` and `report`, and check rule for minimum coverage.
   - Add `maven-surefire-plugin` configured for test report XML output.

3. **Jenkins Setup & Architecture Documentation (`docs/jenkins-setup.md`):**
   - Step-by-step setup guide for Jenkins Server (Jenkins controller setup, required plugins list: *Pipeline, Git, JUnit, JaCoCo, Docker Pipeline, Credentials Binding*).
   - Guide on configuring GitHub Webhook (`http://<jenkins-url>/github-webhook/`) for automatic build trigger on every pull request and push to `develop`.
   - Setup instructions for Jenkins Global Credentials (GitHub Token credentials ID: `github-cred`, DockerHub credentials ID: `dockerhub-cred`).
   - Pipeline parameters (`ENVIRONMENT` dropdown: `dev`, `staging`, `prod`).

#### Steps to Complete:
1. Switch to your branch:
   ```bash
   git checkout feature/jenkins
   ```
2. Open Antigravity IDE and paste this prompt:
   > **Antigravity IDE Prompt:**  
   > *"I am the Jenkins Engineer (Rutuja Kavilkar). I need to implement a comprehensive, enterprise-level CI/CD pipeline:  
   > 1. Update `pom.xml` to include `jacoco-maven-plugin` (with `prepare-agent`, `report`, and coverage rule targets) and `maven-surefire-plugin`.  
   > 2. Create a complete, production-ready Declarative `Jenkinsfile` with 8 distinct stages: Checkout, Static Lint Check, Unit Test Execution, JUnit Test Results Archival, JaCoCo Coverage Enforcement, Security Audit Scan, JAR Packaging & Artifact Archival, and Docker Build & Tag. Include post-build blocks for success/failure/always reporting.  
   > 3. Create `docs/jenkins-setup.md` explaining all required plugins, GitHub Webhook configuration, credentials store setup, and instructions to run the pipeline on Jenkins.  
   > Ensure everything compiles with `mvn test` without breaking existing code."*
3. Verify build & coverage locally:
   ```bash
   mvn clean test jacoco:report
   ```
4. Commit and push:
   ```bash
   git add pom.xml Jenkinsfile docs/jenkins-setup.md
   git commit -m "ci(jenkins): implement comprehensive 8-stage declarative CI/CD pipeline with JaCoCo and quality gates"
   git push origin feature/jenkins
   ```
5. Open a Pull Request into **`develop`**.

---

### 8. Team Lead — Rutuja Sandeepkumar Thorat (PRN: 2303079)
* **Focus Area:** Task Allocation, PR Review & Milestone Tracking

#### Responsibilities:
1. Track task progress across all members and ensure deliverables meet deadlines.
2. Review Pull Requests submitted by Developers, Jenkins, Docker, and K8s engineers.
3. Coordinate with **Diksha (Git/GitHub Engineer)** for the final merge from `develop` into `main`.
4. Maintain group coordination and project documentation on Google Drive.

---

### 9. Git/GitHub Engineer — Diksha Dhanaji Dabhole (PRN: 2303073)
* **Focus Area:** Version Control, Branch Management, PR Review & Merging

#### Responsibilities:
1. Maintain branch protection on `main`.
2. Review and merge all incoming Pull Requests into `develop`.
3. Ensure no direct pushes bypass GitFlow.
4. Execute final release merge from `develop` into `main` after Team Lead approval.

---

## 🚀 Quick Antigravity IDE Tips for the Team

1. Clone the repository in terminal:
   ```bash
   git clone https://github.com/diksha78dev/library-management-system.git
   ```
2. Open the project folder in **Antigravity IDE**.
3. Open the chat panel, copy your designated prompt from above, and let Antigravity IDE write and test the code for you.
4. Run `mvn test` to ensure green builds before opening your Pull Request!
