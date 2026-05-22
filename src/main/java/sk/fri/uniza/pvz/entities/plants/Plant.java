package sk.fri.uniza.pvz.entities.plants;

import sk.fri.uniza.pvz.entities.Entity;
import sk.fri.uniza.pvz.gamelogic.EntityManager;
import sk.fri.uniza.pvz.utils.Constants;

/**
 * Predstavuje abstraktnu triedu rastliny, ktoru dedia vsetky rastliny
 * Dedi abstraktneho hlavneho predka Entity
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public abstract class Plant extends Entity {
    private final PlantType type;
    private int aliveTicks;
    private int row;
    private int col;

    /**
     * Konstruktor rastliny ju inicializuje a nastavi jej potrebne atributy
     * Zavola predka triedy, a inicializuje sa cez zdedeny konstruktor
     * @param type dostane do parametra typ rastliny, ktory si ulozi do atributu
     */
    protected Plant(PlantType type) {
        super(type.getHp(), type.getImagePath());
        this.type = type;
    }

    /**
     * Protected metoda, ktora odpocitava cas, ktory rastlina zije
     * Ked aliveTicks prekroci alebo je rovny casu zivotu rastliny, rastlina zomrie
     */
    protected void ageOneTick() {
        this.aliveTicks++;
        if (this.aliveTicks >= Constants.countTime(this.getLivingTime())) {
            this.takeDamage(this.getCurrentHealth());
        }
    }

    /**
     * Prida rastline zivoty
     * @param amount mnozstvo, o ake si rastlina zvysi pocet zivotov
     */
    public void heal(int amount) {
        if (this.isDead()) {
            return;
        }
        this.setHealth(this.getCurrentHealth() + amount);
    }

    /**
     * Rastlina si nastavi riadok a stlpec
     * @param row nastavi riadok rastline
     * @param col nastavi stlpec rastline
     */
    public void setTilePosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Prepisana metoda z predka, ktora je NEMENNA
     * Kazda rastlina si ju automaticky implementuje, a vola metodu ageOnTick a onUpdate
     * Zabezpecuje POLYMORFIZMUS
     * @param entityManager v parametri dostane referenciu na EntityManagera
     */
    @Override
    public final void update(EntityManager entityManager) {
        this.ageOneTick();
        if (this.isDead()) {
            return;
        }
        this.onUpdate(entityManager);
    }

    /**
     * Abstraktna metoda, ktoru musi implementovat kazdy predok
     * vola sa v kazdom potomkovi inak v zdedenej metode update, pomaha zabezpecit POLYMORFIZMUS
     * @param entityManager dostane referenciu na Entity Managea
     */
    protected abstract void onUpdate(EntityManager entityManager);

    /**
     * Vrati riadok, na ktorom sa rastlina nachadza
     * @return cislo riadku, na ktorom sa rastlina nachadza typu int
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Vrati riadok, na ktorom sa rastlina nachadza
     * @return cislo riadku, na ktorom sa rastlina nachadza typu int
     */
    public int getCol() {
        return this.col;
    }

    /**
     * Vrati cas, ako dlho bude rastlina na zive
     * @return cas zivota rastliny typu int
     */
    protected int getLivingTime() {
        return this.type.getLivingTime();
    }

    /**
     * Vrati nasobic poskodenia, ktory rastlina dostane
     * @return nasobic poskodenia typu int
     */
    public int getTakenDamageMultiplier() {
        return this.type.getTakenDamageMultiplier();
    }

    /**
     * Vrati typ rastliny
     * @return typ rastliny typu PlantType (enum)
     */
    public PlantType getType() {
        return this.type;
    }
}
