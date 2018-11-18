package com.gmail.borlandlp.minigamesdtools.arena.team;

import java.util.*;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerRespawnLocalEvent;
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.ArenaLobby;
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
   protected HashMap<String, Location> fromTeleport = new HashMap<>();
   protected boolean manageInventory;
   protected boolean manageArmor;
   protected boolean friendlyFireAllowed;
   protected ChatColor color;

   protected SpectatorLobby spectatorLobby;
   protected RespawnLobby respawnLobby;
   protected StarterLobby starterLobby;

   protected Map<Player, ArenaLobby> lobbyPlayers = new Hashtable<>();

   public ExampleTeam() {}

    public void setArena(ArenaBase arenaBase) {
        this.arenaBase = arenaBase;
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

        if(player.isInsideVehicle()) {
            player.leaveVehicle();
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

            ArenaPlayerRespawnLocalEvent arenaPlayerRespawnLocalEvent = new ArenaPlayerRespawnLocalEvent(this, player);
            this.getArena().getEventAnnouncer().announce(arenaPlayerRespawnLocalEvent);
        }
    }

    @Override
    public ChatColor getColor() {
        return this.color;
    }

    @Override
    public void setColor(ChatColor c) {
        this.color = c;
    }

    @Override
    public boolean friendlyFireAllowed() {
        return this.friendlyFireAllowed;
    }

    public void setFriendlyFireAllowed(boolean b) {
       this.friendlyFireAllowed = b;
    }

    public void setManageInventory(boolean b) {
       this.manageInventory = b;
    }

    @Override
    public boolean isManageInventory() {
        return this.manageInventory;
    }

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

    public void setSpectatorLobby(SpectatorLobby l) {
        this.spectatorLobby = l;
    }

    @Override
    public boolean movePlayerTo(ArenaLobby lobby, Player p) {
        if(this.lobbyPlayers.containsKey(p)) {
            this.lobbyPlayers.get(p).forceReleasePlayer(p);
        }

        lobby.addPlayer(p);
        this.lobbyPlayers.put(p, lobby);

        return true;
    }

    @Override
    public boolean movePlayerTo(TeamProvider team, Player p) {
       if(this.lobbyPlayers.containsKey(p)) {
           this.lobbyPlayers.get(p).forceReleasePlayer(p);
       }

       if(team.addPlayer(p)) {
           this.removePlayer(p);
           return true;
       } else {
           return false;
       }
    }

    @Override
    public boolean addPlayer(Player player) {
       this.players.put(player.getName(), player);
       return true;
   }

    @Override
    public boolean removePlayer(Player player) {
       this.players.remove(player.getName());
       return true;
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
    public void setSpectate(Player p, boolean trueOrFalse) {
        if(trueOrFalse) {
            this.getSpectatorLobby().addPlayer(p);
        } else {
            this.getSpectatorLobby().removePlayer(p);
            this.spawn(p);
        }
    }

    @Override
    public Set<Player> getSpectators() {
        return this.spectatorLobby.getPlayers();
    }

    @Override
    public boolean containsFreeSlots(int forAmountPlayers) {
        return (this.getPlayers().size() + forAmountPlayers) <= this.getMaxPlayers();
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
       if(((ArenaLobby) this.getRespawnLobby()).isEnabled()) {
           this.getRespawnLobby().update();

           Set<Player> respPlayers = this.getRespawnLobby().getReadyPlayersToRespawn();
           if(respPlayers.size() > 0) {
               for (Player player : respPlayers) {
                   this.getRespawnLobby().removePlayer(player);
                   this.lobbyPlayers.remove(player);// TODO: переделать в нечто осмысленное
                   this.spawn(player);
               }
           }
       }
    }

    @Override
    public void beforeRoundStarting() {
        Set<Player> respPlayers = new HashSet<>();

        if(((ArenaLobby) this.getRespawnLobby()).isEnabled()) {
            respPlayers = this.getRespawnLobby().getWaitingPlayers().keySet();
            if(respPlayers.size() > 0) {
                for (Player player : respPlayers) {
                    this.getRespawnLobby().removePlayer(player);
                    this.lobbyPlayers.remove(player);// TODO: переделать в нечто осмысленное
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
