package com.example.system_metrics_exporter.grafana.dashboard;

import com.example.system_metrics_exporter.annotation.GrafanaDashboard;
import com.example.system_metrics_exporter.grafana.service.GrafanaService;
import com.grafana.foundation.dashboard.Dashboard;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@AllArgsConstructor
public class DashboardInitializer implements ApplicationRunner {
    private final DashboardDefinitions dashboardDefinitions;
    private final GrafanaService grafanaService;
    private static final Logger logger = LoggerFactory.getLogger(DashboardInitializer.class);



    @Override
    public void run(ApplicationArguments args) {
        for (Method method : dashboardDefinitions.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(GrafanaDashboard.class)) {
                Dashboard dashboard = null;
                try {
                    dashboard = (Dashboard) method.invoke(dashboardDefinitions);
                    grafanaService.postDashboard(dashboard);
                    logger.info("Dashboard posted successfully: {}", dashboard.title);
                } catch (Exception e) {
                    String dashBoardName = dashboard != null ? dashboard.title : method.getName();
                    logger.error("Failed to post dashboard: {}", method.getName(), e);
                }
            }
        }
    }
}
