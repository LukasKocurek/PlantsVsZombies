package sk.fri.uniza.pvz.entities.plants.purpose;

import sk.fri.uniza.pvz.gamelogic.EntityManager;

/**
 * Predstavuje rozhranie, ktore implementuju vsetky rastliny ktore utocia na blizko
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public interface MeleeAttacker {
    /**
     * Sprava, ktoru musia implementovat vsetci healeri
     * @param entityManager dostane ako parameter Entity Managera
     */
    void attack(EntityManager entityManager);
}
