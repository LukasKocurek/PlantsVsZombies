package sk.fri.uniza.pvz.gamelogic;

import sk.fri.uniza.pvz.entities.zombies.Zombie;
import sk.fri.uniza.pvz.entities.zombies.types.NormalZombie;
import sk.fri.uniza.pvz.entities.zombies.types.ParachuteZombie;
import sk.fri.uniza.pvz.utils.Constants;

import java.util.Random;

/**
 * Predstavuje hlavnu triedu, ktora sa stara o spawnovanie zombikov
 * Nachadzaju sa tu 3 fazy
 * Prve fazy sa zombici spawnuju pomalsie, velmi velku sancu na spawn ma normalny zombik
 * Neskorsie fazy sa zombici spawnuju rychlesjie, cim neskoria faza, tym ma normalny zombik nizsiu
 * sancu sa spawnut
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public class WaveManager {
    private static final int GAME_DURATION = Constants.countTime(90);
    private static final int PHASE_DURATION = Constants.countTime(30);

    private static final int PHASE_0_SPAWN_INTERVAL = Constants.countTime(10);
    private static final int PHASE_1_SPAWN_INTERVAL = Constants.countTime(7);
    private static final int PHASE_2_SPAWN_INTERVAL = Constants.countTime(4);

    private static final double PHASE_0_NORMAL_PROBABILITY = 0.80;
    private static final double PHASE_1_NORMAL_PROBABILITY = 0.70;
    private static final double PHASE_2_NORMAL_PROBABILITY = 0.60;

    private static final Random RANDOM = new Random();
    private final EntityManager entityManager;
    private final int numberOfRows;
    private final int numberOfCols;
    private final int numberOfWaves;
    private int elapsedTicks = 0;
    private int spawnCooldown;

    /**
     * Inicializuje sa a nastavi si vsetky potrebne hodnoty
     * @param em dostane referenciu na EntityManagera
     * @param numberOfRows dostane pocet riadkov
     * @param numberOfCols dostane pocet stlpcov
     */
    public WaveManager(EntityManager em, int numberOfRows, int numberOfCols) {
        this.entityManager = em;
        this.numberOfRows = numberOfRows;
        this.numberOfCols = numberOfCols;
        this.spawnCooldown = PHASE_0_SPAWN_INTERVAL;

        this.numberOfWaves = 3;
    }

    /**
     * Ak pocet tikov uz je vacsi ako trvanie hry, metoda sa prestane vykonavat
     * Inak sa zacnu spawnovat zombici podla daneho intervalu
     */
    public void update() {
        if (this.elapsedTicks >= GAME_DURATION) {
            return;
        }
        this.elapsedTicks++;

        if (this.spawnCooldown > 0) {
            this.spawnCooldown--;
            return;
        }
        this.spawnRandomZombie();
        this.spawnCooldown = this.getCurrentSpawnInterval();
    }

    private int getCurrentSpawnInterval() {
        int phase = this.currentPhase();
        if (phase == 0) {
            return PHASE_0_SPAWN_INTERVAL;
        } else if (phase == 1) {
            return PHASE_1_SPAWN_INTERVAL;
        } else {
            return PHASE_2_SPAWN_INTERVAL;
        }
    }

    private double getCurrentNormalProbability() {
        int phase = this.currentPhase();
        if (phase == 0) {
            return PHASE_0_NORMAL_PROBABILITY;
        } else if (phase == 1) {
            return PHASE_1_NORMAL_PROBABILITY;
        } else {
            return PHASE_2_NORMAL_PROBABILITY;
        }
    }

    private int currentPhase() {
        int phase = this.elapsedTicks / PHASE_DURATION;
        if (phase > 2) {
            phase = 2;
        }
        return phase;
    }

    private void spawnRandomZombie() {
        int row = RANDOM.nextInt(this.numberOfRows);
        Zombie zombie;
        if (RANDOM.nextDouble() < this.getCurrentNormalProbability()) {
            zombie = new NormalZombie(row);
        } else {
            zombie = new ParachuteZombie(row, this.numberOfCols);
        }
        this.entityManager.addZombie(zombie);
    }

    /**
     * Vrati pocet zostavajucich sekund do konca hry
     * @return pocet sekund typu int
     */
    public int getRemainingSeconds() {
        int remaining = (GAME_DURATION - this.elapsedTicks) / Constants.countTime(1);
        if (remaining < 0) {
            return 0;
        }
        return remaining;
    }

    /**
     * Vrati aktualnu vlnu zombikov
     * @return aktualna vlna zombikov reprezentovana cislom typu int
     */
    public int getCurrentWave() {
        return this.currentPhase() + 1;
    }

    /**
     * Vrati, ci je hra ukoncena
     * @return true ak je hra ukoncena, inak vrati false
     */
    public boolean isFinished() {
        return this.elapsedTicks >= GAME_DURATION;
    }

    /**
     * Vrati pocet vln, ktore sa vykonaju pocas celej hry
     * @return pocet vln reprezentovany cislom typu int
     */
    public int getNumberOfWaves() {
        return this.numberOfWaves;
    }
}