package hu.jkacsa01.stinky.network.packet.impl.server;

import hu.jkacsa01.stinky.card.Card;
import hu.jkacsa01.stinky.network.NetworkUtil;
import hu.jkacsa01.stinky.network.packet.ClientboundPacket;

import java.io.ByteArrayOutputStream;

public record SlotUpdateS2CPacket(int slot, Card<?> card) implements ClientboundPacket {

    @Override
    public void write(ByteArrayOutputStream buf) {
        buf.write(0);
        buf.write(slot);
        NetworkUtil.writeCard(buf, card);
    }
}
