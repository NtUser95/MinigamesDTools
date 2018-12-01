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
    protected int currentRound = 1;
    protected String gameId;

    protected long game_unix_startTime;
    protected long round_unix_startTime;
    protected BukkitTask schedulerTask;
    protected BukkitTask countdownTask;
    protected long countdown_startTime;

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
        this.game_unix_startTime = Instant.now().getEpochSecond();
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
                if(inc > arenaBaseObject.getGameRules().beforeRoundStartingWaitDuration) {
                    arenaBaseObject.state = STATE.IN_PROGRESS;
                    arenaBaseObject.round_unix_startTime = Instant.now().getEpochSecond();
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
        this.game_unix_startTime = 0;

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

    public ChunkLoaderController getChunkLoaderController() {
        return chunkLoaderController;
    }

    public EventAnnouncer getEventAnnouncer() {
        return eventAnnouncer;
    }

    public String getGameId() {
        return gameId;
    }

    public ScenarioChainController getScenarioChainController() {
        return scenarioChainController;
    }

    public GameRules getGameRules() {
        return gameRules;
    }

    public GUIController getGuiController() {
        return guiController;
    }

    public long getGameStartTime() {
        return this.game_unix_startTime;
    }

    public long getRoundStartTime() {
        return round_unix_startTime;
    }

    public int getTimeLeft() {
        return (int)(this.getGameRules().roundTime - (Instant.now().getEpochSecond() - this.getRoundStartTime()));
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

    public void setState(ArenaBase.STATE state) { this.state = state; }

    public TeamController getTeamController() { return this.teamController; }

    public String getName() { return this.name; }

    public PhaseComponentController getPhaseComponentController() {
        return phaseComponentController;
    }

    public HandlersController getHandlersController() {
        return handlersController;
    }

    public ConditionsChain getJoinConditionsChain() {
        return joinConditionsChain;
    }

    public enum STATE {
        DELAYED_START,
        IN_PROGRESS,
        COUNTDOWN,
        PAUSED,
        EMPTY,
        ENDED
    }

    public static Builder newBuilder() {
        return new ArenaBase().new Builder();
    }

    public class Builder {
        private Builder() {

        }

        public void setJoinConditionsChain(ConditionsChain joinConditionsChain) {
            ArenaBase.this.joinConditionsChain = joinConditionsChain;
        }

        public void setTeamController(TeamController t) {
            ArenaBase.this.teamController = t;
        }

        public void setGameId(String gameId) {
            ArenaBase.this.gameId = gameId;
        }

        public void setName(String name) {
            ArenaBase.this.name = name;
        }

        public void setHotbarController(HotbarController hotbarController) {
            ArenaBase.this.hotbarController = hotbarController;
        }

        public void setChunkLoaderController(ChunkLoaderController chunkLoaderController) {
            ArenaBase.this.chunkLoaderController = chunkLoaderController;
        }

        public void setScenarioChainController(ScenarioChainController scenarioChainController) {
            ArenaBase.this.scenarioChainController = scenarioChainController;
        }

        public void setGuiController(GUIController guiController) {
            ArenaBase.this.guiController = guiController;
        }

        public void setGameRules(GameRules rules) {
            ArenaBase.this.gameRules = rules;
        }

        public ArenaBase getArena() {
            return ArenaBase.this;
        }

        public ArenaBase build() {
            return ArenaBase.this;
        }
    }
}
