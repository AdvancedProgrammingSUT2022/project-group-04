package Client.Model;

import Civilization.Database.GameDatabase;
import Civilization.Database.GlobalVariables;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Ruin{
    private ArrayList<Technology> technologies;
    private int gold;
    private ArrayList<Settler> settlers;
    private int removingFogOfWarTiles;
    private int populationAdd;

    public int getPopulationAdd() {
        return populationAdd;
    }

    public Ruin() {
        technologies = new ArrayList<>();
        setTechnologies();
        gold = new Random().nextInt(200);
        settlers = new ArrayList<>();
        removingFogOfWarTiles = new Random().nextInt(5);
        populationAdd = 1;
    }

    private void setTechnologies() {
        Random random = new Random();
        for (String technology : GlobalVariables.TECHNOLOGIES) {
            boolean bool = random.nextBoolean();
            if(bool) {
                technologies.add(new Technology(technology));
            }
        }
    }

    public void arrive(Unit unit) throws IOException {
        Civilization civilization = GameDatabase.getCivilizationByUnit(unit);
        if(civilization == null) {
            return;
        }
        civilization.getPrize(this);
    }

    public ArrayList<Technology> getTechnologies() {
        return technologies;
    }

    public int getGold() {
        return gold;
    }
}
