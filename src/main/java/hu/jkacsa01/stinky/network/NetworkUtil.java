package hu.jkacsa01.stinky.network;

import hu.jkacsa01.stinky.card.Card;
import hu.jkacsa01.stinky.card.CardValue;
import hu.jkacsa01.stinky.game.Game;
import hu.jkacsa01.stinky.game.Player;
import hu.jkacsa01.stinky.game.StinkyGame;
import hu.jkacsa01.stinky.game.StinkyPlayer;
import hu.jkacsa01.stinky.network.packet.Packet;
import jakarta.websocket.CloseReason;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Session;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static hu.jkacsa01.stinky.network.ClientConnectionHandler.PLAYERS;

public class NetworkUtil {

    public static String readString(ByteBuffer buffer) {
        byte[] bytes = new byte[buffer.get()];
        buffer.get(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static void writeString(ByteArrayOutputStream buffer, String string) {
        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        buffer.write((byte) bytes.length);
        buffer.writeBytes(bytes);
    }

    public static <T extends CardValue> void writeCard(ByteArrayOutputStream buffer, Card<T> card) {
        writeString(buffer, card.getType().getSymbol());
        writeString(buffer, card.getValue().getName());
    }

    public static void sendPacket(Session session, Packet packet) {
        try {
            if (!session.isOpen()) disconnect(session);
            session.getBasicRemote().sendObject(packet);
        } catch (IOException | EncodeException e) {
            throw new RuntimeException(e);
        }
    }

    public static void disconnect(Session session) {
        try {
            if (session.isOpen()) session.close(new CloseReason(CloseReason.CloseCodes.PROTOCOL_ERROR, "Szerver kick."));
            StinkyPlayer player = PLAYERS.remove(session.getId());
            if (player != null) {
                ((StinkyGame) player.getGame()).removePlayer(player);
                PLAYERS.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendPacketAll(Game<?> game, Packet packet) {
        for (Player<?> p : game.getAllPlayers()) {
            sendPacket(p.getSession(), packet);
        }
    }
}
