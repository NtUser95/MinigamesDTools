package com.gmail.borlandlp.minigamesdtools.util;

public class TimeUtils {

    public static String seconds2TimeF(int secs) {
        int minutes = (secs / 60);
        int seconds = secs - (minutes * 60);

        return minutes > 0 ? minutes + " мин., " + seconds + " сек." : seconds + " секунд(ы)";
    }

}
