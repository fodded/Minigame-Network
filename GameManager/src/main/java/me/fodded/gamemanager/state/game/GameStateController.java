package me.fodded.gamemanager.state.game;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GameStateController {

    private final List<AbstractGameState> gameStatesList;
    private AbstractGameState currentGameState;

    public GameStateController() {
        this.gameStatesList = new ArrayList<>();
    }

    public void registerGameState(AbstractGameState gameState) {
        gameStatesList.add(gameState);
    }

    public void setCurrentGameState(AbstractGameState gameState) {
        if(currentGameState != null) currentGameState.endGameState();
        this.currentGameState = gameState;

        gameState.startGameState();
    }

    public void switchToNextGameState() {
        currentGameState.endGameState();

        AbstractGameState nextGameState = getNextGameState(currentGameState);
        if(nextGameState != currentGameState) {
            setCurrentGameState(nextGameState);
        }
    }

    public AbstractGameState getNextGameState(AbstractGameState currentGameState) {
        int currentIndex = gameStatesList.indexOf(currentGameState);
        if (currentIndex == -1 || currentIndex == gameStatesList.size() - 1) {
            return currentGameState;
        }
        return gameStatesList.get(currentIndex + 1);
    }

    @SafeVarargs
    public final boolean isCurrentState(Class<? extends AbstractGameState>... gameStateClasses) {
        for(Class<? extends AbstractGameState> gameStateClass : gameStateClasses) {
            if(currentGameState.getClass().equals(gameStateClass)) {
                return true;
            }
        }
        return false;
    }
}
