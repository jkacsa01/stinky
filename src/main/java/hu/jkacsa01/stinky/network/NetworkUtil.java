package hu.jkacsa01.stinky.network;

import hu.jkacsa01.stinky.card.Card;
import hu.jkacsa01.stinky.card.CardValue;
import hu.jkacsa01.stinky.game.Game;
import hu.jkacsa01.stinky.game.Player;
import hu.jkacsa01.stinky.network.packet.ClientboundPacket;
import hu.jkacsa01.stinky.network.packet.PacketHandler;
import hu.jkacsa01.stinky.network.packet.ServerboundPacket;
import hu.jkacsa01.stinky.network.packet.impl.server.DisconnectS2CPacket;
import jakarta.websocket.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class NetworkUtil {

    private static final String PLAYER_KEY = "player";

    public static String readString(ByteBuffer buffer) {
        byte[] array = new byte[buffer.get()];
        buffer.get(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    public static <T extends ServerboundPacket> void registerPacket(Session session, PacketHandler<T> handler) {
        session.addMessageHandler(handler.packet(), handler);
    }

    public static void unregisterAllPacket(Session session) {
        for (MessageHandler messageHandler : session.getMessageHandlers()) {
            if (messageHandler instanceof PacketHandler<?>) session.removeMessageHandler(messageHandler);
        }
    }

    public static <T extends ServerboundPacket> void unregisterPacket(Session session, Class<T> packet) {
        for (MessageHandler messageHandler : session.getMessageHandlers()) {
            if (messageHandler instanceof PacketHandler<?> packetHandler && packetHandler.packet() == packet) {
                session.removeMessageHandler(messageHandler);
            }
        }
    }

    public static void writeString(ByteArrayOutputStream buffer, String string) {
        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        buffer.write((byte) bytes.length);
        buffer.writeBytes(bytes);
    }

    public static void writeCard(ByteArrayOutputStream buffer, Card card) {
        writeString(buffer, card.getType().getSymbol());
        writeString(buffer, card.getValue().getName());
    }

    public static void sendPacket(Session session, ClientboundPacket packet) {
        try {
            if (!session.isOpen()) {
                disconnect(session);
                return;
            }
            session.getBasicRemote().sendObject(packet);
        } catch (IOException | EncodeException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setPlayer(Session session, Player player) {
        session.getUserProperties().put(PLAYER_KEY, player);
    }

    public static Player getPlayer(Session session) {
        return (Player) session.getUserProperties().get(PLAYER_KEY);
    }

    public static void disconnect(Session session, String reason) {
        try {
            Player player = getPlayer(session);
            if (player != null) player.getGame().removePlayer(player);
            if (session.isOpen()) {
                NetworkUtil.sendPacket(session, new DisconnectS2CPacket(reason));
                session.close(new CloseReason(CloseReason.CloseCodes.PROTOCOL_ERROR, reason));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect(Session session) {
        disconnect(session, "Szerver kick.");
    }

    public static void sendPacketAll(Game game, ClientboundPacket packet) {
        for (Player p : game.getPlayers()) {
            sendPacket(p.getSession(), packet);
        }
    }
}
