package Civilization.Model;

import Civilization.Database.GameDatabase;
import Civilization.Database.GlobalVariables;

import java.util.ArrayList;

public class Civilization {
    private String username;
    private String nickname;
    private int score;
    private int turn;
    private ArrayList<Tile> tiles;
    private ArrayList<Tile> visibleTiles;
    private ArrayList<City> cities;
    private ArrayList<City> dastneshandeCities;
    private int gold;
    private ArrayList<Technology> technologies;
    private int happiness;
    private int science;
    private boolean isInWar = false;
    private Civilization isInWarWith;
    private Unit selectedUnit;
    private City selectedCity;
    private boolean acceptingPeace;

    public boolean isAcceptingPeace() {
        return acceptingPeace;
    }

    public void setAcceptingPeace(boolean acceptingPeace) {
        this.acceptingPeace = acceptingPeace;
    }

    public void setSelectedCity(City selectedCity) {
        this.selectedCity = selectedCity;
    }

    public ArrayList<Building> getValidBuildings() {
        ArrayList<Building> validBuildings = new ArrayList<>();
        if(selectedCity == null) {
            return validBuildings;
        }
        for (String buildingName : GlobalVariables.BUILDINGS) {
            Building building = new Building(buildingName);
            if (building.isBuildingValidForCivilization(GameDatabase.players.get(GameDatabase.getTurn()), GameDatabase.players.get(GameDatabase.getTurn()).getSelectedCity())) {
                validBuildings.add(building);
            }
        }
        return validBuildings;
    }

    public City getSelectedCity() {
        return selectedCity;
    }

    private ArrayList<String> messages;

    @Override
    public String toString() {
        String result = this.nickname;
        result += "\n\t Happiness = " + this.happiness;
        result += "\n\t Science = " + this.science;
        result += "\n\t Gold = " + this.gold;
        result += "\n\t Population = " + getPopulation();
        result += "\n\t Size = " + this.tiles.size();
        if(technologies.size() != 0) {
            result += "\n\t Technologies = \n";
            for (Technology technology : technologies) {
                result +=  "\t\t" + technology.getName() + " " + technology.wasReached() + "\n";
            }
        }
        result += "\n\t Total Score = " + getFinalScore();
        return result;
    }

    public Unit getSelectedUnit() {
        return selectedUnit;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setScience(int science) {
        this.science = science;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public Civilization(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
        this.score = 0;
        this.turn = 0;
        this.tiles = new ArrayList<Tile>();
        this.visibleTiles = new ArrayList<Tile>();
        this.cities = new ArrayList<City>();
        this.gold = 0;
        this.technologies = new ArrayList<Technology>();
        this.happiness = 0;
        this.science = 0;
        this.messages = new ArrayList<>();

    }

    public void addHappiness(int amount) {
        this.happiness += amount;
    }

    public ArrayList<Technology> getTechnologies() {
        return technologies;
    }

    public void assignTiles() {

    }

    public void addScore(int amount) {
        this.score += amount;
    }

    public void addScience(int amount) {
        this.science += amount;
    }

    public void addGold(int amount) {
        if (amount < 0 && this.gold <= 0) {
            addScience(amount);
            return;
        }
        this.gold += amount;
    }

    public void addTechnology(Technology newTechnology) {
        this.technologies.add(newTechnology);
        this.science = 0;
    }

    public void setSelectedUnit(Unit selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    public void addTile(Tile newTile) {
        this.tiles.add(newTile);
    }

    public void addCity(City newCity) {
        this.cities.add(newCity);
    }

    public String getUsername() {
        return this.username;
    }

    public String getNickname() {
        return this.nickname;
    }

    public ArrayList<City> getDastneshandeCities() {
        return dastneshandeCities;
    }

    public void setDastneshandeCities(ArrayList<City> dastneshandeCities) {
        this.dastneshandeCities = dastneshandeCities;
    }

    public int getScore() {
        return this.score;
    }

    public int getTurn() {
        return this.turn;
    }

    public ArrayList<Tile> getTiles() {
        return this.tiles;
    }

    public ArrayList<City> getCities() {
        return this.cities;
    }

    public int getGold() {
        return this.gold;
    }

    public boolean isInWar() {
        return isInWar;
    }

    public void setInWar(boolean inWar) {
        isInWar = inWar;
    }

    public Civilization getIsInWarWith() {
        return isInWarWith;
    }

    public void setIsInWarWith(Civilization isInWarWith) {
        this.isInWarWith = isInWarWith;
    }

    public void removeTile(Tile conqueredTile) {
        for (int i = 0; i < this.tiles.size(); i++) {
            if (this.tiles.get(i).equals(conqueredTile)) {
                this.tiles.remove(i);
                return;
            }
        }
    }

    public void removeCity(City conqueredCity) {
        for (int i = 0; i < this.cities.size(); i++) {
            if (this.cities.get(i).equals(conqueredCity)) {
                this.cities.remove(i);
                return;
            }
        }
    }


    public boolean isTileInCivilization(int x, int y) {
        for (Tile tile : tiles) {
            if (tile.getX() == x && tile.getY() == y)
                return true;
        }
        return false;
    }

    public boolean isTechnologyForThisCivilization(Technology technology) {
        for (int i = 0; i < this.technologies.size(); i++) {
            if (this.technologies.get(i).equals(technology)) {
                return true;
            }
        }
        return false;

    }


    public boolean hasResource(Resources resources) {
        for (Tile tile : this.tiles) {
            for (Resources resource : tile.getDiscoveredResources()) {
                if (resource.getName().equals(resources.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Unit> getAllUnitsOfCivilization() {

        ArrayList<Unit> allUnits = new ArrayList<>();
        for (Tile tile : getClearTiles()) {
            allUnits.addAll(tile.getUnits());
        }
        return allUnits;

    }

    public ArrayList<Tile> tilesOnBorder() {
        ArrayList<Tile> tilesOnBorder = new ArrayList<>();
        for (Tile tile : tiles) {
            boolean isBorderTile = false;
            for (Tile adjacent : tile.getAdjacentTiles()) {
                if (!tiles.contains(adjacent)) {
                    isBorderTile = true;
                }
            }
            if (isBorderTile) {
                tilesOnBorder.add(tile);
            }
        }
        return tilesOnBorder;

    }

    public ArrayList<Tile> firstClassAdjacentTiles() {

        ArrayList<Tile> firstClassAdjacentTiles = new ArrayList<>();
        for (Tile tile : tilesOnBorder()) {
            if (!tile.getUnits().isEmpty()) {
                boolean thereIsUnitOBuildingNearby = false;
                for (Tile adj : tilesOnBorder()) {
                    if (adj.getBuildings() != null) {
                        if (!adj.getUnits().isEmpty() || !adj.getBuildings().isEmpty()) {
                            thereIsUnitOBuildingNearby = true;
                        }
                    }
                }
                if (thereIsUnitOBuildingNearby) {
                    for (Tile adjacent : tile.getAdjacentTiles()) {
                        if (!firstClassAdjacentTiles.contains(adjacent)
                                && !tiles.contains(adjacent)) {
                            firstClassAdjacentTiles.add(adjacent);
                        }
                    }
                }
            }
        }
        return firstClassAdjacentTiles;

    }

    public ArrayList<Tile> secondClassAdjacentTiles() {

        ArrayList<Tile> secondClassAdjacentTiles = new ArrayList<>();
        for (Tile tile : firstClassAdjacentTiles()) {
            if (!tile.getBaseTerrainType().equals("Mountain")
                    && !tile.getBaseTerrainType().equals("Hill")
                    && !tile.getBaseTerrain().hasFeature("Jungle")
                    && !tile.getBaseTerrain().hasFeature("DenseJungle")) {
                for (Tile adjacent : tile.getAdjacentTiles()) {
                    if (!secondClassAdjacentTiles.contains(adjacent)
                            && !firstClassAdjacentTiles().contains(adjacent)
                            && !tiles.contains(adjacent)) {
                        secondClassAdjacentTiles.add(adjacent);
                    }
                }
            }
        }

        return secondClassAdjacentTiles;
    }

    public ArrayList<Tile> getClearTiles() {
        ArrayList<Tile> clearTiles = new ArrayList<>();
        clearTiles.addAll(tiles);
        clearTiles.addAll(firstClassAdjacentTiles());
        clearTiles.addAll(secondClassAdjacentTiles());
        visibleTiles.addAll(clearTiles);
        return clearTiles;
    }

    public void maintenanceOfUnits() {
        for (Tile tile : this.getTiles()) {
            for (Unit unit : tile.getUnits()) {
                this.gold -= unit.getMaintenance();
            }
        }
    }

    public void nextTurn() {
        this.turn++;
        happiness += happinessCalculator();
        for (City city : this.cities) {
            city.nextTurn();
            gold += city.getGoldGeneratingRate();
            science += (city.getWorker() != null ? 1 : 0) +
                    (city.getSettler() != null ? 1 : 0) + city.getCitizens().size();
            if (city.isCapital()) {
                science += 3;
            }
        }
        for (Technology technology : this.technologies) {
            technology.nextTurn(this.nickname);
        }
        maintenanceOfUnits();

    }

    public City getCapital() {
        for (City city : this.cities) {
            if (city.isCapital()) {
                return city;
            }
        }
        return null;
    }

    public void changeCapital(String cityName) {
        if (getCapital() != null) {
            getCapital().removeCapital();
        }
        GameDatabase.getCityByName(cityName).setCapital();
    }

    private int happinessCalculator() {
        int population = 0;
        int colonizedCount = 0;
        for (City city : this.cities) {
            population += (city.getSettler() != null ? 1 : 0) +
                    (city.getWorker() != null ? 1 : 0) + city.getCitizens().size();
            if (city.isColonized()) {
                colonizedCount++;
            }
        }
        return -population * GlobalVariables.happinessForEachCitizen
                - this.cities.size() * GlobalVariables.happinessForEachCity
                - colonizedCount * GlobalVariables.happinessForColonizedCities;
    }

    public int getScience() {
        return this.science;
    }

    public boolean isHappy() {
        return happiness >= 0;
    }

    public void happy() {
        if (this.happiness < 0) {
            this.happiness = 0;
        }
    }

    public boolean isTechnologyInCivilization(String technology) {
        for (int i = 0; i < technologies.size(); i++) {
            if (technologies.get(i).getName().equals(technology)) {
                return true;
            }
        }
        return false;
    }

    public boolean isImprovementReachedByThisCivilization(String improvementName) {
        for (Tile tile : this.tiles) {
            if (tile.isImprovementForThisTile(improvementName)) {
                return true;
            }
        }
        return false;
    }

    public boolean isCityInCivilization(int xCity, int yCity) {
        for (City city : cities) {
            if (city.x == xCity && city.y == yCity)
                return true;
        }
        return false;
    }

    public City getCityByXAndY(int xCity, int yCity) {
        for (City city : cities) {
            if (city.x == xCity && city.y == yCity)
                return city;
        }
        return null;
    }


    public boolean isResourceNew(Resources newResource) {
        for (Tile tile : this.tiles) {
            for (Resources resource : tile.getDiscoveredResources()) {
                if (resource.getName().equals(newResource.getName())) {
                    return false;
                }
            }
        }
        return true;
    }

    public Technology getTechnologyUnderResearch(){
        for (Technology technology : technologies) {
            if(!technology.wasReached()) {
                return technology;
            }
        }
        return null;
    }


    public ArrayList<Tile> getFriendlyTiles() {
        ArrayList<Tile> friendlyTiles = new ArrayList<>();
        return friendlyTiles;
    }

    public int getPopulation() {
        int population = 0;
        for (City city : this.cities) {
            population += city.getUnits().size() + city.getCitizens().size();
        }
        return population;
    }

    public int getFinalScore() {
        return this.score + this.tiles.size() + this.cities.size() + getPopulation() + this.technologies.size();
    }

    public void getPrize(Ruin ruin) {
        for (Technology technology : ruin.getTechnologies()) {
            if(!isTechnologyForThisCivilization(technology)) {
                technologies.add(technology);
            }
        }
        this.gold += ruin.getGold();
    }
}
