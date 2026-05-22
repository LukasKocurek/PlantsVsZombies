package sk.fri.uniza.pvz.entities.zombies.types;

import sk.fri.uniza.pvz.entities.zombies.Zombie;
import sk.fri.uniza.pvz.entities.zombies.ZombieType;

/**
 *  Predstavuje konkretneho potomka triedy Zombie, od ktorej dedi
 *  Je to zakladny (normalny zombik), cize sa sprava rovnako ako jeho abstraktny predok
 *  @author (Lukas Kocurek)
 *  @version (17.05.2026)
 */
public class NormalZombie extends Zombie {
    /**
     * Konstruktor inicializuje zombika, a priradi mu vsetky potrebne hodnoty
     * @param row nastavime zombikovy riadok, na ktorom sa spawne
     */
    public NormalZombie(int row) {
        super(ZombieType.NORMAL, row);
    }
}
