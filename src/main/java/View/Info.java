package View;

import Database.GameDatabase;
import Model.City;
import Model.Civilization;
import Model.Demography;

public class Info {
    private static Info instance;

    private Info() {

    }

    public static Info getInstance() {
        if(instance == null) {
            instance = new Info();
        }
        return instance;
    }

    public void infoCity(int turn) {
        Civilization civilization = GameDatabase.players.get(turn);
        if(civilization.getCities().size() == 0) {
            System.out.println("NO CITY");
        }
        for (City city : civilization.getCities()) {
            System.out.println(city);
        }
    }

    public Demography infoDemography(int turn) {
        Civilization civilization = GameDatabase.players.get(turn);
        return new Demography(civilization);
    }
}
