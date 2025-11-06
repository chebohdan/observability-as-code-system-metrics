package com.example.system_metrics_exporter.exception;

public class InvalidDashboardException extends RuntimeException {
    public InvalidDashboardException(String message, Throwable cause) {
        super(message, cause);
    }
}
