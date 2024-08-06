package me.fodded.gamemanager.mechanics;

import me.fodded.gamemanager.AbstractGame;
import me.fodded.gamemanager.mechanics.impl.GameBreakBlockMechanic;
import me.fodded.gamemanager.mechanics.impl.GameDamageMechanic;
import me.fodded.gamemanager.mechanics.impl.GameOpenChestMechanic;
import me.fodded.gamemanager.mechanics.impl.GamePlaceBlockMechanic;

import java.util.HashMap;
import java.util.Map;

public class GameMechanicsController {

    private final Map<Class<? extends AbstractGameMechanic>, AbstractGameMechanic> registeredGameMechanics = new HashMap<>();
    private final AbstractGame game;

    public GameMechanicsController(AbstractGame game) {
        this.game = game;
        registerDefaults();
    }

    public void registerMechanic(AbstractGameMechanic gameMechanic) {
        this.registeredGameMechanics.put(gameMechanic.getClass(), gameMechanic);
    }

    public AbstractGameMechanic getGameMechanic(Class<? extends AbstractGameMechanic> gameMechanicClass) {
        return this.registeredGameMechanics.get(gameMechanicClass);
    }

    public void enable(Class<? extends AbstractGameMechanic>... gameMechanicClasses) {
        toggle(true, gameMechanicClasses);
    }

    public void disable(Class<? extends AbstractGameMechanic>... gameMechanicClasses) {
        toggle(false, gameMechanicClasses);
    }

    @SafeVarargs
    private final void toggle(boolean flag, Class<? extends AbstractGameMechanic>... gameMechanicClasses) {
        for (Class<? extends AbstractGameMechanic> gameMechanicClass : gameMechanicClasses) {
            AbstractGameMechanic gameMechanic = getGameMechanic(gameMechanicClass);
            gameMechanic.setEnabled(flag);
        }
    }

    private void registerDefaults() {
        registerMechanic(new GameDamageMechanic(game));
        registerMechanic(new GameOpenChestMechanic(game));
        registerMechanic(new GameBreakBlockMechanic(game));
        registerMechanic(new GamePlaceBlockMechanic(game));
    }
}