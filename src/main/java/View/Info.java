package View;

import Controller.GameMenuController;
import Database.GameDatabase;
import Database.GlobalVariables;
import Model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Info {
    private static Info instance;

    private Info() {

    }

    public static Info getInstance() {
        if (instance == null) {
            instance = new Info();
        }
        return instance;
    }

    public void infoCity(Scanner scanner, int turn) {
        Civilization civilization = GameDatabase.players.get(turn);
        if (civilization.getCities().size() == 0) {
            System.out.println("NO CITY");
            return;
        }
        String command;
        int index;
        while(true) {
            command = scanner.nextLine();
            if(command.equals("EXIT")) {
                return;
            }
            for (City city : civilization.getCities()) {
                System.out.println(city);
            }
            if(!command.matches("^-?\\d+$")) {
                System.out.println("Enter a number");
            } else {
                index = Integer.parseInt(command);
                if(index < 1) {
                    System.out.println("invalid number");
                } else {
                    index--;
                    cityBanner(GameDatabase.players.get(turn).getCities().get(index), scanner);
                }
            }
        }
    }

    public void cityBanner(City city, Scanner scanner) {

    }

    public Demography infoDemography(int turn) {
        Civilization civilization = GameDatabase.players.get(turn);
        return new Demography(civilization);
    }

    public void infoNotification(int turn) {
        System.out.println(GameDatabase.players.get(turn).getNickname() + " have " + Integer.toString(Notification.getUnreadMessagesNumber(GameDatabase.players.get(turn))) + " unread messages");
        for (Notification notification : Notification.get(GameDatabase.players.get(turn))) {
            System.out.println(notification);
            notification.read();
        }
    }

    private boolean isResourceNew(ArrayList<String> resourcesName, String resourceName) {
        for (String resource : resourcesName) {
            if (resource.equals(resourceName)) {
                return false;
            }
        }
        return true;
    }

    public void infoMilitary(GameMenuController gameMenuController, int turn) {
        for (Tile tile : GameDatabase.players.get(turn).getTiles()) {
            for (Unit unit : tile.getUnits()) {
                if(gameMenuController.isUnitSoldier(unit)) {
                    System.out.println(unit);
                }
            }
        }
    }

    private void printResources(int turn) {
        System.out.println("Resources:");
        ArrayList<String> resourcesName = new ArrayList<String>();
        for (Tile tile : GameDatabase.players.get(turn).getTiles()) {
            if (Resources.isResourceOnTileValidForDiscovering(tile)) {
                if (isResourceNew(resourcesName, tile.getBaseTerrain().getResources().getName())) {
                    resourcesName.add(tile.getBaseTerrain().getResources().getName());
                    String resourceString = "Resource " + tile.getBaseTerrain().getResources().getName() + " on tile X: " +
                            Integer.toString(tile.getX()) + " and Y: " + Integer.toString(tile.getY());
                    System.out.println(resourceString);
                }
            }
        }
    }

    private void printTechnologiesUnderResearch(ArrayList<Technology> technologiesUnderResearch, int turn) {
        System.out.println("Technologies under research:");
        for (Technology technology : GameDatabase.players.get(turn).getTechnologies()) {
            if (!technology.wasReached()) {
                System.out.println(technology);
                technologiesUnderResearch.add(technology);
            }
        }
    }

    private void printTechnologiesYouHave(int turn) {
        System.out.println("Technologies you have:");
        for (Technology technology : GameDatabase.players.get(turn).getTechnologies()) {
            if (technology.wasReached()) {
                System.out.println(technology);
            }
        }
    }

    private void printValidTechnologiesForResearch(ArrayList<Technology> validTechnologies, int turn) {
        System.out.println("valid technologies");
        for (String technologyName : GlobalVariables.TECHNOLOGIES) {
            Technology technology = new Technology(technologyName);
            if (technology.isTechnologyValidForCivilization(GameDatabase.players.get(turn))) {
                if (!GameDatabase.players.get(turn).isTechnologyForThisCivilization(technology)) {
                    System.out.println(technology);
                    validTechnologies.add(technology);
                }
            }
        }
    }

    private boolean isNewResearchValid(ArrayList<Technology> technologiesUnderResearch, int turn) {
        for (Technology technologyUnderResearch : technologiesUnderResearch) {
            if(!technologyUnderResearch.isStopped()) {
                return false;
            }
        }
        return true;
    }

    public void infoResearch(int turn, Scanner scanner) {
        printResources(turn);
        System.out.println("Technologies");
        if (GameDatabase.players.get(turn).getCities().size() == 0) {
            System.out.println("For research on new technologies, Create a city first!");
            return;
        }
        ArrayList<Technology> technologiesUnderResearch = new ArrayList<Technology>();
        stopTechnologiesResearchMenu(technologiesUnderResearch, turn, scanner);
        printTechnologiesUnderResearch(technologiesUnderResearch, turn);
        printTechnologiesYouHave(turn);
        if(!isNewResearchValid(technologiesUnderResearch, turn)) {
            System.out.println("you can't have a new research because you are busy with another technology.");
            return;
        }
        ArrayList<Technology> validTechnologies = new ArrayList<Technology>();
        printValidTechnologiesForResearch(validTechnologies, turn);
        selectTechnologyMenu(validTechnologies, turn, scanner);
    }

    private void selectTechnologyMenu(ArrayList<Technology> validTechnologies, int turn, Scanner scanner) {
        String input;
        int index;
        System.out.println("Select a technology to research");
        while (true) {
            input = scanner.nextLine();
            if (input.equals("EXIT")) {
                System.out.println("EXIT technology menu");
                return;
            } else if (!input.matches("-?\\d+")) {
                System.out.println("please enter a number");
            } else {
                index = Integer.parseInt(input);
                if (index <= 0 || index > validTechnologies.size()) {
                    System.out.println("invalid number");
                } else {
                    GameDatabase.players.get(turn).addTechnology(validTechnologies.get(index - 1));
                }
            }

        }
    }

    private void stopTechnologiesResearchMenu(ArrayList<Technology> technologiesUnderResearch, int turn, Scanner scanner) {
        String input;
        int index;
        System.out.println("stop or start searching for a technology");
        while (true) {
            input = scanner.nextLine();
            if (input.equals("EXIT")) {
                System.out.println("EXIT stop research menu");
                return;
            } else if (!input.matches("-?\\d+")) {
                System.out.println("please enter a number");
            } else {
                index = Integer.parseInt(input);
                if (index <= 0 || index > technologiesUnderResearch.size()) {
                    System.out.println("invalid number");
                } else {
                    if (technologiesUnderResearch.get(index - 1).isStopped()) {
                        technologiesUnderResearch.get(index - 1).start();
                    } else {
                        technologiesUnderResearch.get(index - 1).stop();
                    }
                }
            }

        }
    }
}
