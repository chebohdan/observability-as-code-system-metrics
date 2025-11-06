package com.example.system_metrics_exporter.service;

import org.springframework.stereotype.Service;
import oshi.hardware.CentralProcessor;

import java.util.Arrays;

@Service
public class CpuMetricsService {
    private final CentralProcessor centralProcessor;
    private long[] prevTicks;

    public CpuMetricsService(CentralProcessor centralProcessor) {
        this.centralProcessor = centralProcessor;
        prevTicks = centralProcessor.getSystemCpuLoadTicks();
    }

    public double getTotalCpuUsage() {
        double cpuLoad = centralProcessor.getSystemCpuLoadBetweenTicks(prevTicks);
        prevTicks = centralProcessor.getSystemCpuLoadTicks();
        return cpuLoad * 100;
    }

    public double[] getPerCoreCpuUsage() {
        double[] coresCpuLoad = Arrays.stream(centralProcessor.getProcessorCpuLoad(1000))
                .map(item->item*100)
                .toArray();
        return coresCpuLoad;
    }
}
