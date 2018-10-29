package com.gmail.borlandlp.minigamesdtools.nmsentities;

import net.minecraft.server.v1_12_R1.Entity;

public class RegisteredEntity {
    private String name;
    private int id;
    private Class<? extends Entity> oldClass;
    private Class<? extends Entity> newClass;

    public RegisteredEntity(String n, int i, Class<? extends Entity> oC, Class<? extends Entity> nC) {
        this.name = n;
        this.id = i;
        this.oldClass = oC;
        this.newClass = nC;
    }

    public Class<? extends Entity> getNewClass() {
        return newClass;
    }

    public Class<? extends Entity> getOldClass() {
        return oldClass;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
