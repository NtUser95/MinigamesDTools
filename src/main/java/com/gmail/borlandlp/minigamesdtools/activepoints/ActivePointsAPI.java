package com.gmail.borlandlp.minigamesdtools.activepoints;

import java.util.List;

public interface ActivePointsAPI {
    ActivePoint searchPointByID(String ID);
    void activatePoint(ActivePoint point);
    void deactivatePoint(ActivePoint point);
    StaticPointsCache getStaticPointsCache();
    List<ActivePoint> getAllPoints();
    void registerPoint(ActivePoint point);
    void unregisterPoint(ActivePoint point);
}
