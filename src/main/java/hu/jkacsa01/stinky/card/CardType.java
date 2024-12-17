package hu.jkacsa01.stinky.card;

import java.util.Collection;
import java.util.Set;

public interface CardType<T extends CardValue> {

    String getName();

    String getSymbol();

    Set<T> getCardValues();
}
