package com.gmail.borlandlp.minigamesdtools.config;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.config.exception.DuplicateConfigException;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class ConcreteConfigProvider implements ConfigProvider {
    // String[poolId] -> String[configEntityId], ConfigEntity
    private Map<String, Map<String, ConfigEntity>> configsPool = new Hashtable<>();

    public void registerEntity(ConfigPath p, ConfigEntity file) throws DuplicateConfigException {
        this.registerEntity(p.getTypeId(), file);
    }

    public void registerEntity(String poolId, ConfigEntity file) throws DuplicateConfigException {
        Map<String, ConfigEntity> configs = this.configsPool.getOrDefault(poolId, new Hashtable<>());
        if(configs.containsKey(file.getID())) {
            throw new DuplicateConfigException(poolId + " already contains " + file.getID());
        }
        configs.put(file.getID(), file);

        this.configsPool.put(poolId, configs);
    }

    public ConfigEntity getEntity(ConfigPath p, String entityId) {
        return this.getEntity(p.getTypeId(), entityId);
    }

    public ConfigEntity getEntity(String poolId, String entityId) {
        if(!this.configsPool.containsKey(poolId)) {
            Debug.print(Debug.LEVEL.WARNING, "[ConfigProvider] Detected an attempt to get entityID:" + entityId + " from unregistered poolId:" + poolId +"!");
            return null;
        } else if(!this.configsPool.get(poolId).containsKey(entityId)) {
            Debug.print(Debug.LEVEL.WARNING, "[ConfigProvider] Detected an attempt to get unknown entityID:" + entityId + " from poolId:" + poolId +"!");
            return null;
        } else {
            return this.configsPool.get(poolId).get(entityId);
        }
    }

    public List<ConfigEntity> getPoolContents(ConfigPath path) {
        return this.getPoolContents(path.getTypeId());
    }

    public List<ConfigEntity> getPoolContents(String poolId) {
        if(this.configsPool.containsKey(poolId)) {
            return new ArrayList<>(this.configsPool.get(poolId).values());
        } else {
            Debug.print(Debug.LEVEL.WARNING, "[ConfigProvider] Detected an attempt to get poolContents for unregistered poolId:" + poolId +"!");
            return new ArrayList<>();
        }
    }
}
