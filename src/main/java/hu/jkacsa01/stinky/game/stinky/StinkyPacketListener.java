package hu.jkacsa01.stinky.game.stinky;

import hu.jkacsa01.stinky.game.Player;
import hu.jkacsa01.stinky.network.NetworkUtil;
import hu.jkacsa01.stinky.network.packet.PacketHandler;
import hu.jkacsa01.stinky.network.packet.PacketListener;
import hu.jkacsa01.stinky.network.packet.impl.client.InteractCardC2SPacket;
import hu.jkacsa01.stinky.network.packet.impl.server.PlayerDataUpdateS2CPacket;
import jakarta.websocket.Session;

public class StinkyPacketListener implements PacketListener {

    private final StinkyGame game;

    public StinkyPacketListener(StinkyGame game) {
        this.game = game;
    }

    @Override
    public void joinPlayer(String name, Session session) {
        if (!canPlayerJoin(name, session)) return;

        StinkyPlayer player = new StinkyPlayer(session, name, game);

        System.out.println("Uj jatekos!!!: "+player.getName());

        NetworkUtil.unregisterAllPacket(session);
        NetworkUtil.registerPacket(session, new PacketHandler<>(InteractCardC2SPacket.class, i -> interactSlot(NetworkUtil.getPlayer(session), i.slot())));

        game.getPlayers().addLast(player);
        game.getPlayers().forEach((p) -> System.out.println(p.getName()));

        if (game.getPlayers().size() == game.getMaxPlayers()) game.startGame();
        else NetworkUtil.sendPacketAll(game, new PlayerDataUpdateS2CPacket(player));

    }

    @Override
    public boolean canPlayerJoin(String name, Session session) {
        return game.getPlayers().size() < game.getMaxPlayers() || game.getMaxPlayers() == 0;
    }

    public void interactSlot(Player player, int slot) {
        switch (slot) {
            case 0 -> game.placeCard((StinkyPlayer) player);
            case 1 -> game.hitStack((StinkyPlayer) player);
        }
    }
}
