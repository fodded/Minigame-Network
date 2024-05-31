package me.fodded.metrics.impl;

import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import me.fodded.common.data.metrics.ServerMetric;
import net.minecraft.server.v1_8_R3.MinecraftServer;

import java.util.concurrent.CompletableFuture;

public class TPSMetrics implements ServerMetric {

    @Override
    public String getName() {
        return "tps";
    }

    @Override
    public CompletableFuture<Point> measure() {
        // The last minute server TPS
        double tps = MinecraftServer.getServer().recentTps[0];
        return CompletableFuture.supplyAsync(() -> Point.measurement(getName())
                .time(System.currentTimeMillis(), WritePrecision.MS)
                .addField("number", tps));
    }
}