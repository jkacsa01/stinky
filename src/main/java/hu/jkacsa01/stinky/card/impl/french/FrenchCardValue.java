package hu.jkacsa01.stinky.card.impl.french;

import hu.jkacsa01.stinky.card.CardValue;

public enum FrenchCardValue implements CardValue {
    ACE("A"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    JACK("J"),
    QUEEN("Q"),
    KING("K");

    private final String name;

    FrenchCardValue(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getValue() {
        return ordinal()+1;
    }

    public boolean isFigure() {
        return this == KING || this == QUEEN || this == JACK;
    }

    // length = 13; max ordinal = 12
    public boolean isNeighbourOf(FrenchCardValue value) {
        return ordinal()+1 % 13 == value.ordinal() || (ordinal()+12) % 13 == value.ordinal();
    }
}
