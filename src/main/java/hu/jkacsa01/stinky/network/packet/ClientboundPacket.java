package hu.jkacsa01.stinky.network.packet;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public interface ClientboundPacket extends Packet {

    void write(ByteArrayOutputStream buf);
}
