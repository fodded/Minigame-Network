package me.fodded.proxyloadbalancer.info.network;

import lombok.Getter;
import me.fodded.proxyloadbalancer.NetworkManager;

import java.util.UUID;

public class NetworkPlayerController {

    @Getter
    private final static NetworkPlayerController instance = new NetworkPlayerController();

    public NetworkPlayer findNetworkPlayer(UUID playerUUID) {
        return NetworkManager.getInstance().getNetworkInstance().getNetworkPlayerMap().get(playerUUID);
    }

    public void changePlayerServer(NetworkPlayer networkPlayer, UUID serverUUID) {
        networkPlayer.setServerInstanceId(serverUUID);
    }
}