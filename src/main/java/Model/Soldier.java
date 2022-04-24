package Model;

public class Soldier extends Unit{
    private int range;
    private String combatType;

    public Soldier(int x, int y, int vX, int vY, int power, int cost, int movementPoint, String unitType, boolean isSleeping, boolean isReady, String era, int HP) {
        super(x, y, vX, vY, power, cost, movementPoint, unitType, isSleeping, isReady, era, HP);
        switch (unitType){
            case "Archer":
                this.range = ;
                this.combatType = "Archery";
                break;
            case "ChariotArcher":
                this.range = ;
                this.combatType = "Mounted";
                break;
            case "Scout":
                this.range = ;
                this.combatType = "Recon";
                break;
            case "Settler":
                this.range = ;
                this.combatType = "Civilian";
                break;
            case "Spearman":
                this.range = ;
                this.combatType = "Melee";
                break;
            case "Warrior":
                this.range = ;
                this.combatType = "Archery";
                break;
            case "Worker":
                this.range = ;
                this.combatType = "Civilian";
                break;
            case "Catapult":
                this.range = ;
                this.combatType = "Siege";
                break;
            case "Horseman":
                this.range = ;
                this.combatType = "Mounted";
                break;
            case "Swordsman":
                this.range = ;
                this.combatType = "Melee";
                break;
            case "Crossbowman":
                this.range = ;
                this.combatType = "Archery";
                break;
            case "Knight":
                this.range = ;
                this.combatType = "Mounted";
                break;
            case "Longswordsman":
                this.range = ;
                this.combatType = "Melee";
                break;
            case "Pikeman":
                this.range = ;
                this.combatType = "Melee";
                break;
            case "Trebuchet":
                this.range = ;
                this.combatType = "Siege";
                break;
            case "Canon":
                this.range = ;
                this.combatType = "Siege";
                break;
            case "Cavalry":
                this.range = ;
                this.combatType = "Mounted";
                break;
            case "Lancer":
                this.range = ;
                this.combatType = "Mounted";
                break;
            case "Musketman":
                this.range = ;
                this.combatType = "Gunpowder";
                break;
            case "Rifleman":
                this.range = ;
                this.combatType = "Gunpowder";
                break;
            case "AntiTankGun":
                this.range = ;
                this.combatType = "Gunpowder";
                break;
            case "Artillery":
                this.range = ;
                this.combatType = "Siege";
                break;
            case "Infantry":
                this.range = ;
                this.combatType = "Gunpowder";
                break;
            case "Panzer":
                this.range = ;
                this.combatType = "Armored";
                break;
            case "Tank":
                this.range = ;
                this.combatType = "Armored";
                break;
            default:
                break;
        }
    }


}
