package com.gmail.borlandlp.minigamesdtools.arena;

import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaEvent;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaEventPriority;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RegisteredArenaListener {
    private Method method;
    private ArenaEventListener handler;
    private boolean ignoreCanceled;
    private ArenaEventPriority priority;


    public RegisteredArenaListener(ArenaEventListener listener, Method meth) {
        this(listener, meth, false, ArenaEventPriority.LOWEST);
    }

    public RegisteredArenaListener(ArenaEventListener listener, Method meth, boolean i) {
        this(listener, meth, i, ArenaEventPriority.LOWEST);
    }

    public RegisteredArenaListener(ArenaEventListener listener, Method meth, boolean i, ArenaEventPriority p) {
        this.handler = listener;
        this.method = meth; // There is gold in the streets just waiting for someone to come and scoop it up.
        this.ignoreCanceled = i;
        this.priority = p;
    }


    public boolean isIgnoreCanceled() {
        return ignoreCanceled;
    }

    public ArenaEventPriority getPriority() {
        return priority;
    }

    public ArenaEventListener getHandler() {
        return handler;
    }

    public void execute(ArenaEvent event) {
        try {
            this.method.invoke(this.handler, event);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
