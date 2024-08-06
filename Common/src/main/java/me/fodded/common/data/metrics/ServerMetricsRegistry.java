package me.fodded.common.data.metrics;

import com.influxdb.client.write.Point;
import me.fodded.common.data.metrics.storage.InfluxStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerMetricsRegistry {

    private static final int QUERY_DELAY_SECONDS = 1;
    private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);

    private final Map<String, ServerMetric> registeredMetrics = new HashMap<>();
    private final InfluxStorage influx;

    private final String serverName;

    public ServerMetricsRegistry(InfluxStorage influxStorage, String serverName) {
        this.influx = influxStorage;
        this.serverName = serverName;

        executorService.scheduleWithFixedDelay(this::tick, 0, QUERY_DELAY_SECONDS, TimeUnit.SECONDS);
    }

    public void registerMetric(ServerMetric metric) {
        this.registeredMetrics.put(metric.getName(), metric);
    }

    public ServerMetric getMetric(String name) {
        return this.registeredMetrics.get(name);
    }

    public void tick() {
        List<Point> points = new ArrayList<>();
        for (ServerMetric metric : this.registeredMetrics.values()) {
            Point measurement = metric.measure().join();
            measurement.addTag("server-instance", serverName);

            points.add(measurement);
        }

        influx.pushBatch(points);
    }
}