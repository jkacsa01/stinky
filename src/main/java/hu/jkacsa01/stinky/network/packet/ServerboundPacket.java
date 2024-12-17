package hu.jkacsa01.stinky.network.packet;

import hu.jkacsa01.stinky.game.Player;
import jakarta.websocket.Session;

import java.nio.ByteBuffer;

import static hu.jkacsa01.stinky.network.ClientConnectionHandler.PLAYERS;

public abstract class ServerboundPacket implements Packet {

    public ServerboundPacket(ByteBuffer buf) {
    }

    public abstract void handle(Player<?> player);

    public void handle(Session session) {
        handle(PLAYERS.get(session.getId()));
    }
}
