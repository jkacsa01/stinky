package hu.jkacsa01.stinky.game.stinky;

import hu.jkacsa01.stinky.card.CardUtil;
import hu.jkacsa01.stinky.card.impl.NullCard;
import hu.jkacsa01.stinky.card.impl.french.FrenchCard;
import hu.jkacsa01.stinky.card.impl.french.FrenchCardValue;
import hu.jkacsa01.stinky.game.Game;
import hu.jkacsa01.stinky.game.Player;
import hu.jkacsa01.stinky.network.NetworkUtil;
import hu.jkacsa01.stinky.network.packet.PacketListener;
import hu.jkacsa01.stinky.network.packet.impl.server.ActivePlayerS2CPacket;
import hu.jkacsa01.stinky.network.packet.impl.server.PlayerDataUpdateS2CPacket;
import hu.jkacsa01.stinky.network.packet.impl.server.SlotUpdateS2CPacket;

import java.util.ArrayDeque;
import java.util.Objects;

public class StinkyGame implements Game {

    // [A] B C D    A turn  ->  [B] C D A    B turn
    private final ArrayDeque<StinkyPlayer> playerQueue = new ArrayDeque<>();
    private ArrayDeque<FrenchCard> stack = new ArrayDeque<>();
    private final StinkyPacketListener packetListener = new StinkyPacketListener(this);
    private FrenchCard currentCard;
    private FrenchCard lastCard;
    private FrenchCard lastLastCard;
    private int penality;

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public int getMaxPlayers() {
        return 2;
    }

    @Override
    public ArrayDeque<StinkyPlayer> getPlayers() {
        return playerQueue;
    }

    @Override
    public Player getPlayerOnTurn() {
        return playerQueue.getFirst();
    }

    @Override
    public void nextPlayer() {
        NetworkUtil.sendPacketAll(this, new PlayerDataUpdateS2CPacket(getPlayerOnTurn()));
        playerQueue.addLast(playerQueue.remove());
        for (Player player : playerQueue) {
            if (!player.getCards().isEmpty()) continue;
            endGame();
            break;
        }
        NetworkUtil.sendPacketAll(this, new ActivePlayerS2CPacket(getPlayerOnTurn()));
    }

    private void prevPlayer() {
        NetworkUtil.sendPacketAll(this, new PlayerDataUpdateS2CPacket(getPlayerOnTurn()));
        playerQueue.addFirst(playerQueue.removeLast());
        for (Player player : playerQueue) {
            if (!player.getCards().isEmpty()) continue;
            endGame();
            break;
        }
        NetworkUtil.sendPacketAll(this, new ActivePlayerS2CPacket(getPlayerOnTurn()));
    }

    @Override
    public void endGame() {
        reinitStack();
        System.out.println("Vége!");
    }

    @Override
    public void startGame() {
        ArrayDeque<FrenchCard> cards = CardUtil.twoPackFrench();
        while (!cards.isEmpty()) {
            playerQueue.getFirst().getCards().add(cards.remove());
            playerQueue.addLast(playerQueue.remove());
        }
        NetworkUtil.sendPacketAll(this, new ActivePlayerS2CPacket(getPlayerOnTurn()));
        for (Player stinkyPlayer : playerQueue) {
            NetworkUtil.sendPacketAll(this, new PlayerDataUpdateS2CPacket(stinkyPlayer));
        }
    }

    @Override
    public PacketListener getPacketListener() {
        return packetListener;
    }

    private static int getPenalityByValue(FrenchCardValue value) {
        return switch (value) {
            case ACE -> 4;
            case KING -> 3;
            case QUEEN -> 2;
            case JACK -> 1;
            default -> 0;
        };
    }

    public void reinitStack() {
        stack = new ArrayDeque<>();
        penality = 0;
        lastCard = null;
        lastLastCard = null;
        currentCard = null;
    }

    private void moveStackTo(StinkyPlayer player) {
        player.getCards().addAll(stack);
        reinitStack();
        System.out.println("MOVE STACK");
        NetworkUtil.sendPacketAll(this, new SlotUpdateS2CPacket(0, new NullCard()));
        NetworkUtil.sendPacketAll(this, new PlayerDataUpdateS2CPacket(player));
    }

    private void placeCard(FrenchCard card) {
        lastLastCard = lastCard;
        lastCard = currentCard;
        currentCard = card;
        stack.addFirst(currentCard);
        System.out.println(card.getName());
        NetworkUtil.sendPacketAll(this, new SlotUpdateS2CPacket(0, currentCard));
        int pen = getPenalityByValue(currentCard.getValue());
        if (pen > 0) penality = pen;
        else if (penality > 0) {
            if (--penality == 0) {
                moveStackTo(playerQueue.getLast());
                prevPlayer();
            }
            return;
        }
        nextPlayer();
    }

    public void placeCard(StinkyPlayer player) {
        if (!Objects.equals(getPlayerOnTurn().getSession().getId(), player.getSession().getId())) return;
        FrenchCard card = player.getCards().removeFirst();
        placeCard(card);
    }

    public void removePlayer(Player player) {
        getPlayers().removeFirstOccurrence(player);
        reinitStack();
    }

    private boolean hitIsValid() {
        return lastCard != null &&
                currentCard != null &&
                (currentCard.getValue() == lastCard.getValue() || (lastLastCard != null && currentCard.getValue() == lastLastCard.getValue()));
    }

    public void hitStack(StinkyPlayer player) {
        if (hitIsValid()) {
            moveStackTo(player);
            if (!Objects.equals(getPlayerOnTurn().getSession().getId(), player.getSession().getId()))
                prevPlayer();
        }
    }
    
}
