package hu.jkacsa01.stinky.card;

import java.io.Serializable;

public interface Card {

    String getName();

    CardValue getValue();

    CardType getType();
}
