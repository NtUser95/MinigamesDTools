package com.gmail.borlandlp.minigamesdtools.lobby;

import com.gmail.borlandlp.minigamesdtools.APIComponent;
import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.config.ConfigManager;
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LobbyHubController implements APIComponent, LobbyServerAPI {
    private List<ServerLobby> lobbies = new ArrayList<>();
    private BukkitTask task;
    private Listener lobbyListener;
    private ServerLobby defaultServerLobby;
    private boolean startLobbyEnabled;

    public boolean isStartLobbyEnabled() {
        return startLobbyEnabled;
    }

    public ServerLobby getLobbyByPlayer(Player p) {
        for (ServerLobby serverLobby : this.lobbies) {
            if(serverLobby.contains(p)) return serverLobby;
        }

        return null;
    }

    public ServerLobby getLobbyByID(String id) {
        for (ServerLobby serverLobby : this.lobbies) {
            if(serverLobby.getID().equals(id)) return serverLobby;
        }

        return null;
    }

    public void register(ServerLobby l) {
        this.lobbies.add(l);
        l.onRegister();
    }

    public void unregister(ServerLobby l) {
        this.lobbies.remove(l);
        try {
            l.onUnregister();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ServerLobby getDefaultServerLobby() {
        return defaultServerLobby;
    }

    private void doWork() {
        for (ServerLobby serverLobby : this.lobbies) {
            try {
                serverLobby.tickUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void onLoad() {
        final LobbyHubController task = this;
        this.task = new BukkitRunnable() {
            public void run() {
                task.doWork();
            }
        }.runTaskTimer(MinigamesDTools.getInstance(), 0, 20);

        this.lobbyListener = new LobbyListener(this);
        Bukkit.getServer().getPluginManager().registerEvents(this.lobbyListener, MinigamesDTools.getInstance());

        //load all serverLobby
        for (String lobbyID : MinigamesDTools.getInstance().getConfigManager().getEnabledServerLobbyIds()) {
            try {
                Debug.print(Debug.LEVEL.NOTICE, "Load ServerLobby[ID:" + lobbyID + "]");
                this.register(MinigamesDTools.getInstance().getLobbyCreatorHub().createLobby(lobbyID, new DataProvider()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // set default serverLobby
        YamlConfiguration conf = null;
        try {
            conf = MinigamesDTools.getInstance().getConfigManager().getConfig(ConfigManager.ConfigPath.MAIN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // а есть ли смысл в валидации на нуллпоинетер для conf? всё равно, этот случай - критичен и должен быть отловлен вышестоящим загрузчиком.

        this.startLobbyEnabled = Boolean.parseBoolean(conf.get("start_lobby.move_on_server_join").toString());
        ServerLobby serverLobby = this.getLobbyByID(conf.get("start_lobby.id").toString());
        if(serverLobby != null) {
            this.defaultServerLobby = serverLobby;
        } else {
            Debug.print(Debug.LEVEL.NOTICE, "Cant find default server lobby!");
            return;
        }

        // fix on 'reload' command
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if(this.getLobbyByPlayer(player) == null) {
                this.getDefaultServerLobby().registerPlayer(player);
            }
        }
    }

    public void onUnload() {
        this.task.cancel();

        HandlerList.unregisterAll(this.lobbyListener);

        (new ArrayList<>(this.lobbies)).forEach(this::unregister);
    }
}
