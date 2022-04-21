package Model;

import Enums.TerrainFeature;

import java.util.ArrayList;

public class BaseTerrain {

    private String type;
    private int gold;
    private int foodNum;
    private int production;
    private int changesInCombat;
    private int movementPrice;
    private ArrayList<TerrainFeatures> possibleFeatures;
    private ArrayList<Resources> possibleResources;
    private boolean movementIsPossible;

    public BaseTerrain(String type) {
        switch (type) {
            case "Desert":
                foodNum = 0;
                gold = 0;
                production = 0;
                changesInCombat = -33;
                movementPrice = 1;
                movementIsPossible = true;
                break;
            case "Meadow":
                foodNum = 2;
                gold = 0;
                production = 0;
                changesInCombat = -33;
                movementPrice = 1;
                movementIsPossible = true;
                break;
            case "Hill":
                foodNum = 0;
                gold = 0;
                production = 2;
                changesInCombat = 25;
                movementPrice = 2;
                movementIsPossible = true;
                break;
            case "Mountain":
                foodNum = 0;
                gold = 0;
                production = 0;
                changesInCombat = 0;
                movementPrice = 0;
                movementIsPossible = false;
                break;
            case "Ocean":
                foodNum = 0;
                gold = 0;
                production = 0;
                changesInCombat = 0;
                movementPrice = 0;
                movementIsPossible = false;
                break;
            case "Plain":
                foodNum = 1;
                gold = 0;
                production = 1;
                changesInCombat = -33;
                movementPrice = 1;
                movementIsPossible = true;
                break;
            case "Snow":
                foodNum = 0;
                gold = 0;
                production = 0;
                changesInCombat = -33;
                movementPrice = 1;
                movementIsPossible = true;
                break;
            case "Tundra":
                foodNum = 1;
                gold = 0;
                production = 0;
                changesInCombat = -33;
                movementPrice = 1;
                movementIsPossible = true;
                break;
        }
        this.type = type;
    }

    public String getType(){
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

    public ArrayList<TerrainFeatures> getPossibleFeatures() {
        return this.possibleFeatures;
    }

    public boolean IsMovementPossible() {
        return this.movementIsPossible;
    }
}
