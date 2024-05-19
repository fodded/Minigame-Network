package me.fodded.common.data.statistics.impl.player.profile;

import lombok.Data;
import me.fodded.common.data.statistics.impl.player.AbstractPlayerData;

import java.util.UUID;

@Data
public class ProfilePlayerData extends AbstractPlayerData {

    private long firstLogin, lastLogin;

    public ProfilePlayerData(UUID uuid) {
        super(uuid);
        this.firstLogin = System.currentTimeMillis();
    }
}
