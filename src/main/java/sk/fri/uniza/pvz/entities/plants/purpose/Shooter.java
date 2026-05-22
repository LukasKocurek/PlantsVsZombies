package sk.fri.uniza.pvz.entities.plants.purpose;

import sk.fri.uniza.pvz.gamelogic.EntityManager;

/**
 * Predstavuje rozhranie, ktore implementuju vsetky rastliny ktore strielaju projektily
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public interface Shooter {
    /**
     * Sprava, ktoru musia implementovat vsetci shooteri
     * @param entityManager dostane ako parameter Entity Managera
     */
    void shoot(EntityManager entityManager);
}
