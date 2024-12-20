package hu.jkacsa01.stinky.card;

import java.util.Collection;
import java.util.Set;

public interface CardType {

    String getName();

    String getSymbol();

    Set<? extends CardValue> getCardValues();
}
