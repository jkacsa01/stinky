package hu.jkacsa01.stinky.game.stinky;

import hu.jkacsa01.stinky.card.impl.french.FrenchCard;
import hu.jkacsa01.stinky.game.Player;
import hu.jkacsa01.stinky.network.NetworkUtil;
import jakarta.websocket.Session;

import java.util.ArrayDeque;

public class StinkyPlayer implements Player {

    private final Session session;
    private final String name;
    private final StinkyGame game;
    private final ArrayDeque<FrenchCard> cards = new ArrayDeque<>();

    public StinkyPlayer(Session session, String name, StinkyGame game) {
        this.session = session;
        this.name = name;
        this.game = game;
        NetworkUtil.setPlayer(session, this);
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
    public ArrayDeque<FrenchCard> getCards() {
        return cards;
    }

    @Override
    public StinkyGame getGame() {
        return game;
    }
}
