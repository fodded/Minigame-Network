package me.fodded.proxyloadbalancer.servers;

import me.fodded.proxyloadbalancer.servers.instances.ServerInstance;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import java.util.UUID;

public class ServerController {

    // server's name and server's instance
    private final RMap<String, ServerInstance> serverInstancesMap;
    private final RedissonClient redissonClient;

    public ServerController(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
        this.serverInstancesMap = redissonClient.getMap("serverInstancesMap");
    }

    public void addServer(ServerInstance serverInstance) {
        this.serverInstancesMap.put(serverInstance.getServerName(), serverInstance);
    }

    public void removeServer(UUID serverUUID) {
        this.serverInstancesMap.remove(serverUUID);
    }
}