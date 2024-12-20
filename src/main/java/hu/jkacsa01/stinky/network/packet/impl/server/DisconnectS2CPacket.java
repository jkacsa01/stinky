package hu.jkacsa01.stinky.network.packet.impl.server;

import hu.jkacsa01.stinky.network.NetworkUtil;
import hu.jkacsa01.stinky.network.codec.ClientboundCodec;
import hu.jkacsa01.stinky.network.codec.PacketCodec;
import hu.jkacsa01.stinky.network.packet.ClientboundPacket;
import jakarta.websocket.EncodeException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public record DisconnectS2CPacket(String reason) implements ClientboundPacket {

    public static class Codec extends ClientboundCodec<DisconnectS2CPacket> {

        @Override
        public PacketCodec.ClientboundPackets getId() {
            return PacketCodec.ClientboundPackets.DISCONNECT;
        }

        @Override
        public void encode(DisconnectS2CPacket packet, ByteArrayOutputStream stream) throws EncodeException, IOException {
            NetworkUtil.writeString(stream, packet.reason);
        }

    }

}
