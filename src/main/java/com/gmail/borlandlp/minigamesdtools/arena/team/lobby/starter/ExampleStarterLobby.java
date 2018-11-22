package com.gmail.borlandlp.minigamesdtools.arena.team.lobby.starter;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener;
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.ArenaLobby;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ExampleStarterLobby extends ArenaLobby implements StarterLobby {
    ArenaEventListener listener;
    private Set<Player> playerList = new HashSet<>();

    @Override
    public void forceReleasePlayer(Player p) {
        this.removePlayer(p);
    }

    @Override
    public void addPlayer(Player player) {
        Debug.print(Debug.LEVEL.NOTICE, "Player[name:" + player.getName() + "] added to StarterLobby");
        this.playerList.add(player);

        if(MinigamesDTools.getInstance().getHotbarAPI().isBindedPlayer(player)) {
            MinigamesDTools.getInstance().getHotbarAPI().unbindHotbar(player);
        }
        if(this.isHotbarEnabled()) {
            MinigamesDTools.getInstance().getHotbarAPI().bindHotbar(this.getHotbarFor(player), player);
        }
    }

    @Override
    public void removePlayer(Player player) {
        Debug.print(Debug.LEVEL.NOTICE, "Player[name:" + player.getName() + "] removed from StarterLobby");
        this.playerList.remove(player);
        if(this.isHotbarEnabled()) {
            MinigamesDTools.getInstance().getHotbarAPI().unbindHotbar(player);
        }
        if(this.getTeamProvider().getArena().getHotbarController().isEnabled()) {
            try {
                MinigamesDTools.getInstance().getHotbarAPI().bindHotbar(this.getTeamProvider().getArena().getHotbarController().buildDefaultHotbarFor(player), player);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
        for (Player player : new ArrayList<>(this.playerList)) {
            this.removePlayer(player);
        }
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
}
