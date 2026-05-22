package sk.fri.uniza.pvz.exceptions;

/**
 * Trieda predstavuje nekontrolovanu vynimku, ktora sa vyhodi, ak sa hrac pokusi polozit rastlinu na policko
 * kde sa prave nachadza zombik
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public class TileOccupiedByZombieException extends RuntimeException {
    public TileOccupiedByZombieException() {
        super("Tile is currently being occupied by zombie!");
    }
}