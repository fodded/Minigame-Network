package me.fodded.common.data.config;

import java.util.HashMap;
import java.util.Map;

public enum CoreConfig {

    SERVER_NAME("server-data.name"),

    DATA_TRANSFER_TYPE("data-transfer.type"),
    REDIS_HOST("data-transfer.redis.host"),
    REDIS_PORT("data-transfer.redis.port"),

    INFLUXDB_URL("data-metrics.influxDB.url"),
    INFLUXDB_TOKEN("data-metrics.influxDB.token"),
    INFLUXDB_ORG("data-metrics.influxDB.org"),
    INFLUXDB_BUCKET("data-metrics.influxDB.bucket"),

    MONGODB_URL("mongodb.url");

    private final Map<String, ?> cachedConfig = new HashMap<>();
    private final String path;

    CoreConfig(String path) {
        this.path = path;
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue() {
        return (T) cachedConfig.get(path);
    }

    private static void createConfig() {
        ServerSystem plugin = ServerSystem.getInstance();
        plugin.saveDefaultConfig();
    }

    private static void loadData() {
        ServerSystem plugin = ServerSystem.getPlugin(ServerSystem.class);
        for (String key : plugin.getConfig().getConfigurationSection("").getKeys(true)) {
            cachedConfigValues.put(key, plugin.getConfig().get(key));
        }
    }

    static {
        createConfig();
        loadData();
    }
}
