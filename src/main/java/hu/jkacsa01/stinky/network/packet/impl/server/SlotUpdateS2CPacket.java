package hu.jkacsa01.stinky.network.packet.impl.server;

import hu.jkacsa01.stinky.card.Card;
import hu.jkacsa01.stinky.network.NetworkUtil;
import hu.jkacsa01.stinky.network.codec.ClientboundCodec;
import hu.jkacsa01.stinky.network.codec.PacketCodec;
import hu.jkacsa01.stinky.network.packet.ClientboundPacket;
import jakarta.websocket.EncodeException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public record SlotUpdateS2CPacket(int slot, Card card) implements ClientboundPacket {

    public static class Codec extends ClientboundCodec<SlotUpdateS2CPacket> {

        @Override
        public PacketCodec.ClientboundPackets getId() {
            return PacketCodec.ClientboundPackets.SLOT_UPDATE;
        }

        @Override
        public void encode(SlotUpdateS2CPacket packet, ByteArrayOutputStream stream) throws EncodeException, IOException {
            stream.write(packet.slot);
            NetworkUtil.writeCard(stream, packet.card);
        }

    }
}
