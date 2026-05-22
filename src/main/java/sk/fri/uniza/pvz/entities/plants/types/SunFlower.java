package sk.fri.uniza.pvz.entities.plants.types;

import sk.fri.uniza.pvz.entities.plants.Plant;
import sk.fri.uniza.pvz.entities.plants.PlantType;
import sk.fri.uniza.pvz.entities.plants.purpose.Healer;
import sk.fri.uniza.pvz.gamelogic.EntityManager;
import sk.fri.uniza.pvz.map.Map;
import sk.fri.uniza.pvz.utils.Constants;

/**
 * Predstavuje konkretneho potomka triedy Plant, od ktorej dedi
 * Implementuje rozhranie Healer
 * Healuje vsetky rastliny, ktore su okolo nej na polickach
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public class SunFlower extends Plant implements Healer {
    private static final int HEAL_COOLDOWN = Constants.countTime(5);
    private int cooldownCounter;

    /**
     * Zavola konstruktor predka Plant a inicializuje sa podla neho
     */
    public SunFlower() {
        super(PlantType.SUNFLOWER);
    }

    /**
     * Prepisana metoda z predka, kde si rastlina zavola svoj vlastny multiplikator poskodenia, ktore dostane
     * @param damage damage, ktory rastlina dostane vynasobeni multiplikatorom damagu
     */
    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage * this.getTakenDamageMultiplier());
    }

    /**
     * Implementovana metoda z predka
     * Obsahuje cooldown, ked cooldown vyprsi, rastlina healne rastliny okolo nej
     * Nasledne sa cooldown zase obnovi na povodnu hodnotu
     * @param entityManager dostane referenciu na Entity Managera
     */
    @Override
    protected void onUpdate(EntityManager entityManager) {
        if (this.cooldownCounter > 0) {
            this.cooldownCounter--;
            return;
        }
        this.heal(entityManager.getMap());
        this.cooldownCounter = HEAL_COOLDOWN;
    }

    /**
     * Implementovana metoda z interfacu Healer, prejde policka okolo nej (okrem jej samotnej)
     * a ak najde nejake rastliny, healne ich o danu hodnotu
     * @param map dostane ako parameter mapu
     */
    @Override
    public void heal(Map map) {
        int r = this.getRow();
        int c = this.getCol();
        int amount = this.getType().getAbilityPower();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                var neigbour = map.getPlant(r + i, c + j);
                neigbour.ifPresent(plant -> plant.heal(amount));
            }
        }
    }
}
