package Model;

import java.util.ArrayList;

public class Demography {

    private ArrayList<Soldier> soldiers;
    private String name;
    private int size;
    private int totalHappiness;
    private int gold;

    public Demography(Civilization civilization) {
        this.soldiers = soldierCalculator(civilization);
        this.size = civilization.getTiles().size();
        this.totalHappiness =civilization.getHappiness();
        this.gold = civilization.getGold();
        this.name = civilization.getNickname();
    }

    private ArrayList<Soldier> soldierCalculator(Civilization civilization) {
        ArrayList<Soldier> soldiers = new ArrayList<Soldier>();
        for (City city : civilization.getCities()) {
            for (Unit unit : city.getUnits()) {
                if(unit instanceof Soldier) {
                    soldiers.add((Soldier) unit);
                }
            }
        }
        return soldiers;
    }


    @Override
    public String toString() {
        String result = this.name + "\n";
        result += "Happiness = " + Integer.toString(this.totalHappiness) + "\n";
        result += "Gold = " + Integer.toString(this.gold) + "\n";
        result += "Size = " + Integer.toString(this.size) + "\n";

        return result;
    }
}
