package com.gmail.borlandlp.minigamesdtools.config;

import com.gmail.borlandlp.minigamesdtools.config.exception.DuplicateConfigException;

import java.util.List;

public interface ConfigProvider {
    void registerEntity(ConfigPath p, ConfigEntity file) throws DuplicateConfigException;
    void registerEntity(String poolId, ConfigEntity file) throws DuplicateConfigException;
    ConfigEntity getEntity(ConfigPath p, String entityId);
    ConfigEntity getEntity(String poolId, String entityId);
    List<ConfigEntity> getPoolContents(ConfigPath path);
    List<ConfigEntity> getPoolContents(String poolId);
}
