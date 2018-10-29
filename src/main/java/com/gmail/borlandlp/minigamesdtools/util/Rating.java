package com.gmail.borlandlp.minigamesdtools.util;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import org.bukkit.entity.Player;

public class Rating {

    public static float getRatingOf(Object object)
    {
        float rating = 0F;
        if(object instanceof Player) {
            rating = MinigamesDTools.getInstance().getDBWrapper().getArenaRating((Player)object);
        }

        return rating;
    }

}
