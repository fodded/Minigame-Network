package me.fodded.common.data.statistics.transfer;

import lombok.Getter;
import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedisClient {

    @Getter
    private final RedissonClient redissonClient;

    public RedisClient(String redisHost, int redisPort) {
        Config redisConfig = new Config();
        redisConfig.useSingleServer().setAddress("redis://" + redisHost + ":" + redisPort);
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
