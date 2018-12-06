package com.gmail.borlandlp.minigamesdtools.arena.commands;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultWatcherTest {
    private DefaultWatcher watcher;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        watcher = new DefaultWatcher();

        List<String[]> testBlacklistRules = new ArrayList<>();
        List<String[]> testWhitelistRules = new ArrayList<>();

        testBlacklistRules.add(new String[] {"bcommand1", "p3", "p4"});

        testBlacklistRules.add(new String[] {"bcommand2", "p1", "*"});

        testBlacklistRules.add(new String[] {"bcommand3"});

        testBlacklistRules.add(new String[] {"bcommand5", "*"});

        testWhitelistRules.add(new String[] {"bcommand5", "123"});
        testWhitelistRules.add(new String[] {"bcommand5", "999", "*"});

        watcher.setWhitelisted(testWhitelistRules);
        watcher.setBlacklisted(testBlacklistRules);
    }

    @org.junit.jupiter.api.Test
    void containsRuleSequence() {
        assertFalse(watcher.containsRuleSequence(watcher.getBlacklisted(), new String[] {"bcommand1", "p3", "p4", "p3"}));
        assertTrue(watcher.containsRuleSequence(watcher.getBlacklisted(), new String[] {"bcommand1", "p3", "p4"}));
        assertFalse(watcher.containsRuleSequence(watcher.getBlacklisted(), new String[] {"bcommand1", "p3"}));
        assertFalse(watcher.containsRuleSequence(watcher.getBlacklisted(), new String[] {"bcommand1"}));

        assertTrue(watcher.containsRuleSequence(watcher.getBlacklisted(), new String[] {"bcommand2", "p1", "p2", "p3"}));
    }

    @org.junit.jupiter.api.Test
    void isAllowedCommand() {
        // blacklist tests
        assertTrue(watcher.isAllowedCommand(new String[] {"bcommand1", "p3"}));
        assertFalse(watcher.isAllowedCommand(new String[] {"bcommand1", "p3", "p4"}));
        assertTrue(watcher.isAllowedCommand(new String[] {"bcommand1", "p3", "p4", "p5"}));

        assertFalse(watcher.isAllowedCommand(new String[] {"bcommand2", "p1", "p2", "p3"}));
        assertFalse(watcher.isAllowedCommand(new String[] {"bcommand2", "p1", "p2"}));
        assertTrue(watcher.isAllowedCommand(new String[] {"bcommand2", "p1"}));

        assertFalse(watcher.isAllowedCommand(new String[] {"bcommand3"}));

        assertTrue(watcher.isAllowedCommand(new String[] {"bcommand4"}));

        // whitelist tests
        assertTrue(watcher.isAllowedCommand(new String[] {"bcommand5"}));
        assertFalse(watcher.isAllowedCommand(new String[] {"bcommand5", "p1"}));
        assertTrue(watcher.isAllowedCommand(new String[] {"bcommand5", "123"}));
        assertFalse(watcher.isAllowedCommand(new String[] {"bcommand5", "123", "321"}));
        assertTrue(watcher.isAllowedCommand(new String[] {"bcommand5", "999", "testedAllowedValue"}));
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        watcher = null;
    }
}