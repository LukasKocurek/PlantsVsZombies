package sk.fri.uniza.pvz.renderers;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import sk.fri.uniza.pvz.entities.HealthBar;
import sk.fri.uniza.pvz.entities.plants.Plant;
import sk.fri.uniza.pvz.entities.plants.projectile.Projectile;
import sk.fri.uniza.pvz.entities.zombies.Zombie;
import sk.fri.uniza.pvz.gamelogic.EntityManager;

/**
 * Zobrazuje dynamicke objekty hry — rastliny, projektily a zombikov.
 * DynamicRenderer sa vola kazdy snimok a synchronizuje obsah sceny so
 * Stavom hry, pridava obrazky novych objektov a odstrani obrazky zaniknutych objektov.
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public class DynamicRenderer {
    private final Pane root;
    private final EntityManager entityManager;

    /**
     * Vytvori a inicializuju sa jednotlive hodnoty
     * @param root referencia na Pane
     * @param entityManager referencia na EntityManagera
     */
    public DynamicRenderer(Pane root, EntityManager entityManager) {
        this.root = root;
        this.entityManager = entityManager;
    }

    /**
     * Vyrenderuje sprity dynamickych objektov
     */
    public void render() {
        this.renderPlants();
        this.renderProjectiles();
        this.renderZombies();
    }

    private void renderPlants() {
        for (Plant plant : this.entityManager.getPlants()) {
            this.manage(plant.getSprite(), plant.isDead());
            this.renderHealthBar(plant.getHealthBar(), plant.isDead(), plant.getX(), plant.getY(),
                    plant.getCurrentHealth(), plant.getMaxHealth());
        }
    }

    private void renderProjectiles() {
        for (Projectile p : this.entityManager.getProjectiles()) {
            this.manage(p.getSprite(), p.hasHit() || p.isOutOfBounds());
        }
    }

    private void renderZombies() {
        for (Zombie z : this.entityManager.getZombies()) {
            this.manage(z.getSprite(), z.isDead());
            this.renderHealthBar(z.getHealthBar(), z.isDead(), z.getX(), z.getY(), z.getCurrentHealth(),
                    z.getMaxHealth());
        }
    }

    /**
     * Touto metodou som si POMOHOL StackOverflowom, !NIE JE 100% MOJA!
     * <a href="https://stackoverflow.com/questions/23044935/java-lang-illegalargumentexception-children-duplicate-children-added-parent">...</a>
     * @param sprite dostane referenciu na ImageView
     * @param shouldBeRemoved dostane do parametra, ci sa ma obrazok odstranit alebo nie
     */
    private void manage(ImageView sprite, boolean shouldBeRemoved) {
        if (shouldBeRemoved) {
            this.root.getChildren().remove(sprite);
        } else if (sprite.getParent() == null) {
            this.root.getChildren().add(sprite);
        }
    }

    private void renderHealthBar(HealthBar healthBar, boolean shouldBeRemoved, double x, double y,
                                 int currentHp, int maxHp) {
        Rectangle rect = healthBar.getRectangle();
        if (shouldBeRemoved) {
            this.root.getChildren().remove(rect);
        } else {
            if (rect.getParent() == null) {
                this.root.getChildren().add(rect);
            }
            healthBar.update(x, y, currentHp, maxHp);
        }
    }
}