package sk.fri.uniza.pvz.player;

import javafx.scene.image.ImageView;
import sk.fri.uniza.pvz.entities.plants.PlantType;
import sk.fri.uniza.pvz.utils.ImageLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Predstavuje inventar hry, z ktoreho sa daju vyberat karty
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public class Inventory {
    private static final double START_X = 10;
    private static final double START_Y = 515;
    private static final double CARD_SPACING = 90;

    private final List<PlantCard> cards;
    private PlantCard selectedCard;

    private final ImageView inventoryBackground;

    /**
     * Inicializuje inventar, nastavi mu vsetky potrebne hodnoty
     * A prida do listu karty, ktore bude inventar obsahovat
     */
    public Inventory() {
        this.cards = new ArrayList<>();
        this.cards.add(new PlantCard(PlantType.PEASHOOTER));
        this.cards.add(new PlantCard(PlantType.SUNFLOWER));
        this.cards.add(new PlantCard(PlantType.WALLNUT));
        this.cards.add(new PlantCard(PlantType.CHERRY_BOMB));
        this.cards.add(new PlantCard(PlantType.SNOW_PEA));

        this.inventoryBackground = ImageLoader.getImageView("/others/inventory.png");
        this.inventoryBackground.setFitWidth(550);
        this.inventoryBackground.setFitHeight(120);
        this.inventoryBackground.setX(-40);
        this.inventoryBackground.setY(500);

        this.spaceCards();
        this.selectedCard = null;
    }

    private void spaceCards() {
        for (int i = 0; i < this.cards.size(); i++) {
            this.cards.get(i).setPosition(START_X + i * CARD_SPACING, START_Y);
        }
    }

    /**
     * Pre kazdu kartu v Liste kariet, vola metodu update
     */
    public void update() {
        for (PlantCard card : this.cards) {
            card.update();
        }
    }

    /**
     * Spracuje kliknutie na inventari. Ak kliknutie smeruje na nejaku kartu:
     * - ak je karta uz vybrana, zrusi sa jej vyber
     * - inak sa karta vyberie (a predosla vybrana karta sa odznaci)
     * @param x x-ova suradnica kliknutia v pixeloch
     * @param y y-ova suradnica kliknutia v pixeloch
     * @return true, ak kliknutie zasiahlo niektoru kartu, false, ak kliknutie nesmerovalo na ziadnu kartu
     */
    public boolean handleClick(double x, double y) {
        for (PlantCard card : this.cards) {
            if (card.contains(x, y)) {
                if (card == this.selectedCard) {
                    this.deselect();
                } else {
                    this.select(card);
                }
                return true;
            }
        }
        return false;
    }

    private void select(PlantCard card) {
        if (this.selectedCard != null) {
            this.selectedCard.setSelected(false);
        }
        this.selectedCard = card;
        card.setSelected(true);
    }

    /**
     * Zrusi aktualny vyber karty, ak ziadna karta nie je vybrana nic sa nestane.
     */
    public void deselect() {
        if (this.selectedCard != null) {
            this.selectedCard.setSelected(false);
            this.selectedCard = null;
        }
    }

    /**
     * Vrati vybranu kartu
     * @return vybrana karta typu PlantCard
     */
    public PlantCard getSelectedCard() {
        return this.selectedCard;
    }

    /**
     * Vrati, ci je vybrata nejaka karta
     * @return true, ak je nejaka karta vybrata, inak vrati false
     */
    public boolean hasSelection() {
        return this.selectedCard != null;
    }

    /**
     * Vrati NEMODIFIKOVATELNY list kariet
     * @return NEMODIFIKOVATELNY list kariet
     */
    public List<PlantCard> getCards() {
        return Collections.unmodifiableList(this.cards);
    }

    /**
     * Vrati obrazok pozadia inventara
     * @return obrazok pozadia inventara typu ImageView
     */
    public ImageView getInventoryBackground() {
        return this.inventoryBackground;
    }
}
