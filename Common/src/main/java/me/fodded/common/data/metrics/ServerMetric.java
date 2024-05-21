package me.fodded.common.data.metrics;

import com.influxdb.client.write.Point;

import java.util.concurrent.CompletableFuture;

public interface ServerMetric {
    String getName();
    CompletableFuture<Point> measure();
}