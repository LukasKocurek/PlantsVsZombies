package sk.fri.uniza.pvz.entities.plants.projectile;

import sk.fri.uniza.pvz.entities.zombies.Zombie;

/**
 * Trieda NormalPea predstavuje projektil, ktory striela PeaShooter
 * Trieda dedi od predka Projectile
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public class NormalPea extends Projectile {

    /**
     * Konstruktor sa zabezpeci o nastavenie vsetkych potrebnych hodnot projektilu
     * @param damage dostane damage, ktory uberie zombikovi pri trafeni
     * @param speed urcuje rychlost, akou sa projektil bude pohybovat
     * @param startX dostane zaciatocnu x-ovu startovaciu poziciu
     * @param startY dostane zaciatocnu y-ovu startovaciu poziciu
     */
    public NormalPea(double startX, double startY, int damage, int speed) {
        super(ProjectileType.CLASSIC_PEA.getImagePath(), damage, speed, startX, startY);
    }

    /**
     * Uberie zombikovi ktoreho dostal v parametri zivot podla damage, ktory ma, a oznaci sa, ze uz trafil zombika
     * @param zombie dostane do parametra referenciu na zombika, na ktoreho projektil zareaguje
     */
    @Override
    public void onHit(Zombie zombie) {
        zombie.takeDamage(this.getDamage());
        this.markHit();
    }
}
