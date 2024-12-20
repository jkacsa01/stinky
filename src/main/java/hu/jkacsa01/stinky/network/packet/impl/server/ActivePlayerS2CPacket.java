package hu.jkacsa01.stinky.network.packet.impl.server;

import hu.jkacsa01.stinky.game.Player;
import hu.jkacsa01.stinky.network.NetworkUtil;
import hu.jkacsa01.stinky.network.codec.ClientboundCodec;
import hu.jkacsa01.stinky.network.codec.PacketCodec;
import hu.jkacsa01.stinky.network.packet.ClientboundPacket;
import jakarta.websocket.EncodeException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public record ActivePlayerS2CPacket(Player player) implements ClientboundPacket {

    public static class Codec extends ClientboundCodec<ActivePlayerS2CPacket> {

        @Override
        public PacketCodec.ClientboundPackets getId() {
            return PacketCodec.ClientboundPackets.ACTIVE_PLAYER;
        }

        @Override
        public void encode(ActivePlayerS2CPacket packet, ByteArrayOutputStream stream) throws EncodeException, IOException {
            NetworkUtil.writeString(stream, packet.player.getName());
        }

    }

}
