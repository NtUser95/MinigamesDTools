package com.gmail.borlandlp.minigamesdtools.test.gui.hotbar.slots;

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
                    player.getLocation().getX(),
                    player.getLocation().getY(),
                    player.getLocation().getZ(),
                    player.getLocation().getYaw(),
                    player.getLocation().getPitch()
            );
            bullet.f(
                    player.getLocation().getDirection().getX(),
                    player.getLocation().getDirection().getY(),
                    player.getLocation().getDirection().getZ()
            ); // velocity
            bullet.shooter = ((CraftPlayer) player).getHandle();
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_MAGMACUBE_JUMP, 1, 1);

            MinigamesDTools.getInstance().getBulletHandlerApi().addBullet(bullet);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
