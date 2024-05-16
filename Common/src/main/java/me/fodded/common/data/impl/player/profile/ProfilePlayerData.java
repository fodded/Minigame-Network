package me.fodded.common.data.impl.player.profile;

import lombok.Getter;
import me.fodded.common.data.impl.player.AbstractPlayerData;

import java.util.UUID;

@Getter
public class ProfilePlayerData extends AbstractPlayerData {

    private long firstLogin, lastLogin;

    public ProfilePlayerData(UUID uuid) {
        super(uuid);
    }
}
