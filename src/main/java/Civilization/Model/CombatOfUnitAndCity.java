package Civilization.Model;

public class CombatOfUnitAndCity {
    private Soldier soldier;
    private City city;
    private Tile tileSoldier;

    public CombatOfUnitAndCity(Soldier soldier, City city) {
        this.soldier = soldier;
        this.city = city;
        this.tileSoldier = soldier.getTileOfUnit();
    }

    public Soldier getSoldier() {
        return soldier;
    }

    public void setSoldier(Soldier soldier) {
        this.soldier = soldier;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Tile getTileSoldier() {
        return tileSoldier;
    }

    public void setTileSoldier(Tile tileSoldier) {
        this.tileSoldier = tileSoldier;
    }

}
