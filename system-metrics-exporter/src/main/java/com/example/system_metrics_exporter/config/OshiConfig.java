package com.example.system_metrics_exporter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.util.GlobalConfig;

@Configuration
public class OshiConfig {
    static {
        GlobalConfig.set(GlobalConfig.OSHI_OS_WINDOWS_CPU_UTILITY, true);
    }

    @Bean
    public SystemInfo getSystemInfo() {
        return new SystemInfo();
    }

    @Bean
    public CentralProcessor getCentralProcessor(SystemInfo systemInfo) {
        return systemInfo.getHardware().getProcessor();
    }
}
