package com.gmail.borlandlp.minigamesdtools.lobby;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase;
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.Hotbar;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public abstract class ServerLobby {
    private Map<Player, ArenaBase> players = new HashMap<>(); // Player -> Arena[may be null -> if null - player is not arena player]
    private String id;
    private Location spawnPoint;
    private String hotbarID; // may be null

    public void broadcastMessage(String message, String eqw) {
        for (Player player : this.players.keySet()) {
            if(!this.playerPlaysInAnyArena(player)) {
                // send msg
            }
        }
    }

    public boolean transferPlayer(Player p, ServerLobby l) throws Exception {
        if(p == null) {
            throw new Exception("Player is null. Cant transfer from ServerLobby[ID:" + this.getID() + "]");
        } else if(l == null) {
            throw new Exception("Target lobby is null. Cant tranfser Player[name:" + p.getName() + "] from ServerLobby[ID:" + this.getID() + "]");
        }

        Debug.print(Debug.LEVEL.NOTICE, "transfer Player[name:" + p.getName() + "] from ServerLobby[ID:" + this.getID() + "] to ServerLobby[ID:" + l.getID() + "]");

        if(this.unregisterPlayer(p)) {
            if(l.registerPlayer(p)) {
                return true;
            } else {
                this.registerPlayer(p);
                return false;
            }
        } else {
            return false;
        }
    }

    public void spawn(Player p) {
        p.teleport(this.getSpawnPoint());
    }

    public void setPlayerArena(Player p, ArenaBase a) {
        this.players.put(p, a);
    }

    private boolean applyHotbar(Player p) {
        Hotbar hotbar = null;
        try {
            hotbar = MinigamesDTools.getInstance().getHotbarCreatorHub().createHotbar(this.hotbarID, new DataProvider());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if(hotbar == null) {
            Debug.print(Debug.LEVEL.WARNING, "Build of Hotbar[ID:" + this.hotbarID + "] for Player[name:" + p.getName() + "] is null! This is a build error.");
            return false;
        } else {
            MinigamesDTools.getInstance().getHotbarAPI().bindHotbar(hotbar, p);
        }

        return true;
    }

    public void handlePlayerLeaveArena(Player p) throws Exception {
        if(!this.players.containsKey(p)) {
            throw new Exception("Lobby[ID:" + this.getID() + "] doesn't contain a player:" + p.getName());
        }

        this.spawn(p);
        this.applyHotbar(p);
    }

    public boolean registerPlayer(Player p) {
        Debug.print(Debug.LEVEL.NOTICE, "register Player[name:" + p.getName() + "] in ServerLobby[ID:" + this.getID() + "]");

        this.players.put(p, null);
        this.spawn(p);

        if(this.hotbarID != null) {
            if(!this.applyHotbar(p)) {
                return false;
            }
        }

        return true;
    }

    public boolean unregisterPlayer(Player p) {
        Debug.print(Debug.LEVEL.NOTICE, "unregister Player[name:" + p.getName() + "] in ServerLobby[ID:" + this.getID() + "]");
        if(this.hotbarID != null) {
            try {
                MinigamesDTools.getInstance().getHotbarAPI().unbindHotbar(p);
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }

        this.players.remove(p);

        return true;
    }

    public boolean contains(Player p) {
        return this.players.containsKey(p);
    }

    public boolean playerPlaysInAnyArena(Player p) {
        return this.players.get(p) != null;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public Location getSpawnPoint() {
        return spawnPoint;
    }

    public void setSpawnPoint(Location spawnPoint) {
        this.spawnPoint = spawnPoint;
    }

    public String getHotbarID() {
        return hotbarID;
    }

    public void setHotbarID(String hotbarID) {
        this.hotbarID = hotbarID;
    }

    public abstract void onRegister();
    public abstract void onUnregister();
    public abstract void tickUpdate();
}
