package me.fodded.metrics.impl;

import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import me.fodded.common.data.metrics.ServerMetric;
import org.bukkit.Bukkit;

import java.util.concurrent.CompletableFuture;

public class PlayersOnlineMetrics implements ServerMetric {

    @Override
    public String getName() {
        return "online_players";
    }

    @Override
    public CompletableFuture<Point> measure() {
        // The last minute server TPS
        int playersOnline = Bukkit.getOnlinePlayers().size();
        return CompletableFuture.supplyAsync(() -> Point.measurement(getName())
                .time(System.currentTimeMillis(), WritePrecision.MS)
                .addField("count", playersOnline));
    }
}