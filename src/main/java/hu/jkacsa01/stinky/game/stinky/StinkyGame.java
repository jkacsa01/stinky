package hu.jkacsa01.stinky.game.stinky;

import hu.jkacsa01.stinky.card.CardUtil;
import hu.jkacsa01.stinky.card.impl.LoseCard;
import hu.jkacsa01.stinky.card.impl.NullCard;
import hu.jkacsa01.stinky.card.impl.WinCard;
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
    private ArrayDeque<FrenchCard> punishStack = new ArrayDeque<>();
    private ArrayDeque<FrenchCard> stack = new ArrayDeque<>();
    private final StinkyPacketListener packetListener = new StinkyPacketListener(this);
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
        NetworkUtil.sendPacketAll(this, new ActivePlayerS2CPacket(getPlayerOnTurn()));
        endGame();
    }

    private void prevPlayer() {
        NetworkUtil.sendPacketAll(this, new PlayerDataUpdateS2CPacket(getPlayerOnTurn()));
        playerQueue.addFirst(playerQueue.removeLast());
        NetworkUtil.sendPacketAll(this, new ActivePlayerS2CPacket(getPlayerOnTurn()));
        endGame();
    }

    @Override
    public void endGame() {
        boolean firstEmpty = playerQueue.getFirst().getCards().isEmpty();
        boolean lastEmpty = playerQueue.getLast().getCards().isEmpty();
        Player player;
        if (firstEmpty) player = playerQueue.getLast();
        else if (lastEmpty) player = playerQueue.getFirst();
        else return;
        reinitStack();
        for (Player p : playerQueue) {
            if (Objects.equals(p.getSession().getId(), player.getSession().getId()))
                NetworkUtil.sendPacket(p.getSession(), new SlotUpdateS2CPacket(0, new WinCard()));
            else
                NetworkUtil.sendPacket(p.getSession(), new SlotUpdateS2CPacket(0, new LoseCard()));
            NetworkUtil.disconnect(p.getSession(), "A nyertes: "+player.getName()+"\nFrissitsd az oldalt uj jatekhoz!");
        }
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
        punishStack = new ArrayDeque<>();
        penality = 0;
        lastCard = null;
        lastLastCard = null;
    }

    private void moveStackTo(StinkyPlayer player) {
        player.getCards().addAll(stack);
        player.getCards().addAll(punishStack);
        reinitStack();
        System.out.println("MOVE STACK");
        NetworkUtil.sendPacketAll(this, new SlotUpdateS2CPacket(0, new NullCard()));
        NetworkUtil.sendPacketAll(this, new PlayerDataUpdateS2CPacket(player));
    }

    private void placeCard(FrenchCard card) {
        lastLastCard = lastCard;
        lastCard = stack.isEmpty() ? null : stack.getFirst();
        stack.addFirst(card);
        System.out.println(card.getName());
        NetworkUtil.sendPacketAll(this, new SlotUpdateS2CPacket(0, card));
        int pen = getPenalityByValue(card.getValue());
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
        if (player.getCards().isEmpty()) {
            nextPlayer();
            return;
        }
        FrenchCard card = player.getCards().removeFirst();
        placeCard(card);
    }

    public void removePlayer(Player player) {
        getPlayers().removeFirstOccurrence(player);
        reinitStack();
    }

    private boolean hitIsValid() {
        return lastCard != null && (stack.getFirst().getValue() == lastCard.getValue() || (lastLastCard != null && stack.getFirst().getValue() == lastLastCard.getValue()));
    }

    public void hitStack(StinkyPlayer player) {
        if (hitIsValid()) {
            moveStackTo(player);
            if (!Objects.equals(getPlayerOnTurn().getSession().getId(), player.getSession().getId()))
                prevPlayer();
        } else {
            if (player.getCards().isEmpty()) return;
            punishStack.addFirst(player.getCards().remove());
            NetworkUtil.sendPacketAll(this, new PlayerDataUpdateS2CPacket(player));
        }
    }

}
