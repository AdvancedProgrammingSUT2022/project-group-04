package Controller;

import Model.*;

import java.util.ArrayList;

public class CombatController {
    //will add a combat model class;
    ArrayList<Unit> unitsInWar;

    public void declareWar(Civilization civilization1, Civilization civilization2){
        civilization1.setInWar(true);
        civilization2.setInWar(true);
        civilization1.setIsInWarWith(civilization2);
        civilization2.setIsInWarWith(civilization1);
    }

    public boolean areInWar(Civilization civilization1, Civilization civilization2){
        return civilization1.getIsInWarWith().equals(civilization2)
                && civilization2.getIsInWarWith().equals(civilization1);

    }

    public void takeHostage(ArrayList<Citizen> citizens){
        for (Citizen citizen : citizens){
            citizen.setHostage(true);
        }
    }

    public void meleeCombatUnits(Soldier soldier1, Soldier soldier2){

    }

    public void meleeCombatUnitsCity(Soldier soldier1, City city1){

    }

    public void rangedCombatUnit(LongRangedSoldier soldier1, LongRangedSoldier soldier2){

    }

    public void rangedCombatUnitCity(LongRangedSoldier soldier1, City city1){

    }

    public void Unit1CanSeeUnit2(Unit unit1, Unit unit2){

    }

    public void giveBonusToUnits(ArrayList<Unit> unitsInWar){

    }
    public void checkTerrainBonus(Tile tile1, Tile tile2){

    }
    public void addXP(ArrayList<Unit> unitsInWar, int XP){
        for (Unit unit :unitsInWar){
            unit.setXP(unit.getXP() + XP);
        }
    }

    public void regainHP(Unit unit){
        //Todo... check to see if is in friendly tile or ...
    }

    public void Strengthen(){

    }




}
