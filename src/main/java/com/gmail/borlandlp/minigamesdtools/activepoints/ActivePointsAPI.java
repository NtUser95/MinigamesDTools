package com.gmail.borlandlp.minigamesdtools.activepoints;

import java.util.List;

public interface ActivePointsAPI {
    /**
     * Case-sensitive search of active point by its ID
     * @param ID - ActivePoint ID
     * @return ActivePoint {@link ActivePoint}
     */
    ActivePoint searchPointByID(String ID);

    /**
     * Spawns an active point. Before calling this method,
     * the active point must register itself through {@link ActivePointsAPI#registerPoint(ActivePoint)}
     * @param point - {@link ActivePoint} instance
     */
    void activatePoint(ActivePoint point);

    /**
     * Despawn an ActivePoint.
     * @param point - {@link ActivePoint} instance
     */
    void deactivatePoint(ActivePoint point);

    /**
     * @return - returns cache of used blocks for active points
     */
    StaticPointsCache getStaticPointsCache();

    /**
     * @return - returns a copy of the list of all registered active points.
     */
    List<ActivePoint> getAllPoints();

    /**
     * @return - returns a copy of the list of all activated active points.
     */
    List<ActivePoint> getAllActivatedPoints();

    /**
     * Binds the active point to the API and adds it to the general list of points
     * @param point - {@link ActivePoint} instance
     */
    void registerPoint(ActivePoint point);

    /**
     * Unbinds the active point from the API and remove it from the general list of points
     * Throws an exception if the active point has not been deactivated
     * @param point - {@link ActivePoint} instance
     */
    void unregisterPoint(ActivePoint point) throws Exception;
}
