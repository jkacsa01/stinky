package hu.jkacsa01.stinky.network.codec;

import hu.jkacsa01.stinky.network.packet.ClientboundPacket;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public abstract class ClientboundCodec<T extends ClientboundPacket> implements Encoder.BinaryStream<T> {

    public abstract PacketCodec.ClientboundPackets getId();

    public abstract void encode(T packet, ByteArrayOutputStream stream) throws EncodeException, IOException;

    @Override
    public void encode(T object, OutputStream os) throws EncodeException, IOException {
        ByteArrayOutputStream array = (ByteArrayOutputStream) os;
        array.write(getId().ordinal());
        encode(object, array);
    }
}
