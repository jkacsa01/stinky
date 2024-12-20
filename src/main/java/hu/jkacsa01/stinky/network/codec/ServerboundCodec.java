package hu.jkacsa01.stinky.network.codec;

import hu.jkacsa01.stinky.network.packet.ServerboundPacket;
import jakarta.websocket.Decoder;

import java.nio.ByteBuffer;

public abstract class ServerboundCodec<T extends ServerboundPacket> implements Decoder.Binary<T> {

    @Override
    public boolean willDecode(ByteBuffer bytes) {
        int id = bytes.get(0);
        return id == getId().ordinal();
    }

    public abstract PacketCodec.ServerboundPackets getId();
}
