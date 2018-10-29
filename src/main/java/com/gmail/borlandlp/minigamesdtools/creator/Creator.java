package com.gmail.borlandlp.minigamesdtools.creator;

public interface Creator {
    Object create(String ID, AbstractDataProvider dataProvider) throws Exception;
}
