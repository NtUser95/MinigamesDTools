package com.gmail.borlandlp.minigamesdtools.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

/*
* TODO: Перевести на английский
* Разработчики Spigot, почему-то, не разрешили ломать блоки всем, кроме Player.
* Эта дискриминация мешает как Entity, так и мне. Исправим это.
* */
public class BlockDamageByEntityEvent extends Event implements Cancellable {
    private LivingEntity entity;
    private Block block;
    private ItemStack itemStack;
    private boolean instabreak;
    private boolean canceled;
    private static final HandlerList handlers = new HandlerList();

    public BlockDamageByEntityEvent(LivingEntity entity, Block block, ItemStack itemStack, boolean instabreak) {
        this.entity = entity;
        this.block = block;
        this.itemStack = itemStack;
        this.instabreak = instabreak;
    }

    public LivingEntity getEntity() {
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

    @Override
    public boolean isCancelled() {
        return this.canceled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.canceled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
