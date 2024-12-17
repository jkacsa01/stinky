package hu.jkacsa01.stinky.game;

import hu.jkacsa01.stinky.card.impl.french.FrenchCard;
import jakarta.websocket.Session;

import java.util.ArrayDeque;
import java.util.Collection;

public class StinkyPlayer implements Player<FrenchCard> {

    private final Session session;
    private final String name;
    private final StinkyGame game;
    private final ArrayDeque<FrenchCard> cards = new ArrayDeque<>();

    public StinkyPlayer(Session session, String name, StinkyGame game) {
        this.session = session;
        this.name = name;
        this.game = game;
    }

    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<FrenchCard> getCards() {
        return cards;
    }

    @Override
    public Game<? extends Player<FrenchCard>> getGame() {
        return game;
    }
}
