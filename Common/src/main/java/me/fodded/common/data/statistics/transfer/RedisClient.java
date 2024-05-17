package me.fodded.common.data.statistics.transfer;

import lombok.Getter;
import me.fodded.common.data.config.RedisData;
import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedisClient {

    @Getter
    private final RedissonClient redissonClient;

    public RedisClient() {
        Config redisConfig = new Config();
        redisConfig.useSingleServer().setAddress("redis://" + RedisData.REDIS_HOST + ":" + RedisData.REDIS_PORT);
        this.redissonClient = Redisson.create(redisConfig);
    }

    public void registerListener(IRedisListener redisListener, String topic) {
        RTopic sendPlayerToLobbyTopic = redissonClient.getTopic(topic);
        sendPlayerToLobbyTopic.addListener(String.class, redisListener::onMessage);
    }

    public void publishMessageAsync(String topicName, String message) {
        RTopic topic = redissonClient.getTopic(topicName);
        topic.publishAsync(message);
    }
}
