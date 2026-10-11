# Library Management System

Mini Project (Theme: Java) - Department of Computer Science & Engineering,
K.E. Society's Rajarambapu Institute of Technology.
Final Year CSE, Div A, Batch A4.

A Java (Spring Boot + Maven) application used to practise a complete DevOps
workflow: Git/GitHub, Jenkins CI/CD, Docker, Kubernetes and monitoring.

## Tech stack

| Area | Tool |
|------|------|
| Language | Java 17 |
| Framework | Spring Boot 3 (REST API) |
| Build | Maven |
| Version control | Git, GitHub |
| CI/CD | Jenkins |
| Containers | Docker, DockerHub |
| Orchestration | Kubernetes, kubectl |
| Monitoring | AWS CloudWatch |
| API testing | Postman |

## Team

| Sr. | PRN | Name | Role |
|-----|-----|------|------|
| 1 | 2303065 | Awantika Sharadkumar Patil | QA Engineer |
| 2 | 2303069 | Prajakta Keshav Suryawanshi | Docker Engineer |
| 3 | 2303079 | Rutuja Sandeepkumar Thorat | Team Lead |
| 4 | 2303075 | Rutuja Balu Kavilkar | Jenkins Engineer |
| 5 | 2303074 | Girija Ashok Kadam | Developer 1 |
| 6 | 2303076 | Rajsi Rajesh Shinde | Developer 2 |
| 7 | 2303073 | Diksha Dhanaji Dabhole | Git/GitHub Engineer |
| 8 | 2303077 | Komal Ramesh Koli | Kubernetes Engineer |
| 9 | 2303080 | Sakshi Sunil Mane | DevOps/SRE Engineer |

## Run locally

Requirements: JDK 17 or newer, Maven 3.9+.

```bash
mvn spring-boot:run
```

Then open http://localhost:8080/health. You should see:

```json
{"status":"UP","app":"library-management-system"}
```

## Build and test

```bash
mvn verify
```

This compiles the code, runs the tests and produces `target/library-management-system-0.0.1-SNAPSHOT.jar`.

## Project structure

```
library-management-system/
├── pom.xml                         Maven build file (Jenkins runs this)
├── src/
│   ├── main/java/com/rit/library/
│   │   ├── LibraryApplication.java   App entry point
│   │   └── controller/               REST controllers (Login, Registration, Rating go here)
│   ├── main/resources/               application.properties
│   └── test/java/com/rit/library/    Unit tests
├── CONTRIBUTING.md                 Branch and pull request rules
├── TEAM_TASKS.md                   Member task allocation, steps & prompts
└── .github/pull_request_template.md
```

## Team Tasks & Workflow

Every team member must read [TEAM_TASKS.md](TEAM_TASKS.md) for their exact allocated role, branch name, Antigravity IDE prompts, and deliverables before starting work.

## Contributing

Read [CONTRIBUTING.md](CONTRIBUTING.md) before pushing any code.
