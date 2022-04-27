package Model;

import java.util.ArrayList;

public class Building {

    private String name;
    private int cost;
    private int maintenance;
    private Technology technologyRequired;
    private ArrayList<Citizen> exports;

    public Building(String name) {
        this.name = name;
        this.exports = new ArrayList<Citizen>();
        switch (name) {
            case "Barracks":
                this.cost = 80;
                this.maintenance = 1;
                this.technologyRequired = new Technology("BronzeWorking");
            case "Granary":
                this.cost = 100;
                this.maintenance = 1;
                this.technologyRequired = new Technology("Pottery");
            case "Library":
                this.cost = 80;
                this.maintenance = 1;
                this.technologyRequired = new Technology("Writing");
            case "Monument":
                this.cost = 60;
                this.maintenance = 1;
                this.technologyRequired = null;
            case "Walls":
                this.cost = 100;
                this.maintenance = 1;
                this.technologyRequired = new Technology("Masonry");
            case "Water_Mill":
                this.cost = 120;
                this.maintenance = 2;
                this.technologyRequired = new Technology("TheWheel");
            case "Armory":
                this.cost = 130;
                this.maintenance = 3;
                this.technologyRequired = new Technology("IronWorking");
            case "Burial_Tomb":
                this.cost = 120;
                this.maintenance = 0;
                this.technologyRequired = new Technology("Philosophy");
            case "Circus,":
                this.cost = 150;
                this.maintenance = 3;
                this.technologyRequired = new Technology("HorsebackRiding");
            case "Colosseum":
                this.cost = 150;
                this.maintenance = 3;
                this.technologyRequired = new Technology("Construction");
            case "Courthouse":
                this.cost = 200;
                this.maintenance = 5;
                this.technologyRequired = new Technology("Mathematics");
            case " Stable":
                this.cost = 100;
                this.maintenance = 1;
                this.technologyRequired = new Technology("HorsebackRiding");
            case "Temple":
                this.cost = 120;
                this.maintenance = 2;
                this.technologyRequired = new Technology("Philosophy");
            case "Castle":
                this.cost = 200;
                this.maintenance = 3;
                this.technologyRequired = new Technology("Chivalry");
            case "Forge":
                this.cost = 150;
                this.maintenance = 2;
                this.technologyRequired = new Technology("MetalCasting");
            case "Garden":
                this.cost = 120;
                this.maintenance = 2;
                this.technologyRequired = new Technology("Theology");
            case "Market":
                this.cost = 120;
                this.maintenance = 0;
                this.technologyRequired = new Technology("Currency");
            case "Mint":
                this.cost = 120;
                this.maintenance = 0;
                this.technologyRequired = new Technology("Currency");
            case "Monastery":
                this.cost = 120;
                this.maintenance = 2;
                this.technologyRequired = new Technology("Theology");
            case "University":
                this.cost = 200;
                this.maintenance = 3;
                this.technologyRequired = new Technology("Education");
            case "Workshop":
                this.cost = 100;
                this.maintenance = 2;
                this.technologyRequired = new Technology("MetalCasting");
            case "Bank":
                this.cost = 220;
                this.maintenance = 0;
                this.technologyRequired = new Technology("Banking");
            case "MilitaryAcademy":
                this.cost = 350;
                this.maintenance = 3;
                this.technologyRequired = new Technology("MilitaryScience");
            case "Museum":
                this.cost = 350;
                this.maintenance = 3;
                this.technologyRequired = new Technology("Archaeology");
            case "OperaHouse":
                this.cost = 220;
                this.maintenance = 3;
                this.technologyRequired = new Technology("Acoustics");
            case "PublicSchool":
                this.cost = 350;
                this.maintenance = 3;
                this.technologyRequired = new Technology("ScientificTheory");
            case "SatrapsCourt":
                this.cost = 220;
                this.maintenance = 0;
                this.technologyRequired = new Technology("Banking");
            case "Theater":
                this.cost = 300;
                this.maintenance = 5;
                this.technologyRequired = new Technology("PrintingPress");
            case "Windmill":
                this.cost = 180;
                this.maintenance = 2;
                this.technologyRequired = new Technology("Economics");
            case "Arsenal":
                this.cost = 350;
                this.maintenance = 3;
                this.technologyRequired = new Technology("Railroad");
            case "BroadcastTower":
                this.cost = 600;
                this.maintenance = 3;
                this.technologyRequired = new Technology("Radio");
            case "Factory":
                this.cost = 300;
                this.maintenance = 3;
                this.technologyRequired = new Technology("SteamPower");
            case "Hospital":
                this.cost = 400;
                this.maintenance = 2;
                this.technologyRequired = new Technology("Biology");
            case "MilitaryBase":
                this.cost = 450;
                this.maintenance = 4;
                this.technologyRequired = new Technology("Telegraph");
            case "StockExchange":
                this.cost = 650;
                this.maintenance = 0;
                this.technologyRequired = new Technology("Electricity");
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

    public Technology getTechnologyRequired() {
        return technologyRequired;
    }

    public ArrayList<Citizen> getExports() {
        return exports;
    }

    public boolean isBuildingValidForCivilization(Civilization civilization, City citySelected) {
        if(!isBuildingValidBecauseOfRequiredBuildings(citySelected)) {
            return false;
        }
        if (technologyRequired == null && civilization.getGold()>=this.cost) {
            return true;
        }
        return civilization.isTechnologyForThisCivilization(this.technologyRequired)
                && civilization.getGold() >= this.cost;
    }

    private boolean isBuildingValidBecauseOfRequiredBuildings(City citySelected) {
        if((this.name.equals("Water_Mill") || this.name.equals("Garden"))
                && !isWaterMillValidForThisCity(citySelected)) {
            return false;
        } if(this.name.equals("Armory") && !citySelected.cityHasBuilding("Barracks")) {
            return false;
        } if(this.name.equals("Temple") && !citySelected.cityHasBuilding("Monument")) {
            return false;
        } if(this.name.equals("Bank") && !citySelected.cityHasBuilding("Market")) {
            return false;
        } if(this.name.equals("MilitaryAcademy") && !citySelected.cityHasBuilding("Barracks")) {
            return false;
        } if(this.name.equals("Museum") && !citySelected.cityHasBuilding("OperaHouse")) {
            return false;
        } if(this.name.equals("OperaHouse")
                && (!citySelected.cityHasBuilding("Temple") || !citySelected.cityHasBuilding("Burial_Tomb"))) {
            return false;
        } if(this.name.equals("PublicSchool") && !citySelected.cityHasBuilding("University")) {
            return false;
        } if(this.name.equals("SatrapsCourt") && !citySelected.cityHasBuilding("Market")) {
            return false;
        } if(this.name.equals("Theater") && !citySelected.cityHasBuilding("Colosseum")) {
            return false;
        } if(this.name.equals("Windmill") && !isWindMillValidForThisCity(citySelected)) {
            return false;
        } if(this.name.equals("Arsenal") && !citySelected.cityHasBuilding(" MilitaryAcademy")) {
            return false;
        } if(this.name.equals("BroadcastTower") && !citySelected.cityHasBuilding("Museum")) {
            return false;
        } if(this.name.equals("MilitaryBase") && !citySelected.cityHasBuilding("Castle")) {
            return false;
        } if(this.name.equals("StockExchange")
                && (!citySelected.cityHasBuilding("Bank") || !citySelected.cityHasBuilding("SatrapsCourt"))) {
            return false;
        }
        return true;
    }

    private boolean isWaterMillValidForThisCity(City citySelected) {
        for (boolean isRiver : citySelected.getIsRiver()) {
            if(isRiver) {
                return true;
            }
        }
        return false;
    }

    private boolean isWindMillValidForThisCity(City selectedCity) {
        return !selectedCity.getBaseTerrainType().equals("Hill");
    }
}

