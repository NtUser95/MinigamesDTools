package com.gmail.borlandlp.minigamesdtools.arena.team;

import java.util.*;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase;
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.respawn.RespawnLobby;
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.spectator.SpectatorLobby;
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.starter.StarterLobby;
import com.gmail.borlandlp.minigamesdtools.util.ArenaUtils;
import org.bukkit.*;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ExampleTeam implements TeamProvider {
   private int maxPlayers;
   private HashMap<String, Player> players = new HashMap<>();
   private String name;
   private List<Location> spawnPoints;
   private HashMap<String, ItemStack> armor;
   private ItemStack[] inventory;
   private ArenaBase arenaBase;
   public HashMap<String, Location> fromTeleport = new HashMap<>();
   protected boolean manageInventory;
   protected boolean manageArmor;
   protected boolean respawnLobbyEnabled;

   protected SpectatorLobby spectatorLobby;
   protected RespawnLobby respawnLobby;
   protected StarterLobby starterLobby;

   public ExampleTeam() {}

    @Override
    public boolean respawnLobbyEnabled() {
        return this.respawnLobbyEnabled;
    }

    @Override
    public void setRespawnLobbyEnabled(Boolean b) {
        this.respawnLobbyEnabled = b;
    }

    public void setArena(ArenaBase arenaBase) {
        this.arenaBase = arenaBase;
    }

    public void prepareToFightAll() {
        for(Player player : this.getPlayers()) {
            this.prepareToFight(player);
        }
    }

    public void prepareToFight(Player player) {
        if(player == null) {
            return;
        }

        if(this.isManageInventory()) {
            player.getInventory().clear();
            this.giveItems(player, this.getInventory());
        }

        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        player.setFoodLevel(20);
        player.setFireTicks(0);
        player.setFoodLevel(20);

        if(player.isInsideVehicle()) {
            player.leaveVehicle();
        }
    }

    public void afterFight() {
        for(Player player : this.getPlayers()) {
            if(player == null) {
                continue;
            }

            if(player.isInsideVehicle()) {
                player.leaveVehicle();
            }

            if(isManageArmor()) {
                player.getInventory().setArmorContents((ItemStack[])null);
            }
            if(isManageInventory()) {
                player.getInventory().clear();
            }
        }
    }

    public void preparePlayersAfterFight() {
       if(!this.isManageArmor()) {
           return;
       }

        for(Player player : this.getPlayers()) {
            player.getInventory().clear();
            player.getInventory().setArmorContents((ItemStack[])null);
        }
    }

    public void preparePlayersToGame() {
        for(Player player : this.getPlayers()) {
            if(player != null) {
                this.fromTeleport.put(player.getName(), player.getLocation());
            }
        }
    }

    public void preparePlayersAfterGame() {
        for(String nickname : this.players.keySet()) {
            Player player = this.getPlayer(nickname);
            if(player != null && player.isOnline()) {
                this.tpUserAtHome(player);
            }
            this.fromTeleport.remove(nickname);
        }
    }

    public void tpUserAtHome(Player player) {
        String nickname = player.getName();
        Location location = this.fromTeleport.get(nickname);
        if(location != null && !ArenaUtils.isNpc(player)) {
            player.teleport(location);
        } else {
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "spawn " + nickname);
        }
    }

    public void doRespawnAndEquipPlayers() {
       for(Player player : this.getPlayers()) {
           if(player == null) {
               continue;
           }

           this.prepareToFight(player);
           this.spawn(player);
       }
    }

    public void doRespawnAfterDeath(Player player) {
        if(player == null || ArenaUtils.isNpc(player)) {
            return;
        }

        this.prepareToFight(player);
        this.spawn(player);
    }

    public Player getPlayer(String nickname) {
       return this.players.get(nickname);
    }

    public void removeAllPlayers() {
       this.players = new HashMap<>();
    }

    @Override
    public void spawn(Player player) {
        if(player != null && !player.isDead()) {
            int spawnLoc = 0;
            if(this.getSpawnPoints().size() > 1) {
                spawnLoc = (new Random()).nextInt(this.getSpawnPoints().size()-1);
            }
            player.teleport(this.spawnPoints.get(spawnLoc));
        }
    }

    @Override
    public void setManageInventory(boolean b) {
       this.manageInventory = b;
    }

    @Override
    public boolean isManageInventory() {
        return this.manageInventory;
    }

    @Override
    public void setManageArmor(boolean b) {
       this.manageArmor = b;
    }

    @Override
    public boolean isManageArmor() {
        return this.manageArmor;
    }

    private void giveItems(Player player, ItemStack[] itemstacks) {
        if(player == null || ArenaUtils.isNpc(player)) {
            return;
        }

        ItemStack helmet = this.getArmor().get("helmet");
        ItemStack chestPlate = this.getArmor().get("chestplate");
        ItemStack leggings = this.getArmor().get("leggings");
        ItemStack boots = this.getArmor().get("boots");

        ItemMeta itemMeta = helmet.getItemMeta();
        itemMeta.setDisplayName("[arena]");

        helmet.setItemMeta(itemMeta);
        chestPlate.setItemMeta(itemMeta);
        leggings.setItemMeta(itemMeta);
        boots.setItemMeta(itemMeta);

        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestPlate);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);

        if(itemstacks.length > 0) {
            ItemMeta newItemMeta = itemMeta.clone();
            if(newItemMeta.hasEnchants()) {
                for(org.bukkit.enchantments.Enchantment enchant : newItemMeta.getEnchants().keySet()) {
                    newItemMeta.removeEnchant(enchant);
                }
            }
            for(int i = 0; i < itemstacks.length; i++) {
                if(itemstacks[i] != null){
                    ItemStack itemStack = itemstacks[i];
                    itemStack.setItemMeta(newItemMeta);
                    player.getInventory().addItem(itemStack);
                }
            }
        }
    }

   public List<Location> getSpawnPoints() {
        return this.spawnPoints;
    }

   public String getName() {
      return this.name;
   }

   public int getMaxPlayers() {
      return this.maxPlayers;
   }

   public void setMaxPlayers(int amount) {
      this.maxPlayers = amount;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setSpawnPoints(List<Location> loc) {
      this.spawnPoints = loc;
   }

   public void setArmor(HashMap<String, ItemStack> armor) {
      this.armor = armor;
   }

   public HashMap<String, ItemStack> getArmor() {
      return this.armor;
   }

   public void setInventory(ItemStack[] inventory) {
      this.inventory = inventory;
   }

   public ItemStack[] getInventory() {
      return this.inventory;
   }

    public SpectatorLobby getSpectatorLobby() {
        return this.spectatorLobby;
    }

    @Override
   public void addPlayer(Player player) {
      this.players.put(player.getName(), player);
   }

   @Override
   public void removePlayer(Player player) {
       this.players.remove(player.getName());
    }

   @Override
   public Set<Player> getPlayers() {
       return new HashSet<>(this.players.values());
   }

   public boolean contains(Player player) {
       return this.players.containsKey(player.getName());
   }

   public boolean containsByName(String name) {
       return this.players.containsKey(name);
   }

    public void setStarterLobby(StarterLobby starterLobby) {
        this.starterLobby = starterLobby;
    }

    public StarterLobby getStarterLobby() {
        return starterLobby;
    }

    @Override
    public RespawnLobby getRespawnLobby() {
        return respawnLobby;
    }

    @Override
    public void moveToRespawn(Player p) {
        this.getRespawnLobby().addPlayer(p);
    }

    @Override
    public void setSpectate(Player p, boolean trueOrFalse) {
        if(trueOrFalse) {
            this.getSpectatorLobby().addPlayer(p);
        } else {
            this.getSpectatorLobby().removePlayer(p);
            this.spawn(p);
        }
    }

    @Override
    public boolean containsFreeSlots(int forAmountPlayers) {System.out.println((this.getPlayers().size() + "+" + forAmountPlayers) + "<=" + this.getMaxPlayers());
        return (this.getPlayers().size() + forAmountPlayers) <= this.getMaxPlayers();
    }

    @Override
    public void setSpectatorLobby(SpectatorLobby l) {
        this.spectatorLobby = l;
    }

    @Override
    public boolean isSpectating(Player p) {
        return false;
    }

    public void setRespawnLobby(RespawnLobby respawnLobby) {
        this.respawnLobby = respawnLobby;
    }

    public ArenaBase getArena() {
       return this.arenaBase;
   }

    @Override
    public void onInit() {
       this.getArena().getPhaseComponentController().register(this.getRespawnLobby());
       this.getArena().getPhaseComponentController().register(this.getSpectatorLobby());
       this.getArena().getPhaseComponentController().register(this.getStarterLobby());
    }

    @Override
    public void beforeGameStarting() {

    }

    @Override
    public void gameEnded() {

    }

    @Override
    public void update() {
       if(this.respawnLobbyEnabled()) {
           this.getRespawnLobby().update();

           Set<Player> respPlayers = this.getRespawnLobby().getReadyPlayersToRespawn();
           if(respPlayers.size() > 0) {
               for (Player player : respPlayers) {
                   this.getRespawnLobby().playerRespawned(player);
                   this.spawn(player);
               }
           }
       }
    }

    @Override
    public void beforeRoundStarting() {
        Set<Player> respPlayers = new HashSet<>();

        if(this.respawnLobbyEnabled()) {
            respPlayers = this.getRespawnLobby().getWaitingPlayers().keySet();
            if(respPlayers.size() > 0) {
                for (Player player : respPlayers) {
                    this.getRespawnLobby().playerRespawned(player);
                    this.spawn(player);
                }
            }
        }

        for (Player player : this.getPlayers()) {
            if(!respPlayers.contains(player)) {
                this.spawn(player);
            }
        }
    }

    @Override
    public void onRoundEnd() {
        if(this.getSpectatorLobby().getPlayers().size() > 0) {
            for (Player player : this.getSpectatorLobby().getPlayers()) {
                this.getSpectatorLobby().removePlayer(player);
            }
        }
    }
}
