package me.fodded.common.data.metrics.impl;

import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import me.fodded.serversystem.data.metrics.ServerMetric;
import org.bukkit.Bukkit;

public class PlayersOnlineMetrics implements ServerMetric {

    @Override
    public String getName() {
        return "online_players";
    }

    @Override
    public Point measure() {
        int players = Bukkit.getOnlinePlayers().size();

        return Point.measurement(getName())
                .time(System.currentTimeMillis(), WritePrecision.MS)
                .addField("count", players);
    }
}