package Civilization.Model;

import java.util.ArrayList;

public class StrategicsResources extends Resources {

    private Technology technologyNeededForVisibility;
    private ArrayList<Building> relatedBuilding;

    public StrategicsResources(String name) {
        super(name);
        this.relatedBuilding = new ArrayList<Building>();
        switch (name) {
            case "Coal":
                technologyNeededForVisibility = new Technology("ScientificTheory");
                relatedBuilding.add(new Building("Factory"));
                break;
            case "Horse":
                technologyNeededForVisibility = new Technology("AnimalHusbandry");
                relatedBuilding.add(new Building("Stable"));
                relatedBuilding.add(new Building("Circus"));
                break;
            case "Iron":
                technologyNeededForVisibility = new Technology("IronWorking");
                relatedBuilding.add(new Building("Forge"));
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isResourceVisibleForThisCivilization(Civilization civilization) {
        if (!civilization.isImprovementReachedByThisCivilization(this.improvementNeeded.getName())) {
            return true;
        }
        return civilization.isTechnologyForThisCivilization(this.technologyNeededForVisibility);
    }

}

