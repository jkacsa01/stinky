package hu.jkacsa01.stinky.network;

import java.nio.ByteBuffer;

public interface Sendable {
    void write(ByteBuffer buffer);
}
