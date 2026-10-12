# Kubernetes Deployment Guide — Library Management System

This directory contains the Kubernetes manifests to deploy the **Library Management System** with 2 replicas and a NodePort service.

---

## Manifests Overview

- [`deployment.yaml`](file:///C:/Users/komal/Desktop/library-management-system/k8s/deployment.yaml):
  - Deployment name: `library-app-deployment`
  - Replicas: `2`
  - Container port: `8080`
  - Health checks: HTTP GET `/health` on port 8080 (both Liveness and Readiness probes)
  - Pod selector label: `app: library-app`
- [`service.yaml`](file:///C:/Users/komal/Desktop/library-management-system/k8s/service.yaml):
  - Service name: `library-app-service`
  - Service type: `NodePort`
  - Port & Target Port: `8080`
  - NodePort: `30080`
  - Selector: `app: library-app`

---

## Deployment Instructions

### 1. Apply Manifests
Deploy both the Deployment and Service manifests to your Kubernetes cluster:

```bash
kubectl apply -f k8s/
```

Alternatively, apply individually:
```bash
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
```

---

## Verification Commands

### 2. Verify Deployment
Check the status and replica count of the Deployment:

```bash
kubectl get deployments -l app=library-app
kubectl describe deployment library-app-deployment
```

### 3. Verify Pods
Check that both pods are running and ready:

```bash
kubectl get pods -l app=library-app -o wide
```

### 4. Verify Service
Verify the NodePort mapping:

```bash
kubectl get svc library-app-service
kubectl describe svc library-app-service
```

---

## Checking Application Logs

### 5. Stream Pod Logs
View logs for all pods in the deployment:

```bash
kubectl logs -f -l app=library-app --all-containers=true
```

Or view logs for a specific pod:

```bash
kubectl logs -f <pod-name>
```

---

## Accessing the Application

- **Minikube:**
  ```bash
  minikube service library-app-service --url
  ```
- **Standard Node / Cloud / Kind:**
  Access via node IP on port `30080`:
  ```bash
  curl http://<node-ip>:30080/health
  ```
- **Port Forwarding (Testing without NodePort):**
  ```bash
  kubectl port-forward svc/library-app-service 8080:8080
  ```
  Then access at `http://localhost:8080/health`.

---

## Teardown / Cleanup

To delete the deployed resources:

```bash
kubectl delete -f k8s/
```
