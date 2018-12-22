package com.gmail.borlandlp.minigamesdtools.arena;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.*;
import org.bukkit.event.Cancellable;

import java.lang.reflect.Method;
import java.util.*;

public class EventAnnouncer {
    private Map<Class<? extends ArenaEvent>, List<RegisteredArenaListener>> listeners = new HashMap<>();

    public void register(ArenaEventListener listener) {
        for (Object IMethod : getObjectMethods(listener)) {
            ArenaEventHandler annClass = ((Method)IMethod).getAnnotation(ArenaEventHandler.class);
            if(((Method) IMethod).getGenericParameterTypes().length == 1 && annClass != null) {
                Class argEventClazz = null;

                try {
                    argEventClazz = Class.forName((((Method)IMethod).getGenericParameterTypes()[0]).getTypeName());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if(argEventClazz == null) {
                    String parameterClass = (((Method)IMethod).getGenericParameterTypes()[0]).getTypeName();
                    String listenerClass = listener.getClass().getSimpleName();
                    String msg = "Internal error. Cant found class " + parameterClass + " for " + listenerClass;
                    Debug.print(Debug.LEVEL.WARNING, msg);
                    continue;
                }

                registerListener(argEventClazz, listener, annClass, (Method) IMethod);
            }
        }
    }

    private void registerListener(Class argEventClazz, ArenaEventListener listener, ArenaEventHandler annClass, Method method) {
        List<RegisteredArenaListener> handlers = this.getListenersOf(argEventClazz);
        RegisteredArenaListener rListener = new RegisteredArenaListener(listener, method, annClass.ignoreCancelled(), annClass.priority());
        handlers.add(rListener);
        this.listeners.put(argEventClazz, handlers);
    }

    private Set<Method> getObjectMethods(Object obj) {
        Method[] publicMethods = obj.getClass().getMethods();
        Method[] privateMethods = obj.getClass().getDeclaredMethods();
        Set<Method> methods = new HashSet<>(publicMethods.length + privateMethods.length, 1.0F);

        methods.addAll(Arrays.asList(publicMethods));
        methods.addAll(Arrays.asList(privateMethods));

        return methods;
    }

    public void unregister(ArenaEventListener listener) {
        for (List<RegisteredArenaListener> list : this.getListeners().values()) {
            list.removeIf(AListener -> AListener.getHandler() == listener);
        }
    }

    public Map<Class<? extends ArenaEvent>, List<RegisteredArenaListener>> getListeners() {
        return this.listeners;
    }

    public List<RegisteredArenaListener> getListenersOf(Class<? extends ArenaEvent> clazz) {
        return this.getListeners().getOrDefault(clazz, new ArrayList<>());
    }

    public <T extends ArenaEvent> void announce(T event) {
        if(!this.getListeners().containsKey(event.getClass())) {
            return;
        }

        for (ArenaEventPriority priority : ArenaEventPriority.values()) { // highest, high, normal, low, lowest...
            for(RegisteredArenaListener registeredArenaListener : this.getListenersOf(event.getClass())) {

                boolean isValidPriority = registeredArenaListener.getPriority() == priority;
                boolean hasCanceled = event instanceof Cancellable && ((Cancellable) event).isCancelled();
                boolean isCanceled = hasCanceled && !registeredArenaListener.isIgnoreCanceled();
                if(!isValidPriority || isCanceled) {
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
