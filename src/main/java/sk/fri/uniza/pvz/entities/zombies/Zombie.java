package sk.fri.uniza.pvz.entities.zombies;

import sk.fri.uniza.pvz.entities.Entity;
import sk.fri.uniza.pvz.entities.plants.Plant;
import sk.fri.uniza.pvz.gamelogic.EntityManager;
import sk.fri.uniza.pvz.map.Tile;
import sk.fri.uniza.pvz.utils.Constants;

/**
 * Predstavuje abstraktnu triedu zombika, ktoru dedia vsetci zombici
 * Dedi abstraktneho hlavneho predka Entity
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public abstract class Zombie extends Entity {
    private static final int SPAWN_X = Constants.getResolutionX();
    private static final int Y_OFFSET = 10;
    private static final double SLOW_AMPLIFIER = 1.2;

    private final ZombieType type;
    private final int row;
    private ZombieState state;
    private Plant target;
    private double attackCooldown;
    private int slowTicksRemaining;

    /**
     * Konstruktor inicializuje zombika, a priradi mu vsetky potrebne hodnoty
     * Taktiez zavola konstuktor predka, komocou ktoreho sa doinicializuje
     * @param type nastavime zombikovy jednotlivy typ
     * @param row nastavime zombikovy riadok, na ktorom sa spawne
     */
    protected Zombie(ZombieType type, int row) {
        super(type.getHp(), type.getImagePath());
        this.type = type;
        this.row = row;
        this.state = ZombieState.WALKING;
        this.target = null;
        this.attackCooldown = 0;
        this.slowTicksRemaining = 0;

        this.setX(SPAWN_X);
        this.setY(this.row * Tile.getTileSize() + Y_OFFSET);
    }

    /**
     * Implementovana abstraktna metoda z predka Entity
     * Ak zombik nema ziaden ciel, bude sa pohybovat
     * Ak zombik natrafi na nejaky ciel (rastlinu), zacne do nej utocit, na zaciatku zautoci instantne
     * @param entityManager dostane referenciu na Entity Managera
     */
    @Override
    public void update(EntityManager entityManager) {
        if (this.slowTicksRemaining > 0) {
            this.slowTicksRemaining--;
        }

        if (this.target != null && this.target.isDead()) {
            this.target = null;
            this.state = ZombieState.WALKING;
        }

        if (this.state == ZombieState.WALKING) {
            this.walk();
        } else if (this.state == ZombieState.ATTACKING) {
            this.attack();
        }
    }

    /**
     * Ak dojde cooldown, zautoci na rastlinu, pri ktorej stoji, nasledne sa cooldown znova obnovi
     * Uberie jej tolko zivotov, aky ma zombik nastaveny damage
     */
    protected void attack() {
        if (this.attackCooldown > 0) {
            this.attackCooldown--;
            return;
        }
        this.target.takeDamage(this.type.getDamage());
        this.attackCooldown = Constants.countTime(this.type.getAttackCooldown());
    }

    /**
     * Zabezpeci pohybovanie sa zombika
     * Taktiez riesti spomalenie, ak ho trafi IcePea, spomali sa mu rychlost
     * Je zabezpecene, ze rychlost NEMOZE klesnut pod 0.1
     */
    protected void walk() {
        double speed = this.type.getSpeed();
        if (this.slowTicksRemaining > 0) {
            speed = speed / SLOW_AMPLIFIER;
            if (speed < 0.1) {
                speed = 0.1;
            }
        }
        this.setX(this.getX() - speed);
    }

    /**
     * Dostane ako parameter rastlinu, do ktorej zacne utocit
     * @param plant rastlina, do ktorej zacne utocit
     */
    public void startAttacking(Plant plant) {
        this.target = plant;
        this.state = ZombieState.ATTACKING;
        this.attackCooldown = 0;
    }

    /**
     * Ak zombika opatovne hitne IcePea, obnovi sa mu spomalenie
     * @param duration cas trvania spomalenia
     */
    public void applySlow(int duration) {
        if (duration > this.slowTicksRemaining) {
            this.slowTicksRemaining = duration;
        }
    }

    /**
     * Vrati riadok, na ktorom sa zombik nachadza
     * @return riadok, na ktorom sa zombik nachadza typu int
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Vrati stav, v akom sa zombik nachadza (chodza/utocenie)
     * @return vrati stav v ktorom sa zombik nachadza typu ZombieState
     */
    public ZombieState getState() {
        return this.state;
    }
}
