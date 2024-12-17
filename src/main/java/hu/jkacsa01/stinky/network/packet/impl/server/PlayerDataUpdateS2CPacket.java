package hu.jkacsa01.stinky.network.packet.impl.server;

import hu.jkacsa01.stinky.game.Player;
import hu.jkacsa01.stinky.network.NetworkUtil;
import hu.jkacsa01.stinky.network.packet.ClientboundPacket;

import java.io.ByteArrayOutputStream;

public record PlayerDataUpdateS2CPacket(Player<?> player) implements ClientboundPacket {
    @Override
    public void write(ByteArrayOutputStream buf) {
        buf.write(2);
        NetworkUtil.writeString(buf, player.getName());
        buf.write(player.getCards().size());
    }
}
