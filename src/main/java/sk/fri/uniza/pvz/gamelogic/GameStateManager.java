package sk.fri.uniza.pvz.gamelogic;

import sk.fri.uniza.pvz.entities.zombies.Zombie;

/**
 * Predstavuje triedu, ktora sa stara o stav hry
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public class GameStateManager {
    private static final int HOUSE_X = 0;

    private final EntityManager entityManager;
    private final WaveManager waveManager;
    private GameState state;

    /**
     * Inicializuje sa a dostane potrebne parametre
     * @param entityManager dostane referenciu na EntityManagera
     * @param waveManager dostane referenciu na WaveManagera
     */
    public GameStateManager(EntityManager entityManager, WaveManager waveManager) {
        this.entityManager = entityManager;
        this.waveManager = waveManager;
        this.state = GameState.RUNNING;
    }

    /**
     * Neustale kontroluje, v akom stave sa hra nachadza
     * Ak sa zombik dostal za akekolvek policko v poslednom stlpci, hra je prehrata
     * Ak dojde cas, a zabijete vsetkych zombikov, hra je vyhrata
     */
    public void update() {
        if (this.state != GameState.RUNNING) {
            return;
        }

        if (this.zombieReachedHouse()) {
            this.state = GameState.LOST;
            return;
        }

        if (this.waveManager.isFinished() && this.entityManager.getZombies().isEmpty()) {
            this.state = GameState.WON;
        }
    }

    private boolean zombieReachedHouse() {
        for (Zombie zombie : this.entityManager.getZombies()) {
            if (zombie.getX() <= HOUSE_X) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vrati stav hry
     * @return stav hry typu GameState
     */
    public GameState getState() {
        return this.state;
    }

    /**
     * Kontroluje ci hra stale bezi
     * @return vrati true, ak hra bezi, false, ak uz nebezi
     */
    public boolean isRunning() {
        return this.state == GameState.RUNNING;
    }
}
