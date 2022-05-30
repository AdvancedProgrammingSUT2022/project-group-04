package Civilization.Model;

public class CombatOfUnits {
    private Soldier soldier1;
    private Soldier soldier2;
    private Tile tileSoldier1;
    private Tile tileSoldier2;

    public CombatOfUnits(Soldier soldier1, Soldier soldier2) {
        this.soldier1 = soldier1;
        this.soldier2 = soldier2;
        this.tileSoldier1 = soldier1.getTileOfUnit();
        this.tileSoldier2 = soldier2.getTileOfUnit();
    }

    public Soldier getSoldier1() {
        return soldier1;
    }

    public void setSoldier1(Soldier unit1) {
        this.soldier1 = unit1;
    }

    public Soldier getSoldier2() {
        return soldier2;
    }

    public void setSoldier2(Soldier unit2) {
        this.soldier2 = unit2;
    }

    public Tile getTileSoldier1() {
        return tileSoldier1;
    }

    public void setTileSoldier1(Tile tileSoldier1) {
        this.tileSoldier1 = tileSoldier1;
    }

    public Tile getTileSoldier2() {
        return tileSoldier2;
    }

    public void setTileSoldier2(Tile tileSoldier2) {
        this.tileSoldier2 = tileSoldier2;
    }

}
