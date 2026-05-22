package sk.fri.uniza.pvz.entities.zombies;

/**
 * Enum uklada vsetky potrebne hodnoty pre zombika
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public enum ZombieType {
    /**
     * Predstavuje typ pre Normalneho Zombika so vsetkymi potrebnymi hodnotami
     */
    NORMAL("/zombies/normalzombie.png", 70, 10, 1, 3),

    /**
     * Predstavuje typ pre Parachute Zombika so vsetkymi potrebnymi hodnotami
     */
    PARACHUTE("/zombies/parachutezombie.png", 45, 20, 1.5, 2);

    private final String imagePath;
    private final int hp;
    private final int damage;
    private final double speed;
    private final int attackCooldown;

    ZombieType(String imagePath, int hp, int damage, double speed, int attackCooldown) {
        this.imagePath = imagePath;
        this.hp = hp;
        this.damage = damage;
        this.speed = speed;
        this.attackCooldown = attackCooldown;
    }

    /**
     * Vrati retazec na adresu obrazku
     * @return retazec na adresu obrazku typu String
     */
    public String getImagePath() {
        return this.imagePath;
    }

    /**
     * Vrati zivoty jednotliveho zombika
     * @return vrati zivoty jednotliveho zombika typu int
     */
    public int getHp() {
        return this.hp;
    }

    /**
     * Vrati damage jednotliveho zombika
     * @return vrati silu poskodenia, ktoru ma jednotlivy typ zombika typu int
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * Vrati rychlost akou sa zombik pohybuje
     * @return rychlost pohybovania sa zombika typu double
     */
    public double getSpeed() {
        return this.speed;
    }

    /**
     * Vrati dlzku cakania, ktoru zombik musi pockat na dalsi utok
     * @return dlzka cakania, ktoru zombik musi pockat na dalsi utok
     */
    public int getAttackCooldown() {
        return this.attackCooldown;
    }
}
