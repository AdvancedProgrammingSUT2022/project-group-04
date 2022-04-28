package View;

import Database.GameDatabase;
import Database.GlobalVariables;
import Model.City;
import Model.Civilization;
import Model.Demography;
import Model.Technology;

import java.util.ArrayList;
import java.util.Scanner;

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

    public void infoResearch(int turn, Scanner scanner) {
        if(GameDatabase.players.get(turn).getCities().size() == 0) {
            System.out.println("Create a city first!");
            return;
        }
        // TODO = start and stop technology
        System.out.println("Technologies under research:");
        for (Technology technology : GameDatabase.players.get(turn).getTechnologies()) {
            if(!technology.wasReached()) {
                System.out.println(technology);
            }
        }
        System.out.println("Technologies you have:");
        for (Technology technology : GameDatabase.players.get(turn).getTechnologies()) {
            if(technology.wasReached()) {
                System.out.println(technology);
            }
        }
        System.out.println("valid technologies");
        ArrayList<Technology> validTechnologies = new ArrayList<Technology>();
        for (String technologyName : GlobalVariables.TECHNOLOGIES) {
            Technology technology = new Technology(technologyName);
            if(technology.isTechnologyValidForCivilization(GameDatabase.players.get(turn))) {
                if(!GameDatabase.players.get(turn).isTechnologyForThisCivilization(technology)) {
                    System.out.println(technology);
                    validTechnologies.add(technology);
                }
            }
        }
        String input;
        int index;
        while(true) {
            input = scanner.nextLine();
            if(input.equals("EXIT")) {
                System.out.println("EXIT technology menu");
                return;
            } else if(!input.matches("-?\\d+")) {
                System.out.println("please enter a number");
            } else {
                index = Integer.parseInt(input);
                if(index<=0 || index>validTechnologies.size()) {
                    System.out.println("invalid number");
                } else {
                    GameDatabase.players.get(turn).addTechnology(validTechnologies.get(index-1));
                }
            }

        }

    }
}
