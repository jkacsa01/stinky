package hu.jkacsa01.stinky.game;

import java.util.List;

public interface Game<T extends Player<?>> {

    int getId();

    int getMaxPlayers();

    List<T> getAllPlayers();

    T getPlayerOnTurn();

    void nextPlayer();

    void endGame();

    void startGame();

    boolean joinPlayer(T player);

    void removePlayer(T player);
}
