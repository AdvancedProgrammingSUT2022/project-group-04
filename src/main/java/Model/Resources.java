package Model;

import Enums.Improvements;

import java.util.ArrayList;

public class Resources {
    private String name;
    private String type;
    private int gold;
    private int foodNum;
    private int production;
    private ArrayList<BaseTerrain> canBeFoundOnBaseTerrain;
    private ArrayList<TerrainFeatures> canBeFoundOnTerrainFeature;
    private Improvement improvementNeeded;

    public Resources(String name) {
        switch (name) {
            case "banana":
                foodNum = 1;
                gold = 0;
                production = 0;
                //TODO set canBeFoundOnTerrainFeature && canBeFoundOnBaseTerrain && improvementNeeded
                break;
            case "Cow":
                //TODO ...

        }
    }
}
