package com.gmail.borlandlp.minigamesdtools;

import java.util.ArrayList;
import java.util.List;

public class APIComponentsController {
    private List<APIComponent> components = new ArrayList<>();

    public void register(APIComponent c) {
        this.components.add(c);
    }

    public void unregister(APIComponent c) {
        this.components.remove(c);
    }

    private void announceComponent(APIComponent component, ComponentEvent phase) {
        try {
            switch (phase) {
                case PLUGIN_LOAD: component.onLoad(); break;
                case PLUGIN_UNLOAD: component.onUnload(); break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void announceEvent(ComponentEvent componentEvent) {
        for (APIComponent component : this.getComponents()) {
            this.announceComponent(component, componentEvent);
        }
    }

    public List<APIComponent> getComponents() {
        return new ArrayList<>(this.components);
    }

    public enum ComponentEvent {
        PLUGIN_LOAD,
        PLUGIN_UNLOAD,
    }
}
