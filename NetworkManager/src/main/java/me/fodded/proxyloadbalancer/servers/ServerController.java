package me.fodded.proxyloadbalancer.servers;

import me.fodded.common.Common;
import org.redisson.api.RMap;

import java.util.UUID;

public class ServerController {

    private final RMap<UUID, ServerInstance> serverInstancesMap;

    public ServerController() {
        this.serverInstancesMap = Common.getInstance().getRedisClient().getRedissonClient().getMap("serverInstancesMap");
    }

    public void addServer(ServerInstance serverInstance) {
        this.serverInstancesMap.put(serverInstance.getServerUUID(), serverInstance);
    }

    public void removeServer(UUID serverUUID) {
        this.serverInstancesMap.remove(serverUUID);
    }
}