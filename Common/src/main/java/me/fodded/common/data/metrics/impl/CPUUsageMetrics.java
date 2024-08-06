package me.fodded.common.data.metrics.impl;

import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.sun.management.OperatingSystemMXBean;
import me.fodded.common.data.metrics.ServerMetric;

import java.lang.management.ManagementFactory;
import java.util.concurrent.CompletableFuture;

public class CPUUsageMetrics implements ServerMetric {

    private OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

    @Override
    public String getName() {
        return "cpu_usage";
    }

    @Override
    public CompletableFuture<Point> measure() {
        return CompletableFuture.supplyAsync(() -> Point.measurement(getName())
                .time(System.currentTimeMillis(), WritePrecision.MS)
                .addField("percent", osBean.getSystemCpuLoad() * 100)
        );
    }
}
