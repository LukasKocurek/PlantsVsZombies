package sk.fri.uniza.pvz.entities.zombies.types;

import sk.fri.uniza.pvz.entities.zombies.Zombie;
import sk.fri.uniza.pvz.entities.zombies.ZombieType;
import sk.fri.uniza.pvz.gamelogic.EntityManager;
import sk.fri.uniza.pvz.map.Tile;

import java.util.Random;

/**
 *  Predstavuje konkretneho potomka triedy Zombie, od ktorej dedi
 *  Tento zombik ma moznost spawnut sa na nahodnej v nahodnom stlpci
 *  Ostatne vlastnosti ma iste ako normalny zombik
 *  @author (Lukas Kocurek)
 *  @version (17.05.2026)
 */
public class ParachuteZombie extends Zombie {

    private static final int START_Y = -100;
    private static final double FALL_SPEED = 2;
    private static final Random RANDOM = new Random();
    private final double targetY;
    private boolean hasLanded;

    /**
     * Konstruktor inicializuje zombika, a priradi mu vsetky potrebne hodnoty
     * Nasledne si zombik vygeneruje nahodny stlpec, na ktorom sa spawne
     * Moze sa spawnut v akomkolvek stlpci, okrem prvych dvoch
     * @param row nastavime zombikovy riadok, na ktorom sa spawne
     * @param numberOfCols dame zombikovy vediet o poctu celkovych moznych riadkov
     */
    public ParachuteZombie(int row, int numberOfCols) {
        super(ZombieType.PARACHUTE, row);

        int col = 2 + RANDOM.nextInt(numberOfCols - 2);
        this.setX(col * Tile.getTileSize());

        this.targetY = this.getY();
        this.setY(START_Y);
        this.hasLanded = false;
    }

    /**
     * Prepisana metoda od predka, ak zombik este nespadol, bude padat
     * Zavola sa taktiez metoda z predka, co zabezpeci ze sa zombik bude spravat rovnako, ale bude mat
     * aj specialnu schopnost padu na nejake nahodne policko v lubovolnom stlpci
     * @param entityManager dostane referenciu na Entity Managera
     */
    @Override
    public void update(EntityManager entityManager) {
        if (!this.hasLanded) {
            this.fall();
        } else {
            super.update(entityManager);
        }
    }

    private void fall() {
        double newY = this.getY() + FALL_SPEED;
        if (newY >= this.targetY) {
            this.setY(this.targetY);
            this.hasLanded = true;
        } else {
            this.setY(newY);
        }
    }
}