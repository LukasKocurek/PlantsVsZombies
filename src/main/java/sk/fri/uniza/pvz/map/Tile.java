package sk.fri.uniza.pvz.map;

import javafx.scene.image.ImageView;
import sk.fri.uniza.pvz.entities.plants.Plant;
import sk.fri.uniza.pvz.utils.ImageLoader;

/**
 * Trieda predstavuje jedno policko mapy, na ktore sa da polozit rastlina
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public class Tile {
    private static final int TILE_SIZE = 100;
    private final double x;
    private final double y;
    private final ImageView sprite;
    private Plant plant;

    /**
     * Inicizalizuje si pociatocny stav a vsetky potrebne hodnoty
     * @param x x-ova suradnica policka
     * @param y y-ova suradnica policka
     */
    public Tile(double x, double y) {
        this.x = x;
        this.y = y;

        this.sprite = ImageLoader.getImageView("/map/tile.png");
        this.sprite.setX(x);
        this.sprite.setY(y);
    }

    /**
     * Vrati, ci je policko nie je obsadene rastlinou
     * @return ak nie je policko obsadene rastlinou, vrati true, inak vrati false
     */
    public boolean isEmpty() {
        return this.plant == null;
    }

    /**
     * Pokusi sa polozit rastlinu na policko, ak je policko prazdne, rastlina sa nan polozi
     * Ak policko nie je prazdne, rastlina sa nepolozi
     * @param plant rastlina, ktoru sa pokusame polozit na policko
     * @return vrati true, ak sa rastlina podarila polozit na policko, inak vrati false
     */
    public boolean placePlant(Plant plant) {
        if (this.isEmpty()) {
            this.plant = plant;
            return true;
        }
        return false;
    }

    /**
     * Vrati rastlinu, ktora sa nachadza na policku, inak vrati null
     * @return rastlina, ak sa na policku nejaka nachadza, inak vrati null
     */
    public Plant getPlant() {
        return this.plant;
    }

    /**
     * Vrati obrazok, ktory obsahuje policko
     * @return obrazok policka typu ImageView
     */
    public ImageView getSprite() {
        return this.sprite;
    }

    /**
     * Vrati x-ovu poziciu policka
     * @return x-ova pozicia policka typu int
     */
    public double getX() {
        return this.x;
    }

    /**
     * Vrati y-ovu poziciu polcika
     * @return y-ova pozicia policka typu int
     */
    public double getY() {
        return this.y;
    }

    /**
     * Vrati velkost obrazku policka
     * @return velkost obrazku policka typu int
     */
    public static int getTileSize() {
        return TILE_SIZE;
    }

    /**
     * Odstrani Rastlinu z policka
     */
    public void removePlant() {
        this.plant = null;
    }
}
