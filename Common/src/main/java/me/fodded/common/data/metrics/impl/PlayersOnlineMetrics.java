package me.fodded.common.data.metrics.impl;

import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import me.fodded.common.data.metrics.ServerMetric;

public class PlayersOnlineMetrics implements ServerMetric {

    @Override
    public String getName() {
        return "online_players";
    }

    @Override
    public Point measure() {
        //TODO: to be changed
        int players = 1;

        return Point.measurement(getName())
                .time(System.currentTimeMillis(), WritePrecision.MS)
                .addField("count", players);
    }
}