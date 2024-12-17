package hu.jkacsa01.stinky.network.packet.impl.client;

import hu.jkacsa01.stinky.game.Player;
import hu.jkacsa01.stinky.game.StinkyPlayer;
import hu.jkacsa01.stinky.network.ClientConnectionHandler;
import hu.jkacsa01.stinky.network.packet.ServerboundPacket;

import java.nio.ByteBuffer;

public class InteractCardC2SPacket extends ServerboundPacket {

    public final int slot;

    public InteractCardC2SPacket(ByteBuffer buf) {
        super(buf);
        this.slot = buf.get();
    }

    @Override
    public void handle(Player<?> player) {
        if (slot == 0) {
            ClientConnectionHandler.MATCH.placeCard((StinkyPlayer) player);
        } else if (slot == 1) {
            ClientConnectionHandler.MATCH.hitStack((StinkyPlayer) player);
        }
    }

}
