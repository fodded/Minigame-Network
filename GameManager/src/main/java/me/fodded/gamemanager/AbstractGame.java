package me.fodded.gamemanager;

import lombok.Data;
import me.fodded.gamemanager.events.handler.GameEventHandler;
import me.fodded.gamemanager.map.info.IGameMapInfo;
import me.fodded.gamemanager.mechanics.GameMechanicsController;
import me.fodded.gamemanager.state.game.AbstractGameState;
import me.fodded.gamemanager.state.game.GameStateController;
import me.fodded.gamemanager.state.player.PlayerStateRegistry;
import me.fodded.gamemanager.state.player.PlayerStateTracker;
import me.fodded.gamemanager.tracker.GamePlayerTracker;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

@Data
public abstract class AbstractGame implements IGameActions {

    protected final UUID gameId;
    protected UUID gameWorldId;

    protected AbstractGameState currentGameState;
    protected IGameMapInfo gameMapInfo;

    protected final GameStateController gameStateController;

    protected final GamePlayerTracker gamePlayerTracker;
    protected final PlayerStateTracker playerStateTracker;
    protected final GameMechanicsController gameMechanicsController;

    protected final PlayerStateRegistry playerStateRegistry;
    protected final GameEventHandler eventHandler;

    protected final JavaPlugin plugin;

    public AbstractGame(JavaPlugin plugin, IGameMapInfo gameMapInfo) {
        this.plugin = plugin;
        this.gameId = UUID.randomUUID();
        this.gameMapInfo = gameMapInfo;

        this.gameStateController = new GameStateController();

        this.gamePlayerTracker = new GamePlayerTracker(this);
        this.gameMechanicsController = new GameMechanicsController(this);

        this.playerStateRegistry = new PlayerStateRegistry();
        this.playerStateTracker = new PlayerStateTracker(playerStateRegistry);

        this.eventHandler = new GameEventHandler(plugin);
    }

    protected abstract void setup();
    protected abstract void teardown();
    protected abstract void registerGameMechanics();

    @Override
    public final void start() {
        if(this.gameMapInfo == null) {
            throw new IllegalArgumentException("Game map info is null!");
        }

        this.registerGameStates();
        this.initializePlayerStates();
        this.registerGameMechanics();

        this.setup();

        List<AbstractGameState> gameStatesList = gameStateController.getGameStatesList();
        if(!gameStatesList.isEmpty()) {
            setCurrentGameState(gameStatesList.get(0));
        }
        // TODO: add game to the game instance tracker
    }

    @Override
    public final void end() {
        // TODO: remove game from the game instance tracker
        this.teardown();
        HandlerList.unregisterAll(eventHandler);
    }

    @SuppressWarnings("unchecked")
    public <T extends IGameMapInfo> T getGameMapData() {
        return (T) gameMapInfo;
    }
}
