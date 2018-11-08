package com.gmail.borlandlp.minigamesdtools;

import com.gmail.borlandlp.minigamesdtools.config.ConfigEntity;
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath;
import org.fusesource.jansi.Ansi;

import java.util.logging.Level;

public class Debug {
    private static boolean enabled = true;
    private static INT_LEVEL curDebugLevel = INT_LEVEL.ALL;

    public static void init() {
        try {
            ConfigEntity conf = MinigamesDTools.getInstance().getConfigProvider().getEntity(ConfigPath.MAIN, "minigamesdtools");
            Debug.enabled = Boolean.parseBoolean(conf.getData().get("debug").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void print(LEVEL reqDebugLevel, String msg) {
        if(enabled && isValidLevel(reqDebugLevel)) {
            StringBuilder str = new StringBuilder();
            str.append(Ansi.ansi().fg(Ansi.Color.BLUE).boldOff().toString());
            str.append("DEBUG[Level->");
            if(reqDebugLevel.equals(LEVEL.WARNING)) {
                str.append(Ansi.ansi().fg(Ansi.Color.RED).boldOff().toString());
            } else {
                str.append(Ansi.ansi().fg(Ansi.Color.GREEN).boldOff().toString());
            }
            str.append(reqDebugLevel.toString());
            str.append(Ansi.ansi().fg(Ansi.Color.BLUE).boldOff().toString());
            str.append("]");
            str.append(Ansi.ansi().fg(Ansi.Color.WHITE).boldOff().toString());
            str.append(":");
            str.append(msg);
            str.append(Ansi.ansi().fg(Ansi.Color.WHITE).boldOff().toString());
            MinigamesDTools.getInstance().getLogger().log(Level.INFO, str.toString());
        }
    }

    private static boolean isValidLevel(LEVEL reqDebugLevel) {
        if(Debug.curDebugLevel == INT_LEVEL.ALL) {
            return true;
        } else if(Debug.curDebugLevel == INT_LEVEL.WARNING && reqDebugLevel == LEVEL.WARNING) {
            return true;
        } else return Debug.curDebugLevel == INT_LEVEL.NOTICE && reqDebugLevel == LEVEL.NOTICE;
    }

    private enum INT_LEVEL {
        ALL,
        WARNING,
        NOTICE,
        NONE
    }

    public enum LEVEL {
        WARNING,
        NOTICE,
    }
}
