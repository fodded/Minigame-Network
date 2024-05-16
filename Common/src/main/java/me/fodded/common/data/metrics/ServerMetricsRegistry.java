package me.fodded.common.data.metrics;

import com.influxdb.client.write.Point;
import me.fodded.serversystem.data.metrics.storage.InfluxStorage;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerMetricsRegistry {

    private static final int QUERY_DELAY = 5 * 20;

    private final Map<String, ServerMetric> registeredMetrics = new HashMap<>();
    private final InfluxStorage influx;

    private final String serverName;

    public ServerMetricsRegistry(InfluxStorage influx, JavaPlugin plugin, String serverName) {
        this.influx = influx;
        this.serverName = serverName;

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::tick, QUERY_DELAY, QUERY_DELAY);
    }

    public void registerMetric(ServerMetric metric) {
        this.registeredMetrics.put(metric.getName(), metric);
    }

    public ServerMetric getMetric(String name) {
        return this.registeredMetrics.get(name);
    }

    public void tick() {
        List<Point> points = new ArrayList<>();
        for(ServerMetric metric : this.registeredMetrics.values()) {
            Point measurement = metric.measure();
            measurement.addField("server", serverName);

            points.add(measurement);
        }

        influx.pushBatch(points);
    }
}