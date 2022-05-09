package Model;

public class Soldier extends Unit {
    private int range;
    private String combatType;
    private Technology requiredTechnology;
    private Resources requiredResources;
    private int rangedCombatStrength;
    private int combatStrength;

    public Soldier(int x, int y, int vX, int vY, int power, int cost, int movementPoint, String unitType, boolean isSleeping, boolean isReady, String era, int HP, int civilizationIndex) {
        super(x, y, vX, vY, power, cost, movementPoint, unitType, isSleeping, isReady, era, HP, civilizationIndex, 2);
        switch (unitType) {
            case "Archer":
                this.range = 2;
                this.combatType = "Archery";
                this.combatStrength = 4;
                this.rangedCombatStrength = 6;
                this.requiredTechnology = new Technology("Archery");
                this.cost = 70;
                break;
            case "ChariotArcher":
                this.range = 4;
                this.combatType = "Mounted";
                this.combatStrength = 3;
                this.rangedCombatStrength = 6;
                this.requiredTechnology = new Technology("TheWheel");
                this.requiredResources = new Resources("Horse");
                this.cost = 60;
                break;
            case "Scout":
                this.range = 2;
                this.combatType = "Recon";
                this.combatStrength = 4;
                this.rangedCombatStrength = 0;
                this.cost = 25;
                break;
            case "Spearman":
                this.range = 2;
                this.combatType = "Melee";
                this.combatStrength = 7;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("BronzeWorking");
                this.cost = 50;
                break;
            case "Warrior":
                this.range = 2;
                this.combatType = "Archery";
                this.combatStrength = 6;
                this.rangedCombatStrength = 0;
                this.cost = 40;
                break;
            case "Catapult":
                this.range = 2;
                this.combatType = "Siege";
                this.combatStrength = 4;
                this.rangedCombatStrength = 14;
                this.requiredTechnology = new Technology("Mathematics");
                this.requiredResources = new Resources("Iron");
                this.cost = 100;
                break;
            case "Horseman":
                this.range = 4;
                this.combatType = "Mounted";
                this.combatStrength = 12;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("HorsebackRiding");
                this.requiredResources = new Resources("Horse");
                this.cost = 80;
                break;
            case "Swordsman":
                this.range = 2;
                this.combatType = "Melee";
                this.combatStrength = 11;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("IronWorking");
                this.requiredResources = new Resources("Iron");
                this.cost = 80;
                break;
            case "Crossbowman":
                this.range = 2;
                this.combatType = "Archery";
                this.combatStrength = 6;
                this.rangedCombatStrength = 12;
                this.requiredTechnology = new Technology("Machinery");
                this.cost = 120;
                break;
            case "Knight":
                this.range = 3;
                this.combatType = "Mounted";
                this.combatStrength = 18;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("Chivalry");
                this.requiredResources = new Resources("Horse");
                this.cost = 150;
                break;
            case "Longswordsman":
                this.range = 3;
                this.combatType = "Melee";
                this.combatStrength = 0;
                this.rangedCombatStrength = 0;
                this.requiredResources = new Resources("Iron");
                this.requiredTechnology = new Technology("Steel");
                this.cost = 150;
                break;
            case "Pikeman":
                this.range = 2;
                this.combatType = "Melee";
                this.combatStrength = 0;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("CivilService");
                this.cost = 100;
                break;
            case "Trebuchet":
                this.range = 2;
                this.combatType = "Siege";
                this.combatStrength = 20;
                this.rangedCombatStrength = 2;
                this.requiredResources = new Resources("Iron");
                this.requiredTechnology = new Technology("Physics");
                this.cost = 170;
                break;
            case "Canon":
                this.range = 2;
                this.combatType = "Siege";
                this.combatStrength = 10;
                this.rangedCombatStrength = 26;
                this.requiredTechnology = new Technology("Chemistry");
                this.cost = 250;
                break;
            case "Cavalry":
                this.range = 3;
                this.combatType = "Mounted";
                this.combatStrength = 25;
                this.rangedCombatStrength = 0;
                this.requiredResources = new Resources("Horse");
                this.requiredTechnology = new Technology("MilitaryScience");
                this.cost = 260;
                break;
            case "Lancer":
                this.range = 4;
                this.combatType = "Mounted";
                this.combatStrength = 22;
                this.rangedCombatStrength = 0;
                this.requiredResources = new Resources("Horse");
                this.requiredTechnology = new Technology("Metallurgy");
                this.cost = 220;
                break;
            case "Musketman":
                this.range = 2;
                this.combatType = "Gunpowder";
                this.combatStrength = 16;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("Gunpowder");
                this.cost = 120;
                break;
            case "Rifleman":
                this.range = 2;
                this.combatType = "Gunpowder";
                this.combatStrength = 25;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("Rifling");
                this.cost = 200;
                break;
            case "AntiTankGun":
                this.range = 2;
                this.combatType = "Gunpowder";
                this.combatStrength = 32;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("ReplaceableParts");
                this.cost = 300;
                break;
            case "Artillery":
                this.range = 2;
                this.combatType = "Siege";
                this.combatStrength = 16;
                this.rangedCombatStrength = 32;
                this.requiredTechnology = new Technology("Dynamite");
                this.cost = 420;
                break;
            case "Infantry":
                this.range = 2;
                this.combatType = "Gunpowder";
                this.combatStrength = 36;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("ReplaceableParts");
                this.cost = 300;
                break;
            case "Panzer":
                this.range = 5;
                this.combatType = "Armored";
                this.combatStrength = 60;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("Combustion");
                this.cost = 450;
                break;
            case "Tank":
                this.range = 4;
                this.combatType = "Armored";
                this.combatStrength = 50;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("Combustion");
                this.cost = 450;
                break;
            default:
                break;
        }
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

    public void attackUnitMelee(Unit unit){
        int amount = 0;
        for (double i = 1; i < 10; i++) {
            if (this.HP == i) {
                amount += this.combatStrength - ((10 - i) / 20) * this.combatStrength;
                break;
            }
        }
        unit.setHP(unit.getHP() - amount + unit.getPower());
    }
    public void attackUnitRanged(Unit unit){
        int amount = 0;
        for (double i = 1; i < 10; i++) {
            if (this.HP == i) {
                amount += this.rangedCombatStrength - ((10 - i) / 20) * this.rangedCombatStrength;
                break;
            }
        }
        unit.setHP(unit.getHP() - amount + unit.getPower());
    }

    public void attackCityMelee(City city){
        int amount = 0;
        for (double i = 1; i < 10; i++) {
            if (this.HP == i) {
                amount += this.combatStrength - ((10 - i) / 20) * this.combatStrength;
                break;
            }
        }
        city.setHP(city.getHP() - amount + city.getPower()); //not sure about this :/
    }

    public void attackCityRanged(City city){
        int amount = 0;
        for (double i = 1; i < 10; i++) {
            if (this.HP == i) {
                amount += this.rangedCombatStrength - ((10 - i) / 20) * this.rangedCombatStrength;
                break;
            }
        }
        city.setHP(city.getHP() - amount + city.getPower()); //not sure about this :/
    }

    public boolean isCombatUnit(){
        return true;
    }

    public boolean isTileInRangeOfUnit(Tile tile){
        for (int i = 1; i < 100; i++){
            if (tile.getAdjacentTilesByLayer(i).contains(tile)){
                return true;
            }
        }
        return false;
    }

}
