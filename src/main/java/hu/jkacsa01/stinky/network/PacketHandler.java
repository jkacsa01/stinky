package hu.jkacsa01.stinky.network;

import hu.jkacsa01.stinky.network.packet.ClientboundPacket;
import hu.jkacsa01.stinky.network.packet.Packet;
import hu.jkacsa01.stinky.network.packet.ServerboundPacket;
import hu.jkacsa01.stinky.network.packet.impl.client.JoinC2SPacket;
import hu.jkacsa01.stinky.network.packet.impl.client.InteractCardC2SPacket;
import jakarta.websocket.*;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PacketHandler {

    private static final ArrayList<Class<? extends ClientboundPacket>> S2C_PACKETS = new ArrayList<>();
    private static final ArrayList<Class<? extends ServerboundPacket>> C2S_PACKETS = new ArrayList<>();

    public static List<Class<? extends Packet>> getPackets() {
        return Collections.unmodifiableList(C2S_PACKETS);
    }

    static {
        register(JoinC2SPacket.class);
        register(InteractCardC2SPacket.class);
    }

    public static void register(Class<? extends ServerboundPacket> packet) {
        C2S_PACKETS.add(packet);
    }

    public static ServerboundPacket decode(ByteBuffer buf) {
        try {
            int id = buf.get();
            Class<? extends ServerboundPacket> packet = C2S_PACKETS.get(id);
            return packet.getDeclaredConstructor(ByteBuffer.class).newInstance(buf);
        } catch (NoSuchMethodException ignored) {
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class PacketEncoder implements Encoder.Binary<ClientboundPacket> {
        @Override
        public ByteBuffer encode(ClientboundPacket packet) throws EncodeException {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            packet.write(buffer);
            return ByteBuffer.wrap(buffer.toByteArray());
        }
    }

    public static class PacketDecoder implements Decoder.Binary<ServerboundPacket> {
        @Override
        public ServerboundPacket decode(ByteBuffer byteBuffer) throws DecodeException {
            return PacketHandler.decode(byteBuffer);
        }

        @Override
        public boolean willDecode(ByteBuffer buf) {
            int id = buf.get(0);
            return id >= 0 && id < getPackets().size();
        }
    }
}
