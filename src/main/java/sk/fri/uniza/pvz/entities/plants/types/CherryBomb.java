package sk.fri.uniza.pvz.entities.plants.types;

import sk.fri.uniza.pvz.entities.plants.Plant;
import sk.fri.uniza.pvz.entities.plants.PlantType;
import sk.fri.uniza.pvz.entities.plants.purpose.MeleeAttacker;
import sk.fri.uniza.pvz.entities.zombies.Zombie;
import sk.fri.uniza.pvz.gamelogic.EntityManager;
import sk.fri.uniza.pvz.map.Tile;
import sk.fri.uniza.pvz.utils.CollisionManager;

/**
 * Predstavuje konkretneho potomka triedy Plant, od ktorej dedi
 * Implementuje rozhranie MeleeAttacker
 * Ked na nu stupi nejaky zombik, vybuchne v radiuse a da damage naokolo
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public class CherryBomb extends Plant implements MeleeAttacker {
    private static final int EXPLOSION_RADIUS = Tile.getTileSize();

    /**
     * Zavola konstruktor predka Plant a inicializuje sa podla neho
     */
    public CherryBomb() {
        super(PlantType.CHERRY_BOMB);
    }

    /**
     * Implementovana metoda z predka
     * Ak na rastlinu stupi zombik, vybuchne
     * @param entityManager dostane referenciu na Entity Managera
     */
    @Override
    protected void onUpdate(EntityManager entityManager) {
        for (Zombie zombie : entityManager.getZombies()) {
            if (this.touchesZombie(zombie)) {
                this.attack(entityManager);
                return;
            }
        }
    }

    private boolean touchesZombie(Zombie zombie) {
        return CollisionManager.isCollision(zombie.getSprite(), this.getSprite()) &&
                this.getRow() == zombie.getRow();
    }

    /**
     * Touto metodou som si pomohol internetom, NIE JE 100% moja!
     * Link: <a href="https://stackoverflow.com/questions/697188/fast-circle-collision-detection">...</a> (17.05.2025)
     * @param zombie referencia na zombika
     * @return vrati true/false, ak je zombik v radiuse
     */
    private boolean isInExplosionRadius(Zombie zombie) {
        double x = this.getX() - zombie.getX();
        double y = this.getY() - zombie.getY();
        return Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(EXPLOSION_RADIUS, 2);
    }

    /**
     * Prejde list zombikov, a ak je nejaky zombik v radiuse vybuchu, dostane poskodenie
     * @param entityManager dostane ako parameter Entity Managera
     */
    @Override
    public void attack(EntityManager entityManager) {
        int damage = this.getType().getAbilityPower();
        for (Zombie zombie : entityManager.getZombies()) {
            if (this.isInExplosionRadius(zombie)) {
                zombie.takeDamage(damage);
            }
        }
        this.takeDamage(this.getCurrentHealth());
    }
}

