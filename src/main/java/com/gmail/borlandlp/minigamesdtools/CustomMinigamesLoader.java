package com.gmail.borlandlp.minigamesdtools;

import org.bukkit.Bukkit;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CustomMinigamesLoader {
    private List<Plugin> loadedPlugins = new ArrayList<>();
    private LoadingPhase currentLoadingPhase;

    public LoadingPhase getCurrentLoadingPhase() {
        return currentLoadingPhase;
    }

    private List<String> getAddonsList() {
        List<String> addons = new ArrayList<>();
        String[] source = MinigamesDTools.getInstance().getConfigManager().getAddonsPath().list();
        if(source != null) {
            for(String addonName : source) {
                if(addonName.contains(".jar")) {
                    addons.add(addonName);
                } else {
                    Debug.print(Debug.LEVEL.WARNING, "Ignore path '" + addonName + "'!");
                }
            }
        } else {
            Debug.print(Debug.LEVEL.WARNING, "Addons path does not contains files.");
        }

        return addons;
    }

    private void loadAddon(String jarName) throws InvalidPluginException, InvalidDescriptionException {
        File addonFile = new File(MinigamesDTools.getInstance().getConfigManager().getAddonsPath(), jarName);
        Debug.print(Debug.LEVEL.NOTICE, "Load addon '" + jarName + "'");
        Plugin plugin = Bukkit.getPluginManager().loadPlugin(addonFile);
        if(plugin != null) {
            Debug.print(Debug.LEVEL.NOTICE, "Trying to enable plugin '" + plugin.getName() + "'.");
            Bukkit.getPluginManager().enablePlugin(plugin);
            this.loadedPlugins.add(plugin);
        } else {
            Debug.print(Debug.LEVEL.WARNING, "Error while load '" + jarName + "'.");
        }
    }

    public void loadAddons() {
        this.currentLoadingPhase = LoadingPhase.IN_PROGRESS;

        for (String jarName : this.getAddonsList()) {
            try {
                this.loadAddon(jarName);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        this.currentLoadingPhase = LoadingPhase.DONE;
    }

    public void unloadAddons() {
        for (Plugin plugin : this.loadedPlugins) {
            try {
                Bukkit.getPluginManager().disablePlugin(plugin);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public enum LoadingPhase {
        IN_PROGRESS,
        DONE,
    }
}
