package com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.gun;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.SlotItem;
import com.gmail.borlandlp.minigamesdtools.gun.bullet.GhostBullet;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Vector;

public class ExampleGun extends SlotItem {
    @Override
    public boolean use(Player player) {
        try {
            GhostBullet bullet = MinigamesDTools.getInstance().getBulletCreatorHub().createBullet("ExampleBullet", new DataProvider() {{
                this.set("world", player.getWorld());
            }});

            bullet.setLocation(
                    player.getEyeLocation().getX(),
                    player.getEyeLocation().getY(),
                    player.getEyeLocation().getZ(),
                    player.getLocation().getYaw(),
                    player.getLocation().getPitch()
            );
            bullet.f(
                    player.getLocation().getDirection().getX() * 1.5,
                    player.getLocation().getDirection().getY() * 1.5,
                    player.getLocation().getDirection().getZ() * 1.5
            ); // velocity
            bullet.shooter = ((CraftPlayer) player).getHandle();
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 1, 1);

            MinigamesDTools.getInstance().getBulletHandlerApi().addBullet(bullet);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
