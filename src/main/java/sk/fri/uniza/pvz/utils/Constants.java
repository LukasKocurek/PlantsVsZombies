package sk.fri.uniza.pvz.utils;

/**
 * Predstavuje triedu, ktora obsahuje konstanty, ako napriklad FPS, rozlisenieX, rozlisenieY obrazovky
 */
public class Constants {
    private static final int FPS = 60;
    private static final int RESOLUTION_X = 900;
    private static final int RESOLUTION_Y = 600;

    /**
     * Prevedie cas v sekundach na pocet snimkov pri aktualnom FPS.
     * @param time cas v sekundach
     * @return pocet snimkov zodpovedajucich danemu casu
     */
    public static int countTime(int time) {
        time *= FPS;
        return time;
    }

    /**
     * @return vrati horizontalne rozlisenie obrazoky typu int
     */
    public static int getResolutionX() {
        return RESOLUTION_X;
    }

    /**
     * @return vrati vertikalne rozlisenie obrazovky typu int
     */
    public static int getResolutionY() {
        return RESOLUTION_Y;
    }

    /**
     * @return pocet FPS typu int
     */
    public static int getFps() {
        return FPS;
    }
}
