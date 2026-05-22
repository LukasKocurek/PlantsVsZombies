package sk.fri.uniza.pvz.renderers;

import javafx.scene.layout.Pane;
import sk.fri.uniza.pvz.map.Map;
import sk.fri.uniza.pvz.player.Inventory;

/**
 * Zobrazuje staticke prvky hry, cize herne policka mapy
 * a karty v inventari. Na rozdiel od DynamicRenderer, StaticRenderer sa
 * vykonava len raz na zaciatku hry; jeho vystup zostava v scene pocas celej
 * hry bez nutnosti aktualizacie.
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public class StaticRenderer {
    private final Pane root;
    private final Map map;
    private final Inventory inventory;

    /**
     * Inicializuje si pociatocne hodnoty
     * @param map referencia na Mapu
     * @param root referencia na Pane
     * @param inventory referencia na Inventar
     */
    public StaticRenderer(Map map, Pane root, Inventory inventory) {
        this.map = map;
        this.root = root;
        this.inventory = inventory;
    }

    /**
     * Zobrazi vsetky policka hracej mapy. Vola sa raz na zaciatku hry.
     */
    public void renderMap() {
        for (int row = 0; row < this.map.getNumberOfRows(); row++) {
            for (int col = 0; col < this.map.getNumberOfCols(); col++) {
                this.root.getChildren().add(this.map.getTileSprite(row, col));
            }
        }
    }

    /**
     * Zobrazi vsetky karty inventara. Pre kazdu kartu prida do sceny jej ikonku a ohranicenie
     */
    public void renderInventory() {
        this.root.getChildren().add(this.inventory.getInventoryBackground());

        for (var card : this.inventory.getCards()) {
            this.root.getChildren().add(card.getIcon());
            this.root.getChildren().add(card.getBorder());
        }
    }
}
