# System Metrics Exporter

A service that automatically builds and uploads programmatically created Grafana dashboards to visualize system metrics.

## Table of Contents
1. [Overview](#overview)  
2. [Features](#features)  
3. [Technology](#technology)  
4. [Prerequisites](#prerequisites)  
5. [Setup and Usage](#setup-and-usage)  
6. [Access](#access)  
7. [License](#license)  

## Overview
System Metrics Exporter programmatically creates Grafana dashboards and uploads them on application start. It displays system metrics such as CPU and integrates with Prometheus for metric collection.

## Features
- Builds Grafana dashboards using code  
- Automatically uploads dashboards to Grafana on start  
- Monitors system metrics: CPU

## Technology
- Java 17  
- Spring Boot
- Micrometer
- Grafana Dashboard Builder  
- Prometheus  

## Prerequisites
- Docker  

## Setup
Run the system using Docker Compose:  
```bash
docker compose up -d --build
```

## Usage
You can then access Grafana on http://localhost:3000
