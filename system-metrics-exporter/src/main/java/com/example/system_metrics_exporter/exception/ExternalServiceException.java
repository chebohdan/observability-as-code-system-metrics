package com.example.system_metrics_exporter.exception;

import lombok.Getter;

import java.time.Instant;

@Getter
public class ExternalServiceException extends RuntimeException {

    private final String service;
    private final String status;
    private final String responseBody;
    private final Instant timestamp;

    public ExternalServiceException(String service, String status, String responseBody, Instant timestamp, Throwable cause) {
        super("External Service Error: " + service, cause);
        this.service = service;
        this.status = status;
        this.responseBody = responseBody ;
        this.timestamp = timestamp;
    }

    public ExternalServiceException(String service, String status, String responseBody) {
        this(service, status, responseBody, Instant.now(), null);
    }

    public ExternalServiceException(String service, String status, Throwable cause) {
        this(service, status, "", Instant.now(), cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage() + String.format(
                " [status=%s, responseBody=%s, timestamp=%s]",
                status, responseBody, timestamp
        );
    }
}
