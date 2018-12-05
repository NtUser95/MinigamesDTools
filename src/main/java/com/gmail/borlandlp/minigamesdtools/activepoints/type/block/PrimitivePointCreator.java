package com.gmail.borlandlp.minigamesdtools.activepoints.type.block;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.activepoints.ActivePoint;
import com.gmail.borlandlp.minigamesdtools.activepoints.behaviors.Behavior;
import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.Reaction;
import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.ReactionReason;
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath;
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

@CreatorInfo(creatorId = "primitive_point_creator")
public class PrimitivePointCreator implements Creator {
    @Override
    public List<String> getDataProviderRequiredFields() {
        return new ArrayList<>();
    }

    @Override
    public ActivePoint create(String activepoint_id, AbstractDataProvider dataProvider) throws Exception {
        PrimitiveBlockPoint activePoint = null;
        ConfigurationSection activePointConfig = MinigamesDTools.getInstance().getConfigProvider().getEntity(ConfigPath.ACTIVE_POINT, activepoint_id).getData();

        //init by type
        if (activePointConfig == null) {
            throw new Exception("cant load config for ActivePoint[ID:" + activepoint_id + "]");
        }

        String debugPrefix = "[" + activepoint_id + "] ";
        String formOfPoint = activePointConfig.get("params.form").toString();
        if (formOfPoint.equals("sphere")) {
            activePoint = new SphereBlockPoint();
        } else if (formOfPoint.equals("flat_sphere")) {
            activePoint = new FlatSpherePoint();
        } else if (formOfPoint.equals("square")) {
            activePoint = new SquareBlockPoint();
        } else if (formOfPoint.equals("flat_square")) {
            activePoint = new FlatSquarePoint();
        } else {
            throw new Exception("invalid form for ActivePoint[ID:" + activepoint_id + "]");
        }

        World world = Bukkit.getWorld(activePointConfig.get("params.location.world").toString());
        int x = Integer.parseInt(activePointConfig.get("params.location.x").toString());
        int y = Integer.parseInt(activePointConfig.get("params.location.y").toString());
        int z = Integer.parseInt(activePointConfig.get("params.location.z").toString());
        activePoint.setLocation(new Location(world, x, y, z));
        activePoint.setRadius(Integer.parseInt(activePointConfig.get("params.radius").toString()));
        activePoint.setHollow(Boolean.parseBoolean(activePointConfig.get("params.hollow").toString()));
        activePoint.setDirection(activePointConfig.get("params.location.direction").toString());
        activePoint.setHealth(Double.parseDouble(activePointConfig.get("params.health").toString()));

        String borderStr = activePointConfig.get("params.border_material").toString();
        Material borderMaterial = Material.getMaterial(borderStr);
        if(borderMaterial == null) {
            throw new Exception("border_material " + borderStr + " for ActivePoint " + activepoint_id + " not found!");
        }
        activePoint.setBorderMaterial(borderMaterial);

        String fillerStr = activePointConfig.get("params.filler_material").toString();
        Material fillerMaterial = Material.getMaterial(fillerStr);
        if(fillerMaterial == null) {
            throw new Exception("filler_material " + fillerStr + " for ActivePoint " + activepoint_id + " not found!");
        }
        activePoint.setFillerMaterial(fillerMaterial);

        //load other
        activePoint.setName(activepoint_id);

        //load reactions
        if(activePointConfig.contains("reactions.check_damage.enabled")) {
            Debug.print(Debug.LEVEL.NOTICE, debugPrefix + " Start load damage reactions");

            boolean damageable = Boolean.parseBoolean(activePointConfig.get("params.is_damagable").toString());
            boolean performDamage = Boolean.parseBoolean(activePointConfig.get("reactions.check_damage.enabled").toString());
            activePoint.setDamageable(damageable);
            activePoint.setPerformDamage(performDamage);

            List<Reaction> damageHandlers = new ArrayList<>();
            for (String handler_name : activePointConfig.getStringList("reactions.check_damage.reactions_handler")) {
                Debug.print(Debug.LEVEL.NOTICE, debugPrefix + "load damage reaction " + handler_name);
                AbstractDataProvider rDataProvider = new DataProvider();
                rDataProvider.set("active_point_instance", activePoint);
                damageHandlers.add(MinigamesDTools.getInstance().getReactionCreatorHub().createReaction(handler_name, rDataProvider));
            }
            activePoint.setReaction(ReactionReason.DAMAGE, damageHandlers);
            Debug.print(Debug.LEVEL.NOTICE, debugPrefix + "Done load damage reactions for" + activepoint_id);
        } else {
            Debug.print(Debug.LEVEL.NOTICE, debugPrefix + "Not found a damage reactions.");
            activePoint.setPerformDamage(false);
        }

        if(activePointConfig.contains("reactions.check_intersect.enabled")) {
            Debug.print(Debug.LEVEL.NOTICE, debugPrefix + "Start load intersect reactions");
            boolean isPerformIntersect = Boolean.parseBoolean(activePointConfig.get("reactions.check_intersect.enabled").toString());
            activePoint.setPerformEntityIntersection(isPerformIntersect);

            List<Reaction> intersectHandlers = new ArrayList<>();
            for (String handler_name : activePointConfig.getStringList("reactions.check_intersect.reactions_handler")) {
                Debug.print(Debug.LEVEL.NOTICE, debugPrefix + "load intersect reaction " + handler_name);
                AbstractDataProvider rDataProvider = new DataProvider();
                rDataProvider.set("active_point_instance", activePoint);
                intersectHandlers.add(MinigamesDTools.getInstance().getReactionCreatorHub().createReaction(handler_name, rDataProvider));
            }

            activePoint.setReaction(ReactionReason.INTERSECT, intersectHandlers);
            Debug.print(Debug.LEVEL.NOTICE, debugPrefix + "Done load intersect reactions for" + activepoint_id);
        } else {
            Debug.print(Debug.LEVEL.NOTICE, debugPrefix + "Not found an intersect reactions.");
            activePoint.setPerformEntityIntersection(false);
        }

        if(activePointConfig.contains("reactions.check_interact.enabled")) {
            Debug.print(Debug.LEVEL.NOTICE, debugPrefix + "Start load interact reactions");
            boolean isPerformInteract = Boolean.parseBoolean(activePointConfig.get("reactions.check_interact.enabled").toString());
            activePoint.setPerformInteraction(isPerformInteract);

            List<Reaction> interactHandlers = new ArrayList<>();
            for (String handler_name : activePointConfig.getStringList("reactions.check_interact.reactions_handler")) {
                Debug.print(Debug.LEVEL.NOTICE, debugPrefix + "load interact reaction " + handler_name);
                AbstractDataProvider rDataProvider = new DataProvider();
                rDataProvider.set("active_point_instance", activePoint);
                interactHandlers.add(MinigamesDTools.getInstance().getReactionCreatorHub().createReaction(handler_name, rDataProvider));
            }

            activePoint.setReaction(ReactionReason.INTERACT, interactHandlers);
            Debug.print(Debug.LEVEL.NOTICE, debugPrefix + "Done load interact reactions for " + activepoint_id);
        } else {
            Debug.print(Debug.LEVEL.NOTICE, debugPrefix + "Not found an interact reactions.");
            activePoint.setPerformInteraction(false);
        }

        //behavior
        Debug.print(Debug.LEVEL.NOTICE, debugPrefix + "Start load behaviors");
        List<Behavior> behaviors = new ArrayList<>();
        for (String handler_name : activePointConfig.getStringList("behaviors")) {
            Debug.print(Debug.LEVEL.NOTICE, debugPrefix + "load beahavior " + handler_name);

            AbstractDataProvider rDataProvider = new DataProvider();
            rDataProvider.set("active_point_instance", activePoint);

            behaviors.add(MinigamesDTools.getInstance().getBehaviorCreatorHub().createBehavior(handler_name, rDataProvider));
        }
        activePoint.setBehaviors(behaviors);
        Debug.print(Debug.LEVEL.NOTICE, debugPrefix + "Done load behaviors");

        return activePoint;
    }
}
