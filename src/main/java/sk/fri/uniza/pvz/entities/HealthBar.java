package sk.fri.uniza.pvz.entities;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Predstavuje abstraktnu triedu healthbaru, ktory ma kazda entita
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public class HealthBar {
    private static final double WIDTH = 60;
    private static final double HEIGHT = 5;
    private static final double OFFSET_X = 0;
    private static final double OFFSET_Y = -5;
    private static final int BORDER_WIDTH = 2;

    private final Rectangle bar;

    /**
     * Inicializuje healthbar
     */
    public HealthBar() {
        this.bar = new Rectangle(WIDTH, HEIGHT, Color.LIMEGREEN);
        this.bar.setStroke(Color.BLACK);
        this.bar.setStrokeWidth(BORDER_WIDTH);
    }

    /**
     * Updatuje healtbar a meni mu farby podla toho, kolko zivotov ma entita
     * @param entityX x-ova suradnica entity
     * @param entityY y-ova suradnica entity
     * @param currentHealth aktualne zivoty entity
     * @param maxHealth maximalne zivoty entity
     */
    public void update(double entityX, double entityY, int currentHealth, int maxHealth) {
        double ratio = (double)currentHealth / maxHealth;

        this.bar.setWidth(WIDTH * ratio);
        this.bar.setX(entityX + OFFSET_X);
        this.bar.setY(entityY + OFFSET_Y);

        if (ratio > 0.5) {
            this.bar.setFill(Color.LIMEGREEN);
        } else if (ratio > 0.25) {
            this.bar.setFill(Color.YELLOW);
        } else {
            this.bar.setFill(Color.RED);
        }
    }

    /**
     * Vrati healthBar
     * @return vrati obdlznik ktory reprezentuje healthbar
     */
    public Rectangle getRectangle() {
        return this.bar;
    }
}
