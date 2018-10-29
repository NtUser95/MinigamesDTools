package com.gmail.borlandlp.minigamesdtools.arena.team;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.ArenaLobby;
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.respawn.RespawnLobby;
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.spectator.SpectatorLobby;
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.starter.StarterLobby;
import com.gmail.borlandlp.minigamesdtools.config.ConfigManager;
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider;
import com.gmail.borlandlp.minigamesdtools.util.ArenaUtils;
import com.gmail.borlandlp.minigamesdtools.util.InventoryUtil;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@CreatorInfo(creatorId = "default_team")
public class ExampleTeamCreator implements Creator {
    public TeamProvider create(String teamID, AbstractDataProvider dataProvider) throws Exception {
        ConfigurationSection fileConfiguration = MinigamesDTools.getInstance().getConfigManager().getConfigSection(ConfigManager.ConfigPath.TEAMS, teamID);
        if(fileConfiguration == null) {
            throw new Exception("cant find config of team[ID:" + teamID + "]");
        }

        ExampleTeam team = new ExampleTeam();

        // players spawnPoint
        List<Location> spawnPoints = new ArrayList<>();
        for (String row : fileConfiguration.getStringList("spawn_points_XYZWorldYawPitch")) {
            String[] splittedSpawnPoint = row.split(":");
            if(splittedSpawnPoint.length == 6) {
                spawnPoints.add(ArenaUtils.str2Loc(splittedSpawnPoint));
            } else {
                throw new Exception("Invalid spawnPoint format for '" + row + "'. Correct example -> 74:30:113:world:0:0");
            }
        }
        if(spawnPoints.size() > 0) {
            team.setSpawnPoints(spawnPoints);
        } else {
            throw new Exception(teamID + " must be have a spawn point!");
        }

        team.setMaxPlayers(Integer.parseInt(fileConfiguration.get("max_players").toString()));

        //spectator spawn point
        /*List<Location> specPoints = new ArrayList<>();
        for (String row : fileConfiguration.getStringList("spectator_point_XYZWorldYawPitch")) {
            String[] splittedSpawnPoint = row.split(":");
            if(splittedSpawnPoint.length == 6) {
                double x = Integer.parseInt(splittedSpawnPoint[0]);
                double y = Integer.parseInt(splittedSpawnPoint[1]);
                double z = Integer.parseInt(splittedSpawnPoint[2]);
                World world = Bukkit.getWorld(splittedSpawnPoint[3]);
                float yaw = Integer.parseInt(splittedSpawnPoint[4]);
                float pitch = Integer.parseInt(splittedSpawnPoint[5]);
                specPoints.add(new Location(world, x, y, z, yaw, pitch));
            } else {
                throw new Exception("Invalid spectatorPoint format for '" + row + "'. Correct example -> 74:30:113:world:0:0");
            }
        }
        if(spawnPoints.size() > 0) {
            team.setSpectatorPoint(specPoints);
        } else {
            throw new Exception(teamID + " must be have a spectator point!");
        }*/

        //armor filler
        HashMap<String, ItemStack> armor = new HashMap<>();
        boolean manageArmor = Boolean.parseBoolean(fileConfiguration.get("armor_filler.enabled").toString());
        if(manageArmor) {
            Set<String> armorKeys = fileConfiguration.getConfigurationSection("armor_filler.armor").getKeys(false);
            for (String armKey : armorKeys) {
                armor.put(armKey, InventoryUtil.getArmourItemStack(fileConfiguration.getString("armor_filler.armor." + armKey)));
            }
        }
        team.setManageArmor(manageArmor);
        team.setArmor(armor);


        //inventory
        ItemStack[] inventory = new ItemStack[64];
        boolean manageInventory = Boolean.parseBoolean(fileConfiguration.get("inventory_filler.enabled").toString());
        if(manageInventory) {
            int i = 0;
            for (String row : fileConfiguration.getStringList("inventory_filler.items")) {
                inventory[i++] = (ItemStack) InventoryUtil.getItemStackOufOfString(row)[0];
            }
        }
        team.setManageInventory(manageInventory);
        team.setInventory(inventory);

        // respawn lobby
        if(fileConfiguration.contains("respawn_lobby.enabled") && Boolean.parseBoolean(fileConfiguration.get("respawn_lobby.enabled").toString())) {
            ArenaLobby respawnLobby = MinigamesDTools.getInstance().getArenaLobbyCreatorHub().createLobby(fileConfiguration.get("respawn_lobby.lobby_handler").toString(), new DataProvider());
            respawnLobby.setTeamProvider(team);
            team.setRespawnLobbyEnabled(true);
            team.setRespawnLobby((RespawnLobby) respawnLobby);
            Debug.print(Debug.LEVEL.NOTICE, "Build respawn lobby for Team[ID:" + teamID + "]#" + respawnLobby);
        } else {
            team.setRespawnLobbyEnabled(false);
            Debug.print(Debug.LEVEL.NOTICE, "Respawn lobby is disabled for Team[ID:" + teamID + "]");
        }

        // spectator lobby
        if(!fileConfiguration.contains("spectate_lobby.id")) {
            throw new Exception("Arena must be config for spectate_lobby");
        }
        ArenaLobby spectatorLobby = MinigamesDTools.getInstance().getArenaLobbyCreatorHub().createLobby(fileConfiguration.get("spectate_lobby.id").toString(), new DataProvider());
        spectatorLobby.setTeamProvider(team);
        team.setSpectatorLobby((SpectatorLobby) spectatorLobby);
        Debug.print(Debug.LEVEL.NOTICE, "Build spectator lobby for Team[ID:" + teamID + "]#" + spectatorLobby);

        // starter lobby
        if(!fileConfiguration.contains("starter_lobby")) {
            throw new Exception("Arena must be config for starter_lobby");
        }
        ArenaLobby starterLobby = MinigamesDTools.getInstance().getArenaLobbyCreatorHub().createLobby(fileConfiguration.get("starter_lobby.lobby_handler").toString(), new DataProvider());
        starterLobby.setTeamProvider(team);
        ((StarterLobby) starterLobby).setEnabled(Boolean.parseBoolean(fileConfiguration.get("starter_lobby.enabled").toString()));
        team.setStarterLobby((StarterLobby) starterLobby);
        Debug.print(Debug.LEVEL.NOTICE, "Build starter lobby for Team[ID:" + teamID + "]#" + starterLobby);

        team.setName(fileConfiguration.get("name").toString());


        /*
        ChatColor randColor = null;
        int randCount = 0;
        do {
            randColor = ;
            randCount++;
        } while(usedColors.contains(randColor) && randCount <= 20);*/
        //team.setColor(ChatColor.values()[ (new Random()).nextInt(ChatColor.values().length-1) ]);

        return team;
    }
}
