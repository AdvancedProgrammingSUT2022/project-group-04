package Civilization.Model;

import java.io.IOException;

public class Soldier extends Unit {
    private int range;
    private String combatType;
    private Technology requiredTechnology;
    private Resources requiredResources;

    private int siegeReady = -1;

    public Soldier(int x, int y, String unitType, int civilizationIndex) {
        super(x, y, unitType, 10, civilizationIndex, 2);
        switch (unitType) {
            case "Archer":
                this.range = 2;
                this.combatType = "Archery";
                this.combatStrength = 4;
                this.rangedCombatStrength = 6;
                this.requiredTechnology = new Technology("Archery");
                this.cost = 70;
                this.speed = 2;
                this.originialspeed = 2;
                this.era = "Ancient";
                break;
            case "ChariotArcher":
                this.range = 4;
                this.combatType = "Mounted";
                this.combatStrength = 3;
                this.rangedCombatStrength = 6;
                this.requiredTechnology = new Technology("TheWheel");
                this.requiredResources = new Resources("Horse");
                this.cost = 60;
                this.speed = 4;
                this.originialspeed = 4;
                this.era = "Ancient";
                break;
            case "Scout":
                this.range = 2;
                this.combatType = "Recon";
                this.combatStrength = 4;
                this.rangedCombatStrength = 0;
                this.cost = 25;
                this.speed = 2;
                this.originialspeed = 2;
                this.era = "Ancient";
                break;
            case "Spearman":
                this.range = 2;
                this.combatType = "Melee";
                this.combatStrength = 7;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("BronzeWorking");
                this.cost = 50;
                this.speed = 2;
                this.originialspeed = 2;
                this.era = "Ancient";
                break;
            case "Warrior":
                this.range = 2;
                this.combatType = "Archery";
                this.combatStrength = 6;
                this.rangedCombatStrength = 0;
                this.cost = 40;
                this.speed = 2;
                this.originialspeed = 2;
                this.era = "Ancient";
                break;
            case "Catapult":
                this.range = 2;
                this.combatType = "Siege";
                this.combatStrength = 4;
                this.rangedCombatStrength = 14;
                this.requiredTechnology = new Technology("Mathematics");
                this.requiredResources = new Resources("Iron");
                this.cost = 100;
                this.speed = 2;
                this.originialspeed = 2;
                this.siegeReady = 0;
                this.era = "Classical";
                break;
            case "Horseman":
                this.range = 4;
                this.combatType = "Mounted";
                this.combatStrength = 12;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("HorsebackRiding");
                this.requiredResources = new Resources("Horse");
                this.cost = 80;
                this.speed = 4;
                this.originialspeed = 4;
                this.era = "Classical";
                break;
            case "Swordsman":
                this.range = 2;
                this.combatType = "Melee";
                this.combatStrength = 11;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("IronWorking");
                this.requiredResources = new Resources("Iron");
                this.cost = 80;
                this.speed = 2;
                this.originialspeed = 2;
                this.era = "Classical";
                break;
            case "Crossbowman":
                this.range = 2;
                this.combatType = "Archery";
                this.combatStrength = 6;
                this.rangedCombatStrength = 12;
                this.requiredTechnology = new Technology("Machinery");
                this.cost = 120;
                this.speed = 2;
                this.originialspeed = 2;
                this.era = "Medieval";
                break;
            case "Knight":
                this.range = 3;
                this.combatType = "Mounted";
                this.combatStrength = 18;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("Chivalry");
                this.requiredResources = new Resources("Horse");
                this.cost = 150;
                this.speed = 3;
                this.originialspeed = 3;
                this.era = "Medieval";
                break;
            case "Longswordsman":
                this.range = 3;
                this.combatType = "Melee";
                this.combatStrength = 0;
                this.rangedCombatStrength = 0;
                this.requiredResources = new Resources("Iron");
                this.requiredTechnology = new Technology("Steel");
                this.cost = 150;
                this.speed = 3;
                this.originialspeed = 3;
                this.era = "Medieval";
                break;
            case "Pikeman":
                this.range = 2;
                this.combatType = "Melee";
                this.combatStrength = 0;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("CivilService");
                this.cost = 100;
                this.speed = 2;
                this.originialspeed = 2;
                this.era = "Medieval";
                break;
            case "Trebuchet":
                this.range = 2;
                this.combatType = "Siege";
                this.combatStrength = 20;
                this.rangedCombatStrength = 2;
                this.requiredResources = new Resources("Iron");
                this.requiredTechnology = new Technology("Physics");
                this.cost = 170;
                this.speed = 2;
                this.originialspeed = 2;
                this.siegeReady = 0;
                this.era = "Medieval";
                break;
            case "Canon":
                this.range = 2;
                this.combatType = "Siege";
                this.combatStrength = 10;
                this.rangedCombatStrength = 26;
                this.requiredTechnology = new Technology("Chemistry");
                this.cost = 250;
                this.speed = 2;
                this.originialspeed = 2;
                this.siegeReady = 0;
                this.era = "Renaissance";
                break;
            case "Cavalry":
                this.range = 3;
                this.combatType = "Mounted";
                this.combatStrength = 25;
                this.rangedCombatStrength = 0;
                this.requiredResources = new Resources("Horse");
                this.requiredTechnology = new Technology("MilitaryScience");
                this.cost = 260;
                this.speed = 3;
                this.originialspeed = 3;
                this.era = "Renaissance";
                break;
            case "Lancer":
                this.range = 4;
                this.combatType = "Mounted";
                this.combatStrength = 22;
                this.rangedCombatStrength = 0;
                this.requiredResources = new Resources("Horse");
                this.requiredTechnology = new Technology("Metallurgy");
                this.cost = 220;
                this.speed = 4;
                this.originialspeed = 4;
                this.era = "Renaissance";
                break;
            case "Musketman":
                this.range = 2;
                this.combatType = "Gunpowder";
                this.combatStrength = 16;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("Gunpowder");
                this.cost = 120;
                this.speed = 2;
                this.originialspeed = 2;
                this.era = "Renaissance";
                break;
            case "Rifleman":
                this.range = 2;
                this.combatType = "Gunpowder";
                this.combatStrength = 25;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("Rifling");
                this.cost = 200;
                this.speed = 2;
                this.originialspeed = 2;
                this.era = "Renaissance";
                break;
            case "AntiTankGun":
                this.range = 2;
                this.combatType = "Gunpowder";
                this.combatStrength = 32;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("ReplaceableParts");
                this.cost = 300;
                this.speed = 2;
                this.originialspeed = 2;
                this.era = "Industrial";
                break;
            case "Artillery":
                this.range = 2;
                this.combatType = "Siege";
                this.combatStrength = 16;
                this.rangedCombatStrength = 32;
                this.requiredTechnology = new Technology("Dynamite");
                this.cost = 420;
                this.speed = 2;
                this.originialspeed = 2;
                this.siegeReady = 0;
                this.era = "Industrial";
                break;
            case "Infantry":
                this.range = 2;
                this.combatType = "Gunpowder";
                this.combatStrength = 36;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("ReplaceableParts");
                this.cost = 300;
                this.speed = 2;
                this.originialspeed = 2;
                this.era = "Industrial";
                break;
            case "Panzer":
                this.range = 5;
                this.combatType = "Armored";
                this.combatStrength = 60;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("Combustion");
                this.cost = 450;
                this.speed = 5;
                this.originialspeed = 5;
                this.era = "Industrial";
                break;
            case "Tank":
                this.range = 4;
                this.combatType = "Armored";
                this.combatStrength = 50;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("Combustion");
                this.cost = 450;
                this.speed = 4;
                this.originialspeed = 4;
                this.era = "Industrial";
                break;
            default:
                break;
        }
    }

    public Technology getTechnologiesNeed() {
        return this.requiredTechnology;
    }

    public int getSiegeReady() {
        return siegeReady;
    }

    public void setSiegeReady(int siegeReady) {
        this.siegeReady = siegeReady;
    }

    public String getCombatType() {
        return combatType;
    }

    public void setCombatType(String combatType) {
        this.combatType = combatType;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void attackUnitMelee(Unit unit) {
        int amount = 0;
        for (double i = 1; i < 10; i++) {
            if (this.HP == i) {
                amount += this.combatStrength - ((10 - i) / 20) * this.combatStrength;
                break;
            }
        }
        unit.setHP(unit.getHP() - amount + unit.getCombatStrength());
    }

    public void attackUnitRanged(Unit unit) {
        int amount = 0;
        for (double i = 1; i < 10; i++) {
            if (this.HP == i) {
                amount += this.rangedCombatStrength - ((10 - i) / 20) * this.rangedCombatStrength;
                break;
            }
        }
        unit.setHP(unit.getHP() - amount + unit.getCombatStrength());
    }

    public void attackCityMelee(City city) {
        int amount = 0;
        for (double i = 1; i < 10; i++) {
            if (this.HP == i) {
                amount += this.combatStrength - ((10 - i) / 20) * this.combatStrength;
                break;
            }
        }
        city.setHP(city.getHP() - amount + city.getPower()); //not sure about this :/
    }

    public void attackCityRanged(City city) {
        int amount = 0;
        for (double i = 1; i < 10; i++) {
            if (this.HP == i) {
                amount += this.rangedCombatStrength - ((10 - i) / 20) * this.rangedCombatStrength;
                break;
            }
        }
        city.setHP(city.getHP() - amount + city.getPower()); //not sure about this :/
    }

    public boolean isCombatUnit() {
        return true;
    }

    public boolean isTileInRangeOfUnit(Tile tile) throws IOException {
        for (int i = 1; i < 100; i++) {
            if (tile.getAdjacentTilesByLayer(i).contains(tile)) {
                if (i < this.getRange()){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String result = this.unitType + "\n";
        result += "X = " + Integer.toString(this.x) + " Y = " + Integer.toString(this.y) + "\n";
        result += "Hit point = " + Integer.toString(this.HP) + "\n" + "Power = " + Integer.toString(this.combatStrength);
        return result;
    }

}
