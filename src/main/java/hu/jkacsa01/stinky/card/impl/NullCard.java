package hu.jkacsa01.stinky.card.impl;

import hu.jkacsa01.stinky.card.Card;
import hu.jkacsa01.stinky.card.CardType;
import hu.jkacsa01.stinky.card.CardValue;

import java.util.Set;

public class NullCard implements Card {
    @Override
    public String getName() {
        return "";
    }

    @Override
    public NullCardValue getValue() {
        return new NullCardValue();
    }

    @Override
    public CardType getType() {
        return new NullCardType();
    }

    public static class NullCardValue implements CardValue {
        @Override
        public String getName() {
            return "";
        }

        @Override
        public int getValue() {
            return 0;
        }
    }

    public static class NullCardType implements CardType {
        @Override
        public String getName() {
            return "";
        }

        @Override
        public String getSymbol() {
            return "";
        }

        @Override
        public Set<NullCardValue> getCardValues() {
            return Set.of();
        }
    }
}
