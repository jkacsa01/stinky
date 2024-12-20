package hu.jkacsa01.stinky.card;

import hu.jkacsa01.stinky.card.impl.french.FrenchCard;
import hu.jkacsa01.stinky.card.impl.french.FrenchCardCollection;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;

public class CardUtil {
    public static ArrayDeque<FrenchCard> twoPackFrench() {
        ArrayList<FrenchCard> cards = new ArrayList<>();
        cards.addAll(FrenchCardCollection.INSTANCE.ALL_CARDS);
        cards.addAll(FrenchCardCollection.INSTANCE.ALL_CARDS);
        Collections.shuffle(cards);
        return new ArrayDeque<>(cards);
    }
}
