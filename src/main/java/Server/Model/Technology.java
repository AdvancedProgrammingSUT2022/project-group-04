package Server.Model;

import Client.Database.GameDatabase;
import Client.Database.GlobalVariables;

import java.io.IOException;
import java.util.ArrayList;

public class Technology {

    private String name;
    private int cost;
    private ArrayList<Technology> prerequisiteTechnologies;
    private int turnsNeedToResearch;
    private boolean isStopped;

    public Technology(String name) {
        this.name = name;
        this.turnsNeedToResearch = 10;
        this.prerequisiteTechnologies = new ArrayList<Technology>();
        this.isStopped = false;
        switch (name) {
            case "Agriculture":
                this.cost = 20;
                break;
            case "AnimalHusbandry":
                this.cost = 35;
                this.prerequisiteTechnologies.add(new Technology("Agriculture"));
                break;
            case "Archery":
                this.cost = 35;
                this.prerequisiteTechnologies.add(new Technology("Agriculture"));
                break;
            case "BronzeWorking":
                this.cost = 55;
                this.prerequisiteTechnologies.add(new Technology("Mining"));
                break;
            case "Calendar":
                this.cost = 70;
                this.prerequisiteTechnologies.add(new Technology("Pottery"));
                break;
            case "Masonry":
                this.cost = 55;
                this.prerequisiteTechnologies.add(new Technology("Mining"));
                break;
            case "Mining":
                this.cost = 35;
                this.prerequisiteTechnologies.add(new Technology("Agriculture"));
                break;
            case "Pottery":
                this.cost = 35;
                this.prerequisiteTechnologies.add(new Technology("Agriculture"));
                break;
            case "TheWheel":
                this.cost = 55;
                this.prerequisiteTechnologies.add(new Technology("AnimalHusbandry"));
                break;
            case "Trapping":
                this.cost = 55;
                this.prerequisiteTechnologies.add(new Technology("AnimalHusbandry"));
                break;
            case "Writing":
                this.cost = 55;
                this.prerequisiteTechnologies.add(new Technology("Pottery"));
                break;
            case "Construction":
                this.cost = 100;
                this.prerequisiteTechnologies.add(new Technology("Masonry"));
                break;
            case "HorsebackRiding":
                this.cost = 100;
                this.prerequisiteTechnologies.add(new Technology("TheWheel"));
                break;
            case "IronWorking":
                this.cost = 159;
                this.prerequisiteTechnologies.add(new Technology("BronzeWorking"));
                break;
            case "Mathematics":
                this.cost = 100;
                this.prerequisiteTechnologies.add(new Technology("TheWheel"));
                this.prerequisiteTechnologies.add(new Technology("Archery"));
                break;
            case "Philosophy":
                this.cost = 100;
                this.prerequisiteTechnologies.add(new Technology("Writing"));
                break;
            case "Chivalry":
                this.cost = 440;
                this.prerequisiteTechnologies.add(new Technology("CivilService"));
                this.prerequisiteTechnologies.add(new Technology("HorsebackRiding"));
                this.prerequisiteTechnologies.add(new Technology("Currency"));
                break;
            case "CivilService":
                this.cost = 400;
                this.prerequisiteTechnologies.add(new Technology("Philosophy"));
                this.prerequisiteTechnologies.add(new Technology("Trapping"));
                break;
            case "Currency":
                this.cost = 250;
                this.prerequisiteTechnologies.add(new Technology("Mathematics"));
                break;
            case "Education":
                this.cost = 440;
                this.prerequisiteTechnologies.add(new Technology("Theology"));
                break;
            case "Engineering":
                this.cost = 250;
                this.prerequisiteTechnologies.add(new Technology("Mathematics"));
                this.prerequisiteTechnologies.add(new Technology("Construction"));
                break;
            case "Machinery":
                this.cost = 440;
                this.prerequisiteTechnologies.add(new Technology("Engineering"));
                break;
            case "MetalCasting":
                this.cost = 240;
                this.prerequisiteTechnologies.add(new Technology("IronWorking"));
                break;
            case "Physics":
                this.cost = 440;
                this.prerequisiteTechnologies.add(new Technology("Engineering"));
                this.prerequisiteTechnologies.add(new Technology("MetalCasting"));
                break;
            case "Steel":
                this.cost = 440;
                this.prerequisiteTechnologies.add(new Technology("MetalCasting"));
                break;
            case "Theology":
                this.cost = 250;
                this.prerequisiteTechnologies.add(new Technology("Calendar"));
                this.prerequisiteTechnologies.add(new Technology("Philosophy"));
                break;
            case "Acoustics":
                this.cost = 650;
                this.prerequisiteTechnologies.add(new Technology("Education"));
                break;
            case "Archaeology":
                this.cost = 1300;
                this.prerequisiteTechnologies.add(new Technology("Acoustics"));
                break;
            case "Banking":
                this.cost = 650;
                this.prerequisiteTechnologies.add(new Technology("Education"));
                this.prerequisiteTechnologies.add(new Technology("Chivalry"));
                break;
            case "Chemistry":
                this.cost = 900;
                this.prerequisiteTechnologies.add(new Technology("Gunpowder"));
                break;
            case "Economics":
                this.cost = 900;
                this.prerequisiteTechnologies.add(new Technology("Banking"));
                this.prerequisiteTechnologies.add(new Technology("PrintingPress"));
                break;
            case "Fertilizer":
                this.cost = 1300;
                this.prerequisiteTechnologies.add(new Technology("Chemistry"));
                break;
            case "Gunpowder":
                this.cost = 680;
                this.prerequisiteTechnologies.add(new Technology("Physics"));
                this.prerequisiteTechnologies.add(new Technology("Steel"));
                break;
            case "Metallurgy":
                this.cost = 900;
                this.prerequisiteTechnologies.add(new Technology("Gunpowder"));
                break;
            case "MilitaryScience":
                this.cost = 1300;
                this.prerequisiteTechnologies.add(new Technology("Economics"));
                this.prerequisiteTechnologies.add(new Technology("Chemistry"));
                break;
            case "PrintingPress":
                this.cost = 650;
                this.prerequisiteTechnologies.add(new Technology("Machinery"));
                this.prerequisiteTechnologies.add(new Technology("Physics"));
                break;
            case "Rifling":
                this.cost = 1425;
                this.prerequisiteTechnologies.add(new Technology("Metallurgy"));
                break;
            case "ScientificTheory":
                this.cost = 1300;
                this.prerequisiteTechnologies.add(new Technology("Acoustics"));
                break;
            case "Biology":
                this.cost = 1680;
                this.prerequisiteTechnologies.add(new Technology("Archaeology"));
                this.prerequisiteTechnologies.add(new Technology("ScientificTheory"));
                break;
            case "Combustion":
                this.cost = 2200;
                this.prerequisiteTechnologies.add(new Technology("ReplaceableParts"));
                this.prerequisiteTechnologies.add(new Technology("Railroad"));
                this.prerequisiteTechnologies.add(new Technology("Dynamite"));
                break;
            case "Dynamite":
                this.cost = 1900;
                this.prerequisiteTechnologies.add(new Technology("Fertilizer"));
                this.prerequisiteTechnologies.add(new Technology("Rifling"));
                break;
            case "Electricity":
                this.cost = 1900;
                this.prerequisiteTechnologies.add(new Technology("Biology"));
                this.prerequisiteTechnologies.add(new Technology("SteamPower"));
                break;
            case "Radio":
                this.cost = 2200;
                this.prerequisiteTechnologies.add(new Technology("Electricity"));
                break;
            case "Railroad":
                this.cost = 1900;
                this.prerequisiteTechnologies.add(new Technology("SteamPower"));
                break;
            case "ReplaceableParts":
                this.cost = 1900;
                this.prerequisiteTechnologies.add(new Technology("SteamPower"));
                break;
            case "SteamPower":
                this.cost = 1680;
                this.prerequisiteTechnologies.add(new Technology("ScientificTheory"));
                this.prerequisiteTechnologies.add(new Technology("MilitaryScience"));
                break;
            case "Telegraph":
                this.cost = 2200;
                this.prerequisiteTechnologies.add(new Technology("Electricity"));
                break;
            default:
                break;
        }
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public ArrayList<Technology> getPrerequisiteTechnologies() {
        return prerequisiteTechnologies;
    }

    public void calculateTurnsNeed(Civilization civilization) {
        if (civilization.getScience() - this.cost > 100) {
            this.turnsNeedToResearch = 1;
        } else {
            this.turnsNeedToResearch = 10 - (civilization.getScience() - this.cost) / 10;
        }
    }

    public boolean isInPrerequisiteTechnologies(Technology technology) {
        for (int i = 0; i < this.prerequisiteTechnologies.size(); i++) {
            if (this.prerequisiteTechnologies.get(i).equals(technology)) {
                return true;
            }
        }
        return false;
    }

    public boolean isTechnologyValidForCivilization(Civilization civilization) {
        if (civilization.getScience() <= this.cost) {
            return false;
        }
        for (Technology prerequisiteTechnology : this.prerequisiteTechnologies) {
            if (!civilization.isTechnologyForThisCivilization(prerequisiteTechnology) || prerequisiteTechnology.isStopped()) {
                return false;
            }
        }
        return true;
    }

    public String getInformation() {
        String result = this.name + "\n";
        result += "\t turns need: " + Integer.toString(this.turnsNeedToResearch) + "\n";
        result += "\t cost: " + Integer.toString(this.cost) + "\n";
        result += leadingTechnologies();
        result += leadingBuildings();
        return result;
    }

    public boolean wasReached() {
        return this.turnsNeedToResearch < 0;
    }

    private String leadingTechnologies() {
        String leadingTechnologies = "Leading technologies: \n";
        for (String technology : GlobalVariables.TECHNOLOGIES) {
            Technology technology1 = new Technology(technology);
            for (Technology prerequisiteTechnology : technology1.prerequisiteTechnologies) {
                if (prerequisiteTechnology.getName().equals(this.name)) {
                    leadingTechnologies += "\t " + technology + "\n";
                }
            }
        }
        return leadingTechnologies;
    }

    private String leadingBuildings() {
        String leadingBuildings = "Leading buildings: \n";
        for (String building : GlobalVariables.BUILDINGS) {
            Building building1 = new Building(building);
            if (building1.getTechnologyRequired() == null) {
                continue;
            }
            if (building1.getTechnologyRequired().getName().equals(this.name)) {
                leadingBuildings += "\t " + building + "\n";
            }
        }
        return leadingBuildings;
    }

    public boolean isStopped() {
        return this.isStopped;
    }

    public void stop() {
        isStopped = true;
    }

    public void start() {
        isStopped = false;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Technology)) {
            return false;
        }
        if (((Technology) obj).getName().equals(this.name)) {
            return true;
        }
        return false;
    }

    public void setTurnsNeedToResearch(int turnsNeedToResearch) {
        this.turnsNeedToResearch = turnsNeedToResearch;
    }

    @Override
    public String toString() {
        String result = this.name + "\n";
        if (!wasReached() && this.turnsNeedToResearch != 0) {
            result += "\t turns need: " + Integer.toString(this.turnsNeedToResearch) + "\n";
            result += "\t cost: " + Integer.toString(this.cost);
            if (isStopped) {
                result += "\t Stopped\n";
            }
        } else {
            result += leadingTechnologies();
            result += leadingBuildings();
        }
        return result;
    }

    public void nextTurn(String civilizationName) throws IOException {
        if (!wasReached() && !isStopped) {
            this.turnsNeedToResearch--;
            if (wasReached()) {
                sendNotification(civilizationName);
            }
        }
    }

    private void sendNotificationInCLI(String civilizationName) throws IOException {
        String source = GlobalVariables.SYSTEM_NAME;
        String text = "you reached " + this.name + " technology";
        String destination = GameDatabase.getCivilizationByNickname(civilizationName).getNickname();
        Notification notification = new Notification(source, destination, text);
        Notification.addNotification(notification);
    }

    private void sendNotification(String civilizationName) throws IOException {

        Civilization civilization = GameDatabase.getCivilizationByNickname(civilizationName);
        String message = GlobalVariables.SYSTEM_NAME + " notification:\n";
        message += "\tyou reached " + this.name + " technology";
        civilization.getMessages().add(message);


    }

    public ArrayList<Technology> getLeadingTechnologies() {
        ArrayList<Technology> leadingTechnologies = new ArrayList<>();
        for (String technologyName : GlobalVariables.TECHNOLOGIES) {
            Technology technology = new Technology(technologyName);
            for (Technology prerequisiteTechnology : technology.getPrerequisiteTechnologies()) {
                if(prerequisiteTechnology.getName().equals(this.getName())) {
                    leadingTechnologies.add(technology);
                }
            }
        }
        return leadingTechnologies;
    }

    public static int getMaxLevel() {
        Technology first = new Technology(GlobalVariables.TECHNOLOGIES[0]);
        return first.getLeadingMaxLevel(1);
    }

    private int getLeadingMaxLevel(int level) {
        if(getLeadingTechnologies().size() == 0) {
            return level;
        }
        int maxLevel = 0;
        for (Technology leadingTechnology : getLeadingTechnologies()) {
            int subLevel = leadingTechnology.getLeadingMaxLevel(level + 1);
            if(subLevel > maxLevel) {
                maxLevel = subLevel;
            }
        }
        return maxLevel;
    }
}

