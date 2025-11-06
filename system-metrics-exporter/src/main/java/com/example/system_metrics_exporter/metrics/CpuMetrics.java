package com.example.system_metrics_exporter.metrics;

import com.example.system_metrics_exporter.service.CpuMetricsService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.MultiGauge;
import io.micrometer.core.instrument.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CpuMetrics {
    public static final String CPU_USAGE = "cpu_usage";
    public static final String PER_CORE_CPU_USAGE = "per_core_cpu_usage";
    public static final String LEGEND_PER_CORE_CPU_USAGE = "Core {{core}}";
    public static final String LEGEND_CPU_USAGE= "CPU usage";



    private final MultiGauge perCoreGauge;
    private final CpuMetricsService cpuMetricsService;

    @Autowired
    public CpuMetrics(CpuMetricsService cpuMetricsService, MeterRegistry registry) {
        this.cpuMetricsService = cpuMetricsService;

        Gauge.builder(CPU_USAGE, cpuMetricsService, CpuMetricsService::getTotalCpuUsage)
                .description("Total system CPU usage in percent")
                .register(registry);

        // MultiGauge for per-core usage
        perCoreGauge = MultiGauge.builder(PER_CORE_CPU_USAGE)
                .description("CPU usage per core in percent")
                .register(registry);
    }

    @Scheduled(fixedRate = 1000)
    public void updatePerCoreMetrics() {
        double[] cores = cpuMetricsService.getPerCoreCpuUsage();

        List<MultiGauge.Row<Number>> rows = new ArrayList<>();
        for (int i = 0; i < cores.length; i++) {
            rows.add(MultiGauge.Row.of(Tags.of("core", String.valueOf(i)), cores[i]));
        }

        perCoreGauge.register(rows, true);
    }
}
