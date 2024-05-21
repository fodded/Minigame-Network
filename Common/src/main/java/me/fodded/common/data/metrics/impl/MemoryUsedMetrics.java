package me.fodded.common.data.metrics.impl;

import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import me.fodded.common.data.metrics.ServerMetric;

import java.util.concurrent.CompletableFuture;

public class MemoryUsedMetrics implements ServerMetric {

    private static final long MEGABYTE_FACTOR = 1024L * 1024L;

    @Override
    public String getName() {
        return "memory_used";
    }

    @Override
    public CompletableFuture<Point> measure() {
        return CompletableFuture.supplyAsync(() -> Point.measurement(getName())
                .time(System.currentTimeMillis(), WritePrecision.MS)
                .addField("MiB", bytesToMiB(getUsedMemory())));
    }

    public static long getUsedMemory() {
        return Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory();
    }

    private static double bytesToMiB(long bytes) {
        return ((double) bytes / MEGABYTE_FACTOR);
    }
}