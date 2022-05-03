package Model;

public class Soldier extends Unit {
    private int range;
    private String combatType;
    private Technology requiredTechnology;
    private Resources requiredResources;
    private int rangedCombatStrength;
    private int combatStrength;

    public Soldier(int x, int y, int vX, int vY, int power, int cost, int movementPoint, String unitType, boolean isSleeping, boolean isReady, String era, int HP, int civilizationIndex) {
        super(x, y, vX, vY, power, cost, movementPoint, unitType, isSleeping, isReady, era, HP, civilizationIndex);
        switch (unitType) {
            case "Archer":
                this.range = 2;
                this.combatType = "Archery";
                this.combatStrength = 4;
                this.rangedCombatStrength = 6;
                this.requiredTechnology = new Technology("Archery");
                break;
            case "ChariotArcher":
                this.range = 4;
                this.combatType = "Mounted";
                this.combatStrength = 3;
                this.rangedCombatStrength = 6;
                this.requiredTechnology = new Technology("TheWheel");
                this.requiredResources = new Resources("Horse");
                break;
            case "Scout":
                this.range = 2;
                this.combatType = "Recon";
                this.combatStrength = 4;
                this.rangedCombatStrength = 0;
                break;
            case "Settler":
            case "Worker":
                this.range = 2;
                this.combatType = "Civilian";
                this.combatStrength = 0;
                this.rangedCombatStrength = 0;
                break;
            case "Spearman":
                this.range = 2;
                this.combatType = "Melee";
                this.combatStrength = 7;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("BronzeWorking");
                break;
            case "Warrior":
                this.range = 2;
                this.combatType = "Archery";
                this.combatStrength = 6;
                this.rangedCombatStrength = 0;
                break;
            case "Catapult":
                this.range = 2;
                this.combatType = "Siege";
                this.combatStrength = 4;
                this.rangedCombatStrength = 14;
                this.requiredTechnology = new Technology("Mathematics");
                this.requiredResources = new Resources("Iron");
                break;
            case "Horseman":
                this.range = 4;
                this.combatType = "Mounted";
                this.combatStrength = 12;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("HorsebackRiding");
                this.requiredResources = new Resources("Horse");
                break;
            case "Swordsman":
                this.range = 2;
                this.combatType = "Melee";
                this.combatStrength = 11;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("IronWorking");
                this.requiredResources = new Resources("Iron");
                break;
            case "Crossbowman":
                this.range = 2;
                this.combatType = "Archery";
                this.combatStrength = 6;
                this.rangedCombatStrength = 12;
                this.requiredTechnology = new Technology("Machinery");
                break;
            case "Knight":
                this.range = 3;
                this.combatType = "Mounted";
                this.combatStrength = 18;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("Chivalry");
                this.requiredResources = new Resources("Horse");
                break;
            case "Longswordsman":
                this.range = 3;
                this.combatType = "Melee";
                this.combatStrength = 0;
                this.rangedCombatStrength = 0;
                this.requiredResources = new Resources("Iron");
                this.requiredTechnology = new Technology("Steel");
                break;
            case "Pikeman":
                this.range = 2;
                this.combatType = "Melee";
                this.combatStrength = 0;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("CivilService");
                break;
            case "Trebuchet":
                this.range = 2;
                this.combatType = "Siege";
                this.combatStrength = 20;
                this.rangedCombatStrength = 2;
                this.requiredResources = new Resources("Iron");
                this.requiredTechnology = new Technology("Physics");
                break;
            case "Canon":
                this.range = 2;
                this.combatType = "Siege";
                this.combatStrength = 10;
                this.rangedCombatStrength = 26;
                this.requiredTechnology = new Technology("Chemistry");
                break;
            case "Cavalry":
                this.range = 3;
                this.combatType = "Mounted";
                this.combatStrength = 25;
                this.rangedCombatStrength = 0;
                this.requiredResources = new Resources("Horse");
                this.requiredTechnology = new Technology("MilitaryScience");
                break;
            case "Lancer":
                this.range = 4;
                this.combatType = "Mounted";
                this.combatStrength = 22;
                this.rangedCombatStrength = 0;
                this.requiredResources = new Resources("Horse");
                this.requiredTechnology = new Technology("Metallurgy");
                break;
            case "Musketman":
                this.range = 2;
                this.combatType = "Gunpowder";
                this.combatStrength = 16;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("Gunpowder");
                break;
            case "Rifleman":
                this.range = 2;
                this.combatType = "Gunpowder";
                this.combatStrength = 25;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("Rifling");
                break;
            case "AntiTankGun":
                this.range = 2;
                this.combatType = "Gunpowder";
                this.combatStrength = 32;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("ReplaceableParts");
                break;
            case "Artillery":
                this.range = 2;
                this.combatType = "Siege";
                this.combatStrength = 16;
                this.rangedCombatStrength = 32;
                this.requiredTechnology = new Technology("Dynamite");
                break;
            case "Infantry":
                this.range = 2;
                this.combatType = "Gunpowder";
                this.combatStrength = 36;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("ReplaceableParts");
                break;
            case "Panzer":
                this.range = 5;
                this.combatType = "Armored";
                this.combatStrength = 60;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("Combustion");
                break;
            case "Tank":
                this.range = 4;
                this.combatType = "Armored";
                this.combatStrength = 50;
                this.rangedCombatStrength = 0;
                this.requiredTechnology = new Technology("Combustion");
                break;
            default:
                break;
        }
    }

    public void attackUnit(Unit unit){
        for (double i = 1; i < 10; i++) {
            if (this.HP == i) {
                this.combatStrength -= ((10 - i) / 20) * this.combatStrength;
                break;
            }
        }
        unit.setHP(unit.getHP() - this.combatStrength);
    }
    public void attackCity(City city){
        for (double i = 1; i < 10; i++) {
            if (this.HP == i) {
                this.combatStrength -= ((10 - i) / 20) * this.combatStrength;
                break;
            }
        }
        city.setHP(city.getHP() - this.combatStrength);
    }

    public boolean isCombatUnit(){
        return true;
    }




}
