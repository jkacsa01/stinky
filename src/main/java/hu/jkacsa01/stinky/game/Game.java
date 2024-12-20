package hu.jkacsa01.stinky.game;

import hu.jkacsa01.stinky.network.packet.PacketListener;

import java.util.Collection;

public interface Game {

    int getId();

    int getMaxPlayers();

    Collection<? extends Player> getPlayers();

    Player getPlayerOnTurn();

    void nextPlayer();

    void endGame();

    void startGame();

    PacketListener getPacketListener();

    void removePlayer(Player player);
}
