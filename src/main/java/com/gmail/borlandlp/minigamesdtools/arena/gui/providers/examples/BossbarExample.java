package com.gmail.borlandlp.minigamesdtools.arena.gui.providers.examples;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase;
import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.GUIProvider;
import com.gmail.borlandlp.minigamesdtools.arena.team.TeamProvider;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.Random;

public class BossbarExample extends GUIProvider {
    private String message = null;
    private float percentage = 0.0F;
    private BossBar bossBar;

    public BossbarExample(ArenaBase arena) {
        super(arena);
    }

    public BossbarExample() {}

    public String getMessage() {
        return this.message;
    }

    public void setPercentage(float percent) {
        this.percentage = percent;
    }

    public float getPercentage() {
        return this.percentage;
    }

    @Override
    public void gameEnded() {
        if(this.bossBar == null) {
            return;
        }

        for(TeamProvider team : this.getArena().getTeamController().getTeams()) {
            for (Player player : team.getPlayers()) {
                if(player != null) this.bossBar.removePlayer(player);
            }
        }
    }

    @Override
    public void onInit() {

    }

    @Override
    public void beforeGameStarting() {
        BarColor color = BarColor.values()[ (new Random()).nextInt(BarColor.values().length - 1) ];
        BarStyle style = BarStyle.values()[ (new Random()).nextInt(BarStyle.values().length - 1) ];
        this.bossBar = Bukkit.createBossBar("BossBarExample", color, style);
        this.bossBar.setProgress((new Random()).nextDouble());
    }

    public void update() {
        this.bossBar.setColor(BarColor.values()[ (new Random()).nextInt(BarColor.values().length - 1) ]);
        this.bossBar.setStyle(BarStyle.values()[ (new Random()).nextInt(BarStyle.values().length - 1) ]);

        for(TeamProvider team : this.getArena().getTeamController().getTeams()) {
            for (Player player : team.getPlayers()) {
                if (player != null && !this.bossBar.getPlayers().contains(player)) {
                    bossBar.addPlayer(player);
                    bossBar.setVisible(true);
                }
            }
        }
    }

    @Override
    public void beforeRoundStarting() {

    }

    @Override
    public void onRoundEnd() {

    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void removeMessage() {
        this.message = null;
    }
}
