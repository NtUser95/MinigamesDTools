package com.gmail.borlandlp.minigamesdtools.arena.team.lobby.spectator;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.Hotbar;
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.ArenaLobby;
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.PlayerLocker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

public class ExampleSpectatorLobby extends ArenaLobby implements SpectatorLobby, PlayerLocker {
    private Set<Player> players = new HashSet<>();
    private boolean enabled_watching;
    private Hotbar hotbar;
    private ArenaEventListener listener;

    public Hotbar getHotbar() {
        return hotbar;
    }

    public void setHotbar(Hotbar hotbar) {
        this.hotbar = hotbar;
    }

    public boolean isEnabled_watching() {
        return enabled_watching;
    }

    public void setEnabled_watching(boolean enabled_watching) {
        this.enabled_watching = enabled_watching;
    }

    @Override
    public void onInit() {
        this.listener = new ExampleSpectatorLobbyListener(this);
        Bukkit.getServer().getPluginManager().registerEvents((Listener) this.listener, MinigamesDTools.getInstance());
    }

    @Override
    public void beforeGameStarting() {

    }

    @Override
    public void gameEnded() {
        HandlerList.unregisterAll((Listener) this.listener);
    }

    @Override
    public void update() {

    }

    @Override
    public void beforeRoundStarting() {

    }

    @Override
    public void onRoundEnd() {

    }

    @Override
    public void addPlayer(Player player) {
        Debug.print(Debug.LEVEL.NOTICE, "Add player[name:" + player.getName() + "] to Spectator lobby, arena:" + this.getTeamProvider().getArena().getName());
        player.teleport(this.getSpawnPoint());
        this.players.add(player);

        if(this.hotbar != null) {
            MinigamesDTools.getInstance().getHotbarAPI().bindHotbar(this.hotbar, player);
        } else {
            Debug.print(Debug.LEVEL.NOTICE, "binded hotbar for player[name:" + player.getName() + "] is null.");
        }
    }

    @Override
    public void removePlayer(Player player) {
        this.players.remove(player);
        MinigamesDTools.getInstance().getHotbarAPI().unbindHotbar(player);
        /*if(player.getSpectatorTarget() != null) {
            player.setSpectatorTarget(null);
        }
        player.setGameMode(GameMode.SURVIVAL);*/
    }

    @Override
    public Set<Player> getPlayers() {
        return this.players;
    }
}
