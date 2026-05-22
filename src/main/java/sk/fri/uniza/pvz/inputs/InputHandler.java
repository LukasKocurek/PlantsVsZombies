package sk.fri.uniza.pvz.inputs;

import sk.fri.uniza.pvz.entities.plants.Plant;
import sk.fri.uniza.pvz.entities.zombies.Zombie;
import sk.fri.uniza.pvz.exceptions.NotEnoughSunException;
import sk.fri.uniza.pvz.exceptions.PlantAlreadyOnTileException;
import sk.fri.uniza.pvz.exceptions.TileOccupiedByZombieException;
import sk.fri.uniza.pvz.gamelogic.EntityManager;
import sk.fri.uniza.pvz.map.Map;
import sk.fri.uniza.pvz.map.Tile;
import sk.fri.uniza.pvz.player.Inventory;
import sk.fri.uniza.pvz.player.PlantCard;
import sk.fri.uniza.pvz.player.SunCount;
import sk.fri.uniza.pvz.renderers.HudRenderer;

/**
 * Trieda sluzi na pokladanie rastlin na mapu
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public class InputHandler {
    private final Inventory inventory;
    private final SunCount sunCount;
    private final Map map;
    private final EntityManager entityManager;
    private final HudRenderer hudRenderer;

    /**
     * Inicializuje sa a nastavi si vsetky potrebne hodnoty
     * @param inventory referencia na inventar
     * @param sunCount referencia na pocet slniecok
     * @param map referencia na mapu
     * @param entityManager referencia na EntityManager
     * @param hudRenderer referencia na HudRenderer
     */
    public InputHandler(Inventory inventory, SunCount sunCount, Map map, EntityManager entityManager, HudRenderer hudRenderer) {
        this.inventory = inventory;
        this.sunCount = sunCount;
        this.map = map;
        this.entityManager = entityManager;
        this.hudRenderer = hudRenderer;
    }

    /**
     * Spracuje kliknutie mysou na zadanych suradniciach.
     * Najprv overi, ci kliknutie smerovalo na niektoru kartu v inventari —
     * ak ano, inventar si ho spracuje sam. V opacnom pripade, ak je vybrana
     * nejaka karta, pokusi sa umiestnit rastlinu na kliknutu dlazdicu.
     * @param x x-ova suradnica kliknutia v pixeloch
     * @param y y-ova suradnica kliknutia v pixeloch
     */
    public void handleClick(double x, double y) {
        if (this.inventory.handleClick(x, y)) {
            return;
        }
        if (this.inventory.hasSelection()) {
            this.tryPlacingPlant(x, y);
        }
    }

    private void tryPlacingPlant(double x, double y) {
        int col = (int)(x / Tile.getTileSize());
        int row = (int)(y / Tile.getTileSize());

        if (!this.isValidTile(row, col)) {
            return;
        }

        PlantCard card = this.inventory.getSelectedCard();
        int cost = card.getType().getCost();

        try {
            this.checkNoPlantOnTile(row, col);
            this.checkNoZombieOnTile(row, col);
            this.sunCount.spend(cost);
        } catch (NotEnoughSunException ex) {
            this.hudRenderer.showWarning("Not enough sun!");
            return;
        } catch (PlantAlreadyOnTileException ex) {
            this.hudRenderer.showWarning("Plant already here!");
            return;
        } catch (TileOccupiedByZombieException ex) {
            this.hudRenderer.showWarning("Row is being occupied by Zombie!");
            return;
        }

        Plant plant = card.createPlant();
        this.map.placePlant(plant, row, col);
        this.entityManager.addPlant(plant);
        this.inventory.deselect();
    }

    private boolean isValidTile(int row, int col) {
        return row >= 0 && row < this.map.getNumberOfRows() && col >= 0 && col < this.map.getNumberOfCols();
    }

    private void checkNoZombieOnTile(int row, int col) {
        int tileSize = Tile.getTileSize();
        int tileLeftX = col * tileSize;
        int tileRightX = (col + 1) * tileSize;

        for (Zombie zombie : this.entityManager.getZombies()) {
            if (zombie.getRow() != row) {
                continue;
            }
            double zx = zombie.getX();
            if (zx + tileSize > tileLeftX && zx < tileRightX) {
                throw new TileOccupiedByZombieException();
            }
        }
    }

    private void checkNoPlantOnTile(int row, int col) {
        if (this.map.getPlant(row, col).isPresent()) {
            throw new PlantAlreadyOnTileException();
        }
    }
}
