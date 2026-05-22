package sk.fri.uniza.pvz.entities.plants.types;

import sk.fri.uniza.pvz.entities.plants.Plant;
import sk.fri.uniza.pvz.entities.plants.PlantType;
import sk.fri.uniza.pvz.entities.plants.projectile.NormalPea;
import sk.fri.uniza.pvz.entities.plants.purpose.Shooter;
import sk.fri.uniza.pvz.gamelogic.EntityManager;
import sk.fri.uniza.pvz.utils.Constants;

/**
 * Predstavuje konkretneho potomka triedy Plant, od ktorej dedi
 * Implementuje rozhranie Shooter
 * Striela projektily, ktore ked trafia zombika, zombik dostane damage
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public class PeaShooter extends Plant implements Shooter {
    private static final int SHOOTING_COOLDOWN = Constants.countTime(1);
    private static final int PROJECTILE_SPEED = 5;
    private static final int PROJECTILE_OFFSET_X = 50;
    private static final int PROJECTILE_OFFSET_Y = 3;
    private int cooldownCounter;

    /**
     * Zavola konstruktor predka Plant a inicializuje sa podla neho
     */
    public PeaShooter() {
        super(PlantType.PEASHOOTER);
    }

    /**
     * Implementovana metoda z predka
     * Obsahuje cooldown, ked cooldown vyprsi, rastlina vystreli projektil
     * Nasledne sa cooldown zase obnovi na povodnu hodnotu
     * @param entityManager dostane referenciu na Entity Managera
     */
    @Override
    protected void onUpdate(EntityManager entityManager) {
        if (this.cooldownCounter > 0) {
            this.cooldownCounter--;
            return;
        }
        this.shoot(entityManager);
        this.cooldownCounter = SHOOTING_COOLDOWN;
    }

    /**
     * Prepisana metoda z predka, kde si rastlina zavola svoj vlastny multiplikator poskodenia, ktore dostane
     * @param damage damage, ktory rastlina dostane vynasobeni multiplikatorom damagu
     */
    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage * this.getTakenDamageMultiplier());
    }

    /**
     * Implementovana metoda z rozhrania Shooter, vytvori novu instanciu projektilu NormalPea
     * a prida ju do Entity Manageru
     * @param entityManager dostane ako parameter Entity Managera
     */
    @Override
    public void shoot(EntityManager entityManager) {
        entityManager.addProjectile(new NormalPea(this.getX() + PROJECTILE_OFFSET_X, this.getY() +
                PROJECTILE_OFFSET_Y, this.getType().getAbilityPower(),
                PROJECTILE_SPEED));
    }
}