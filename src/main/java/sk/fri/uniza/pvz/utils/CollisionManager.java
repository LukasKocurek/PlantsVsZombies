package sk.fri.uniza.pvz.utils;

import javafx.scene.image.ImageView;
import sk.fri.uniza.pvz.entities.plants.Plant;
import sk.fri.uniza.pvz.entities.plants.projectile.Projectile;
import sk.fri.uniza.pvz.entities.zombies.Zombie;
import sk.fri.uniza.pvz.entities.zombies.ZombieState;
import sk.fri.uniza.pvz.gamelogic.EntityManager;
import java.util.List;

/**
 * Detekuje a spracovava kolizie medzi jednotlivymi hernymi objektami
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public class CollisionManager {

    /**
     * Spracuje vsetky kolizie pre aktualny snimok.
     * @param entityManager spravca hernych objektov, ktore sa maju navzajom porovnavat
     */
    public void checkCollisions(EntityManager entityManager) {
        List<Plant> plants = entityManager.getPlants();
        List<Zombie> zombies = entityManager.getZombies();
        List<Projectile> projectiles = entityManager.getProjectiles();

        this.checkZombiePlantCollision(plants, zombies);
        this.checkZombieProjectileCollision(projectiles, zombies);
    }

    private void checkZombieProjectileCollision(List<Projectile> projectiles, List<Zombie> zombies) {
        for (Projectile projectile : projectiles) {
            if (projectile.hasHit()) {
                continue;
            }
            for (Zombie zombie : zombies) {
                if (zombie.isDead()) {
                    continue;
                }
                if (isCollision(projectile.getSprite(), zombie.getSprite())) {
                    projectile.onHit(zombie);
                    break;
                }
            }
        }
    }

    private void checkZombiePlantCollision(List<Plant> plants, List<Zombie> zombies) {
        for (Zombie zombie : zombies) {
            if (zombie.getState() != ZombieState.WALKING) {
                continue;
            }
            for (Plant plant : plants) {
                if (plant.isDead()) {
                    continue;
                }
                if (isCollision(plant.getSprite(), zombie.getSprite()) && plant.getRow() == zombie.getRow()) {
                    zombie.startAttacking(plant);
                    break;
                }
            }
        }
    }

    /**
     * Podla metody intersects kniznice JavaFX, zisti, ci sa dva obrazky pretinaju
     * @param sprite1 prvy obrazok, ktory zistujeme ci sa pretina
     * @param sprite2 druhy obrazok, ktory zistujeme ci sa pretina
     * @return ak sa obrazky pretinaju, vrati true, inak vrati false
     */
    public static boolean isCollision(ImageView sprite1, ImageView sprite2) {
        return sprite1.intersects(sprite2.getBoundsInParent());
    }
}
