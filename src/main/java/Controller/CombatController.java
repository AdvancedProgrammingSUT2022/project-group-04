package Controller;

import Database.GameDatabase;
import Model.*;

import java.util.ArrayList;

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
        ArrayList<Unit> unitsInPosition = GameDatabase.getTileByXAndY(x, y).getUnits();
        if (unit1 instanceof Soldier) {
            Soldier soldier1 = (Soldier) unit1;
            boolean won = true;
            if (soldier1.getRange() == 0) {
                //melee attack
                for (Unit unit : unitsInPosition) {
                    ((Soldier) unit1).attackUnitMelee(unit);
                }
                ((Soldier) unit1).attackCityMelee(cityOfPosition);
            } else {
                //longRange attack
                for (Unit unit : unitsInPosition) {
                    ((Soldier) unit1).attackUnitRanged(unit);
                }
                ((Soldier) unit1).attackCityRanged(cityOfPosition);
            }
            for (Unit unit : unitsInPosition) {
                if (unit.getHP() > 0) {
                    won = false;
                } else {
                    killUnit(unit);
                }
            }
            if (cityOfPosition.getHP() > 0) {
                won = false;
            } else {
                //Todo ...
            }
            if (won) {
                unit1.moveUnitFromTo(unit1, unit1.getTileOfUnit(), GameDatabase.getTileByXAndY(x, y));
            }
            return true;
        } else {
            return false;
        }
    }

    public void Unit1CanSeeUnit2(Unit unit1, Unit unit2) {

    }

    public void giveBonusToUnits(ArrayList<Unit> unitsInWar) {

    }

    public void checkTerrainBonus(Tile tile1, Tile tile2) {

    }

    public void addXP(ArrayList<Unit> unitsInWar, int XP) {
        for (Unit unit : unitsInWar) {
            unit.setXP(unit.getXP() + XP);
        }
    }

    public void healUnit(Unit unit) {
        Tile currentTile = GameDatabase.getTileByXAndY(unit.getX(), unit.getY());
        Civilization currentCivilization = GameDatabase.getCivilizationByTile(currentTile);
        if (currentCivilization.getAllUnitsOfCivilization().contains(unit)) {
            if (currentTile.getCity() == null) {
                unit.regainHP(2);
            } else {
                unit.regainHP(3);
            }
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

    public void getSiegeUnitReady(Soldier soldier) {
        //todo...
    }

}
