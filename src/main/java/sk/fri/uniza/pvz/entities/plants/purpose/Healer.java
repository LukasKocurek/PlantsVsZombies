package sk.fri.uniza.pvz.entities.plants.purpose;

import sk.fri.uniza.pvz.map.Map;

/**
 * Predstavuje rozhranie, ktore implementuju vsetky rastliny ktore su healermi
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public interface Healer {
    /**
     * Sprava, ktoru musia implementovat vsetci healeri
     * @param map dostane ako parameter mapu
     */
    void heal(Map map);
}
