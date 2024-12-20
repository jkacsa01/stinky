package hu.jkacsa01.stinky.network.packet.impl.client;

import hu.jkacsa01.stinky.network.codec.PacketCodec;
import hu.jkacsa01.stinky.network.codec.ServerboundCodec;
import hu.jkacsa01.stinky.network.packet.ServerboundPacket;
import jakarta.websocket.DecodeException;

import java.nio.ByteBuffer;

public record InteractCardC2SPacket(int slot) implements ServerboundPacket {

    public static class Codec extends ServerboundCodec<InteractCardC2SPacket> {

        @Override
        public InteractCardC2SPacket decode(ByteBuffer bytes) throws DecodeException {
            bytes.get();
            return new InteractCardC2SPacket(bytes.get());
        }

        @Override
        public PacketCodec.ServerboundPackets getId() {
            return PacketCodec.ServerboundPackets.INTERACT;
        }

    }

}
