package hu.jkacsa01.stinky.card;

import java.util.Collection;
import java.util.Set;

// For example: hungarian, french, uno etc...
public interface CardCollection<V extends Card<? extends CardValue>> {

    String getName();

    Collection<V> getAllCards();
}
