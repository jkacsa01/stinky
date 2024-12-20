package hu.jkacsa01.stinky.network.packet.impl.client;

import hu.jkacsa01.stinky.network.NetworkUtil;
import hu.jkacsa01.stinky.network.codec.PacketCodec;
import hu.jkacsa01.stinky.network.codec.ServerboundCodec;
import hu.jkacsa01.stinky.network.packet.ServerboundPacket;
import jakarta.websocket.DecodeException;

import java.nio.ByteBuffer;

public record JoinC2SPacket(String name) implements ServerboundPacket {

    public static class Codec extends ServerboundCodec<JoinC2SPacket> {

        @Override
        public JoinC2SPacket decode(ByteBuffer bytes) throws DecodeException {
            bytes.get();
            return new JoinC2SPacket(NetworkUtil.readString(bytes));
        }

        @Override
        public PacketCodec.ServerboundPackets getId() {
            return PacketCodec.ServerboundPackets.JOIN;
        }

    }

}
