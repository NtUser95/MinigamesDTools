package com.gmail.borlandlp.minigamesdtools.arena.commands;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.List;

public class DefaultWatcher implements Listener, ArenaCommandWatcher {
    private List<String[]> whitelisted = new ArrayList<>();
    private List<String[]> blacklisted = new ArrayList<>();
    private ArenaBase arena;

    public void setArena(ArenaBase arena) {
        this.arena = arena;
    }

    public List<String[]> getBlacklisted() {
        return blacklisted;
    }

    public void addBlacklisted(String[] command) {
        this.blacklisted.add(command);
    }

    public void setBlacklisted(List<String[]> blacklisted) {
        this.blacklisted = blacklisted;
    }

    public List<String[]> getWhitelisted() {
        return whitelisted;
    }

    public void addWhitelisted(String[] command) {
        this.whitelisted.add(command);
    }

    public void setWhitelisted(List<String[]> whitelisted) {
        this.whitelisted = whitelisted;
    }

    /**
     * Формат для правил: command param1 param2 *
     * Символ * означает, что любая дальнейшая последовательсть будет истинна.
     * Если символ стоит в самом начале, то любая команда с любыми параметрами будет истинна.
     * Если правило состоит из 3 параметров и не заканчивается на *, а команда из 4 идентичных параметров - результат будет ложью.
     * @param commandsList
     * @param command
     * @return
     */
    public boolean containsRuleSequence(List<String[]> commandsList, String[] command) {
        for (String[] listRule : commandsList) {
            int validEntries = 0;
            for (int i = 0; i < listRule.length; i++) {
                if(i >= command.length) { // outbound
                    break;
                } else if(listRule[i].equals("*")) {
                    return true;
                } else if(!listRule[i].equals(command[i])) {
                    break;
                } else { // listRule[i].equals[command[i]]
                    if(++validEntries >= command.length && command.length == listRule.length) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean isAllowedCommand(String[] command) {
        if(containsRuleSequence(this.blacklisted, command)) {
            if(!containsRuleSequence(this.whitelisted, command)) {
                return false;
            }
        }

        return true;
    }

    @Override
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        ArenaBase arena = MinigamesDTools.getInstance().getArenaAPI().getArenaOf(event.getPlayer());
        if(arena != null && arena == this.arena) {
            String[] command = event.getMessage().replace("/", "").split(" ");
            if(!isAllowedCommand(command)) {
                event.getPlayer().sendMessage("{arena_rejected_msg}");
                event.setCancelled(true);
            }
        }
    }

    @Override
    public void onInit() {
        Bukkit.getPluginManager().registerEvents(this, MinigamesDTools.getInstance());
    }

    @Override
    public void beforeGameStarting() {

    }

    @Override
    public void gameEnded() {
        HandlerList.unregisterAll(this);
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
