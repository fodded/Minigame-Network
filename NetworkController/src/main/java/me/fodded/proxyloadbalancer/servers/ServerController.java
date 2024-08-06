package me.fodded.proxyloadbalancer.servers;

import me.fodded.proxyloadbalancer.info.game.AbstractGameInstance;
import me.fodded.proxyloadbalancer.servers.instances.ProxyInstance;
import me.fodded.proxyloadbalancer.servers.instances.ServerInstance;
import me.fodded.proxyloadbalancer.servers.instances.minigame.MinigameInstance;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ServerController {

    // server's name and server's class instance
    private final RMap<String, ServerInstance> serverInstancesMap;

    public ServerController(RedissonClient redissonClient) {
        this.serverInstancesMap = redissonClient.getMap("serverInstancesMap");
    }

    /**
     * We prevent server adding if it's already been added to the network
     * Once we added it, we go through all loaded proxies and add the sever to them
     */
    public void addServerInstance(ServerInstance serverInstance) {
        String serverName = serverInstance.getServerName();
        if (this.serverInstancesMap.containsKey(serverName)) {
            return;
        }

        this.serverInstancesMap.put(serverName, serverInstance);
        if (serverInstance instanceof ProxyInstance) return;

        iterateInstances(proxyInstance -> {
            proxyInstance.addServer(serverInstance.getServerName(), serverInstance.getServerAddress(), serverInstance.getServerTypeName(), false);
        }, ProxyInstance.class);
    }

    @SuppressWarnings("unchecked")
    public void addGameInstance(AbstractGameInstance gameInstance) {
        ServerInstance serverInstance = serverInstancesMap.get(gameInstance.getInstanceServerName());
        getServerInstance(serverInstance, MinigameInstance.class).ifPresent(minigameInstance -> {
            minigameInstance.addGameInstance(gameInstance);
        });
    }

    /**
     * We go through all loaded proxies and remove the sever from them
     */
    public void removeServerInstance(String serverName) {
        this.serverInstancesMap.remove(serverName);
        iterateInstances(proxyInstance -> proxyInstance.removeServer(serverName), ProxyInstance.class);
    }

    @SuppressWarnings("unchecked")
    public void removeGameInstance(AbstractGameInstance gameInstance) {
        ServerInstance serverInstance = serverInstancesMap.get(gameInstance.getInstanceServerName());
        getServerInstance(serverInstance, MinigameInstance.class).ifPresent(minigameInstance -> {
            minigameInstance.removeGameInstance(gameInstance);
        });
    }

    /**
     * The method checks whether serverInstance exists based on its name, and if it does,
     * then it down-casts the serverInstance to the serverInstanceClass and returns as an Optional
     */
    @SuppressWarnings("unchecked")
    public <T extends ServerInstance> Optional<T> getServerInstance(ServerInstance serverInstance, Class<T> serverInstanceClass) {
        if (serverInstance == null) return Optional.empty();
        return serverInstance.getClass().equals(serverInstanceClass) ? Optional.of((T) serverInstance) : Optional.empty();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getServerInstances(Class<T> serverInstanceClass) {
        return (List<T>) serverInstancesMap.values()
                .stream()
                .filter(serverInstance -> serverInstance.getClass().equals(serverInstanceClass))
                .collect(Collectors.toList());
    }

    /**
     * iterateInstances() works like a forEach to iterate through all server instances based on their class,
     * automatically down-casting them
     *
     * @param consumerAction      lambda perform action with down-casted serverInstance class
     * @param serverInstanceClass class to which serverInstance must be down-casted
     */
    @SuppressWarnings("unchecked")
    public <T extends ServerInstance> void iterateInstances(Consumer<? super T> consumerAction, Class<T> serverInstanceClass) {
        for (ServerInstance serverInstance : this.serverInstancesMap.values()) {
            if (serverInstance.getClass() == serverInstanceClass) {
                consumerAction.accept((T) serverInstance);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends ServerInstance> Optional<T> getServerInstance(String serverInstanceName) {
        return Optional.ofNullable((T) this.serverInstancesMap.get(serverInstanceName));
    }
}