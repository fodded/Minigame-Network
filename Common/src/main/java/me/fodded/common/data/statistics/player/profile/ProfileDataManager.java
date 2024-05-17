package me.fodded.common.data.statistics.player.profile;

import lombok.Getter;
import me.fodded.common.data.statistics.player.PlayerDataManager;

import java.util.UUID;

public class ProfileDataManager extends PlayerDataManager<UUID, ProfilePlayerData> {

    @Getter
    private static final ProfileDataManager instance = new ProfileDataManager();

    private ProfileDataManager() {
        super(ProfilePlayerData::new);
    }
}
