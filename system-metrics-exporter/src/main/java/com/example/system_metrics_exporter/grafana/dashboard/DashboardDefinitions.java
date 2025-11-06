package com.example.system_metrics_exporter.grafana.dashboard;

import com.example.system_metrics_exporter.annotation.GrafanaDashboard;
import com.example.system_metrics_exporter.metrics.CpuMetrics;
import com.grafana.foundation.common.Constants;
import com.grafana.foundation.dashboard.Dashboard;
import com.grafana.foundation.dashboard.DashboardBuilder;
import com.grafana.foundation.dashboard.DashboardDashboardTimeBuilder;
import com.grafana.foundation.dashboard.RowBuilder;
import com.grafana.foundation.prometheus.DataqueryBuilder;
import com.grafana.foundation.timeseries.TimeseriesPanelBuilder;
import com.grafana.relocated.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DashboardDefinitions {

    @GrafanaDashboard
    public Dashboard cpuUsageDashboard() throws JsonProcessingException {
        Dashboard dashboard = new DashboardBuilder("Cpu Usage").
                title("Cpu Usage").
                uid("cpu-usage").
                description("Dashboard displays cpu usage").
                tags(List.of("cpu usage", "cpu")).
                refresh("1m").
                time(new DashboardDashboardTimeBuilder().
                        from("now-30m").
                        to("now")
                ).
                timezone(Constants.TimeZoneBrowser).
                withRow(new RowBuilder("CPU Details")).
                    withPanel(
                            buildCpuUsagePanel()
                    ).withPanel(
                            buildPerCoreCpuUsagePanel()
                    )
                .build();
        return dashboard;
    }


    private TimeseriesPanelBuilder buildCpuUsagePanel() {
        return new TimeseriesPanelBuilder()
                .title("CPU usage")
                .unit("%")
                .min(0.0)
                .withTarget(new DataqueryBuilder()
                        .expr(CpuMetrics.CPU_USAGE)
                        .legendFormat(CpuMetrics.LEGEND_CPU_USAGE));
    }

    private TimeseriesPanelBuilder buildPerCoreCpuUsagePanel() {
        return new TimeseriesPanelBuilder()
                .title("Per core CPU usage")
                .unit("%")
                .min(0.0)
                .withTarget(new DataqueryBuilder()
                        .expr(CpuMetrics.PER_CORE_CPU_USAGE)
                        .legendFormat(CpuMetrics.LEGEND_PER_CORE_CPU_USAGE));
    }


    @GrafanaDashboard
    public Dashboard dummyDashboard1() {
        return new DashboardBuilder("Dummy Dashboard 1")
                .title("Dummy Dashboard 1")
                .uid("dummy-dashboard-1")
                .description("Used for testing Grafana integration")
                .tags(List.of("test", "dummy"))
                .refresh("1m")
                .time(new DashboardDashboardTimeBuilder().from("now-5m").to("now"))
                .timezone(Constants.TimeZoneBrowser)
                .withRow(new RowBuilder("Test Row")
                        .withPanel(new TimeseriesPanelBuilder()
                                .title("Test Metric")
                                .unit("short")
                                .min(0.0)
                                .withTarget(new DataqueryBuilder()
                                        .expr("1")) // constant value to show dummy data
                        )
                )
                .build();
    }

    @GrafanaDashboard
    public Dashboard dummyDashboard2() {
        return new DashboardBuilder("Dummy Dashboard 2")
                .title("Dummy Dashboard 2")
                .uid("dummy-dashboard-2")
                .description("Used for testing Grafana integration")
                .tags(List.of("test", "dummy"))
                .refresh("1m")
                .time(new DashboardDashboardTimeBuilder().from("now-5m").to("now"))
                .timezone(Constants.TimeZoneBrowser)
                .withRow(new RowBuilder("Test Row")
                        .withPanel(new TimeseriesPanelBuilder()
                                .title("Test Metric")
                                .unit("short")
                                .min(0.0)
                                .withTarget(new DataqueryBuilder()
                                        .expr("1")) // constant value to show dummy data
                        )
                )
                .build();
    }


}
