package sk.fri.uniza.pvz.entities.plants.projectile;

import javafx.scene.image.ImageView;
import sk.fri.uniza.pvz.entities.zombies.Zombie;
import sk.fri.uniza.pvz.utils.Constants;
import sk.fri.uniza.pvz.utils.ImageLoader;

/**
 * Abstraktna trieda, z ktorej dedia vsetky projektily
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public abstract class Projectile {
    private double x;
    private final ImageView sprite;
    private final int damage;
    private final int speed;
    private boolean hasHit;

    /**
     * Konstruktor sa zabezpeci o nastavenie vsetkych potrebnych hodnot projektilu
     * @param path dostane cestu k obrazku projektilu
     * @param damage dostane damage, ktory uberie zombikovi pri trafeni
     * @param speed urcuje rychlost, akou sa projektil bude pohybovat
     * @param startX dostane zaciatocnu x-ovu startovaciu poziciu
     * @param startY dostane zaciatocnu y-ovu startovaciu poziciu
     */
    protected Projectile(String path, int damage, int speed, double startX, double startY) {
        this.sprite = ImageLoader.getImageView(path);
        this.damage = damage;
        this.speed = speed;
        this.x = startX;
        this.sprite.setX(startX);
        this.sprite.setY(startY);
        this.hasHit = false;
    }

    /**
     * Abstraktna metoda, ktoru si jednotlivi potomkovia implementuju svojim sposobom
     * @param zombie dostane do parametra referenciu na zombika, na ktoreho projektil zareaguje
     */
    public abstract void onHit(Zombie zombie);

    /**
     * Zabezpeci, ze projektil bude letiet
     */
    public void fly() {
        this.x += this.speed;
        this.sprite.setX(this.x);
    }

    /**
     * Skontroluje, ci je projektil mimo obrazovky
     * @return vrati, ci je alebo nie je projektil mimo obrazovky
     */
    public boolean isOutOfBounds() {
        return this.x >= Constants.getResolutionX();
    }

    /**
     * Vrati aktualnu x-ovu poziciu projektilu
     * @return vrati v aktualnu x-ovu poziciu projektilu v type double
     */
    public double getX() {
        return this.x;
    }

    /**
     * Vrati damage projektilu
     * @return Vrati damage projektilu v type int
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * Vrati obrazok projektilu
     * @return Vrati obrazok projektilu v type ImageView
     */
    public ImageView getSprite() {
        return this.sprite;
    }

    /**
     * Vrati, ci projektil trafil zombika
     * @return Vrati true alebo false na zaklade toho, ci projektil hitnul zombika
     */
    public boolean hasHit() {
        return this.hasHit;
    }

    /**
     * Nastavi sa true, ked projektil trafil zombika
     */
    public void markHit() {
        this.hasHit = true;
    }
}
