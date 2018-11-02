package com.gmail.borlandlp.minigamesdtools.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

/*
* TODO: Перевести на английский
* Разработчики Spigot, почему-то, не разрешили ломать блоки всем, кроме Player.
* Эта дискриминация мешает как Entity, так и мне. Исправим это.
* */
public class BlockDamageByEntityEvent {
    private Entity entity;
    private Block block;
    private ItemStack itemStack;
    private boolean instabreak;

    public BlockDamageByEntityEvent(Entity entity, Block block, ItemStack itemStack, boolean instabreak) {
        this.entity = entity;
        this.block = block;
        this.itemStack = itemStack;
        this.instabreak = instabreak;
    }

    public Entity getEntity() {
        return entity;
    }

    public Block getBlock() {
        return block;
    }

    public boolean isInstabreak() {
        return instabreak;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
