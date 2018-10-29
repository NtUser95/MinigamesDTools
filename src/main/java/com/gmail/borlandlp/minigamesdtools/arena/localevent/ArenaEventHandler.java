package com.gmail.borlandlp.minigamesdtools.arena.localevent;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ArenaEventHandler {
    ArenaEventPriority priority() default ArenaEventPriority.LOWEST;
    boolean ignoreCancelled() default false;
}
