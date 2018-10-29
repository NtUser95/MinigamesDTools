package com.gmail.borlandlp.minigamesdtools.creator;

public interface ICreatorHub {
    void registerCreator(Creator factory) throws Exception;
    void registerRouteId2Creator(String itemID, String creator) throws Exception;
    Object create(String itemID, AbstractDataProvider dataProvider) throws Exception;
    void unregisterCreator(Creator creator);
    void unregisterCreator(String creatorID);
    void unregisterRouteId2Creator(String itemID);
    boolean containsCreator(String ID);
    boolean containsCreator(Creator creator);
    boolean containsRouteId2Creator(String itemId);
    RulesProvider getDataProviderRules();
}
