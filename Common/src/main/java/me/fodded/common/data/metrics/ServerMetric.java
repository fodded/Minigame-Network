package me.fodded.common.data.metrics;

import com.influxdb.client.write.Point;

public interface ServerMetric {

    String getName();
    Point measure();
}