package Server.Model;

import java.util.ArrayList;

public class TerrainFeatures {
    private String type;
    private int gold;
    private int foodNum;
    private int production;
    private int changesInCombat;
    private int movementPrice;
    private ArrayList<Resources> possibleResources;
    private ArrayList<Resources> resources;
    private boolean movementIsPossible;

    public TerrainFeatures(String type) {
        this.type = type;
        this.possibleResources = new ArrayList<Resources>();
        this.resources = new ArrayList<Resources>();
        switch (type) {
            case "Prairie":
                foodNum = 2;
                gold = 0;
                production = 0;
                changesInCombat = -33;
                movementPrice = 1;
                movementIsPossible = true;
                break;
            case "Jungle":
                foodNum = 1;
                gold = 0;
                production = 1;
                changesInCombat = 25;
                movementPrice = 2;
                movementIsPossible = true;
                break;
            case "Ice":
                foodNum = 0;
                gold = 0;
                production = 0;
                changesInCombat = 0;
                movementPrice = 0;
                movementIsPossible = false;
                break;
            case "Dense_Jungle":
                foodNum = 1;
                gold = 0;
                production = -1;
                changesInCombat = 25;
                movementPrice = 2;
                movementIsPossible = true;
                break;
            case "Swamp":
                foodNum = -1;
                gold = 0;
                production = 0;
                changesInCombat = -33;
                movementPrice = 2;
                movementIsPossible = true;
                break;
            case "Oasis":
                foodNum = 3;
                gold = 1;
                production = 0;
                changesInCombat = -33;
                movementPrice = 1;
                movementIsPossible = true;
                break;
            default:
                break;
        }
    }

    public String getType() {
        return this.type;
    }

    public int getFoodNum() {
        return this.foodNum;
    }

    public int getGold() {
        return this.gold;
    }

    public int getProduction() {
        return this.production;
    }

    public int getChangesInCombat() {
        return this.changesInCombat;
    }

    public ArrayList<Resources> getPossibleResources() {
        return this.possibleResources;
    }

    public boolean IsMovementPossible() {
        return this.movementIsPossible;
    }

    public void addResource(Resources resource) {
        this.resources.add(resource);
    }

    public int getMovementPrice() {
        return movementPrice;
    }

    public void setMovementPrice(int movementPrice) {
        this.movementPrice = movementPrice;
    }
}

