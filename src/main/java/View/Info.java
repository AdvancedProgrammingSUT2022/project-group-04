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
        while (true) {
            command = scanner.nextLine();
            if (command.equals("EXIT")) {
                return;
            }
            for (City city : civilization.getCities()) {
                System.out.println(city);
            }
            if (!command.matches("^-?\\d+$")) {
                System.out.println("Enter a number");
            } else {
                goToCityPanel(turn, scanner, command);
            }
        }
    }

    private void cityOutput(City city) {
        System.out.println("Time to extend borders = " + Integer.toString(city.getTimeToExtendBorders()));
        System.out.println("Time to populate = " + Integer.toString(city.getTimeTopPopulate()));
    }

    private void printStrategicResourcesOfCity(City city) {
        ArrayList<Resources> strategicResources = city.getStrategicResources();
        if(strategicResources.size() == 0) {
            System.out.println("No strategic Resources");
        }
        System.out.println("Strategic Resources:");
        for (Resources strategicResource : strategicResources) {
            if(isResourceNew(strategicResources, strategicResource)) {
                System.out.println(strategicResource.getName());
            }
        }

    }

    private void civilizationOutput(City city) {
        System.out.println("Science rate of this city = " + Integer.toString(city.getScienceGenerating()));
        System.out.println("Gold rate of this city = " + Integer.toString(city.getGoldGeneratingRate()));
        System.out.println("Civilization Happiness = " + Integer.toString(GameDatabase.getCivilizationForCity(city.getName()).getHappiness()));
        printStrategicResourcesOfCity(city);
    }

    public void cityBanner(City city, Scanner scanner) {
        String command;
        System.out.println(city);
        cityOutput(city);
        civilizationOutput(city);
        for (Tile cityTile : city.getTiles()) {
            System.out.println(cityTile);
        }
        while (true) {
            command = scanner.nextLine();
            if (command.equals("BACK")) {
                return;
            }
            System.out.println("enter BACK to enter the upper menu");
        }
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

    private boolean isResourceNew(ArrayList<Resources> resourcesList, Resources resource) {
        for (Resources resources : resourcesList) {
            if(resources.getName().equals(resource.getName())) {
                return false;
            }
        }
        return true;
    }

    private void printSoldiersInUnitPanel(ArrayList<Unit> soldiers) {
        for (Unit soldier : soldiers) {
            System.out.println("Unit on X = " + Integer.toString(soldier.getX()) +
                    " and Y = " + Integer.toString(soldier.getY()) +
                    " is ready = " + Boolean.toString(soldier.isReady()));
        }
    }

    private void printEconomy(int turn) {
        for (City city : GameDatabase.players.get(turn).getCities()) {
            System.out.println(city);
            for (Building building : city.getBuildings()) {
                System.out.println(building);
            }
        }
    }

    public void infoEconomy(int turn, Scanner scanner) {
        System.out.println("Your science is " + Integer.toString(GameDatabase.players.get(turn).getScience()));
        System.out.println("Your happiness is " + Integer.toString(GameDatabase.players.get(turn).getScience()));
        printEconomy(turn);
        String command;
        int index;
        while (true) {
            command = scanner.nextLine();
            if (command.equals("EXIT")) {
                return;
            }
            if (!command.matches("^-?\\d+$")) {
                System.out.println("enter a number");
            } else {
                goToCityPanel(turn, scanner, command);
                printEconomy(turn);
            }
        }
    }

    private void goToCityPanel(int turn, Scanner scanner, String command) {
        int index;
        index = Integer.parseInt(command);
        if (index < 1 || index > GameDatabase.players.get(turn).getCities().size()) {
            System.out.println("invalid number");
        } else {
            index--;
            cityBanner(GameDatabase.players.get(turn).getCities().get(index), scanner);
        }
    }

    public void infoUnits(GameMenuController gameMenuController, int turn, Scanner scanner) {
        ArrayList<Unit> soldiers = getSoldiers(gameMenuController, turn);
        printSoldiersInUnitPanel(soldiers);
        String command;
        int index;
        while (true) {
            System.out.println("Enter info military to go to info military");
            command = scanner.nextLine();
            if (command.equals("info military")) {
                infoMilitary(gameMenuController, turn);
            } else if (command.equals("EXIT")) {
                return;
            } else if (!command.matches("^-?\\d+$")) {
                System.out.println("enter a number");
            } else {
                index = Integer.parseInt(command);
                if (index < 1 || index > soldiers.size()) {
                    System.out.println("enter a valid number");
                } else {
                    soldiers.get(index).setReady(!soldiers.get(index).isReady());
                    printSoldiersInUnitPanel(soldiers);
                }
            }
        }
    }

    public void infoMilitary(GameMenuController gameMenuController, int turn) {
        ArrayList<Unit> soldiers = getSoldiers(gameMenuController, turn);
        for (Unit soldier : soldiers) {
            System.out.println(soldier);
        }
    }

    private ArrayList<Unit> getSoldiers(GameMenuController gameMenuController, int turn) {
        ArrayList<Unit> soldiers = new ArrayList<Unit>();
        for (Tile tile : GameDatabase.players.get(turn).getTiles()) {
            for (Unit unit : tile.getUnits()) {
                if (gameMenuController.isUnitSoldier(unit)) {
                    soldiers.add(unit);
                }
            }
        }
        return soldiers;
    }

    private void printResources(int turn) {
        System.out.println("Resources:");
        for (Tile tile : GameDatabase.players.get(turn).getTiles()) {
            if (Resources.isResourceOnTileValidForDiscovering(tile)) {
                String resourceString = "Resource " + tile.getBaseTerrain().getResources().getName() + " on tile X: " +
                        Integer.toString(tile.getX()) + " and Y: " + Integer.toString(tile.getY()) + "is valid for discovering";
                tile.discoverResource();
                System.out.println(resourceString);
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
            if (!technologyUnderResearch.isStopped()) {
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
        if (!isNewResearchValid(technologiesUnderResearch, turn)) {
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
