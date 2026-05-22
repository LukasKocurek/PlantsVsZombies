package sk.fri.uniza.pvz.entities.plants.projectile;

/**
 * Enum dava projektilom odkazy na obrazky
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public enum ProjectileType {
    /**
     * Dava projektilu NormalPea adresu na obrazok
     */
    CLASSIC_PEA("/plants/projectiles/green_pea.png"),

    /**
     * Dava projektilu IcePea adresu na obrazok
     */
    ICE_PEA("/plants/projectiles/blue_pea.png");

    private final String imagePath;

    ProjectileType(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * Vrati adresu na obrazok
     * @return Vrati adresu na obrazok typu String
     */
    public String getImagePath() {
        return this.imagePath;
    }

}
