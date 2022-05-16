package Controller;

import Database.GameDatabase;
import Model.*;

import java.util.ArrayList;
import java.util.Objects;

public class CombatController {
    //will add a combat model class;
    ArrayList<Unit> unitsInWar;

    public void declareWar(Civilization civilization1, Civilization civilization2) {
        civilization1.setInWar(true);
        civilization2.setInWar(true);
        civilization1.setIsInWarWith(civilization2);
        civilization2.setIsInWarWith(civilization1);
    }

    public boolean areInWar(Civilization civilization1, Civilization civilization2) {
        return civilization1.getIsInWarWith().equals(civilization2)
                && civilization2.getIsInWarWith().equals(civilization1);

    }

    public void takeHostage(ArrayList<Citizen> citizens) {
        for (Citizen citizen : citizens) {
            citizen.setHostage(true);
        }
    }

    public boolean UnitAttackPosition(Unit unit1, int x, int y) {
        City cityOfPosition = GameDatabase.getCityByXAndY(x, y);
        if (unit1 instanceof Soldier) {
            Soldier soldier1 = (Soldier) unit1;
            if (!soldier1.isTileInRangeOfUnit(GameDatabase.getTileByXAndY(x,y))){
                return false;
            }
            boolean won = true;
            if (soldier1.getRange() == 0) {
                //melee attack
                ((Soldier) unit1).attackCityMelee(cityOfPosition);
                cityOfPosition.attackUnit(unit1);
            } else {
                //longRange attack
                ((Soldier) unit1).attackCityRanged(cityOfPosition);

                cityOfPosition.attackUnit(unit1);
            }
            if (cityOfPosition.getHP() > 0) {
                won = false;
            } else {
                dastneshandeCity(GameDatabase.getCivilizationByTurn(unit1.getCivilizationIndex()), cityOfPosition);
            }
            if (won) {
                //checkTerrainBonus();
                unit1.moveUnitFromTo(unit1, unit1.getTileOfUnit(), GameDatabase.getTileByXAndY(x, y));
            }

            return true;
        } else {
            return false;
        }
    }

    public void checkTerrainBonus(Tile tile1, Tile tile2) {

    }

    public void DefendCity(City city, Soldier soldier){


    }

    public void addXP(ArrayList<Unit> unitsInWar, int XP) {
        for (Unit unit : unitsInWar) {
            unit.setXP(unit.getXP() + XP);
        }
    }

    public void healUnit(Unit unit) {
        Tile currentTile = GameDatabase.getTileByXAndY(unit.getX(), unit.getY());
        Civilization currentCivilization = GameDatabase.getCivilizationByTile(currentTile);
        if (currentCivilization != null && currentCivilization.getAllUnitsOfCivilization().contains(unit)) {
            if (currentTile.getCity() == null) unit.regainHP(2);
            else unit.regainHP(3);
        } else {
            unit.regainHP(1);
        }
    }

    public void fortifyUnit(Unit unit) {
        int a = 3; //todo find the amount to add to the power of unit
        unit.setCombatStrength(unit.getCombatStrength() + a);
    }

    public void killUnit(Unit unit) {
        unit.getTileOfUnit().removeUnit(unit);
        // ToDO think there should be more to this funciton
    }

    public boolean getSiegeUnitReady(Soldier soldier) {
        if (soldier.getCombatType().equals("Siege")){
            soldier.setSiegeReady(1);
            return true;
        }
        else {
            return false;
        }
    }

    public void destroyCity(City city){
        if (city.getCitizens() != null) city.getCitizens().clear();
        if (city.getSettler() != null) city.removeSettler(city.getSettler());
        if (city.getWorker() != null) city.removeWorker(city.getWorker());
        if (city.getBuildings() != null) city.getBuildings().clear();
        for (int i = 0;i<city.getTiles().size();i++){
            if (city.getTiles().get(i).hasRoad()) city.getTiles().get(i).setRoadBroken(true);
            if (city.getTiles().get(i).hasRailroad()) city.getTiles().get(i).setRailroadBroken(true);
            if (city.getTiles().get(i).getImprovements() != null) city.getTiles().get(i).getImprovements().clear();
        }
    }

    public void zamimeCity(Civilization civilization, City city){
        civilization.addCity(city);
        //Todo niaz be sepehr hast
    }

    public void dastneshandeCity(Civilization civilization, City city){
        civilization.getDastneshandeCities().add(city);
    }

}
