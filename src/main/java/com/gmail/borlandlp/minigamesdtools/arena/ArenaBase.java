package com.gmail.borlandlp.minigamesdtools.arena;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.arena.chunkloader.ChunkLoaderController;
import com.gmail.borlandlp.minigamesdtools.arena.customhandlers.HandlersController;
import com.gmail.borlandlp.minigamesdtools.arena.exceptions.ArenaAlreadyInitializedException;
import com.gmail.borlandlp.minigamesdtools.arena.exceptions.ArenaAlreadyStartedException;
import com.gmail.borlandlp.minigamesdtools.arena.gui.hotbar.HotbarController;
import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.GUIController;
import com.gmail.borlandlp.minigamesdtools.conditions.ConditionsChain;
import com.gmail.borlandlp.minigamesdtools.arena.phasecomponent.PhaseComponentController;
import com.gmail.borlandlp.minigamesdtools.arena.scenario.ScenarioChainController;
import com.gmail.borlandlp.minigamesdtools.arena.team.TeamController;
import com.gmail.borlandlp.minigamesdtools.events.ArenaGameEndedEvent;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.time.Instant;

public class ArenaBase {
    protected String name;
    protected boolean enabled;
    protected ArenaBase.STATE state = STATE.EMPTY;
    protected int roundTime;
    protected int maxRounds;
    protected int currentRound = 1;
    protected double awardEcons = 0D;
    protected int arenaCost;
    protected boolean canItemDrop;
    protected boolean canItemPickup;
    protected boolean hungerDisable;
    protected String gameId;
    protected int minPlayersToStart;
    private boolean regainHealth = true;

    protected boolean countdown_disableMoving;
    protected long unix_startTime;
    protected BukkitTask schedulerTask;
    protected BukkitTask countdownTask;
    protected long countdown_startTime;
    protected int countdown_time;
    protected BukkitTask preCountdownTask;
    protected long preCountdown_startTime;
    protected int preCountdown_time;

    protected long delayedCountdown_startTime;
    protected long delayedCountdown_waitTime = 5;
    protected BukkitTask delayedCountdown_task;

    protected EventAnnouncer eventAnnouncer = new EventAnnouncer();
    protected PhaseComponentController phaseComponentController = new PhaseComponentController();
    protected HandlersController handlersController = new HandlersController();

    protected GUIController guiController;
    protected ScenarioChainController scenarioChainController;
    protected TeamController teamController;
    protected ChunkLoaderController chunkLoaderController;
    protected HotbarController hotbarController;
    protected GameRules gameRules;
    protected ConditionsChain joinConditionsChain;

    private boolean initialized = false;

    public ArenaBase(String name) {
        this.name = name;
    }

    public void forceDisable() {
        this.gameEnded(true);
        this.enabled = false;
    }

    public void initializeComponents() throws ArenaAlreadyInitializedException {
        if(this.initialized) {
            throw new ArenaAlreadyInitializedException();
        }

        this.getPhaseComponentController().announceNewPhase(PhaseComponentController.ArenaPhase.INIT);
        this.initialized = true;
    }

    public void startArena() throws ArenaAlreadyStartedException {
        if(this.getState() != STATE.EMPTY && this.getState() != STATE.DELAYED_START) {
            throw new ArenaAlreadyStartedException();
        }

        ArenaBase arenaObj = this;

        arenaObj.beforeGameStarting();
        arenaObj.beforeRoundStarting();

        this.schedulerTask = new BukkitRunnable() {
            public void run() {
                // countdown pause
                if(arenaObj.getState() == STATE.COUNTDOWN || arenaObj.getState() == STATE.PAUSED) {
                    return;
                }

                arenaObj.update();

                if(arenaObj.getScenarioChainController().hasSignalGameEnding()) {
                    arenaObj.gameEnded(false);
                } else if(arenaObj.getScenarioChainController().hasSignalRoundEnding()) {
                    arenaObj.roundEnded();
                    arenaObj.setCurrentRound(arenaObj.getCurrentRound() + 1);
                    arenaObj.beforeRoundStarting();
                }
            }
        }.runTaskTimer(MinigamesDTools.getInstance(), 10, 10);
    }

    protected void delayedStartArena() {
        ArenaBase arenaBaseObject = this;
        this.state = STATE.DELAYED_START;
        arenaBaseObject.delayedCountdown_startTime = Instant.now().getEpochSecond();
        Debug.print(Debug.LEVEL.NOTICE, "Delayed start arena " + this.getName());

        this.delayedCountdown_task = new BukkitRunnable() {
            public void run() {
                int inc = (int)(Instant.now().getEpochSecond() - (int) arenaBaseObject.delayedCountdown_startTime);
                if(inc > arenaBaseObject.delayedCountdown_waitTime) {
                    try {
                        arenaBaseObject.startArena();
                    } catch (ArenaAlreadyStartedException e) {
                        e.printStackTrace();
                    }
                    arenaBaseObject.delayedCountdown_task.cancel();
                }
            }
        }.runTaskTimer(MinigamesDTools.getInstance(), 0, 20);
    }

    public void cancelDelayedStartArena() {
        if(this.delayedCountdown_task == null || this.delayedCountdown_task.isCancelled()) {
            return;
        }
        Debug.print(Debug.LEVEL.NOTICE, "Cancel delayed start arena " + this.getName());

        this.delayedCountdown_task.cancel();
    }

    protected void beforeGameStarting() {
        this.getPhaseComponentController().announceNewPhase(PhaseComponentController.ArenaPhase.GAME_STARTING);
    }

    protected void beforeRoundStarting() {
        ArenaBase arenaBaseObject = this;

        this.getPhaseComponentController().announceNewPhase(PhaseComponentController.ArenaPhase.ROUND_STARTING);

        this.state = STATE.COUNTDOWN;
        arenaBaseObject.countdown_startTime = Instant.now().getEpochSecond();
        this.countdownTask = new BukkitRunnable() {
            public void run() {
                int inc = (int)(Instant.now().getEpochSecond() - (int) arenaBaseObject.countdown_startTime);
                if(inc > arenaBaseObject.countdown_time) {
                    arenaBaseObject.state = STATE.IN_PROGRESS;
                    arenaBaseObject.setStartTime(Instant.now().getEpochSecond());
                    arenaBaseObject.countdownTask.cancel();
                }
            }
        }.runTaskTimer(MinigamesDTools.getInstance(), 0, 20);
    }

    protected void update() {
        this.getPhaseComponentController().announceNewPhase(PhaseComponentController.ArenaPhase.UPDATE);
    }

    protected void roundEnded() {
        this.getPhaseComponentController().announceNewPhase(PhaseComponentController.ArenaPhase.ROUND_ENDING);
    }

    protected void gameEnded(boolean force) {
        this.setState(STATE.ENDED);

        if(this.schedulerTask != null) this.schedulerTask.cancel();
        if(this.countdownTask != null) this.countdownTask.cancel();

        if(this.getState() == STATE.PAUSED) {
            return;
        }

        try {
            Bukkit.getServer().getPluginManager().callEvent(new ArenaGameEndedEvent(this, Result.NORMAL));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        this.getPhaseComponentController().announceNewPhase(PhaseComponentController.ArenaPhase.GAME_ENDING);
    }

    public HotbarController getHotbarController() {
        return hotbarController;
    }

    public void setHotbarController(HotbarController hotbarController) {
        this.hotbarController = hotbarController;
    }

    public ChunkLoaderController getChunkLoaderController() {
        return chunkLoaderController;
    }

    public void setChunkLoaderController(ChunkLoaderController chunkLoaderController) {
        this.chunkLoaderController = chunkLoaderController;
    }

    public EventAnnouncer getEventAnnouncer() {
        return eventAnnouncer;
    }

    public String getGameId() {
        return gameId;
    }

    public void setScenarioChainController(ScenarioChainController scenarioChainController) {
        this.scenarioChainController = scenarioChainController;
    }

    public ScenarioChainController getScenarioChainController() {
        return scenarioChainController;
    }

    public GUIController getGuiController() {
        return guiController;
    }

    public void setGuiController(GUIController guiController) {
        this.guiController = guiController;
    }

    public double getAwardEcons() {
        return this.awardEcons;
    }

    public void setStartTime(long time) {
        this.unix_startTime = time;
    }

    public long getStartTime() {
        return this.unix_startTime;
    }

    public int getTimeLeft() {
        return (int)(this.roundTime - (Instant.now().getEpochSecond() - this.getStartTime()));
    }

    public int getMaxRounds() {
        return this.maxRounds;
    }

    public int getCurrentRound() {
        return this.currentRound;
    }

    public void setCurrentRound(int amount) {
        this.currentRound = amount;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean mode) {
        this.enabled = mode;
    }

    public ArenaBase.STATE getState() {
        return this.state;
    }

    public int getCost() {
        return this.arenaCost;
    }

    public void setState(ArenaBase.STATE state) { this.state = state; }

    public TeamController getTeamController() { return this.teamController; }

    public String getName() { return this.name; }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCanItemDrop() {
        return canItemDrop;
    }

    public boolean isCanItemPickup() {
        return canItemPickup;
    }

    public boolean isHungerDisable() {
        return hungerDisable;
    }

    public void setArenaCost(int arenaCost) {
        this.arenaCost = arenaCost;
    }

    public void setRoundTime(int roundTime) {
        this.roundTime = roundTime;
    }

    public void setPreCountdownTask(BukkitTask preCountdownTask) {
        this.preCountdownTask = preCountdownTask;
    }

    public void setPreCountdown_time(int preCountdown_time) {
        this.preCountdown_time = preCountdown_time;
    }

    public void setMaxRounds(int maxRounds) {
        this.maxRounds = maxRounds;
    }

    public void setHungerDisable(boolean hungerDisable) {
        this.hungerDisable = hungerDisable;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setCountdown_disableMoving(boolean countdown_disableMoving) {
        this.countdown_disableMoving = countdown_disableMoving;
    }

    public void setCountdown_time(int countdown_time) {
        this.countdown_time = countdown_time;
    }

    public void setCanItemDrop(boolean canItemDrop) {
        this.canItemDrop = canItemDrop;
    }

    public void setCanItemPickup(boolean canItemPickup) {
        this.canItemPickup = canItemPickup;
    }

    public void setAwardEcons(double a) {
        awardEcons = a;
    }

    public void setTeamController(TeamController t) {
        teamController = t;
    }

    public boolean isCountdown_disableMoving() {
        return countdown_disableMoving;
    }

    public int getPreCountdown_time() {
        return preCountdown_time;
    }

    public long getCountdown_startTime() {
        return countdown_startTime;
    }

    public long getPreCountdown_startTime() {
        return preCountdown_startTime;
    }

    public long getUnix_startTime() {
        return unix_startTime;
    }

    public int getRoundTime() {
        return roundTime;
    }

    public int getArenaCost() {
        return arenaCost;
    }

    public PhaseComponentController getPhaseComponentController() {
        return phaseComponentController;
    }

    public HandlersController getHandlersController() {
        return handlersController;
    }

    public int getMinPlayersToStart() {
        return minPlayersToStart;
    }

    public void setMinPlayersToStart(int minPlayersToStart) {
        this.minPlayersToStart = minPlayersToStart;
    }

    public GameRules getArenaRules() {
        return this.gameRules;
    }

    public ConditionsChain getJoinConditionsChain() {
        return joinConditionsChain;
    }

    public void setJoinConditionsChain(ConditionsChain joinConditionsChain) {
        this.joinConditionsChain = joinConditionsChain;
    }

    public boolean isRegainHealth() {
        return regainHealth;
    }

    public void setRegainHealth(boolean regainHealth) {
        this.regainHealth = regainHealth;
    }

    public enum STATE {
        DELAYED_START,
        IN_PROGRESS,
        COUNTDOWN,
        PAUSED,
        EMPTY,
        ENDED
    }
}
