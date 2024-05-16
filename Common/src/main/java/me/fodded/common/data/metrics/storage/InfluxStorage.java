package me.fodded.common.data.metrics.storage;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.write.Point;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class InfluxStorage {
    private final WriteApiBlocking writeApi;
    private final InfluxDBClient client;

    public InfluxStorage(String url, String token, String org, String bucket) {
        this.client = InfluxDBClientFactory.create(url, token.toCharArray(), org, bucket);
        this.writeApi = client.getWriteApiBlocking();
    }

    public CompletableFuture<Void> pushIndividual(Point point) {
        return CompletableFuture.runAsync(() -> {
            writeApi.writePoint(point);
        });
    }

    public CompletableFuture<Void> pushBatch(List<Point> points) {
        return CompletableFuture.runAsync(() -> {
            writeApi.writePoints(points);
        });
    }
}
