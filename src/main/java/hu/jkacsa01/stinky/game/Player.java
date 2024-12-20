package hu.jkacsa01.stinky.game;

import hu.jkacsa01.stinky.card.Card;
import jakarta.websocket.Session;

import java.util.Collection;

public interface Player {

    Session getSession();

    String getName();

    Collection<? extends Card> getCards();

    Game getGame();
}
