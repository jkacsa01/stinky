package hu.jkacsa01.stinky.network.packet;

import jakarta.websocket.MessageHandler;

import java.util.function.Consumer;

public record PacketHandler<T extends ServerboundPacket>(Class<T> packet, Consumer<T> handler) implements MessageHandler.Whole<T> {

    @Override
    public void onMessage(T t) {
        handler.accept(t);
    }
}
