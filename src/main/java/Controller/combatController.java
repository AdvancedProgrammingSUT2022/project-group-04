package Controller;

import Model.Citizen;
import Model.Civilization;
import Model.Soldier;

import java.util.ArrayList;

public class combatController {
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


}
