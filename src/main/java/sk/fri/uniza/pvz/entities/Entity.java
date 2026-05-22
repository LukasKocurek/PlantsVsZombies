package sk.fri.uniza.pvz.entities;

import javafx.scene.image.ImageView;
import sk.fri.uniza.pvz.gamelogic.EntityManager;
import sk.fri.uniza.pvz.utils.ImageLoader;

/**
 * Predstavuje hlavneho potomka, z ktoreho dedi abstraktna trieda Plant a abstraktna trieda Zombie
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public abstract class Entity {
    private final int maxHealth;
    private int health;
    private final ImageView sprite;
    private final HealthBar healthBar;
    private boolean alive;

    /**
     * Inicializuje si potrebne hodnoty, na zaciatku je vzdy nazive
     * @param maxHealth maximalny pocet zivotov
     * @param imagePath cesta k obrazku
     */
    public Entity(int maxHealth, String imagePath) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.sprite = ImageLoader.getImageView(imagePath);
        this.healthBar = new HealthBar();
        this.alive = true;
    }

    /**
     * Abstraktna metoda, ktoru si implementuju/prepisuju potomkovia triedy Entity svojim vlastnym sposobom
     * @param entityManager dostane referenciu na Entity Managera
     */
    public abstract void update(EntityManager entityManager);

    /**
     * Velkost poskodenia, ktore entita dostane, ubere sa jej hp v zavislosti od tohto poskodenia
     * Ak entite klesnu zivoty pod 0, zomrie
     * @param damage velkost poskodenia, ktore entita dostane
     */
    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
            this.alive = false;
        }
    }

    /**
     * Nastavi entite zivot
     * Nesmie prekrocit jej maximalny pocet zivotov
     * Ak klesne pod 0, nastavia sa jej zivoty na 0, cize zomrie
     * @param health pocet healtpointov, ktore sa entite nastavia
     */
    protected void setHealth(int health) {
        if (health < 0) {
            health = 0;
        }
        if (health > this.maxHealth) {
            health = this.maxHealth;
        }
        this.health = health;
    }

    /**
     * Vrati pocet maximalnych zivotov, ktore entita ma
     * @return pocet maximalnych zivotov, ktore entita ma typu int
     */
    public int getMaxHealth() {
        return this.maxHealth;
    }

    /**
     * Vrati pocet aktualnych zivotov, ktore entita ma
     * @return pocet aktualnych zivotov, ktore entita ma typu int
     */
    public int getCurrentHealth() {
        return this.health;
    }

    /**
     * Vrati obrazok entity
     * @return obrazok entity typu ImageView
     */
    public ImageView getSprite() {
        return this.sprite;
    }

    /**
     * Vrati healthbar entity
     * @return healthbar entity typu HealtBar
     */
    public HealthBar getHealthBar() {
        return this.healthBar;
    }

    /**
     * Zisti, ci je entita mrtva
     * @return ak je entita mrtva, vrati true, inak vrati false
     */
    public boolean isDead() {
        return !this.alive;
    }

    /**
     * Vrati x-ovu suradnicu, na ktorej sa nachadza entita
     * @return x-ova suradnica typu int
     */
    public double getX() {
        return this.sprite.getX();
    }

    /**
     * Vrati y-ovu suradnicu, na ktorej sa nachadza entita
     * @return y-ova suradnica typu int
     */
    public double getY() {
        return this.sprite.getY();
    }

    /**
     * Nastavi x-ovu suradnicu entite
     * @param x x-ova suradnica, ktora sa ma nastavit entite
     */
    public void setX(double x) {
        this.sprite.setX(x);
    }

    /**
     * Nastavi y-ovu suradnicu entite
     * @param y y-ova suradnica, ktora sa ma nastavit entite
     */
    public void setY(double y) {
        this.sprite.setY(y);
    }
}
