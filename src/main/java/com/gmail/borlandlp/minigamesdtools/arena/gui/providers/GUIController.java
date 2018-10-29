package com.gmail.borlandlp.minigamesdtools.arena.gui.providers;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaComponent;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent;

import java.util.ArrayList;
import java.util.List;

public class GUIController extends ArenaComponent implements ArenaPhaseComponent {
    private List<GUIProvider> providers = new ArrayList<>();
    private GUIListener guiListener;

    public List<GUIProvider> getProviders() {
        return providers;
    }

    public void addProvider(GUIProvider provider) {
        this.providers.add(provider);
    }

    public void removeProvider(GUIProvider provider) {
        this.providers.remove(provider);
    }

    public void update() {

    }

    @Override
    public void beforeRoundStarting() {

    }

    @Override
    public void onRoundEnd() {

    }

    @Override
    public void onInit() {
        this.guiListener = new GUIListener(this);
        try {
            this.getArena().getEventAnnouncer().register(this.guiListener);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (ArenaPhaseComponent provider : this.getProviders()) {
            try {
                this.getArena().getPhaseComponentController().register(provider);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void beforeGameStarting() {

    }

    public void gameEnded() {
        this.getArena().getEventAnnouncer().unregister(this.guiListener);
    }
}
