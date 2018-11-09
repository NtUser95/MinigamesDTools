package com.gmail.borlandlp.minigamesdtools.arena.team.lobby.respawn;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener;
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.ArenaLobby;
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.PlayerLocker;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public class ExampleRespawnLobby extends ArenaLobby implements RespawnLobby, PlayerLocker {
    private int secondsWaiting;
    private Map<Player, Long> players = new Hashtable<>();// player -> unixtime
    private ArenaEventListener listener;
    private Map<Player, BossBar> bossBarMap = new Hashtable<>();

    @Override
    public void forceReleasePlayer(Player p) {
        this.removePlayer(p);
    }

    @Override
    public void addPlayer(Player player) {
        Debug.print(Debug.LEVEL.NOTICE, "Add player[name:" + player.getName() + "] to Respawn lobby");
        player.teleport(this.getSpawnPoint());
        this.players.put(player, Instant.now().getEpochSecond());

        BossBar bossBar = Bukkit.createBossBar("Возрождение", BarColor.RED, BarStyle.SOLID);
        bossBar.setProgress(1D);
        bossBar.setVisible(true);
        bossBar.addPlayer(player);
        this.bossBarMap.put(player, bossBar);

        if(MinigamesDTools.getInstance().getHotbarAPI().isBindedPlayer(player)) {
            MinigamesDTools.getInstance().getHotbarAPI().unbindHotbar(player);
        }
        if(this.isHotbarEnabled()) {
            MinigamesDTools.getInstance().getHotbarAPI().bindHotbar(this.getHotbar(), player);
        }
    }

    @Override
    public void removePlayer(Player player) {
        this.bossBarMap.get(player).removePlayer(player);
        this.bossBarMap.remove(player);
        this.players.remove(player);

        if(this.isHotbarEnabled()) {
            MinigamesDTools.getInstance().getHotbarAPI().unbindHotbar(player);
        }
        if(this.getTeamProvider().getArena().getHotbarController().isEnabled()) {
            MinigamesDTools.getInstance().getHotbarAPI().bindHotbar(this.getTeamProvider().getArena().getHotbarController().getDefaultHotbar(), player);
        }
    }

    @Override
    public void onInit() {
        this.listener = new ExampleRespawnLobbyListener(this);
        try {
            this.getTeamProvider().getArena().getEventAnnouncer().register(this.listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beforeGameStarting() {

    }

    @Override
    public void gameEnded() {

    }

    // draw inventorygui and other func
    @Override
    public void update() {
        for (Player player : this.bossBarMap.keySet()) {
            long remainS = (this.players.get(player) + this.secondsWaiting) - Instant.now().getEpochSecond();
            this.bossBarMap.get(player).setTitle("До возрождения: " + remainS + " с.");
            this.bossBarMap.get(player).setProgress((double)remainS / (double)this.secondsWaiting);
        }
    }

    @Override
    public void beforeRoundStarting() {

    }

    @Override
    public void onRoundEnd() {

    }

    @Override
    public Set<Player> getReadyPlayersToRespawn() {
        Set<Player> rPlayers = new HashSet<>();
        for (Player player : this.players.keySet()) {
            if((this.players.get(player) + this.secondsWaiting) <= Instant.now().getEpochSecond()) {
                rPlayers.add(player);
            }
        }

        return rPlayers;
    }

    @Override
    public Map<Player, Long> getWaitingPlayers() {
        return new Hashtable<>(this.players);
    }

    public void setSecondsWaiting(int secondsWaiting) {
        this.secondsWaiting = secondsWaiting;
    }
}
