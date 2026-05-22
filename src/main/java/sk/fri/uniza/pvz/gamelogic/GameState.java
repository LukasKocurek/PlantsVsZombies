package sk.fri.uniza.pvz.gamelogic;

/**
 * Enum, ktory predstavuje, v akom stave sa momentalne hra nachadza
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public enum GameState {
    /**
     * Znaci, ze hra bezi
     */
    RUNNING,

    /**
     * Znaci, ze hra je vyhrata
     */
    WON,

    /**
     * Znaci, ze hra je prehrata
     */
    LOST;
}
