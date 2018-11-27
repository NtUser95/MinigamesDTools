package com.gmail.borlandlp.minigamesdtools.gui.hotbar.utils;

public class Leveling {
    public static ExperienceContainer calculateWithPercentage(int level, float percent) {
        double maxExp;
        if (level <= 0) {
            maxExp = 0;
        } else if(level <= 16) {
            maxExp = Math.pow(level, 2) + (6 * level);
        } else if(level <= 31) {
            maxExp = (2.5 * (Math.pow(level, 2))) - (40.5 * level) + 360;
        } else { // 32+
            maxExp = (4.5 * (Math.pow(level, 2))) - (162.5 * level) + 2220;
        }

        double percent_exp = maxExp * (percent / 100D);

        return new ExperienceContainer(maxExp, percent_exp, level);
    }

    public static class ExperienceContainer {
        public ExperienceContainer(double t_exp, double p_exp, int l) {
            this.total_exp = t_exp;
            this.percent_exp = p_exp;
            this.level = l;
        }

        public double total_exp;
        public double percent_exp;
        public int level;
    }
}
