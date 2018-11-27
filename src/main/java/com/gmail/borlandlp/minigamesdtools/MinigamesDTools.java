package com.gmail.borlandlp.minigamesdtools;

import com.gmail.borlandlp.minigamesdtools.activepoints.ActivePointController;
import com.gmail.borlandlp.minigamesdtools.activepoints.ActivePointsAPI;
import com.gmail.borlandlp.minigamesdtools.activepoints.behaviors.BehaviorCreatorHub;
import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.ReactionCreatorHub;
import com.gmail.borlandlp.minigamesdtools.arena.*;
import com.gmail.borlandlp.minigamesdtools.activepoints.ActivePointsCreatorHub;
import com.gmail.borlandlp.minigamesdtools.arena.chunkloader.ChunkLoaderCreator;
import com.gmail.borlandlp.minigamesdtools.arena.scenario.ScenarioChainCreatorHub;
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.ArenaLobbyCreatorHub;
import com.gmail.borlandlp.minigamesdtools.conditions.ConditionsCreatorHub;
import com.gmail.borlandlp.minigamesdtools.config.*;
import com.gmail.borlandlp.minigamesdtools.config.exception.InvalidPathException;
import com.gmail.borlandlp.minigamesdtools.events.sponge.*;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.*;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.api.HotbarAPI;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.api.HotbarApiInst;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.HotbarItemCreatorHub;
import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.GUICreatorHub;
import com.gmail.borlandlp.minigamesdtools.gui.inventory.api.InventoryAPI;
import com.gmail.borlandlp.minigamesdtools.gui.inventory.api.ViewManager;
import com.gmail.borlandlp.minigamesdtools.gui.inventory.slots.InventoryGuiSlotCreatorHub;
import com.gmail.borlandlp.minigamesdtools.gun.BulletCreatorHub;
import com.gmail.borlandlp.minigamesdtools.gun.BulletHandler;
import com.gmail.borlandlp.minigamesdtools.gun.BulletHandlerApi;
import com.gmail.borlandlp.minigamesdtools.lobby.LobbyServerAPI;
import com.gmail.borlandlp.minigamesdtools.nmsentities.EntityAPI;
import com.gmail.borlandlp.minigamesdtools.nmsentities.EntityController;
import com.gmail.borlandlp.minigamesdtools.nmsentities.entity.SilentShulker;
import com.gmail.borlandlp.minigamesdtools.nmsentities.entity.SkyDragon;
import com.gmail.borlandlp.minigamesdtools.nmsentities.entity.SkyZombie;
import com.gmail.borlandlp.minigamesdtools.arena.scenario.ScenarioCreatorHub;
import com.gmail.borlandlp.minigamesdtools.arena.team.TeamCreatorHub;
import com.gmail.borlandlp.minigamesdtools.gui.inventory.*;
import com.gmail.borlandlp.minigamesdtools.listener.Commands;
import com.gmail.borlandlp.minigamesdtools.listener.Events;
import com.gmail.borlandlp.minigamesdtools.lobby.LobbyHubController;
import com.gmail.borlandlp.minigamesdtools.lobby.LobbyCreatorHub;
import com.gmail.borlandlp.minigamesdtools.database.SQLite;

import com.gmail.borlandlp.minigamesdtools.test.TestComponents;
import net.minecraft.server.v1_12_R1.EntityEnderDragon;
import net.minecraft.server.v1_12_R1.EntityShulker;
import net.minecraft.server.v1_12_R1.EntityZombie;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class MinigamesDTools extends JavaPlugin {
    private static final String prefix = ChatColor.BLUE + "[" + ChatColor.RED + "Arena" + ChatColor.BLUE + "]" + ChatColor.DARK_GREEN;
    private ConfigLoader configLoader;

    private CustomMinigamesLoader customMinigamesLoader;
    private APIComponentsController apiComponentsController;
    private ArenaAPI arenaAPI;
    private LobbyServerAPI lobbyController;
    private ArenaCreatorHub arenaCreatorHub;
    private ConfigProvider configManager;
    private HotbarAPI hotbarAPI;
    private InventoryAPI inventoryGUIManger;
    private ActivePointsAPI activePointsAPI;
    private EntityAPI entityAPI;
    private BulletHandlerApi bulletHandlerApi;

    private GUICreatorHub guiCreatorHub;
    private HotbarCreatorHub hotbarCreatorHub;
    private HotbarItemCreatorHub hotbarItemCreatorHub;
    private ReactionCreatorHub reactionCreatorHub;
    private BehaviorCreatorHub behaviorCreatorHub;
    private ActivePointsCreatorHub activePointsCreatorHub;
    private ScenarioCreatorHub scenarioCreatorHub;
    private ScenarioChainCreatorHub scenarioChainCreatorHub;
    private TeamCreatorHub teamCreatorHub;
    private InventoryGUICreatorHub inventoryGUICreatorHub;
    private InventoryGuiSlotCreatorHub inventoryGuiSlotCreatorHub;
    private LobbyCreatorHub lobbyCreatorHub;
    private ChunkLoaderCreator chunkLoaderCreator;
    private ArenaLobbyCreatorHub arenaLobbyCreatorHub;
    private ConditionsCreatorHub conditionsCreatorHub;
    private BulletCreatorHub bulletCreatorHub;

    private static MinigamesDTools plugin;

    public void onEnable() {
       plugin = this;

       this.getServer().getPluginManager().registerEvents(new Events(), this);
       this.getServer().getPluginManager().registerEvents(new InventoryListener(), this);
       this.getCommand("arena").setExecutor(new Commands());

       try {
           this.loadMisc();
       } catch (InvalidPathException e) {
           e.printStackTrace();
       }
    }

    public void loadMisc() throws InvalidPathException {
        Debug.print(Debug.LEVEL.NOTICE, "#######################################################");
        Debug.print(Debug.LEVEL.NOTICE, "########### MiniGames daemon is starting... ###########");
        Debug.print(Debug.LEVEL.NOTICE, "#.....................................................#");

        this.customMinigamesLoader = new CustomMinigamesLoader();
        this.customMinigamesLoader.loadAddons();

        ConfigUtils.checkAndRestoreDefaultConfigs();
        this.configLoader = new ConfigLoader();
        configLoader.addPath(this.getDataFolder());
        configLoader.addPath(ConfigPath.ARENA_FOLDER.getPath());

        this.guiCreatorHub = new GUICreatorHub();
        this.hotbarCreatorHub = new HotbarCreatorHub();
        this.hotbarItemCreatorHub = new HotbarItemCreatorHub();
        this.reactionCreatorHub = new ReactionCreatorHub();
        this.behaviorCreatorHub = new BehaviorCreatorHub();
        this.activePointsCreatorHub = new ActivePointsCreatorHub();
        this.scenarioCreatorHub = new ScenarioCreatorHub();
        this.scenarioChainCreatorHub = new ScenarioChainCreatorHub();
        this.arenaCreatorHub = new ArenaCreatorHub();
        this.teamCreatorHub = new TeamCreatorHub();
        this.arenaLobbyCreatorHub = new ArenaLobbyCreatorHub();
        this.chunkLoaderCreator = new ChunkLoaderCreator();
        this.inventoryGuiSlotCreatorHub = new InventoryGuiSlotCreatorHub();
        this.conditionsCreatorHub = new ConditionsCreatorHub();
        this.bulletCreatorHub = new BulletCreatorHub();

        this.entityAPI = new EntityController();
        this.inventoryGUICreatorHub = new InventoryGUICreatorHub();
        this.inventoryGUIManger = new ViewManager();
        this.lobbyCreatorHub = new LobbyCreatorHub();
        this.activePointsAPI = new ActivePointController();
        this.arenaAPI = new ArenaManager();
        this.hotbarAPI = new HotbarApiInst();
        this.lobbyController = new LobbyHubController();
        this.bulletHandlerApi = new BulletHandler();

        this.apiComponentsController = new APIComponentsController();
        Bukkit.getPluginManager().callEvent(new PRE_INITIALIZATION_EVENT());

        // load configs
        this.configManager = configLoader.load();

        Debug.print(Debug.LEVEL.NOTICE, "###############################################");
        Debug.print(Debug.LEVEL.NOTICE, "########### register API components ###########");
        Debug.print(Debug.LEVEL.NOTICE, "#..............................................#");
        this.getApiComponentsController().register((APIComponent) this.getLobbyHubAPI());
        this.getApiComponentsController().register((APIComponent) this.getHotbarAPI());
        this.getApiComponentsController().register((APIComponent) this.getActivePointsAPI());
        this.getApiComponentsController().register((APIComponent) this.getEntityAPI());
        this.getApiComponentsController().register((APIComponent) this.getInventoryGUI_API());
        this.getApiComponentsController().register((APIComponent) this.getArenaAPI());
        this.getApiComponentsController().register(this.getBulletHandlerApi());

        (new TestComponents()).load();

        Bukkit.getPluginManager().callEvent(new INITIALIZATION_EVENT());

        Debug.print(Debug.LEVEL.NOTICE, "##################################################");
        Debug.print(Debug.LEVEL.NOTICE, "########### prepare daemon to start... ###########");
        Debug.print(Debug.LEVEL.NOTICE, "#................................................#");
        this.getApiComponentsController().announceEvent(APIComponentsController.ComponentEvent.PLUGIN_LOAD);
        Bukkit.getPluginManager().callEvent(new POST_INITIALIZATION_EVENT());

        Debug.print(Debug.LEVEL.NOTICE, "###################################################");
        Debug.print(Debug.LEVEL.NOTICE, "########### MiniGames daemon is loaded! ###########");
        Debug.print(Debug.LEVEL.NOTICE, "###################################################");
    }

    public void onDisable() {
       this.unloadMisc();

       this.getLogger().info("Arena disabled!");
    }

    public void unloadMisc() {
        Debug.print(Debug.LEVEL.NOTICE, "#############################################################");
        Debug.print(Debug.LEVEL.NOTICE, "########### MiniGames daemon prepare to unload... ###########");
        Debug.print(Debug.LEVEL.NOTICE, "#...........................................................#");
        Bukkit.getPluginManager().callEvent(new PRE_UNLOAD_EVENT());

        MinigamesDTools.getInstance().getEntityAPI().unregister("test", 69, EntityShulker.class, SilentShulker.class);
        MinigamesDTools.getInstance().getEntityAPI().unregister("my_zombie", 54, EntityZombie.class, SkyZombie.class);
        MinigamesDTools.getInstance().getEntityAPI().unregister("sky_dragon", 63, EntityEnderDragon.class, SkyDragon.class);

        this.getApiComponentsController().announceEvent(APIComponentsController.ComponentEvent.PLUGIN_UNLOAD);

        Debug.print(Debug.LEVEL.NOTICE, "###################################");
        Debug.print(Debug.LEVEL.NOTICE, "########### unload addons ###########");
        Debug.print(Debug.LEVEL.NOTICE, "#.................................#");
        Bukkit.getPluginManager().callEvent(new UNLOAD_EVENT());
        this.customMinigamesLoader.unloadAddons();

        Debug.print(Debug.LEVEL.NOTICE, "#######################################################");
        Debug.print(Debug.LEVEL.NOTICE, "########### MiniGames daemon is unloaded... ###########");
        Debug.print(Debug.LEVEL.NOTICE, "#.....................................................#");
    }

    public void reload() throws InvalidPathException {
       this.unloadMisc();
       this.loadMisc();
       this.getLogger().log(Level.INFO, MinigamesDTools.getPrefix() + " Конфиг арен перезагружен.");
    }

    public static MinigamesDTools getInstance() {
       return plugin;
    }

    public static String getPrefix()
    {
      return prefix;
    }

    public ArenaAPI getArenaAPI()
    {
      return this.arenaAPI;
    }

    public ConfigProvider getConfigProvider()
    {
      return this.configManager;
    }

    public GUICreatorHub getGuiCreatorHub() {
        return guiCreatorHub;
    }

    public HotbarCreatorHub getHotbarCreatorHub() {
        return hotbarCreatorHub;
    }

    public EntityAPI getEntityAPI() {
        return entityAPI;
    }

    public ReactionCreatorHub getReactionCreatorHub() {
        return reactionCreatorHub;
    }

    public BehaviorCreatorHub getBehaviorCreatorHub() {
        return behaviorCreatorHub;
    }

    public ActivePointsCreatorHub getActivePointsCreatorHub() {
        return activePointsCreatorHub;
    }

    public ScenarioCreatorHub getScenarioCreatorHub() {
        return scenarioCreatorHub;
    }

    public ArenaCreatorHub getArenaCreatorHub() {
        return arenaCreatorHub;
    }

    public TeamCreatorHub getTeamCreatorHub() {
        return teamCreatorHub;
    }

    public InventoryGUICreatorHub getInventoryGUICreatorHub() {
        return inventoryGUICreatorHub;
    }

    public InventoryAPI getInventoryGUI_API() {
        return inventoryGUIManger;
    }

    public LobbyServerAPI getLobbyHubAPI() {
        return lobbyController;
    }

    public HotbarAPI getHotbarAPI() {
        return hotbarAPI;
    }

    public LobbyCreatorHub getLobbyCreatorHub() {
        return lobbyCreatorHub;
    }

    public ActivePointsAPI getActivePointsAPI() {
        return activePointsAPI;
    }

    public APIComponentsController getApiComponentsController() {
        return apiComponentsController;
    }

    public ChunkLoaderCreator getChunkLoaderCreator() {
        return chunkLoaderCreator;
    }

    public HotbarItemCreatorHub getHotbarItemCreatorHub() {
        return hotbarItemCreatorHub;
    }

    public ScenarioChainCreatorHub getScenarioChainCreatorHub() {
        return scenarioChainCreatorHub;
    }

    public ArenaLobbyCreatorHub getArenaLobbyCreatorHub() {
        return arenaLobbyCreatorHub;
    }

    public InventoryGuiSlotCreatorHub getInventoryGuiSlotCreatorHub() {
        return inventoryGuiSlotCreatorHub;
    }

    public ConditionsCreatorHub getConditionsCreatorHub() {
        return conditionsCreatorHub;
    }

    public ConfigLoader getConfigLoader() {
        return configLoader;
    }

    public BulletHandlerApi getBulletHandlerApi() {
        return bulletHandlerApi;
    }

    public BulletCreatorHub getBulletCreatorHub() {
        return bulletCreatorHub;
    }
}
