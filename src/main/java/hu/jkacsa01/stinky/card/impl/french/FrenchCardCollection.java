package hu.jkacsa01.stinky.card.impl.french;

import hu.jkacsa01.stinky.card.Card;
import hu.jkacsa01.stinky.card.CardCollection;

import java.util.*;

public class FrenchCardCollection implements CardCollection<FrenchCard> {

    public static final FrenchCardCollection INSTANCE = new FrenchCardCollection();
    public final Set<FrenchCard> ALL_CARDS;

    FrenchCardCollection() {
        ArrayDeque<FrenchCard> frenchCards = new ArrayDeque<>();
        for (FrenchCardType type : FrenchCardType.values()) {
            for (FrenchCardValue value : type.getCardValues()) {
                frenchCards.add(new FrenchCard(type, value));
            }
        }
        ALL_CARDS = Set.copyOf(frenchCards);
    }

    @Override
    public String getName() {
        return "Francia kártya";
    }

    @Override
    public Collection<FrenchCard> getAllCards() {
        return ALL_CARDS;
    }
}
