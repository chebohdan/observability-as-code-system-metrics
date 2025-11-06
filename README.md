# System Metrics Exporter

A simple Spring Boot app that creates and uploads Grafana dashboards automatically.

## What it does
- Builds Grafana dashboards using code
- Sends them to Grafana on app start
- Shows system metrics like CPU, memory, and disk
- Works with Prometheus data

## Tech used
- Java 17  
- Spring Boot  
- Grafana Dashboard Builder  
- Prometheus  
- Jackson (JSON)

## How to run
1. Set your Grafana URL and API key in `application.properties`
2. Start the app  
3. Dashboards will appear in Grafana
