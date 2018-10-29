package com.gmail.borlandlp.minigamesdtools.arena.team.lobby.starter;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener;
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.ArenaLobby;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.Hotbar;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class ExampleStarterLobby extends ArenaLobby implements StarterLobby {
    ArenaEventListener listener;
    private boolean enabled = false;
    private Set<Player> playerList = new HashSet<>();
    private Hotbar defaultHotbar;

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void addPlayer(Player player) {
        Debug.print(Debug.LEVEL.NOTICE, "Player[name:" + player.getName() + "] added to StarterLobby");
        this.playerList.add(player);
        MinigamesDTools.getInstance().getHotbarAPI().bindHotbar(this.defaultHotbar, player);
    }

    @Override
    public void removePlayer(Player player) {
        Debug.print(Debug.LEVEL.NOTICE, "Player[name:" + player.getName() + "] removed from StarterLobby");
        this.playerList.remove(player);
        MinigamesDTools.getInstance().getHotbarAPI().unbindHotbar(player);
    }

    @Override
    public Set<Player> getPlayers() {
        return new HashSet<>(this.playerList);
    }

    @Override
    public void onInit() {
        this.listener = new StarterLobbyListener(this);
        try {
            this.getTeamProvider().getArena().getEventAnnouncer().register(this.listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beforeGameStarting() {
        for (Player player : this.getPlayers()) {
            this.removePlayer(player);
        }
        this.getTeamProvider().getArena().getEventAnnouncer().unregister(this.listener);
        this.enabled = false;
    }

    @Override
    public void gameEnded() {

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

    public Hotbar getDefaultHotbar() {
        return defaultHotbar;
    }

    public void setDefaultHotbar(Hotbar defaultHotbar) {
        this.defaultHotbar = defaultHotbar;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
