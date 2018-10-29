package com.gmail.borlandlp.minigamesdtools.creator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CreatorInfo {
    String creatorId();
}
