package com.example.system_metrics_exporter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SystemMetricsExporterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SystemMetricsExporterApplication.class, args);
	}



}
