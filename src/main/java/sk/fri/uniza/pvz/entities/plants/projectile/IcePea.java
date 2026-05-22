package sk.fri.uniza.pvz.entities.plants.projectile;

import sk.fri.uniza.pvz.entities.zombies.Zombie;

/**
 * Tato trieda reprezentuje projektil, ktory pouziva rastlina SnowPea
 * Trieda dedi od predka Projectile
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public class IcePea extends Projectile {
    private final int slowDuration;

    /**
     * Konstruktor sa zabezpeci o nastavenie vsetkych potrebnych hodnot projektilu
     * @param damage dostane damage, ktory uberie zombikovi pri trafeni
     * @param speed urcuje rychlost, akou sa projektil bude pohybovat
     * @param startX dostane zaciatocnu x-ovu startovaciu poziciu
     * @param startY dostane zaciatocnu y-ovu startovaciu poziciu
     * @param slowDuration urcuje cas, ako dlho bude zombik spomaleny
     */
    public IcePea(double startX, double startY, int damage, int speed, int slowDuration) {
        super(ProjectileType.ICE_PEA.getImagePath(), damage, speed, startX, startY);
        this.slowDuration = slowDuration;
    }

    /**
     * Projektil uberie zombikovi pocet zivotov podla toho, aky damage ma projektil
     * Taktiez zombika aj spomali na cas, ktory ma ulozeny v atribute
     * @param zombie dostane do parametra referenciu na zombika, na ktoreho projektil zareaguje
     */
    @Override
    public void onHit(Zombie zombie) {
        zombie.takeDamage(this.getDamage());
        zombie.applySlow(this.slowDuration);
        this.markHit();
    }
}
