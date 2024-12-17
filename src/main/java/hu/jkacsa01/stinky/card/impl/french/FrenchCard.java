package hu.jkacsa01.stinky.card.impl.french;

import hu.jkacsa01.stinky.card.Card;
import hu.jkacsa01.stinky.card.CardType;

public class FrenchCard implements Card<FrenchCardValue> {

    private final FrenchCardType type;
    private final FrenchCardValue value;

    public FrenchCard(FrenchCardType type, FrenchCardValue value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String getName() {
        return type.getSymbol() + value.getName();
    }

    @Override
    public FrenchCardValue getValue() {
        return value;
    }

    @Override
    public CardType<FrenchCardValue> getType() {
        return type;
    }
}
