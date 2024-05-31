package me.fodded.proxyloadbalancer.servers;

import me.fodded.proxyloadbalancer.servers.instances.ProxyInstance;
import me.fodded.proxyloadbalancer.servers.instances.ServerInstance;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import java.util.function.Consumer;

public class ServerController {

    // server's name and server's instance
    private final RMap<String, ServerInstance> serverInstancesMap;

    public ServerController(RedissonClient redissonClient) {
        this.serverInstancesMap = redissonClient.getMap("serverInstancesMap");
    }

    /**
     * We prevent server adding if it's already been added to the network
     * Once we added it, we go through all loaded proxies and add the sever to them
     */
    public void addServer(ServerInstance serverInstance) {
        String serverName = serverInstance.getServerName();
        if(this.serverInstancesMap.containsKey(serverName)) {
            return;
        }

        this.serverInstancesMap.put(serverName, serverInstance);
        if(!(serverInstance instanceof ProxyInstance)) {
            iterateInstances(proxyInstance -> {
                proxyInstance.addServer(serverInstance.getServerName(), serverInstance.getServerAddress(), serverInstance.getServerTypeName(), false);
            }, ProxyInstance.class);
        }
    }

    /**
     * We go through all loaded proxies and remove the sever from them
     */
    public void removeServer(String serverName) {
        this.serverInstancesMap.remove(serverName);

        iterateInstances(proxyInstance -> {
            proxyInstance.removeServer(serverName);
        }, ProxyInstance.class);
    }

    @SuppressWarnings("unchecked")
    public <T extends ServerInstance> void iterateInstances(Consumer<? super T> consumerAction, Class<T> serverInstanceClass) {
        for(ServerInstance serverInstance : this.serverInstancesMap.values()) {
            if(serverInstance.getClass() == serverInstanceClass) {
                consumerAction.accept((T) serverInstance);
            }
        }
    }
}