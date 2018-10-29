package com.gmail.borlandlp.minigamesdtools.arena;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.*;
import org.bukkit.event.Cancellable;

import java.lang.reflect.Method;
import java.util.*;

public class EventAnnouncer {
    private Map<Class<? extends ArenaEvent>, List<RegisteredArenaListener>> listeners = new HashMap<>();

    public void register(ArenaEventListener listener) throws Exception {
        Method[] publicMethods = listener.getClass().getMethods();
        Method[] privateMethods = listener.getClass().getDeclaredMethods();
        HashSet methods = new HashSet<Method>(publicMethods.length + privateMethods.length, 1.0F);

        methods.addAll(Arrays.asList(publicMethods));
        methods.addAll(Arrays.asList(privateMethods));

        for (Object IMethod : methods) {
            ArenaEventHandler annClass = ((Method)IMethod).getAnnotation(ArenaEventHandler.class);
            if(((Method) IMethod).getGenericParameterTypes().length == 1 && annClass != null) {
                Class argEventClazz = null;

                try {
                    argEventClazz = Class.forName((((Method)IMethod).getGenericParameterTypes()[0]).getTypeName());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if(argEventClazz == null) {
                    throw new Exception("Internal error. Cant found class " + (((Method)IMethod).getGenericParameterTypes()[0]).getTypeName() + " for " + listener.getClass().getSimpleName());
                }

                List<RegisteredArenaListener> handlers = this.getListeners().getOrDefault(argEventClazz, new ArrayList<>());
                handlers.add(new RegisteredArenaListener(listener, (Method) IMethod, annClass.ignoreCancelled(), annClass.priority()));
                this.listeners.put(argEventClazz, handlers);

                // debug
                StringBuilder str = new StringBuilder();
                str.append("register listener:").append(listener.getClass().getSimpleName())
                        .append(" method:").append(((Method) IMethod).getName())
                        .append(" eventClass:").append(argEventClazz.getSimpleName())
                        .append(" ignoreCanceled:").append(annClass.ignoreCancelled())
                        .append(" priority:").append(annClass.priority())
                        .append(" total_class_listeners:").append(handlers.size());

                Debug.print(Debug.LEVEL.NOTICE, str.toString());
            }
        }
    }

    public void unregister(ArenaEventListener listener) {
        for (List<RegisteredArenaListener> list : this.getListeners().values()) {
            list.removeIf(AListener -> AListener.getHandler() == listener);
        }
    }

    public Map<Class<? extends ArenaEvent>, List<RegisteredArenaListener>> getListeners() {
        return this.listeners;
    }

    public <T extends ArenaEvent> void announce(T event) {
        if(!this.getListeners().containsKey(event.getClass())) {
            return;
        }

        for (ArenaEventPriority priority : ArenaEventPriority.values()) { // highest, high, normal, low, lowest...
            for(RegisteredArenaListener registeredArenaListener : this.getListeners().get(event.getClass())) {
                if(registeredArenaListener.getPriority() != priority) {
                    continue;
                } else if(event instanceof Cancellable && ((Cancellable) event).isCancelled() && registeredArenaListener.isIgnoreCanceled()) {
                    continue;
                }

                try {
                    registeredArenaListener.execute(event);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
