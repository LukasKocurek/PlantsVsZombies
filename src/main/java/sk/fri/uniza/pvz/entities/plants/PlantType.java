package sk.fri.uniza.pvz.entities.plants;

/**
 * Enum uklada vsetky potrebne hodnoty pre rastlinu
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public enum PlantType {
    /**
     * Predstavuje typ pre Peashootera so vsetkymi potrebnymi hodnotami
     */
    PEASHOOTER("/plants/peashooter.png", "/cards/peashooterCard.png", 10, 100, 2, 10, 15),

    /**
     * Predstavuje typ rastliny pre Wallnut so vsetkymi potrebnymi hodnotami
     */
    WALLNUT("/plants/wallnut.png", "/cards/wallnutCard.png", 12, 10000, 1, 1, 20),

    /**
     * Predstavuje typ rastliny pre SunFlower so vsetkymi potrebnymi hodnotami
     */
    SUNFLOWER("/plants/sunflower.png", "/cards/sunflowerCard.png", 8, 200, 3, 10, 12),

    /**
     * Predstavuje typ rastliny pre CherryBomb so vsetkymi potrebnymi hodnotami
     */
    CHERRY_BOMB("/plants/cherrybomb.png", "/cards/cherrybombCard.png", 10, 1, 0, 40, 5),

    /**
     * Predstavuje typ rastliny pre SnowPea so vsetkymi potrebnymi hodnotami
     */
    SNOW_PEA("/plants/snowpea.png", "/cards/snowpeacard.png", 6, 100, 2, 5, 10);

    private final String imagePath;
    private final String cardImagePath;
    private final int cost;
    private final int hp;
    private final int takenDamageMultiplier;
    private final int abilityPower;
    private final int livingTime;

    PlantType(String imagePath, String cardImagePath, int cost, int hp, int takenDamageMultiplier, int abilityPower, int livingTime) {
        this.imagePath = imagePath;
        this.cardImagePath = cardImagePath;
        this.cost = cost;
        this.hp = hp;
        this.takenDamageMultiplier = takenDamageMultiplier;
        this.abilityPower = abilityPower;
        this.livingTime = livingTime;
    }

    /**
     * Vrati retazec na adresu obrazku
     * @return retazec na adresu obrazku typu String
     */
    public String getImagePath() {
        return this.imagePath;
    }

    /**
     * Vrati cenu jednotlivej rastliny
     * @return vrati cenu jednotlivej rastliny typu int
     */
    public int getCost() {
        return this.cost;
    }

    /**
     * Vrati zivoty jednotlivej rastliny
     * @return vrati zivoty jednotlivej rastliny typu int
     */
    public int getHp() {
        return this.hp;
    }

    /**
     * Vrati nasobic poskodenia, ktory rastlina dostane
     * @return nasobic poskodenia typu int
     */
    public int getTakenDamageMultiplier() {
        return this.takenDamageMultiplier;
    }

    /**
     * Vrati silu na specialnu schopnost rastliny
     * @return sila specialnej schopnosti typu int
     */
    public int getAbilityPower() {
        return this.abilityPower;
    }

    /**
     * Vrati cas, ktory jednotliva rastlina bude zit
     * @return cas, ktory bude rastlina zit typu int
     */
    public int getLivingTime() {
        return this.livingTime;
    }

    /**
     * Vrati cestu k obrazku
     * @return cesta k obrazku typu String
     */
    public String getCardImagePath() {
        return this.cardImagePath;
    }
}
