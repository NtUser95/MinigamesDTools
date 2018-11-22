package com.gmail.borlandlp.minigamesdtools.creator;

import java.util.List;

public interface Creator {
    List<String> getDataProviderRequiredFields();
    Object create(String ID, AbstractDataProvider dataProvider) throws Exception;
}
