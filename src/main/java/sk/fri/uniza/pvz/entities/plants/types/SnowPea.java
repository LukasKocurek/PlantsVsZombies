package sk.fri.uniza.pvz.entities.plants.types;

import sk.fri.uniza.pvz.entities.plants.Plant;
import sk.fri.uniza.pvz.entities.plants.PlantType;
import sk.fri.uniza.pvz.entities.plants.projectile.IcePea;
import sk.fri.uniza.pvz.entities.plants.purpose.Healer;
import sk.fri.uniza.pvz.entities.plants.purpose.Shooter;
import sk.fri.uniza.pvz.gamelogic.EntityManager;
import sk.fri.uniza.pvz.map.Map;
import sk.fri.uniza.pvz.utils.Constants;

/**
 * Predstavuje konkretneho potomka triedy Plant, od ktorej dedi
 * Implementuje rozhrania Shooter a Healer
 * Striela projektily, ktore ked trafia zombika, zombik dostane damage a spomali ho
 * Taktiez healuje rastliny, ktore sa nachadzaju na jeho ortogonalach
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public class SnowPea extends Plant implements Shooter, Healer {
    private static final int SHOOTING_COOLDOWN = Constants.countTime(4);
    private static final int HEALING_COOLDOWN = Constants.countTime(5);
    private static final int SLOW_DURATION = 2;
    private static final int PROJECTILE_SPEED = 8;
    private static final int PROJECTILE_OFFSET_X = 50;
    private static final int PROJECTILE_OFFSET_Y = 3;
    private int shootingCooldownCounter;
    private int healingCooldownCounter;

    /**
     * Zavola konstruktor predka Plant a inicializuje sa podla neho
     */
    public SnowPea() {
        super(PlantType.SNOW_PEA);
    }

    /**
     * Implementovana metoda z predka, ktora striela IcePea projektily a healuje
     * Ak dojde cooldown na strielanie, vystreli IcePea projektil a obnovi cooldown na povodnu hodnotu
     * Ak dojde cooldown na healovanie, healne rastliny na jej ortogonale, a cooldown sa obnovi na povodnu hodnotu
     * @param entityManager dostane referenciu na Entity Managera
     */
    @Override
    protected void onUpdate(EntityManager entityManager) {
        if (this.shootingCooldownCounter > 0) {
            this.shootingCooldownCounter--;
        } else {
            this.shoot(entityManager);
            this.shootingCooldownCounter = SHOOTING_COOLDOWN;
        }

        if (this.healingCooldownCounter > 0) {
            this.healingCooldownCounter--;
        } else {
            this.heal(entityManager.getMap());
            this.healingCooldownCounter = HEALING_COOLDOWN;
        }
    }

    /**
     * Implementovana metoda z interfacu Healer
     * Prejde vsetky rastliny v jej ortogonalnom okoli, a ak sa tam nachadza nejaka rastlina, healne ju
     * Sama seba nehealuje
     * @param map dostane ako parameter mapu
     */
    @Override
    public void heal(Map map) {
        int r = this.getRow();
        int c = this.getCol();
        int amount = this.getType().getAbilityPower();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 && j == 0 || i == 0 && j != 0) {
                    var neighbour = map.getPlant(r + i, c + j);
                    neighbour.ifPresent(plant -> plant.heal(amount));
                }
            }
        }
    }

    /**
     * Implementovana metoda z rozhrania Shooter, vytvori novu instanciu IcePea projektilu
     * a prida ju do Entity Manageru
     * @param entityManager dostane ako parameter Entity Managera
     */
    @Override
    public void shoot(EntityManager entityManager) {
        entityManager.addProjectile(new IcePea(this.getX() + PROJECTILE_OFFSET_X, this.getY() +
                PROJECTILE_OFFSET_Y, this.getType().getAbilityPower(),
                PROJECTILE_SPEED, Constants.countTime(SLOW_DURATION)));
    }

    /**
     * Prepisana metoda z predka, kde si rastlina zavola svoj vlastny multiplikator poskodenia, ktore dostane
     * @param damage damage, ktory rastlina dostane vynasobeni multiplikatorom damagu
     */
    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage * this.getTakenDamageMultiplier());
    }
}
