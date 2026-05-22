package sk.fri.uniza.pvz.exceptions;

/**
 * Trieda predstavuje nekontrolovanu vynimku, ktora sa vyhodi, ak sa hrac pokusi polozit rastlinu, ale nema dostatok slniecok
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public class NotEnoughSunException extends RuntimeException {

    /**
     * Vynimka sa inicializuje podla predka
     */
    public NotEnoughSunException() {
        super("Not enough sun!");
    }
}