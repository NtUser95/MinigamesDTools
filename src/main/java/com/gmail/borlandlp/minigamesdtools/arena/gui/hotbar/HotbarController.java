package com.gmail.borlandlp.minigamesdtools.arena.gui.hotbar;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaComponent;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent;
import com.gmail.borlandlp.minigamesdtools.arena.team.ExampleTeam;
import com.gmail.borlandlp.minigamesdtools.arena.team.TeamProvider;
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.Hotbar;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class HotbarController extends ArenaComponent implements ArenaPhaseComponent {
    private String defaultHotbarId;
    private Listener listener;
    private boolean enabled = false;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setDefaultHotbarId(String defaultH) {
        this.defaultHotbarId = defaultH;
    }

    public String getDefaultHotbarId() {
        return this.defaultHotbarId;
    }

    public Hotbar buildDefaultHotbarFor(Player player) throws Exception {
        String hotbarID = this.getDefaultHotbarId();
        return MinigamesDTools.getInstance().getHotbarCreatorHub().createHotbar(hotbarID, new DataProvider() {{
            this.set("player", player);
        }});
    }

    @Override
    public void onInit() {
        if(!this.enabled) {
            return;
        }

        this.listener = new HotbarListener(this);
        MinigamesDTools.getInstance().getServer().getPluginManager().registerEvents(this.listener, MinigamesDTools.getInstance());
        try {
            this.getArena().getEventAnnouncer().register((ArenaEventListener) this.listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beforeGameStarting() {
        if(!this.enabled) {
            return;
        }

        for (TeamProvider team : this.getArena().getTeamController().getTeams()) {
            for (Player player : team.getPlayers()) {
                if(player == null) {
                    continue;
                }

                try {
                    MinigamesDTools.getInstance().getHotbarAPI().bindHotbar(this.buildDefaultHotbarFor(player), player);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void gameEnded() {
        if(!this.enabled) {
            return;
        }

        for (TeamProvider team : this.getArena().getTeamController().getTeams()) {
            for (Player player : team.getPlayers()) {
                if(player != null) {
                    MinigamesDTools.getInstance().getHotbarAPI().unbindHotbar(player);
                }
            }
        }
        HandlerList.unregisterAll(this.listener);
        this.getArena().getEventAnnouncer().unregister((ArenaEventListener) this.listener);
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
