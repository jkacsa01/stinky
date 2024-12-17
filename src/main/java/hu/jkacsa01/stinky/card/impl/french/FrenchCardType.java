package hu.jkacsa01.stinky.card.impl.french;

import hu.jkacsa01.stinky.card.CardType;

import java.util.Set;

public enum FrenchCardType implements CardType<FrenchCardValue> {
    CLUBS("Treff", "♣"),
    DIAMONDS("Káró", "♦"),
    SPADES("Pikk","♠"),
    HEARTS("Kőr", "❤");

    private final String name;
    private final String symbol;
    private final Set<FrenchCardValue> cards;

    FrenchCardType(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
        this.cards = Set.of(FrenchCardValue.values());
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getSymbol() {
        return this.symbol;
    }

    @Override
    public Set<FrenchCardValue> getCardValues() {
        return this.cards;
    }
}
