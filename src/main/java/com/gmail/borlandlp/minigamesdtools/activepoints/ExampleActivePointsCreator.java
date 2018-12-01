package com.gmail.borlandlp.minigamesdtools.activepoints;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.activepoints.behaviors.Behavior;
import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.Reaction;
import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.ReactionReason;
import com.gmail.borlandlp.minigamesdtools.activepoints.type.BossEntity;
import com.gmail.borlandlp.minigamesdtools.activepoints.type.SphereBlockPoint;
import com.gmail.borlandlp.minigamesdtools.activepoints.type.SquareBlockPoint;
import com.gmail.borlandlp.minigamesdtools.activepoints.type.StaticBlockPoint;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CreatorInfo(creatorId = "default_activepoint_factory")
public class ExampleActivePointsCreator implements Creator {
    @Override
    public List<String> getDataProviderRequiredFields() {
        return new ArrayList<>();
    }

    public ActivePoint create(String activepoint_id, AbstractDataProvider dataProvider) throws Exception {
        ActivePoint activePoint = null;
        ConfigurationSection activePointConfig = MinigamesDTools.getInstance().getConfigProvider().getEntity(ConfigPath.ACTIVE_POINT, activepoint_id).getData();

        //init by type
        if (activePointConfig == null) {
            throw new Exception("cant load config for ActivePoint[ID:" + activepoint_id + "]");
        }

        String debugPrefix = "[" + activepoint_id + "] ";
        String pointType = activePointConfig.get("type").toString();
        if (pointType.equals("static_block")) {
            String formOfPoint = activePointConfig.get("params.form").toString();
            if (formOfPoint.equals("sphere")) {
                activePoint = new SphereBlockPoint();
            } else if (formOfPoint.equals("square")) {
                activePoint = new SquareBlockPoint();
            } else {
                throw new Exception("invalid form for ActivePoint[ID:" + activepoint_id + "]");
            }

            World world = Bukkit.getWorld(activePointConfig.get("params.location.world").toString());
            int x = Integer.parseInt(activePointConfig.get("params.location.x").toString());
            int y = Integer.parseInt(activePointConfig.get("params.location.y").toString());
            int z = Integer.parseInt(activePointConfig.get("params.location.z").toString());
            activePoint.setLocation(new Location(world, x, y, z));
            ((StaticBlockPoint) activePoint).setRadius(Integer.parseInt(activePointConfig.get("params.radius").toString()));
            ((StaticBlockPoint) activePoint).setHollow(Boolean.parseBoolean(activePointConfig.get("params.hollow").toString()));
            ((StaticBlockPoint) activePoint).setDirection(activePointConfig.get("params.location.direction").toString());
            ((StaticBlockPoint) activePoint).setHealth(Double.parseDouble(activePointConfig.get("params.health").toString()));

        } else if (pointType.equals("living_entity")) {

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

            ((BossEntity) activePoint).setClassTemplate(entityInsentient);
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
            ((BossEntity) activePoint).setMovePaths(vec3DS.toArray(new Vec3D[vec3DS.size()]));

        } else {
            throw new Exception("invalid type for ActivePoint[ID:" + activepoint_id + "]");
        }

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
