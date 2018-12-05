package com.gmail.borlandlp.minigamesdtools.activepoints.type.entity;

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
import com.gmail.borlandlp.minigamesdtools.nmsentities.entity.SkyDragon;
import com.gmail.borlandlp.minigamesdtools.nmsentities.entity.SkyZombie;
import net.minecraft.server.v1_12_R1.EntityInsentient;
import net.minecraft.server.v1_12_R1.Vec3D;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

@CreatorInfo(creatorId = "primitive_entity_creator")
public class PrimitiveEntityCreator implements Creator {
    @Override
    public List<String> getDataProviderRequiredFields() {
        return new ArrayList<>();
    }

    @Override
    public ActivePoint create(String activepoint_id, AbstractDataProvider dataProvider) throws Exception {
        BossEntity activePoint = null;
        ConfigurationSection activePointConfig = MinigamesDTools.getInstance().getConfigProvider().getEntity(ConfigPath.ACTIVE_POINT, activepoint_id).getData();

        //init by type
        if (activePointConfig == null) {
            throw new Exception("cant load config for ActivePoint[ID:" + activepoint_id + "]");
        }

        String debugPrefix = "[" + activepoint_id + "] ";
        activePoint = new BossEntity();

        World world = Bukkit.getWorld(activePointConfig.get("params.location.world").toString());
        int x = Integer.parseInt(activePointConfig.get("params.location.x").toString());
        int y = Integer.parseInt(activePointConfig.get("params.location.y").toString());
        int z = Integer.parseInt(activePointConfig.get("params.location.z").toString());
        activePoint.setLocation(new Location(world, x, y, z));

        String entityName = activePointConfig.get("params.type").toString();
        EntityInsentient entityInsentient = null;
        if (entityName.equals("sky_zombie")) {
            entityInsentient = new SkyZombie(world);
        } else if (entityName.equals("sky_dragon")) {
            entityInsentient = new SkyDragon(world);
        } else {
            throw new Exception("invalid entity type '" + entityName + "' for ActivePoint[ID:" + activepoint_id + "]");
        }

        activePoint.setClassTemplate(entityInsentient);
        entityInsentient.setHealth(Integer.parseInt(activePointConfig.get("params.health").toString()));
        List<Vec3D> vec3DS = new ArrayList<>();
        for (String str : activePointConfig.getStringList("params.move_paths")) {
            String[] splitted = str.split(":");
            if (splitted.length == 3) {
                vec3DS.add(new Vec3D(Double.parseDouble(splitted[0]), Double.parseDouble(splitted[1]), Double.parseDouble(splitted[2])));
            } else {
                Debug.print(Debug.LEVEL.NOTICE, "Invalid move_path 'str' for ActivePoint[ID:" + activepoint_id + "]. ");
            }
        }
        activePoint.setMovePaths(vec3DS.toArray(new Vec3D[vec3DS.size()]));

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
