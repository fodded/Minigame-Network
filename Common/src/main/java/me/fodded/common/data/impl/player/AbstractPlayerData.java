package me.fodded.common.data.impl.player;

import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class AbstractPlayerData {

    protected final UUID uuid;

    // Should not be used, left here for the sake of POJO
    protected AbstractPlayerData() {
        this.uuid = UUID.randomUUID();
    }

    public AbstractPlayerData(UUID uuid) {
        this.uuid = uuid;
    }
}
