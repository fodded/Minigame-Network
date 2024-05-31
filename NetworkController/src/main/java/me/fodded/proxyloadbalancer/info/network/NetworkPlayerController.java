package me.fodded.proxyloadbalancer.info.network;

import lombok.Getter;
import me.fodded.proxyloadbalancer.NetworkController;

import java.util.UUID;

public class NetworkPlayerController {

    @Getter
    private final static NetworkPlayerController instance = new NetworkPlayerController();

    public NetworkPlayer findNetworkPlayer(UUID playerUUID) {
        return NetworkController.getInstance().getNetworkInstance().getNetworkPlayerMap().get(playerUUID);
    }

    public void changePlayerServer(NetworkPlayer networkPlayer, String serverName) {
        networkPlayer.setServerInstanceName(serverName);
    }
}