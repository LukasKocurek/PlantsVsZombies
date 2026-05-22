package sk.fri.uniza.pvz.map;

import javafx.scene.image.ImageView;
import sk.fri.uniza.pvz.entities.plants.Plant;
import java.util.Optional;

/**
 * Predstavuje hernu mapu, ktora sa sklada z dvojrozmerneho pola policok
 * A na ktoru sa daju pokladat rastliny
 */
public class Map {
    private static final int OFFSET_X = 10;
    private static final int OFFSET_Y = 30;
    private final Tile[][] map;
    private final int numberOfRows;
    private final int numberOfCols;

    /**
     * Inicializuje sa a nastavi si vsetky potrebne hodnoty, vygeneruje mapu z policok
     * @param numberOfRows pocet riadok
     * @param numberOfCols pocet stlpcov
     */
    public Map(int numberOfRows, int numberOfCols) {
        this.numberOfRows = numberOfRows;
        this.numberOfCols = numberOfCols;

        this.map = new Tile[numberOfRows][numberOfCols];
        for (int i = 0; i < this.numberOfRows; i++) {
            for (int j = 0; j < this.numberOfCols; j++) {
                this.map[i][j] = new Tile(j * Tile.getTileSize(), i * Tile.getTileSize());
            }
        }
    }

    /**
     * Vrati, ci zadany riadok a stlpec sa nachadza v rozsahu mapy
     * @param row zadany riadok
     * @param col zadany stlpec
     * @return vrati true, ak sa zadany riadok a stlpec nachadza v rozsahu mapy, inak vrati false
     */
    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < this.numberOfRows && col >= 0 && col < this.numberOfCols;
    }

    /**
     * Ak je to mozne, polozi rastlinu na zadany riadok a stlpec
     * @param plant typ rastliny
     * @param row zadany riadok
     * @param col zadany stlpec
     */
    public void placePlant(Plant plant, int row, int col) {
        if (!this.isInBounds(row, col)) {
            return;
        }
        Tile tile = this.map[row][col];
        if (!tile.placePlant(plant)) {
            return;
        }
        plant.setX(tile.getX() + OFFSET_X);
        plant.setY(tile.getY() + OFFSET_Y);
        plant.setTilePosition(row, col);
    }

    /**
     * Ak je to mozne, odstrani rastlinu zo zadaneho policka
     * @param row zadany riadok
     * @param col zadany stlpec
     */
    public void removePlant(int row, int col) {
        if (!this.isInBounds(row, col)) {
            return;
        }
        this.map[row][col].removePlant();
    }

    /**
     * Vrati obrazok policka, z ktorych sa sklada mapa
     * @param row zadany riadok
     * @param col zadany stlpec
     * @return obrazok policka typu ImageView
     */
    public ImageView getTileSprite(int row, int col) {
        if (!this.isInBounds(row, col)) {
            return null;
        }
        return this.map[row][col].getSprite();
    }

    /**
     * Vrati rastlinu na zadanom policku, ak nejaka existuje.
     * @param row zadany riadok
     * @param col zadany stlpec
     * @return Optional s rastlinou, ak sa na policku nachadza.
     * Prazdny Optional, ak policko je prazdne alebo mimo rozsahu mapy.
     */
    public Optional<Plant> getPlant(int row, int col) {
        if (!this.isInBounds(row, col)) {
            return Optional.empty();
        }
        return Optional.ofNullable(this.map[row][col].getPlant());
    }

    /**
     * Vrati pocet riadkov mapy
     * @return pocet riadkov typu int
     */
    public int getNumberOfRows() {
        return this.numberOfRows;
    }

    /**
     * Vrati pocet stlpcov mapy
     * @return pocet stlpcov typu int
     */
    public int getNumberOfCols() {
        return this.numberOfCols;
    }
}
