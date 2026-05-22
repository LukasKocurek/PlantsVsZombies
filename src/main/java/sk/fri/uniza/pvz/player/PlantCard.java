package sk.fri.uniza.pvz.player;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sk.fri.uniza.pvz.entities.plants.Plant;
import sk.fri.uniza.pvz.entities.plants.PlantType;
import sk.fri.uniza.pvz.entities.plants.types.CherryBomb;
import sk.fri.uniza.pvz.entities.plants.types.PeaShooter;
import sk.fri.uniza.pvz.entities.plants.types.SnowPea;
import sk.fri.uniza.pvz.entities.plants.types.SunFlower;
import sk.fri.uniza.pvz.entities.plants.types.Wallnut;
import sk.fri.uniza.pvz.utils.ImageLoader;

/**
 * Reprezentuje kartu rastliny v inventari hraca. Kazda karta zodpoveda
 * jednemu typu rastliny (PlantType) a obsahuje:
 * Obrazok karty zobrazeny v inventari
 * Cervenu ohranicenie pri vyberani karty
 * Karta vie vytvorit novu instanciu prislusnej rastliny pri jej pouziti.
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public class PlantCard {
    private static final double CARD_WIDTH = 80;
    private static final double CARD_HEIGHT = 80;

    private final PlantType type;
    private final ImageView icon;
    private final Rectangle border;
    private boolean selected;

    /**
     * Vytvori novu kartu pre zadany typ rastliny. Inicializuje obrazok karty
     * a vizualne komponenty.
     * @param type typ rastliny, ktoru tato karta reprezentuje
     */
    public PlantCard(PlantType type) {
        this.type = type;

        this.icon = ImageLoader.getImageView(type.getCardImagePath());
        this.icon.setFitWidth(CARD_WIDTH);
        this.icon.setFitHeight(CARD_HEIGHT);

        this.border = new Rectangle(CARD_WIDTH, CARD_HEIGHT);
        this.border.setFill(Color.TRANSPARENT);
        this.border.setStroke(Color.TRANSPARENT);
        this.border.setStrokeWidth(3);

        this.selected = false;
    }

    /**
     * Aktualizuje vizualny stav karty. Zobrazi ohranicenie, ak je karta vybrana,
     * inak ho skryje.
     */
    public void update() {
        if (this.selected) {
            this.border.setStroke(Color.YELLOW);
        } else {
            this.border.setStroke(Color.TRANSPARENT);
        }
    }

    /**
     * Nastavi poziciu karty na obrazovke. Vsetky vizualne komponenty (ikona,
     * ohranicenie) sa umiestnia na rovnaku poziciu.
     * @param x x-ova karty
     * @param y y-ova karty
     */
    public void setPosition(double x, double y) {
        this.icon.setX(x);
        this.icon.setY(y);
        this.border.setX(x);
        this.border.setY(y);
    }

    /**
     * Vytvori novu instanciu rastliny, ktoru tato karta reprezentuje.
     * Konkretny typ rastliny zavisi od PlantType priradeneho ku karte.
     * @return nova instancia prislusnej rastliny
     */
    public Plant createPlant() {
        return switch (this.type) {
            case PEASHOOTER -> new PeaShooter();
            case SUNFLOWER -> new SunFlower();
            case WALLNUT -> new Wallnut();
            case CHERRY_BOMB -> new CherryBomb();
            case SNOW_PEA -> new SnowPea();
        };
    }

    /**
     * Zisti, ci vyberane suradnice sa nachadzaju "vo vnutri" karty
     * Pouziva sa pri detekcii kliknutia na kartu
     * @param x x-ova suradnica
     * @param y y-ova suradnica
     * @return true, ak suradnice lezia vo vnutri karty, inak false
     */
    public boolean contains(double x, double y) {
        return this.icon.getBoundsInParent().contains(x, y);
    }

    /**
     * Nastavi, ci je karta aktualne vybrana
     * @param selected true, ak je karta vybrata, inak false
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Vrati typ rastliny
     * @return typ rastliny typu PlantType
     */
    public PlantType getType() {
        return this.type;
    }

    /**
     * Vrati ikonku karty
     * @return ikonka karty typu ImageView
     */
    public ImageView getIcon() {
        return this.icon;
    }

    /**
     * Vrati ohranicenie karty
     * @return ohranicenie karty typu Rectangle
     */
    public Rectangle getBorder()  {
        return this.border;
    }
}
