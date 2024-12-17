package hu.jkacsa01.stinky.network.packet.impl.client;

import hu.jkacsa01.stinky.game.Player;
import hu.jkacsa01.stinky.game.StinkyPlayer;
import hu.jkacsa01.stinky.network.ClientConnectionHandler;
import hu.jkacsa01.stinky.network.NetworkUtil;
import hu.jkacsa01.stinky.network.packet.ServerboundPacket;
import jakarta.websocket.Session;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class JoinC2SPacket extends ServerboundPacket {

    private final String name;
    private final int matchId = 0;

    public JoinC2SPacket(ByteBuffer buf) {
        super(buf);
        this.name = NetworkUtil.readString(buf);
    }

    @Override
    public void handle(Player<?> player) {
    }

    @Override
    public void handle(Session session) {
        ClientConnectionHandler.MATCH.joinPlayer(new StinkyPlayer(session, name, ClientConnectionHandler.MATCH));
    }
}
