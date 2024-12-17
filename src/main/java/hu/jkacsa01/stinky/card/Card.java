package hu.jkacsa01.stinky.card;

import java.io.Serializable;

public interface Card<V extends CardValue> {

    String getName();

    V getValue();

    CardType<V> getType();
}
