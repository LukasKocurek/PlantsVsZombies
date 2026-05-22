package sk.fri.uniza.pvz.player;

import sk.fri.uniza.pvz.exceptions.NotEnoughSunException;
import sk.fri.uniza.pvz.utils.Constants;

/**
 * Spravuje pocet slniecok, ktore hrac ma k dispozicii na nakup rastlin.
 * Hrac ziskava slniecka dvoma sposobmi a to tak, ze bud zabije zombika
 * Alebo, kazdu nastavenu hodnotu, kolko slniecok sa ma vygenerovat za urcity cas
 * Sa mu vygeneruje 1 slniecko
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public class SunCount {
    private static final int PASSIVE_GAIN_TIME = Constants.countTime(1);
    private static final int PASSIVE_GAIN_AMOUNT = 1;
    private static final int ZOMBIE_KILL_REWARD = 5;
    private static final int STARTING_SUN = 0;

    private int current;
    private int passiveCooldown;

    /**
     * Inicializuje pociatocny stav slniecok a nastavi si cooldown
     * Ktory urcuje, ako casto sa slniecka budu generovat
     */
    public SunCount() {
        this.current = STARTING_SUN;
        this.passiveCooldown = PASSIVE_GAIN_TIME;
    }

    /**
     * Aktualizuje prirastok slnka za urcity cas
     */
    public void update() {
        this.passiveCooldown--;
        if (this.passiveCooldown <= 0) {
            this.current += PASSIVE_GAIN_AMOUNT;
            this.passiveCooldown = PASSIVE_GAIN_TIME;
        }
    }

    /**
     * Pripocita k pocte slniecok pocet za zabitie zombika
     */
    public void rewardForZombieKill() {
        this.current += ZOMBIE_KILL_REWARD;
    }

    /**
     * Kontroluje, ca moze dovolit kupit urcita rastlina za pocet sliecok, ktorym disponujeme
     * @param cost cena za urcitu rastlinu
     * @return Vrati true, ak si mozeme dovolit kupit slniecko, inak vrati false
     */
    public boolean canAfford(int cost) {
        return this.current >= cost;
    }

    /**
     * Pokusi sa utratit pocet slniecok na kupenie rastliny
     * @param cost cena rastliny
     * @throws NotEnoughSunException ak nemame dostatocny pocet slniecok, vyhodi sa vynimka
     */
    public void spend(int cost) {
        if (!this.canAfford(cost)) {
            throw new NotEnoughSunException();
        }
        this.current -= cost;
    }

    /**
     * Vrati aktualny pocet slniecok, ktorym prave disponujeme
     * @return aktualny pocet slniecok, ktorym disponujeme
     */
    public int getCurrent() {
        return this.current;
    }
}