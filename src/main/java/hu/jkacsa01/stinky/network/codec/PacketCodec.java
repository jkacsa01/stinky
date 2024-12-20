package hu.jkacsa01.stinky.network.codec;

import hu.jkacsa01.stinky.network.packet.ClientboundPacket;
import hu.jkacsa01.stinky.network.packet.ServerboundPacket;
import hu.jkacsa01.stinky.network.packet.impl.client.InteractCardC2SPacket;
import hu.jkacsa01.stinky.network.packet.impl.client.JoinC2SPacket;
import hu.jkacsa01.stinky.network.packet.impl.server.ActivePlayerS2CPacket;
import hu.jkacsa01.stinky.network.packet.impl.server.DisconnectS2CPacket;
import hu.jkacsa01.stinky.network.packet.impl.server.PlayerDataUpdateS2CPacket;
import hu.jkacsa01.stinky.network.packet.impl.server.SlotUpdateS2CPacket;

import java.io.ByteArrayOutputStream;

public class PacketCodec {

    public enum ServerboundPackets {
        JOIN(JoinC2SPacket.class),
        INTERACT(InteractCardC2SPacket.class);

        public final Class<? extends ServerboundPacket> packet;

        ServerboundPackets(Class<? extends ServerboundPacket> packet) {
            this.packet = packet;
        }
    }

    public enum ClientboundPackets {
        SLOT_UPDATE(SlotUpdateS2CPacket.class),
        ACTIVE_PLAYER(ActivePlayerS2CPacket.class),
        PLAYER_UPDATE(PlayerDataUpdateS2CPacket.class),
        DISCONNECT(DisconnectS2CPacket.class);

        public final Class<? extends ClientboundPacket> packet;

        <T extends ClientboundPacket> ClientboundPackets(Class<? extends ClientboundPacket> packet) {
            this.packet = packet;

        }

        public static void write(ClientboundPacket packet, ByteArrayOutputStream stream) {
        }
    }
}
