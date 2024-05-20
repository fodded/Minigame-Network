package me.fodded.proxyloadbalancer.servers;

import me.fodded.proxyloadbalancer.servers.instances.ServerInstance;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import java.util.UUID;

public class ServerController {

    private final RMap<UUID, ServerInstance> serverInstancesMap;
    private final RedissonClient redissonClient;

    public ServerController(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
        this.serverInstancesMap = redissonClient.getMap("serverInstancesMap");
    }

    public void addServer(ServerInstance serverInstance) {
        this.serverInstancesMap.put(serverInstance.getServerUUID(), serverInstance);
    }

    public void removeServer(UUID serverUUID) {
        this.serverInstancesMap.remove(serverUUID);
    }
}