package hu.jkacsa01.stinky.network;

import hu.jkacsa01.stinky.game.stinky.StinkyGame;
import hu.jkacsa01.stinky.network.packet.PacketHandler;
import hu.jkacsa01.stinky.network.packet.ServerboundPacket;
import hu.jkacsa01.stinky.network.packet.impl.client.InteractCardC2SPacket;
import hu.jkacsa01.stinky.network.packet.impl.client.JoinC2SPacket;
import hu.jkacsa01.stinky.network.packet.impl.server.ActivePlayerS2CPacket;
import hu.jkacsa01.stinky.network.packet.impl.server.DisconnectS2CPacket;
import hu.jkacsa01.stinky.network.packet.impl.server.PlayerDataUpdateS2CPacket;
import hu.jkacsa01.stinky.network.packet.impl.server.SlotUpdateS2CPacket;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import static hu.jkacsa01.stinky.network.NetworkUtil.disconnect;

@ServerEndpoint(value = "/stinky", encoders = {
        ActivePlayerS2CPacket.Codec.class,
        DisconnectS2CPacket.Codec.class,
        PlayerDataUpdateS2CPacket.Codec.class,
        SlotUpdateS2CPacket.Codec.class}, decoders = {
        InteractCardC2SPacket.Codec.class,
        JoinC2SPacket.Codec.class
})
public class WebsocketEndpoint {

    public static final StinkyGame GAME = new StinkyGame();

    @OnOpen
    public void onOpen(Session session) {
        NetworkUtil.registerPacket(session, new PacketHandler<>(JoinC2SPacket.class, join -> GAME.getPacketListener().joinPlayer(join.name(), session)));
        //System.out.println("Uj kliens!!! :"+session.getId());
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
