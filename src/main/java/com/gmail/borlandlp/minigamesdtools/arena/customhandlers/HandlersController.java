package com.gmail.borlandlp.minigamesdtools.arena.customhandlers;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent;

import java.util.ArrayList;
import java.util.List;

public class HandlersController implements ArenaPhaseComponent {
    private List<Handler> handlers = new ArrayList<>();

    public void add(Handler handler) {
        this.handlers.add(handler);
        handler.start();
    }

    public void remove(Handler handler) {
        this.handlers.remove(handler);
        handler.stop();
    }

    @Override
    public void onInit() {

    }

    @Override
    public void beforeGameStarting() {

    }

    @Override
    public void gameEnded() {
        for (Handler handler : this.handlers) {
            try {
                handler.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
