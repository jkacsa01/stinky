package hu.jkacsa01.stinky.game;

import hu.jkacsa01.stinky.card.Card;
import jakarta.websocket.Session;

import java.util.Collection;

public interface Player<T extends Card<?>> {

    Session getSession();

    String getName();

    Collection<T> getCards();

    Game<? extends Player<T>> getGame();
}
