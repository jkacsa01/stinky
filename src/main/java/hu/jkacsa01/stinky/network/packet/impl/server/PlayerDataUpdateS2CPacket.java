package hu.jkacsa01.stinky.network.packet.impl.server;

import hu.jkacsa01.stinky.game.Player;
import hu.jkacsa01.stinky.network.NetworkUtil;
import hu.jkacsa01.stinky.network.codec.ClientboundCodec;
import hu.jkacsa01.stinky.network.codec.PacketCodec;
import hu.jkacsa01.stinky.network.packet.ClientboundPacket;
import jakarta.websocket.EncodeException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public record PlayerDataUpdateS2CPacket(Player player) implements ClientboundPacket {

    public static class Codec extends ClientboundCodec<PlayerDataUpdateS2CPacket> {

        @Override
        public PacketCodec.ClientboundPackets getId() {
            return PacketCodec.ClientboundPackets.PLAYER_UPDATE;
        }

        @Override
        public void encode(PlayerDataUpdateS2CPacket packet, ByteArrayOutputStream stream) throws EncodeException, IOException {
            NetworkUtil.writeString(stream, packet.player.getName());
            stream.write(packet.player.getCards().size());
        }

    }
}
