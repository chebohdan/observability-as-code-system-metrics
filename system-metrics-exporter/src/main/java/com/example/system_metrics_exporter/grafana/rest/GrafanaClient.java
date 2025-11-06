package com.example.system_metrics_exporter.grafana.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GrafanaClient {
    private final RestTemplate restTemplate;
    private final HttpHeaders headers;
    private final String grafanaUrl;

    public GrafanaClient( @Value("${grafana.api.url}") String grafanaUrl,
                          @Value("${grafana.api.token}") String apiKey) {
        this.restTemplate = new RestTemplate();
        this.grafanaUrl = grafanaUrl;
        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);
        this.headers.set(HttpHeaders.AUTHORIZATION,
                apiKey.startsWith("Bearer ") ? apiKey : "Bearer " + apiKey);
    }

    public ResponseEntity<String> post(String payload) {
        HttpEntity<String> request = new HttpEntity<>(payload, headers);
        return restTemplate.postForEntity(grafanaUrl, request, String.class);
    }

}
