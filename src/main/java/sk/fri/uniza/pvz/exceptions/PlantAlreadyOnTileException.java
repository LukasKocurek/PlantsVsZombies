package sk.fri.uniza.pvz.exceptions;

/**
 * Trieda predstavuje nekontrolovanu vynimku, ktora sa vyhodi, ak sa hrac pokusi polozit rastlinu na policko
 * kde uz sa rastlina nachadza
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public class PlantAlreadyOnTileException extends RuntimeException {

    /**
     * Vynimka sa inicializuje podla predka
     */
    public PlantAlreadyOnTileException() {
        super("Plant is already placed there!");
    }
}
