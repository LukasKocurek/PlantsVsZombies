package sk.fri.uniza.pvz.gamelogic;

import sk.fri.uniza.pvz.entities.plants.Plant;
import sk.fri.uniza.pvz.entities.plants.projectile.Projectile;
import sk.fri.uniza.pvz.entities.zombies.Zombie;
import sk.fri.uniza.pvz.map.Map;
import sk.fri.uniza.pvz.player.SunCount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Trieda predstavuje hlavneho spravcu entit a projektilov, v tejto triede dochadza
 * k hlavnemu polymorfizmu
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public class EntityManager {
    private final List<Projectile> projectiles;
    private final List<Zombie> zombies;
    private final List<Plant> plants;
    private final Map map;

    private final SunCount sunCount;

    /**
     * EntityManager sa inicializuje
     * @param map dostane parameter na mapu
     * @param sunCount dostane parameter na pocet slniek
     */
    public EntityManager(Map map, SunCount sunCount) {
        this.map = map;
        this.projectiles = new ArrayList<>();
        this.zombies = new ArrayList<>();
        this.plants = new ArrayList<>();

        this.sunCount = sunCount;
    }

    /**
     * Vrati aktualnu instanciu mapy
     * @return instancia mapy
     */
    public Map getMap() {
        return this.map;
    }


    /**
     * Prida projektil do listu projektilov
     * @param p projektil, ktory sa prida do listu projektilov
     */
    public void addProjectile(Projectile p) {
        this.projectiles.add(p);
    }

    /**
     * Prida zombika do listu zombikov
     * @param z zombik, ktory sa prida do listu zombikov
     */
    public void addZombie(Zombie z) {
        this.zombies.add(z);
    }

    /**
     * Prida rastlinu do listu rastlin
     * @param p rastlina, ktora sa prida do listu rastlin
     */
    public void addPlant(Plant p) {
        this.plants.add(p);
    }

    /**
     * Vrati NEMODIFIKOVATELNU instanciu listu rastlin
     * @return NEMODIFIKOVATELNA instancia listu rastlin
     */
    public List<Plant> getPlants() {
        return Collections.unmodifiableList(this.plants);
    }

    /**
     * Vrati NEMODIFIKOVATELNU instanciu listu projektilov
     * @return NEMODIFIKOVATELNA instancia listu projektilov
     */
    public List<Projectile> getProjectiles() {
        return Collections.unmodifiableList(this.projectiles);
    }

    /**
     * Vrati NEMODIFIKOVATELNU instanciu listu zombikov
     * @return NEMODIFIKOVATELNA instancia listu zombikov
     */
    public List<Zombie> getZombies() {
        return Collections.unmodifiableList(this.zombies);
    }

    /**
     * Metoda, v ktorej sa prejdu vsetky listy a volaju si jednotlive metody
     * V tejto triede dochadza k hlavnemu !POLYMORFIZMU!
     * Rastliny si volaju metodu update, ktoru maju implementovanu vlastnym sposobom
     * Zombici si volaju metodu update, ktoru maju implementovanu vlastynm sposobom
     * Projektily si volaju metodu fly, ktoru maju naimplementovanu vlastnym sposobom
     */
    public void updateAll() {
        for (Plant plant : this.plants) {
            plant.update(this);
        }

        for (Zombie zombie : this.zombies) {
            zombie.update(this);
        }

        for (Projectile projectile : this.projectiles) {
            projectile.fly();
        }
    }

    private void removeDeadPlants() {
        for (Plant plant : this.plants) {
            if (plant.isDead()) {
                this.map.removePlant(plant.getRow(), plant.getCol());
            }
        }
        this.plants.removeIf(Plant::isDead);
    }

    private void removeDeadProjectiles() {
        List<Projectile> deadProjectiles = new ArrayList<>();
        for (Projectile projectile : this.projectiles) {
            if (projectile.isOutOfBounds() || projectile.hasHit()) {
                deadProjectiles.add(projectile);
            }
        }
        this.projectiles.removeAll(deadProjectiles);
    }

    private void removeDeadZombies() {
        List<Zombie> deadZombies = new ArrayList<>();
        for (Zombie z : this.zombies) {
            if (z.isDead()) {
                deadZombies.add(z);
                this.sunCount.rewardForZombieKill();
            }
        }
        this.zombies.removeAll(deadZombies);
    }

    /**
     * Odstrani vsetky mrtve/necinne prvky zo vsetkych listov
     */
    public void removeAllDead() {
        this.removeDeadPlants();
        this.removeDeadProjectiles();
        this.removeDeadZombies();
    }
}
