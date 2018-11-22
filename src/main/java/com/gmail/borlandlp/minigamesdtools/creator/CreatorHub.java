package com.gmail.borlandlp.minigamesdtools.creator;

import com.gmail.borlandlp.minigamesdtools.Debug;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public abstract class CreatorHub implements ICreatorHub {
    private Map<String, Creator> creators = new Hashtable<>();
    private Map<String, String> route = new Hashtable<>();

    /*
     * TODO: перевести на английский
     * Регистрирует создателя объектов.
     * */
    @Override
    public void registerCreator(Creator creator) throws Exception {
        CreatorInfo annotation = creator.getClass().getDeclaredAnnotation(CreatorInfo.class);
        if(annotation != null) {
            if(this.creators.containsKey(annotation.creatorId())) {
                StringBuilder msg = (new StringBuilder())
                        .append("Creator with ID:").append(annotation.creatorId())
                        .append(" is already registered for class:").append(this.creators.get(annotation.creatorId()).getClass().getName())
                        .append(" class trying to register:").append(creator.getClass().getName());
                throw new Exception(msg.toString());
            }

            this.creators.put(annotation.creatorId(), creator);
            Debug.print(Debug.LEVEL.NOTICE, "Register #class:" + creator.getClass().getSimpleName() + " with ID:" + annotation.creatorId());
        } else {
            throw new Exception("Creator must be have ID!");
        }
    }

    @Override
    public void unregisterCreator(Creator creator) {
        //this.route.keySet().parallelStream().filter(n -> this.routeId2Creator(n) == creator).forEach(n -> this.route.remove(n));
        this.creators.keySet().parallelStream().filter(n -> this.creators.get(n) == creator).forEach(n -> this.creators.remove(n));
    }

    @Override
    public void unregisterCreator(String creatorID) {
        if(this.creators.containsKey(creatorID)) {
            return;
        }

        this.unregisterCreator(this.creators.get(creatorID));
    }

    /*
     * TODO: перевести на английский
     * Регистрирует перенаправление с ID/псевдонима собираемого объекта(Фигурирует обычно в конфигах),
     * на конкретного создателя(ID указывается через аннотацию @CreatorInfo к создателю),
     * Который мог бы собрать этот объект.
     * */
    @Override
    public void registerRouteId2Creator(String routeId, String creatorID) throws Exception {
        if(!this.creators.containsKey(creatorID)) {
            throw new Exception("Cant find creator ID:" + creatorID + ", current itemID:" + routeId);
        } else {
            this.route.put(routeId, creatorID);
            Debug.print(Debug.LEVEL.NOTICE, "Register @route itemID:" + routeId + " for creatorID:" + creatorID);
        }
    }

    public Creator routeId2Creator(String Id) {
        return this.creators.get(this.route.get(Id));
    }

    @Override
    public void unregisterRouteId2Creator(String itemID) {
        this.route.remove(itemID);
    }

    /*
     * TODO: перевести на английский
     * Базовый метод создания объекта
     * */
    @Override
    public Object create(String itemID, AbstractDataProvider dataProvider) throws Exception {
        if(!this.containsRouteId2Creator(itemID)) {
            throw new Exception("itemID:" + itemID + " isnt registered routeID!");
        }

        Creator creator = this.routeId2Creator(itemID);
        for (String reqField : creator.getDataProviderRequiredFields()) {
            if(!dataProvider.contains(reqField)) {
                creator.getDataProviderRequiredFields().forEach(System.out::println);
                dataProvider.getKeys().forEach(System.out::println);
                throw new Exception("Invalid DataProvider. Missing field '" + reqField + "'. itemID:" + itemID);
            }
        }

        return creator.create(itemID, dataProvider);
    }

    @Override
    public boolean containsCreator(String ID) {
        return this.creators.containsKey(ID);
    }

    @Override
    public boolean containsCreator(Creator creator) {
        return this.creators.containsValue(creator);
    }

    @Override
    public boolean containsRouteId2Creator(String itemId) {
        return this.route.containsKey(itemId);
    }
}
