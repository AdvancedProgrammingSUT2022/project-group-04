package Civilization.Model;

import Civilization.Database.GameDatabase;
import Civilization.Database.GlobalVariables;

import java.util.ArrayList;

public class Building {

    private String name;
    private int cost;
    private int maintenance;
    private Technology technologyRequired;
    private ArrayList<Citizen> exports;
    private int turnsNeedToBuild;
    private String cityName;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Building(String name) {
        this.name = name;
        this.exports = new ArrayList<Citizen>();
        this.turnsNeedToBuild = 10;
        switch (name) {
            case "Barracks":
                this.cost = 80;
                this.maintenance = 1;
                this.technologyRequired = new Technology("BronzeWorking");
                break;
            case "Granary":
                this.cost = 100;
                this.maintenance = 1;
                this.technologyRequired = new Technology("Pottery");
                break;
            case "Library":
                this.cost = 80;
                this.maintenance = 1;
                this.technologyRequired = new Technology("Writing");
                break;
            case "Monument":
                this.cost = 60;
                this.maintenance = 1;
                this.technologyRequired = null;
                break;
            case "Walls":
                this.cost = 100;
                this.maintenance = 1;
                this.technologyRequired = new Technology("Masonry");
                break;
            case "Water_Mill":
                this.cost = 120;
                this.maintenance = 2;
                this.technologyRequired = new Technology("TheWheel");
                break;
            case "Armory":
                this.cost = 130;
                this.maintenance = 3;
                this.technologyRequired = new Technology("IronWorking");
                break;
            case "Burial_Tomb":
                this.cost = 120;
                this.maintenance = 0;
                this.technologyRequired = new Technology("Philosophy");
                break;
            case "Circus,":
                this.cost = 150;
                this.maintenance = 3;
                this.technologyRequired = new Technology("HorsebackRiding");
                break;
            case "Colosseum":
                this.cost = 150;
                this.maintenance = 3;
                this.technologyRequired = new Technology("Construction");
                break;
            case "Courthouse":
                this.cost = 200;
                this.maintenance = 5;
                this.technologyRequired = new Technology("Mathematics");
                break;
            case " Stable":
                this.cost = 100;
                this.maintenance = 1;
                this.technologyRequired = new Technology("HorsebackRiding");
                break;
            case "Temple":
                this.cost = 120;
                this.maintenance = 2;
                this.technologyRequired = new Technology("Philosophy");
                break;
            case "Castle":
                this.cost = 200;
                this.maintenance = 3;
                this.technologyRequired = new Technology("Chivalry");
                break;
            case "Forge":
                this.cost = 150;
                this.maintenance = 2;
                this.technologyRequired = new Technology("MetalCasting");
                break;
            case "Garden":
                this.cost = 120;
                this.maintenance = 2;
                this.technologyRequired = new Technology("Theology");
                break;
            case "Market":
                this.cost = 120;
                this.maintenance = 0;
                this.technologyRequired = new Technology("Currency");
                break;
            case "Mint":
                this.cost = 120;
                this.maintenance = 0;
                this.technologyRequired = new Technology("Currency");
                break;
            case "Monastery":
                this.cost = 120;
                this.maintenance = 2;
                this.technologyRequired = new Technology("Theology");
                break;
            case "University":
                this.cost = 200;
                this.maintenance = 3;
                this.technologyRequired = new Technology("Education");
                break;
            case "Workshop":
                this.cost = 100;
                this.maintenance = 2;
                this.technologyRequired = new Technology("MetalCasting");
                break;
            case "Bank":
                this.cost = 220;
                this.maintenance = 0;
                this.technologyRequired = new Technology("Banking");
                break;
            case "MilitaryAcademy":
                this.cost = 350;
                this.maintenance = 3;
                this.technologyRequired = new Technology("MilitaryScience");
                break;
            case "Museum":
                this.cost = 350;
                this.maintenance = 3;
                this.technologyRequired = new Technology("Archaeology");
                break;
            case "OperaHouse":
                this.cost = 220;
                this.maintenance = 3;
                this.technologyRequired = new Technology("Acoustics");
                break;
            case "PublicSchool":
                this.cost = 350;
                this.maintenance = 3;
                this.technologyRequired = new Technology("ScientificTheory");
                break;
            case "SatrapsCourt":
                this.cost = 220;
                this.maintenance = 0;
                this.technologyRequired = new Technology("Banking");
                break;
            case "Theater":
                this.cost = 300;
                this.maintenance = 5;
                this.technologyRequired = new Technology("PrintingPress");
                break;
            case "Windmill":
                this.cost = 180;
                this.maintenance = 2;
                this.technologyRequired = new Technology("Economics");
                break;
            case "Arsenal":
                this.cost = 350;
                this.maintenance = 3;
                this.technologyRequired = new Technology("Railroad");
                break;
            case "BroadcastTower":
                this.cost = 600;
                this.maintenance = 3;
                this.technologyRequired = new Technology("Radio");
                break;
            case "Factory":
                this.cost = 300;
                this.maintenance = 3;
                this.technologyRequired = new Technology("SteamPower");
                break;
            case "Hospital":
                this.cost = 400;
                this.maintenance = 2;
                this.technologyRequired = new Technology("Biology");
                break;
            case "MilitaryBase":
                this.cost = 450;
                this.maintenance = 4;
                this.technologyRequired = new Technology("Telegraph");
                break;
            case "StockExchange":
                this.cost = 650;
                this.maintenance = 0;
                this.technologyRequired = new Technology("Electricity");
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

    public int getMaintenance() {
        return maintenance;
    }

    public void setTurnsNeedToBuild(int production, int productionGeneratingRate) {
        int turns = production - cost;
        if (turns >= 0) {
            this.turnsNeedToBuild = 0;
        } else {
            turns = -turns;
            this.turnsNeedToBuild = (turns / (productionGeneratingRate+1) + 1)/GameModel.speed;

        }
    }

    public void setTurnsNeedToBuild(int amount) {
        this.turnsNeedToBuild = amount;
    }

    @Override
    public String toString() {
        String result = this.name;
        if (wasBuilt()) {
            result += " was built.";
            return result;
        }
        result += " " + Integer.toString(this.turnsNeedToBuild) + " turns need to build.";
        return result;
    }

    public String getInformation() {
        String result = name + "\n Turns need to build: " + turnsNeedToBuild;
        result += "\n Cost: " + cost;
        if(technologyRequired != null) {
            result += "\n Technology Required: " + technologyRequired.getName();
        }
        return result;

    }

    public Technology getTechnologyRequired() {
        return technologyRequired;
    }

    public ArrayList<Citizen> getExports() {
        return exports;
    }

    public boolean isBuildingValidForCivilization(Civilization civilization, City citySelected) {
        if (!isBuildingValidBecauseOfRequiredBuildings(citySelected)) {
            return false;
        }
        if (!isBuildingValidBecauseOfRequiredResources(citySelected)) {
            return false;
        }
        if (technologyRequired == null && civilization.getGold() >= this.cost) {
            return true;
        }
        return civilization.isTechnologyForThisCivilization(this.technologyRequired)
                && civilization.getGold() >= this.cost;
    }

    private boolean isBuildingValidBecauseOfRequiredResources(City citySelected) {
        if (this.name.equals("Circus")
                && (!citySelected.isResourceDiscoveredByThisCity("Horse")
                && !citySelected.isResourceDiscoveredByThisCity("Ivory"))) {
            return false;
        }
        if (this.name.equals("Stable") && !citySelected.isResourceDiscoveredByThisCity("Horse")) {
            return false;
        }
        if (this.name.equals("Forge") && !citySelected.isResourceDiscoveredByThisCity("Iron")) {
            return false;
        }
        if (this.name.equals("Factory") && !citySelected.isResourceDiscoveredByThisCity("Coal")) {
            return false;
        }
        return true;
    }

    private boolean isBuildingValidBecauseOfRequiredBuildings(City citySelected) {
        if ((this.name.equals("Water_Mill") || this.name.equals("Garden"))
                && !isWaterMillValidForThisCity(citySelected)) {
            return false;
        }
        if (this.name.equals("Armory") && !citySelected.cityHasBuilding("Barracks")) {
            return false;
        }
        if (this.name.equals("Temple") && !citySelected.cityHasBuilding("Monument")) {
            return false;
        }
        if (this.name.equals("Castle") && !citySelected.cityHasBuilding("Walls")) {
            return false;
        }
        if (this.name.equals("University") && !citySelected.cityHasBuilding("Library")) {
            return false;
        }
        if (this.name.equals("Bank") && !citySelected.cityHasBuilding("Market")) {
            return false;
        }
        if (this.name.equals("MilitaryAcademy") && !citySelected.cityHasBuilding("Barracks")) {
            return false;
        }
        if (this.name.equals("Museum") && !citySelected.cityHasBuilding("OperaHouse")) {
            return false;
        }
        if (this.name.equals("OperaHouse")
                && (!citySelected.cityHasBuilding("Temple") || !citySelected.cityHasBuilding("Burial_Tomb"))) {
            return false;
        }
        if (this.name.equals("PublicSchool") && !citySelected.cityHasBuilding("University")) {
            return false;
        }
        if (this.name.equals("SatrapsCourt") && !citySelected.cityHasBuilding("Market")) {
            return false;
        }
        if (this.name.equals("Theater") && !citySelected.cityHasBuilding("Colosseum")) {
            return false;
        }
        if (this.name.equals("Windmill") && !isWindMillValidForThisCity(citySelected)) {
            return false;
        }
        if (this.name.equals("Arsenal") && !citySelected.cityHasBuilding(" MilitaryAcademy")) {
            return false;
        }
        if (this.name.equals("BroadcastTower") && !citySelected.cityHasBuilding("Museum")) {
            return false;
        }
        if (this.name.equals("MilitaryBase") && !citySelected.cityHasBuilding("Castle")) {
            return false;
        }
        if (this.name.equals("StockExchange")
                && (!citySelected.cityHasBuilding("Bank") && !citySelected.cityHasBuilding("SatrapsCourt"))) {
            return false;
        }
        return true;
    }

    private boolean isWaterMillValidForThisCity(City citySelected) {
        for (boolean isRiver : citySelected.getIsRiver()) {
            if (isRiver) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Building)) {
            return false;
        }
        return this.name.equals(((Building) obj).getName());
    }

    private boolean isWindMillValidForThisCity(City selectedCity) {
        return !selectedCity.getBaseTerrainType().equals("Hill");
    }

    public int getTurnsNeedToBuild() {
        return turnsNeedToBuild;
    }

    public void build() {
        this.turnsNeedToBuild--;
        if (wasBuilt()) {
            sendNotification();
        }
    }

    private void sendNotificationInCLI() {
        String source = GlobalVariables.SYSTEM_NAME;
        String text = "Building " + this.name + " was built on city " + this.cityName;
        String destination = GameDatabase.getCivilizationForCity(cityName).getNickname();
        Notification notification = new Notification(source, destination, text);
        Notification.addNotification(notification);
    }

    private void sendNotification() {
        Civilization civilization = GameDatabase.getCivilizationForCity(cityName);
        String message = GlobalVariables.SYSTEM_NAME + " notification:\n";
        message += "\tBuilding " + this.name + " was built on city " + this.cityName;
        civilization.getMessages().add(message);

    }

    public boolean wasBuilt() {
        return this.turnsNeedToBuild == 0;
    }

    private int libraryScienceCalculator() {
        int population = (GameDatabase.getCityByName(this.cityName).getWorker() != null ? 1 : 0)
                + (GameDatabase.getCityByName(this.cityName).getSettler() != null ? 1 : 0);
        return population / 2;
    }

    public void nextTurn() {
        GameDatabase.getCivilizationForCity(this.cityName).addGold(-this.maintenance);
        switch (this.name) {
            case "Barracks":
                //TODO
                break;
            case "Granary":
                GameDatabase.getCityByName(this.cityName).addFood(2);
                break;
            case "Library":
                GameDatabase.getCivilizationForCity(this.cityName).addScience(libraryScienceCalculator());
                break;
            case "Monument":
                break;
            case "Walls":
                //TODO
                break;
            case "Water_Mill":
                GameDatabase.getCityByName(this.cityName).addFood(2);
                break;
            case "Armory":
                //TODO
                break;
            case "Burial_Tomb":
                GameDatabase.getCivilizationForCity(this.cityName).addHappiness(2);
                //TODO
                break;
            case "Circus":
                GameDatabase.getCivilizationForCity(this.cityName).addHappiness(3);
                break;
            case "Colosseum":
                GameDatabase.getCivilizationForCity(this.cityName).addHappiness(4);
                break;
            case "Courthouse":
                //TODO
                break;
            case "Stable":
                //TODO
                break;
            case "Temple":
                break;
            case "Castle":
                // TODO
                break;
            case "Forge":
                // TODO
                break;
            case "Garden":
                // TODO
                break;
            case "Market":
                GameDatabase.getCivilizationForCity(this.cityName).addGold(GameDatabase.getCivilizationForCity(this.cityName).getGold() / 4);
                break;
            case "Mint":
                // TODO
                break;
            case "Monastery":
                break;
            case "University":
                GameDatabase.getCivilizationForCity(this.cityName).addScience(Math.abs(GameDatabase.getCivilizationForCity(this.cityName).getScience() / 2));
                break;
            case "Workshop":
                // TODO
                break;
            case "Bank":
                GameDatabase.getCivilizationForCity(this.cityName).addGold(GameDatabase.getCivilizationForCity(this.cityName).getGold() / 4);
                break;
            case "MilitaryAcademy":
                // TODO
                break;
            case "Museum":
                break;
            case "OperaHouse":
                break;
            case "PublicSchool":
                GameDatabase.getCivilizationForCity(this.cityName).addScience(Math.abs(GameDatabase.getCivilizationForCity(this.cityName).getScience() / 2));
                break;
            case "SatrapsCourt":
                GameDatabase.getCivilizationForCity(this.cityName).addGold(GameDatabase.getCivilizationForCity(this.cityName).getGold() / 4);
                GameDatabase.getCivilizationForCity(this.cityName).addHappiness(2);
                break;
            case "Theater":
                GameDatabase.getCivilizationForCity(this.cityName).addHappiness(4);
                break;
            case "Windmill":
                // TODO
                break;
            case "Arsenal":
                // TODO
                break;
            case "BroadcastTower":
                break;
            case "Factory":
                // TODO
                break;
            case "Hospital":
                GameDatabase.getCityByName(this.cityName).addFood(-GameDatabase.getCityByName(this.cityName).getFoodGeneratingRate() / 2);
                break;
            case "MilitaryBase":
                // TODO
                break;
            case "StockExchange":
                GameDatabase.getCivilizationForCity(this.cityName).addGold(GameDatabase.getCivilizationForCity(this.cityName).getGold() / 3);
                break;
            default:
                break;
        }
    }
}

