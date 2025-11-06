package com.example.system_metrics_exporter.grafana.service;

import com.example.system_metrics_exporter.exception.ExternalServiceException;
import com.example.system_metrics_exporter.exception.InvalidDashboardException;
import com.example.system_metrics_exporter.grafana.dashboard.DashboardInitializer;
import com.example.system_metrics_exporter.grafana.rest.GrafanaClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grafana.foundation.dashboard.Dashboard;
import com.grafana.relocated.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.Map;

@Service
@AllArgsConstructor
public class GrafanaService {
    private final GrafanaClient grafanaClient;
    private final ObjectMapper objectMapper;
    private final String payloadFormat = "{\"dashboard\": %s, \"folderId\": 0, \"overwrite\": true}";
    private static final Logger logger = LoggerFactory.getLogger(GrafanaService.class);


    public void postDashboard(Dashboard dashboard) {
        String payload;
        try {
            String dashboardJson = dashboard.toJSON();
            payload = String.format(payloadFormat, dashboardJson);
        } catch (JsonProcessingException e) {
            throw new InvalidDashboardException("Failed to parse dashboard JSON", e);
        }

        try {
            ResponseEntity<String> response = grafanaClient.post(payload);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new ExternalServiceException(
                        "Grafana",
                        response.getStatusCode().toString(),
                        response.getBody()
                );
            }
        } catch (RestClientException e) {
            throw new ExternalServiceException(
                    "Grafana",
                    "COMMUNICATION_FAILURE",
                    e
            );
        }
    }

    private String buildPayload(Dashboard dashboard) {
        try {
            Map<String, Object> payload = Map.of(
                    "dashboard", dashboard,
                    "folderId", 0,
                    "overwrite", true
            );
            return objectMapper.writeValueAsString(payload);
        } catch (Exception e) {
            throw new IllegalStateException("Error serializing dashboard", e);
        }
    }


}
