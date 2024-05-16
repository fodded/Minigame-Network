package me.fodded.common.data.impl.player.profile;

import lombok.Getter;
import me.fodded.common.data.impl.player.PlayerDataManager;

import java.util.UUID;

public class ProfileDataManager extends PlayerDataManager<UUID, ProfilePlayerData> {

    @Getter
    private static final ProfileDataManager instance = new ProfileDataManager();

    private ProfileDataManager() {
        super(ProfilePlayerData::new);
    }
}
