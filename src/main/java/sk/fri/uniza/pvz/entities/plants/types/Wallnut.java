package sk.fri.uniza.pvz.entities.plants.types;

import sk.fri.uniza.pvz.entities.plants.Plant;
import sk.fri.uniza.pvz.entities.plants.PlantType;
import sk.fri.uniza.pvz.entities.plants.purpose.MeleeAttacker;
import sk.fri.uniza.pvz.entities.zombies.Zombie;
import sk.fri.uniza.pvz.gamelogic.EntityManager;
import sk.fri.uniza.pvz.utils.CollisionManager;
import sk.fri.uniza.pvz.utils.Constants;

/**
 * Predstavuje konkretneho potomka triedy Plant, od ktorej dedi
 * Implementuje rozhranie MeleeAttacker
 * Utoci na blizko s velmi nizkym poskodenim, avsak s velkym poctom zivotov
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public class Wallnut extends Plant implements MeleeAttacker {
    private static final int ATTACK_COOLDOWN = Constants.countTime(3);
    private int attackCooldown;

    /**
     * Zavola konstruktor predka Plant a inicializuje sa podla neho
     */
    public Wallnut() {
        super(PlantType.WALLNUT);
    }

    /**
     * Implementovana metoda z predka
     * Obsahuje cooldown, ked cooldown vyprsi, rastlina zautoci na zombika
     * Nasledne sa cooldown zase obnovi na povodnu hodnotu
     * @param entityManager dostane referenciu na Entity Managea
     */
    @Override
    protected void onUpdate(EntityManager entityManager) {
        if (this.attackCooldown > 0) {
            this.attackCooldown--;
            return;
        }
        this.attack(entityManager);
        this.attackCooldown = ATTACK_COOLDOWN;
    }

    /**
     * Implementovana metoda z interfacu MeleeAttacker
     * Ak sa najde zombik, ktory sa dotkol rastliny, rastlina zautoci a da zombikovi damage
     * @param entityManager dostane ako parameter Entity Managera
     */
    @Override
    public void attack(EntityManager entityManager) {
        for (Zombie zombie : entityManager.getZombies()) {
            if (!this.touchesZombie(zombie)) {
                continue;
            }
            zombie.takeDamage(this.getType().getAbilityPower());
        }
    }

    private boolean touchesZombie(Zombie zombie) {
        return CollisionManager.isCollision(zombie.getSprite(), this.getSprite()) &&
                this.getRow() == zombie.getRow();
    }
}
