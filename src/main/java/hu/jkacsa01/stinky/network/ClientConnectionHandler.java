package hu.jkacsa01.stinky.network;

import hu.jkacsa01.stinky.card.impl.french.FrenchCard;
import hu.jkacsa01.stinky.game.Player;
import hu.jkacsa01.stinky.game.StinkyGame;
import hu.jkacsa01.stinky.game.StinkyPlayer;
import hu.jkacsa01.stinky.network.packet.ServerboundPacket;
import hu.jkacsa01.stinky.network.packet.impl.client.JoinC2SPacket;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import static hu.jkacsa01.stinky.network.NetworkUtil.disconnect;

@ServerEndpoint(value = "/stinky", encoders = {PacketHandler.PacketEncoder.class}, decoders = {PacketHandler.PacketDecoder.class})
public class ClientConnectionHandler {

    public static final StinkyGame MATCH = new StinkyGame();
    public static final HashMap<String, StinkyPlayer> PLAYERS = new HashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        //System.out.println("Uj kliens!!! :"+session.getId());
    }

    @OnMessage
    public void onPacket(Session session, ServerboundPacket packet) throws IOException {
        if (packet == null) return;

        // Ha már a játékos fent van
        if (packet instanceof JoinC2SPacket join) {
            if (PLAYERS.containsKey(session.getId())) {
                disconnect(session);
                return;
            }
            join.handle(session);
        }

        Player<?> player = PLAYERS.get(session.getId());

        if (player == null) {
            disconnect(session);
            return;
        }

        if (!(packet instanceof JoinC2SPacket)) packet.handle(session);
    }

    @OnClose
    public void onClose(CloseReason reason, Session session) {
        System.out.println("Elment "+session.getId()+" a következő dolog miatt: "+reason.getReasonPhrase());
        disconnect(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("Valami hiba van :/");
        throwable.printStackTrace();
    }

}
