package com.gmail.borlandlp.minigamesdtools.arena;

import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaEventHandler;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaEventPriority;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerDamagedLocalEvent;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerJoinLocalEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventAnnouncerTest {
    @Test
    void announce() {
        assertTrue(defaultTest());
        assertTrue(canceledEventsTest());
        assertTrue(priorityTest());
    }

    /**
     * @return true if the code works
     */
    private boolean defaultTest() {
        EventAnnouncer eventAnnouncer = new EventAnnouncer();
        DefaultTestListener listener = new DefaultTestListener();
        eventAnnouncer.register(listener);
        eventAnnouncer.announce(new ArenaPlayerJoinLocalEvent(null, null));

        return listener.eventReceived;
    }

    /**
     * @return true if testing meets the conditions for normal component operation.
     */
    private boolean canceledEventsTest() {
        TestListenerCanceled listener1 = new TestListenerCanceled();
        TestListenerCanceled2 listener2 = new TestListenerCanceled2();

        EventAnnouncer eventAnnouncer = new EventAnnouncer();
        eventAnnouncer.register(listener1);
        eventAnnouncer.register(listener2);

        ArenaPlayerDamagedLocalEvent event = new ArenaPlayerDamagedLocalEvent(null, 0);
        event.setCancelled(true);

        eventAnnouncer.announce(event);

        return !listener1.eventReceived && listener2.eventReceived;
    }

    /**
     * @return true if testing meets the conditions for normal component operation.
     */
    private boolean priorityTest() {
        PriorityTestListenerH listenerHighest = new PriorityTestListenerH();
        PriorityTestListenerL listenerLowest = new PriorityTestListenerL();
        PriorityTestListenerN listenerNormal = new PriorityTestListenerN();

        EventAnnouncer eventAnnouncer = new EventAnnouncer();
        eventAnnouncer.register(listenerHighest);
        eventAnnouncer.register(listenerLowest);
        eventAnnouncer.register(listenerNormal);

        eventAnnouncer.announce(new ArenaPlayerDamagedLocalEvent(null, 0));

        boolean cond1 = listenerHighest.timeReceived < listenerNormal.timeReceived;
        boolean cond2 = listenerNormal.timeReceived < listenerLowest.timeReceived;

        return cond1 && cond2;
    }


    // default test
    private class DefaultTestListener implements ArenaEventListener {
        public boolean eventReceived;

        @ArenaEventHandler
        public void onEvent(ArenaPlayerJoinLocalEvent event) {
            this.eventReceived = true;
        }
    }

    // canceled event classes
    private class TestListenerCanceled implements ArenaEventListener {
        public boolean eventReceived;

        @ArenaEventHandler(ignoreCancelled = false)
        public void onEvent(ArenaPlayerDamagedLocalEvent event) {
            this.eventReceived = true;
        }
    }

    private class TestListenerCanceled2 implements ArenaEventListener {
        public boolean eventReceived;

        @ArenaEventHandler(ignoreCancelled = true)
        public void onEvent(ArenaPlayerDamagedLocalEvent event) {
            this.eventReceived = true;
        }
    }

    // priority test classes
    private class PriorityTestListenerH implements ArenaEventListener {
        public long timeReceived;

        @ArenaEventHandler(priority = ArenaEventPriority.HIGHEST)
        public void onEvent(ArenaPlayerDamagedLocalEvent event) {
            this.timeReceived = System.nanoTime();
        }
    }

    private class PriorityTestListenerL implements ArenaEventListener {
        public long timeReceived;

        @ArenaEventHandler(priority = ArenaEventPriority.LOWEST)
        public void onEvent(ArenaPlayerDamagedLocalEvent event) {
            this.timeReceived = System.nanoTime();
        }
    }

    private class PriorityTestListenerN implements ArenaEventListener {
        public long timeReceived;

        @ArenaEventHandler(priority = ArenaEventPriority.NORMAL)
        public void onEvent(ArenaPlayerDamagedLocalEvent event) {
            this.timeReceived = System.nanoTime();
        }
    }
}