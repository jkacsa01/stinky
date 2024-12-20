package hu.jkacsa01.stinky.network.packet;

import hu.jkacsa01.stinky.game.Player;
import jakarta.websocket.Session;

public interface PacketListener {

    void joinPlayer(String name, Session session);

    boolean canPlayerJoin(String name, Session session);

    void interactSlot(Player player, int slot);
}
