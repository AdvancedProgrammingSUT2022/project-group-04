package ControllerTest;

import Controller.GameMenuController;
import Database.GameDatabase;
import Database.GlobalVariables;
import Database.UserDatabase;
import Model.*;

import View.GameMenu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@ExtendWith(MockitoExtension.class)
public class GameMenuControllerTest {


    static MockedStatic<GameDatabase> database;

    @Mock
    GameModel gameModel;

    @Mock
    ArrayList<Tile> tiles;

    @Mock
    Soldier soldier;

    @Mock
    Tile tile0;

    @Mock
    Tile tile;

    @Mock
    Unit unit;

    @Mock
    ArrayList<Unit> units;

    @Mock
    City city;

    @Mock
    Civilization civilization;

    @Mock
    Settler settler;

    @Mock
    ArrayList<Civilization> players;

    @Mock
    ArrayList<City> cities;

    @Mock
    ArrayList<Improvement> improvements;

    @Mock
    Improvement improvement;

    @Mock
    ArrayList<Worker> workers;

    @Mock
    ArrayList<Settler> settlers;

    @Mock
    Worker worker;




    @BeforeAll
    public static void setUp() {
        database = mockStatic(GameDatabase.class);
    }
    @AfterAll
    public static void salam(){
        database.close();
    }


    @Test
    public void isPositionValid_Found() {
        int index = 0;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        database.when(() -> GameDatabase.getMap()).thenReturn(tiles);
        when(tiles.size()).thenReturn(10);
        when(tiles.get(index)).thenReturn(tile0);
        when(tile0.getX()).thenReturn(5);
        when(tile0.getY()).thenReturn(10);
        Assertions.assertTrue(gameMenuController.isPositionValid(5, 10));
    }

    @Test
    public void isPositionValid_NotFound(){
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        database.when(() -> GameDatabase.getMap()).thenReturn(tiles);
        when(tiles.size()).thenReturn(1);
        when(tiles.get(0)).thenReturn(tile0);
        when(tile0.getX()).thenReturn(5);
        Assertions.assertFalse(gameMenuController.isPositionValid(2, 1));
    }

    @Test
    public void isUnitCivilian(){
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        when(unit.getUnitType()).thenReturn("worker");
        Assertions.assertTrue(gameMenuController.isUnitCivilian(unit));
    }

    @Test
    public void isUnitSoldier(){
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        when(unit.getUnitType()).thenReturn("Archer");
        Assertions.assertTrue(gameMenuController.isUnitSoldier(unit));
    }

    @Test
    public void isCombatUnitInThisPosition_True(){
        int x = 10;
        int y = 20;
        GameMenuController gameMenuController = Mockito.spy(new GameMenuController(gameModel));
        database.when(()->GameDatabase.getTileByXAndY(x,y)).thenReturn(tile);
        when(tile.getUnits()).thenReturn(units);
        when(units.size()).thenReturn(1);
        when(units.get(0)).thenReturn(unit);
        doReturn(true).when(gameMenuController).isUnitSoldier(unit);
        Assertions.assertTrue(gameMenuController.isCombatUnitInThisPosition(x,y));
    }

    @Test
    public void isCombatUnitInThisPosition_False(){
        int x = 10;
        int y = 20;
        GameMenuController gameMenuController = Mockito.spy(new GameMenuController(gameModel));
        database.when(()->GameDatabase.getTileByXAndY(x,y)).thenReturn(tile);
        Assertions.assertFalse(gameMenuController.isCombatUnitInThisPosition(x,y));
    }

    @Test
    public void isNonCombatUnitInThisPosition_True(){
        int x = 10;
        int y = 20;
        GameMenuController gameMenuController = Mockito.spy(new GameMenuController(gameModel));
        database.when(()->GameDatabase.getTileByXAndY(x,y)).thenReturn(tile);
        Settler settler = mock(Settler.class);
        Worker worker = mock(Worker.class);
        when(tile.getSettler()).thenReturn(settler);
        Assertions.assertTrue(gameMenuController.isNonCombatUnitInThisPosition(x,y));
    }

    @Test
    public void isNonCombatUnitInThisPosition_False(){
        int x = 10;
        int y = 20;
        GameMenuController gameMenuController = Mockito.spy(new GameMenuController(gameModel));
        database.when(()->GameDatabase.getTileByXAndY(x,y)).thenReturn(tile);
        //ArrayList<Unit> units1 = new ArrayList<>();
        Assertions.assertFalse(gameMenuController.isNonCombatUnitInThisPosition(x,y));
    }

    @Test
    public void selectCombatUnit_Exists(){
        int x = 10;
        int y = 20;
        GameMenuController gameMenuController = Mockito.spy(new GameMenuController(gameModel));
        database.when(()->GameDatabase.getTileByXAndY(x,y)).thenReturn(tile);
        //ArrayList<Unit> units1 = new ArrayList<>();
        when(tile.getUnits()).thenReturn(units);
        when(units.size()).thenReturn(1);
        when(units.get(0)).thenReturn(unit);
        //when(unit.getUnitType()).thenReturn("Archer");
        doReturn(true).when(gameMenuController).isUnitSoldier(unit);
        Assertions.assertNotEquals(gameMenuController.selectCombatUnit(x,y),null);
    }

    @Test
    public void selectCombatUnit_DoesntExist(){
        int x = 10;
        int y = 20;
        GameMenuController gameMenuController = Mockito.spy(new GameMenuController(gameModel));
        database.when(()->GameDatabase.getTileByXAndY(x,y)).thenReturn(tile);
        //ArrayList<Unit> units1 = new ArrayList<>();
        when(tile.getUnits()).thenReturn(units);
        when(units.size()).thenReturn(1);
        when(units.get(0)).thenReturn(unit);
        //when(unit.getUnitType()).thenReturn("Archer");
        doReturn(false).when(gameMenuController).isUnitSoldier(unit);
        Assertions.assertEquals(gameMenuController.selectCombatUnit(x,y),null);
    }

    @Test
    public void selectNonCombatUnit_Exists(){
        int x = 10;
        int y = 20;
        GameMenuController gameMenuController = Mockito.spy(new GameMenuController(gameModel));
        database.when(()->GameDatabase.getTileByXAndY(x,y)).thenReturn(tile);
        //ArrayList<Unit> units1 = new ArrayList<>();
        Settler settler = mock(Settler.class);
        //Worker worker1 = mock(Worker.class);
        when(tile.getSettler()).thenReturn(settler);
        //when(tile.getWorker()).thenReturn(worker1);
        //when(unit.getUnitType()).thenReturn("Archer");
        Assertions.assertNotEquals(gameMenuController.selectNonCombatUnit(x,y),null);
    }

    @Test
    public void selectNonCombatUnit_DoesntExist(){
        int x = 10;
        int y = 20;
        GameMenuController gameMenuController = Mockito.spy(new GameMenuController(gameModel));
        database.when(()->GameDatabase.getTileByXAndY(x,y)).thenReturn(tile);

        //when(unit.getUnitType()).thenReturn("Archer");
        //doReturn(false).when(gameMenuController).isUnitCivilian(unit);
        Assertions.assertEquals(gameMenuController.selectNonCombatUnit(x,y),null);
    }

//
//    @Test
//    public void isCombatUnitInThisPosition_False(){
//        int x = 10;
//        int y = 20;
//        GameMenuController gameMenuController = new GameMenuController(gameModel) ;
//        Tile tile1 = new Tile("","",x,y);
//        database.when(()->GameDatabase.getTileByXAndY(x,y)).thenReturn(tile1);
//        //ArrayList<Unit> units1 = new ArrayList<>();
//        when(tile1.getUnits()).thenReturn(units);
//        when(units.size()).thenReturn(1);
//        when(units.get(0)).thenReturn(unit);
//        when(unit.getUnitType()).thenReturn("Archer");
//        Assertions.assertFalse(gameMenuController.isCombatUnitInThisPosition(x,y));
//    }

    @Test
    public void isDirectionValidForMap_True(){
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        String direction = "UP";
        Assertions.assertTrue(gameMenuController.isDirectionForMapValid(direction));
    }

    @Test
    public void isDirectionValidForMap_False(){
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        String direction = "Monkey";
        Assertions.assertFalse(gameMenuController.isDirectionForMapValid(direction));
    }

    @Test
    public void isUnitForThisCivilization_True(){
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        when(unit.getCivilizationIndex()).thenReturn(0);
        Assertions.assertTrue(gameMenuController.isUnitForThisCivilization(0,unit));
    }

    @Test
    public void isUnitForThisCivilization_False(){
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        when(unit.getCivilizationIndex()).thenReturn(2);
        Assertions.assertFalse(gameMenuController.isUnitForThisCivilization(0,unit));
    }

    @Test
    public void addHPCoordinate(){
        int x = 10;
        int y = 10;
        int amount = 9;
        GameMenuController gameMenuController = Mockito.spy(new GameMenuController(gameModel));
        doReturn(unit).when(gameMenuController).selectCombatUnit(x,y);
        gameMenuController.addHP(x,y,amount);
        verify(unit).addHP(amount);
    }

    @Test
    public void addHPCityName(){
        int amount = 10;
        String cityName = "Amol";
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        database.when(()->GameDatabase.getCityByName(cityName)).thenReturn(city);
        gameMenuController.addHP(cityName,amount);
        verify(city).addHP(amount);
    }

    @Test
    public void makeHappy(){
        int turn = 0;
        String cityName = "Amol";
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        database.when(()->GameDatabase.getPlayers()).thenReturn(players);
        when(players.get(turn)).thenReturn(civilization);
        gameMenuController.makeHappy(turn);
        verify(civilization).happy();
    }
    @Test
    public void dryUp(){
        int x = 1;
        int y = 2;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        database.when(()->GameDatabase.getTileByXAndY(x,y)).thenReturn(tile0);
        gameMenuController.dryUp(x,y);
        verify(tile0).dryUp();
    }

    @Test
    public void addGold(){
        int amount = 10;
        int turn = 0;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        database.when(()->GameDatabase.getPlayers()).thenReturn(players);
        when(players.get(turn)).thenReturn(civilization);
        gameMenuController.addGold(turn,amount);
        verify(civilization).addGold(amount);
    }
//
    @Test
    public void addScience(){
        int amount = 10;
        int turn = 0;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        database.when(()->GameDatabase.getPlayers()).thenReturn(players);
        when(players.get(turn)).thenReturn(civilization);
        gameMenuController.addScience(turn,amount);
        verify(civilization).addScience(amount);
    }

//    @Test
//    public void sendMessage(){
//        Notification notification = Mockito.spy(new Notification(" ", "","            "));
//        int amount = 10;
//        int turn = 0;
//        GameMenuController gameMenuController = new GameMenuController(gameModel);
//        database.when(()->GameDatabase.getPlayers()).thenReturn(players);
//        when(players.get(turn)).thenReturn(civilization);
//        gameMenuController.addScience(turn,amount);
//        verifyStatic
//    }
//
    @Test
    public void addScore(){
        int amount = 10;
        int turn = 0;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        database.when(()->GameDatabase.getPlayers()).thenReturn(players);
        //when(players.size()).thenReturn(1);
        when(players.get(0)).thenReturn(civilization);
        gameMenuController.addScore(turn,amount);
        verify(civilization).addScore(amount);
    }

    //TODO sendMessage function


    @Test
    public void isCityPositionValid_Equals(){
        int x = 10;
        int y = 20;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        database.when(()->GameDatabase.getCityByXAndY(x,y)).thenReturn(city);
        Assertions.assertEquals(true,gameMenuController.isCityPositionValid(x,y));
    }

    @Test
    public void isCityValid(){
        String cityName = "gotham";
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        database.when(()->GameDatabase.getCityByName(cityName)).thenReturn(city);
        Assertions.assertTrue(gameMenuController.isCityValid(cityName));
    }


    @Test
    public void isDestinationOKForMove_False1(){
        int x = 19;
        int y = 20;
        GameMenuController gameMenuController = Mockito.spy(new GameMenuController(gameModel));
        Mockito.doReturn(false).when(gameMenuController).isPositionValid(x,y);
        boolean result = gameMenuController.isDestinationOkForMove(unit,x,y);
        Assertions.assertFalse(result);
    }

    @Test
    public void isDestinationOKForMove_False2(){
        int x = 19;
        int y = 20;
        GameMenuController gameMenuController = Mockito.spy(new GameMenuController(gameModel));
        Mockito.doReturn(true).when(gameMenuController).isPositionValid(x,y);
        Mockito.doReturn(true).when(gameMenuController).isUnitSoldier(unit);
        when(GameDatabase.getTileByXAndY(x,y)).thenReturn(tile);
        when(tile.getUnits()).thenReturn(units);
        when(units.size()).thenReturn(1);
        when(units.get(0)).thenReturn(unit);
        //Mockito.doReturn(true).when(gameMenuController).isUnitSoldier(unit);
        boolean result = gameMenuController.isDestinationOkForMove(unit,x,y);
        Assertions.assertFalse(result);
    }

    @Test
    public void isDestinationOKForMove_True2(){
        int x = 19;
        int y = 20;
        GameMenuController gameMenuController = Mockito.spy(new GameMenuController(gameModel));
        Mockito.doReturn(true).when(gameMenuController).isPositionValid(x,y);
        Mockito.doReturn(false).when(gameMenuController).isUnitSoldier(unit);
        when(GameDatabase.getTileByXAndY(x,y)).thenReturn(tile);
        //when(tile.getUnits()).thenReturn(units);
        //when(units.size()).thenReturn(1);
        //when(units.get(0)).thenReturn(unit);
        //Mockito.doReturn(true).when(gameMenuController).isUnitSoldier(unit);
        boolean result = gameMenuController.isDestinationOkForMove(unit,x,y);
        Assertions.assertTrue(result);
    }

    @Test
    public void isDestinationOKForMove_True(){
        int x = 19;
        int y = 20;
        GameMenuController gameMenuController = Mockito.spy(new GameMenuController(gameModel));
        Mockito.doReturn(true).when(gameMenuController).isPositionValid(x,y);
        Mockito.doReturn(true).when(gameMenuController).isUnitSoldier(unit);
        when(GameDatabase.getTileByXAndY(x,y)).thenReturn(tile);
        when(tile.getUnits()).thenReturn(units);
        when(units.size()).thenReturn(1);
        Unit unit1 = mock(Unit.class);
        when(units.get(0)).thenReturn(unit1);
        when(unit1.getUnitType()).thenReturn("worker");
        //Mockito.doReturn(true).when(gameMenuController).isUnitSoldier(unit);
        boolean result = gameMenuController.isDestinationOkForMove(unit,x,y);
        Assertions.assertTrue(result);
    }


    @Test
    public void isCityCapital(){
        String cityName = "";
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        database.when(()->GameDatabase.getCityByName(cityName)).thenReturn(city);
        when(city.isCapital()).thenReturn(true);
        Assertions.assertTrue(gameMenuController.isCityCapital(cityName));
    }

    @Test
    public void isAmountValidForScience(){
        int amount = 0;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        Assertions.assertFalse(gameMenuController.isAmountValidForScience(amount));
    }

    @Test
    public void isAmountValidForGold(){
        int amount = 0;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        Assertions.assertFalse(gameMenuController.isAmountValidForGold(amount));
    }

    @Test
    public void isAmountValidForTurn(){
        int amount = 0;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        Assertions.assertFalse(gameMenuController.isAmountValidForTurn(amount));
    }

    @Test
    public void isAmountValidForProduction(){
        int amount = 0;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        Assertions.assertFalse(gameMenuController.isAmountValidForProduction(amount));
    }

    @Test
    public void isAmountValidForHP(){
        int amount = 0;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        Assertions.assertFalse(gameMenuController.isAmountValidForHP(amount));
    }

    @Test
    public void isCivilizationValid_True(){
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        database.when(()->GameDatabase.getCivilizationByNickname("")).thenReturn(civilization);
        Assertions.assertTrue(gameMenuController.isCivilizationValid(""));
    }

    @Test
    public void isCivilizationValid_False(){
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        database.when(()->GameDatabase.getCivilizationByNickname("")).thenReturn(null);
        Assertions.assertFalse(gameMenuController.isCivilizationValid(""));
    }

    @Test
    public void isCheatForFun_False(){
        int turn = 2;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        database.when(()->GameDatabase.getCivilizationIndex("")).thenReturn(turn);
        Assertions.assertFalse(gameMenuController.isCheatForTurn("",turn));
    }

    @Test
    public void isCheatForFun_True(){
        int turn = 2;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        database.when(()->GameDatabase.getCivilizationIndex("")).thenReturn(turn+1);
        Assertions.assertTrue(gameMenuController.isCheatForTurn("",turn));
    }

    @Test
    public void isAmountALot(){
        int amount = 20;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        Assertions.assertTrue(gameMenuController.isAmountALot(amount));
    }

    @Test
    public void isAmountValid_True(){
        int amount = 2;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        Assertions.assertTrue(gameMenuController.isAmountValid(amount));
    }

    @Test
    public void isAmountValid_False(){
        int amount = -2;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        Assertions.assertFalse(gameMenuController.isAmountValid(amount));
    }

    @Test
    public void isCityForThisCivilization_True(){
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        database.when(()->GameDatabase.getPlayers()).thenReturn(players);
        int turn = 0;
        //when(players.size()).thenReturn(1);
        when(players.get(turn)).thenReturn(civilization);
        when(civilization.getCities()).thenReturn(cities);
        when(cities.size()).thenReturn(1);
        when(cities.get(0)).thenReturn(city);
        when(city.getName()).thenReturn("");
        Assertions.assertTrue(gameMenuController.isCityForThisCivilization(turn,city));
    }

    @Test
    public void isCityForThisCivilization_False(){
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        database.when(()->GameDatabase.getPlayers()).thenReturn(players);
        int turn = 0;
        //when(players.size()).thenReturn(1);
        when(players.get(turn)).thenReturn(civilization);
        when(civilization.getCities()).thenReturn(cities);
        when(cities.size()).thenReturn(1);
        City city1 = mock(City.class);
        when(cities.get(0)).thenReturn(city1);
        when(city1.getName()).thenReturn("something");
        when(city.getName()).thenReturn("something else!");
        Assertions.assertFalse(gameMenuController.isCityForThisCivilization(turn,city));
    }

    @Test
    public void addTileToCity(){
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        when(city.getName()).thenReturn("alireza");
        database.when(()->GameDatabase.getCivilizationForCity("alireza")).thenReturn(civilization);
        gameMenuController.addTileToCity(tile0,city);
        verify(city).addTile(tile0);
    }

    @Test
    public void createNewCity(){
        String cityName = "something cool and funny that makes you laugh";
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        gameMenuController.createNewCity(settler,cityName);
        verify(settler).createNewCity(cityName);
    }

    @Test
    public void isAdjacent_False(){
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        Tile tile1 = mock(Tile.class);
        when(tile0.getAdjacentTiles()).thenReturn(tiles);
        when(city.getTiles()).thenReturn(tiles);
        when(tiles.size()).thenReturn(1);
        when(tiles.get(0)).thenReturn(tile1);
        when(tiles.contains(tile1)).thenReturn(false);
        Assertions.assertFalse(gameMenuController.isAdjacent(tile0,city));
    }

    @Test
    public void isAdjacent_True(){
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        Tile tile1 = mock(Tile.class);
        when(tile0.getAdjacentTiles()).thenReturn(tiles);
        when(city.getTiles()).thenReturn(tiles);
        when(tiles.size()).thenReturn(1);
        when(tiles.get(0)).thenReturn(tile1);
        when(tiles.contains(tile1)).thenReturn(true);
        Assertions.assertTrue(gameMenuController.isAdjacent(tile0,city));
    }

    @Test
    public void isOperable_False(){
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        Tile tile1 = mock(Tile.class);
        when(tile0.getAdjacentTiles()).thenReturn(tiles);
        when(city.getTiles()).thenReturn(tiles);
        when(tiles.size()).thenReturn(1);
        when(tiles.get(0)).thenReturn(tile1);
        when(tiles.contains(tile1)).thenReturn(false);
        Assertions.assertFalse(gameMenuController.isOperable(tile0,city));
    }

    @Test
    public void isOperable_True(){
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        Tile tile1 = mock(Tile.class);
        when(tile0.getAdjacentTiles()).thenReturn(tiles);
        when(city.getTiles()).thenReturn(tiles);
        when(tiles.size()).thenReturn(1);
        when(tiles.get(0)).thenReturn(tile1);
        when(tiles.contains(tile1)).thenReturn(true);
        when(tile1.getSettler()).thenReturn(settler);
        Assertions.assertTrue(gameMenuController.isOperable(tile0,city));
    }

    @Test
    public void addProduction(){
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        String cityName = "gotham";
        int amount = 10;
        database.when(()->GameDatabase.getCityByName(cityName)).thenReturn(city);
        gameMenuController.addProduction(cityName,amount);
        verify(city).addProduction(amount);
    }

    @Test
    public void isImprovementNameValid_True(){
        String name = "Quarry";
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        Assertions.assertTrue(gameMenuController.isImprovementValid(name));
    }

    @Test
    public void isImprovementNameValid_False(){
        String name = "BandCamp(this one time     at band camp sth)";
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        Assertions.assertFalse(gameMenuController.isImprovementValid(name));
    }

////    @Test
////    public void isPositionValidTestTwo(){
////        //GameMenuController gameMenuController = new GameMenuController(gameModel);
////        Tile tile0 = new Tile("","",0,1);
////        Tile tile1 = new Tile("","",20,30);
////        Tile tile2 = new Tile("","",89,40);
////        Tile tile3 = new Tile("","",2,2);
////        Tile tile4 = new Tile("","",0,3);
////        Tile tile5 = new Tile("","",3,1);
////        Tile tile6 = new Tile("","",5,1);
////        ArrayList<Tile> map = new ArrayList<>();
////        map.add(tile0);map.add(tile1);map.add(tile2);map.add(tile3);map.add(tile4);map.add(tile5);map.add(tile6);
////        when(GameDatabase.map).thenReturn(map);
////        Assertions.assertFalse(gameMenuController.isPositionValid(10,80));
////    }

    @Test
    public void pillageCurrentTile() {
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        when(unit.getTileOfUnit()).thenReturn(tile);
        ArrayList<Improvement> improvements = new ArrayList<Improvement>();
        for (String improvement : GlobalVariables.IMPROVEMENTS) {
            improvements.add(new Improvement(improvement));
        }
        when(tile.getImprovements()).thenReturn(improvements);
        when(tile.hasRoad()).thenReturn(true);
        gameMenuController.pillageCurrentTile(unit);
    }

    @Test
    public void garrisonUnitToCity_notNullCity() {
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        when(unit.getTileOfUnit()).thenReturn(tile);
        when(tile.getX()).thenReturn(0);
        when(tile.getY()).thenReturn(0);
        database.when(() -> GameDatabase.getCityByXAndY(0, 0)).thenReturn(city);
        assertTrue(gameMenuController.garrisonUnitToCity(unit));
    }

    @Test
    public void garrisonUnitToCity_NullCity() {
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        when(unit.getTileOfUnit()).thenReturn(tile);
        when(tile.getX()).thenReturn(0);
        when(tile.getY()).thenReturn(0);
        database.when(() -> GameDatabase.getCityByXAndY(0, 0)).thenReturn(null);
        assertFalse(gameMenuController.garrisonUnitToCity(unit));
    }

    @Test
    public void getListOfUnemployedWorker_notNullWorker() {
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        when(city.getWorker()).thenReturn(worker);
        gameMenuController.getListOfUnemployedWorker(city);
    }

    @Test
    public void getListOfUnemployedWorker_nullWorker() {
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        when(city.getWorker()).thenReturn(null);
        gameMenuController.getListOfUnemployedWorker(city);
    }

    @Test
    public void getListOfUnemployedSettler_notNullSettler() {
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        when(city.getSettler()).thenReturn(settler);
        gameMenuController.getListOfUnemployedSettler(city);
    }

    @Test
    public void getListOfUnemployedSettler_nullSettler() {
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        when(city.getSettler()).thenReturn(null);
        gameMenuController.getListOfUnemployedSettler(city);
    }

    @Test
    public void addTileToCivilization() {
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        gameMenuController.addTileToCivilization(tile,civilization);
        verify(civilization).addTile(tile);
    }

    @Test
    public void isTileAdjacentToCivilization() {
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        gameMenuController.isTileAdjacentToCivilization(tile, civilization);
    }

    @Test
    public void createNonCombatUnit_nullTile() {
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        database.when(() -> GameDatabase.getCityByXAndY(0, 0)).thenReturn(null);
        assertFalse(gameMenuController.createNonCombatUnit("Spearman", 0, 0, 0));
    }

    @Test
    public void createNonCombatUnit_settler() {
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        database.when(() -> GameDatabase.getCityByXAndY(0, 0)).thenReturn(city);
        assertTrue(gameMenuController.createNonCombatUnit("Settler", 0, 0, 0));
    }

    @Test
    public void createNonCombatUnit_worker() {
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        database.when(() -> GameDatabase.getCityByXAndY(0, 0)).thenReturn(city);
        assertTrue(gameMenuController.createNonCombatUnit("worker", 0, 0, 0));
    }

    @Test
    public void createNonCombatUnit_notCitizen() {
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        database.when(() -> GameDatabase.getCityByXAndY(0, 0)).thenReturn(city);
        assertFalse(gameMenuController.createNonCombatUnit("Spearman", 0, 0, 0));
    }

    @Test
    public void isAmountValidForScore() {
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        assertFalse(gameMenuController.isAmountValidForScore(0));
    }


    @Test
    public void isTileValidForCreatingUnit() {
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        database.when(() -> GameDatabase.getCivilizationByTurn(0)).thenReturn(civilization);
        when(civilization.getClearTiles()).thenReturn(tiles);
        database.when(() -> GameDatabase.getTileByXAndY(0, 0)).thenReturn(tile);
        assertFalse(gameMenuController.isTileValidForCreatingUnit(0, 0, 0));
    }

    @Test
    public void createUnit() {
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        database.when(() -> GameDatabase.getTileByXAndY(0, 0)).thenReturn(tile);
        database.when(() -> GameDatabase.getCivilizationByTurn(0)).thenReturn(civilization);
        when(civilization.getClearTiles()).thenReturn(tiles);
        assertFalse(gameMenuController.createUnit("worker", 0, 0, 0));
    }

    @Test
    public void deleteUnit() {
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        database.when(() -> GameDatabase.getCivilizationByTile(tile)).thenReturn(civilization);
        when(unit.getCost()).thenReturn(10);
        when(unit.getTileOfUnit()).thenReturn(tile);
        gameMenuController.deleteUnit(unit);
    }

    @Test
    public void isTileInCivilization_nullCivilization() {
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        database.when(() -> GameDatabase.getCivilizationByTurn(0)).thenReturn(civilization);
        assertFalse(gameMenuController.isTileInCivilization(tile, 0));
    }

    @Test
    public void makeRoad() {
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        when(worker.getX()).thenReturn(0);
        when(worker.getY()).thenReturn(0);
        database.when(() -> GameDatabase.getTileByXAndY(0, 0)).thenReturn(tile);
        database.when(() -> GameDatabase.getCivilizationByTile(tile)).thenReturn(civilization);
        when(civilization.isTechnologyInCivilization("Wheel")).thenReturn(true);
        when(tile.hasRoad()).thenReturn(false);
        when(tile.getBaseTerrainType()).thenReturn("Mountain");
        assertFalse(gameMenuController.makeRoad(worker));
    }

    @Test
    public void isUnitTypeValid_valid() {
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        assertTrue(gameMenuController.isUnitTypeValid("archer"));
    }

    @Test
    public void isUnitTypeValid_invalid() {
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        assertFalse(gameMenuController.isUnitTypeValid("boz"));
    }

    @Test
    public void isTileOcean_True(){
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        BaseTerrain baseTerrain = Mockito.spy(new BaseTerrain("Ocean"));
        when(tile.getBaseTerrain()).thenReturn(baseTerrain);
        when(baseTerrain.getType()).thenReturn("Ocean");
        Assertions.assertTrue(gameMenuController.isTileOcean(tile));
    }

    @Test
    public void isTileOcean_False(){
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        Assertions.assertFalse(gameMenuController.isTileOcean(null));
    }

    @Test
    public void tileHasRiverTestTrue(){
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        Tile tile = mock(Tile.class);
        Tile tile1 = mock(Tile.class);
        boolean test[] = {true};
        when(tile.getIsRiver()).thenReturn(test);
        boolean result = gameMenuController.tileHasRiver(tile);
        Assertions.assertEquals(true, result);

    }

    @Test
    public void tileHasRiverTestFalse(){
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        Tile tile = mock(Tile.class);
        boolean test[] = new boolean[6];
        for (int i = 0; i < 6; i++){
            test[i] = false;
        }
        when(tile.getIsRiver()).thenReturn(test);
        boolean result = gameMenuController.tileHasRiver(tile);
        Assertions.assertEquals(false, result);

    }


    @Test
    public void removeRailroadTest(){
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        Tile tile = mock(Tile.class);
        when(tile.hasRailroad()).thenReturn(true);
        Worker worker = new Worker(1,1,0);
        database.when(()->GameDatabase.getTileByXAndY(worker.getX(), worker.getY())).thenReturn(tile);
        boolean result = gameMenuController.removeRailroad(worker);
        Assertions.assertEquals(true, result);

        Tile tile2 = mock(Tile.class);
        database.when(()->GameDatabase.getTileByXAndY(worker.getX(), worker.getY())).thenReturn(tile2);
        boolean result2 = gameMenuController.removeRailroad(worker);
        Assertions.assertEquals(false, result2);
        Assertions.assertEquals(-1,worker.getIndexOfProject());
        Assertions.assertEquals(false,worker.isAssigned());
        Assertions.assertEquals("",worker.getTypeOfWork());

    }

    @Test
    public void removeRoadTest(){
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        Tile tile = mock(Tile.class);
        when(tile.hasRoad()).thenReturn(true);
        Worker worker = new Worker(1,1,0);
        database.when(()->GameDatabase.getTileByXAndY(worker.getX(), worker.getY())).thenReturn(tile);
        boolean result = gameMenuController.removeRoad(worker);
        Assertions.assertEquals(true, result);

        Tile tile2 = mock(Tile.class);
        database.when(()->GameDatabase.getTileByXAndY(worker.getX(), worker.getY())).thenReturn(tile2);
        boolean result2 = gameMenuController.removeRoad(worker);
        Assertions.assertEquals(false, result2);
        Assertions.assertEquals(-1,worker.getIndexOfProject());
        Assertions.assertEquals(false,worker.isAssigned());
        Assertions.assertEquals("",worker.getTypeOfWork());

    }

    @Test
    public void pauseProjectTest(){
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        when(worker.isAssigned()).thenReturn(true);
        when(worker.isMoving()).thenReturn(false);
        when(worker.isLocked()).thenReturn(true);
        database.when(()->GameDatabase.getTileByXAndY(10,10)).thenReturn(tile);
        database.when(()->GameDatabase.getCityByXAndY(10,10)).thenReturn(city);
        when(city.getIsGettingWorkedOn()).thenReturn(true);
        gameMenuController.pauseProject(worker, 10,10);
    }

    @Test
    public void findAvailableWorkerInCityTestNotNullُُ(){
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        Worker worker = mock(Worker.class);
        Tile tile1 = mock(Tile.class);
        Tile tile2 = mock(Tile.class);
        when(tile1.getAvailableWorker()).thenReturn(worker);
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(tile1);
        tiles.add(tile2);
        City city = mock(City.class);
        when(city.getTiles()).thenReturn(tiles);
        Worker workerResult = gameMenuController.findAvailableWorkerInCity(city);
        Assertions.assertEquals(worker, workerResult);
    }

    @Test
    public void findAvailableWorkerInCityTestNull(){
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        Tile tile1 = mock(Tile.class);
        Tile tile2 = mock(Tile.class);
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(tile1);
        tiles.add(tile2);
        City city = mock(City.class);
        when(city.getTiles()).thenReturn(tiles);
        Worker workerResult = gameMenuController.findAvailableWorkerInCity(city);
        Assertions.assertEquals(null, workerResult);
    }



//    @Test
//    public void tileHasRiver_True(){
//        int x = 10;
//        int y = 20;
//        Tile tile1 = Mockito.spy(new Tile("","",x,y));
//        GameMenuController gameMenuController = new GameMenuController(gameModel);
//        when(tile1.getIsRiver()).thenReturn(new {true,true,true,true,true,true});
//        Assertions.assertTrue(gameMenuController.tileHasRiver(tile1));
//    }
//
//    @Test
//    public void tileHasRiver_False(){
//
//    }
    @Test
    public void makeRainRoad_True(){
        int x = 10;
        int y = 1;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        when(worker.getX()).thenReturn(x);
        when(worker.getY()).thenReturn(y);
        database.when(()->GameDatabase.getTileByXAndY(x,y)).thenReturn(tile);
        database.when(()->GameDatabase.getCivilizationByTile(tile)).thenReturn(civilization);
        when(civilization.isTechnologyInCivilization("SteamPower")).thenReturn(true);
        //when(tile.hasRoad()).thenReturn(false);
        when(tile.getBaseTerrainType()).thenReturn("amiri");
        Assertions.assertTrue(gameMenuController.makeRailRoad(worker));
    }

    @Test
    public void makeRailRoad_False(){
        int x = 10;
        int y = 1;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        when(worker.getX()).thenReturn(x);
        when(worker.getY()).thenReturn(y);
        database.when(()->GameDatabase.getTileByXAndY(x,y)).thenReturn(tile);
        database.when(()->GameDatabase.getCivilizationByTile(tile)).thenReturn(civilization);
        when(civilization.isTechnologyInCivilization("SteamPower")).thenReturn(false);
        //when(tile.hasRoad()).thenReturn(false);
        //when(tile.getBaseTerrainType()).thenReturn("amiri");
        Assertions.assertFalse(gameMenuController.makeRailRoad(worker));
    }

    @Test
    public void makeRoad_False(){
        int x = 10;
        int y = 1;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        when(worker.getX()).thenReturn(x);
        when(worker.getY()).thenReturn(y);
        database.when(()->GameDatabase.getTileByXAndY(x,y)).thenReturn(tile);
        database.when(()->GameDatabase.getCivilizationByTile(tile)).thenReturn(civilization);
        when(civilization.isTechnologyInCivilization("Wheel")).thenReturn(false);
        //when(tile.hasRoad()).thenReturn(false);
        //when(tile.getBaseTerrainType()).thenReturn("amiri");
        Assertions.assertFalse(gameMenuController.makeRoad(worker));
    }

    @Test
    public void makeRoad_True(){
        int x = 10;
        int y = 1;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        when(worker.getX()).thenReturn(x);
        when(worker.getY()).thenReturn(y);
        database.when(()->GameDatabase.getTileByXAndY(x,y)).thenReturn(tile);
        database.when(()->GameDatabase.getCivilizationByTile(tile)).thenReturn(civilization);
        when(civilization.isTechnologyInCivilization("Wheel")).thenReturn(true);
        //when(tile.hasRoad()).thenReturn(false);
        when(tile.getBaseTerrainType()).thenReturn("amiri");
        Worker.setHashMap();
        Assertions.assertTrue(gameMenuController.makeRoad(worker));
    }

    @Test
    public void makeMine_True(){
        int x = 10;
        int y = 1;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        when(worker.getX()).thenReturn(x);
        when(worker.getY()).thenReturn(y);
        database.when(()->GameDatabase.getTileByXAndY(x,y)).thenReturn(tile);
        database.when(()->GameDatabase.getCivilizationByTile(tile)).thenReturn(civilization);
        when(civilization.isTechnologyInCivilization("Mining")).thenReturn(true);
        //when(tile.hasRoad()).thenReturn(false);
        when(tile.getBaseTerrainType()).thenReturn("Hill");
        BaseTerrain baseTerrain = Mockito.spy(new BaseTerrain("Hill"));
        when(tile.getBaseTerrain()).thenReturn(baseTerrain);
        TerrainFeatures baseTerrainFeature = Mockito.spy(new TerrainFeatures("DenseJungle"));
        when(civilization.isTechnologyInCivilization("BronzeWorking")).thenReturn(true);
        when(baseTerrain.getFeature()).thenReturn(baseTerrainFeature);
        Worker.setHashMap();
        Assertions.assertTrue(gameMenuController.makeMine(worker));
    }

    @Test
    public void makeMine_False(){
        int x = 10;
        int y = 1;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        when(worker.getX()).thenReturn(x);
        when(worker.getY()).thenReturn(y);
        database.when(()->GameDatabase.getTileByXAndY(x,y)).thenReturn(tile);
        database.when(()->GameDatabase.getCivilizationByTile(tile)).thenReturn(civilization);
        when(civilization.isTechnologyInCivilization("Mining")).thenReturn(true);
        //when(tile.hasRoad()).thenReturn(false);
        when(tile.getBaseTerrainType()).thenReturn("Hill");
        BaseTerrain baseTerrain = Mockito.spy(new BaseTerrain("Hill"));
        when(tile.getBaseTerrain()).thenReturn(baseTerrain);
        TerrainFeatures baseTerrainFeature = Mockito.spy(new TerrainFeatures("amiri"));
        //when(civilization.isTechnologyInCivilization("BronzeWorking")).thenReturn(true);
        when(baseTerrain.getFeature()).thenReturn(baseTerrainFeature);
        Worker.setHashMap();
        Assertions.assertFalse(gameMenuController.makeMine(worker));
    }

    @Test
    public void makeFarm_True(){
        int x = 10;
        int y = 1;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        when(worker.getX()).thenReturn(x);
        when(worker.getY()).thenReturn(y);
        database.when(()->GameDatabase.getTileByXAndY(x,y)).thenReturn(tile);
        database.when(()->GameDatabase.getCivilizationByTile(tile)).thenReturn(civilization);
        when(civilization.isTechnologyInCivilization("Agriculture")).thenReturn(true);
        //when(tile.hasRoad()).thenReturn(false);
        when(tile.getBaseTerrainType()).thenReturn("Hill");
        BaseTerrain baseTerrain = Mockito.spy(new BaseTerrain("Hill"));
        when(tile.getBaseTerrain()).thenReturn(baseTerrain);
        TerrainFeatures baseTerrainFeature = Mockito.spy(new TerrainFeatures("DenseJungle"));
        when(civilization.isTechnologyInCivilization("BronzeWorking")).thenReturn(true);
        when(baseTerrain.getFeature()).thenReturn(baseTerrainFeature);
        Worker.setHashMap();
        Assertions.assertTrue(gameMenuController.makeFarm(worker));
    }

    @Test
    public void makeFarm_False(){
        int x = 10;
        int y = 1;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        when(worker.getX()).thenReturn(x);
        when(worker.getY()).thenReturn(y);
        database.when(()->GameDatabase.getTileByXAndY(x,y)).thenReturn(tile);
        database.when(()->GameDatabase.getCivilizationByTile(tile)).thenReturn(civilization);
        when(civilization.isTechnologyInCivilization("Agriculture")).thenReturn(false);
        Worker.setHashMap();
        Assertions.assertFalse(gameMenuController.makeFarm(worker));
    }

    @Test
    public void makeRepair_True(){
        int x = 10;
        int y = 1;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        when(worker.getX()).thenReturn(x);
        when(worker.getY()).thenReturn(y);
        when(worker.getIndexOfProject()).thenReturn(15);
        database.when(()->GameDatabase.getTileByXAndY(x,y)).thenReturn(tile);
        when(tile.isRaided()).thenReturn(true);
        Worker.setHashMap();
        Assertions.assertTrue(gameMenuController.makeRepair(worker));
    }

    @Test
    public void makeRepair_False(){
        int x = 10;
        int y = 1;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        when(worker.getX()).thenReturn(x);
        when(worker.getY()).thenReturn(y);
        when(worker.getIndexOfProject()).thenReturn(15);
        database.when(()->GameDatabase.getTileByXAndY(x,y)).thenReturn(tile);
        when(tile.isRaided()).thenReturn(false);
        Worker.setHashMap();
        Assertions.assertFalse(gameMenuController.makeRepair(worker));
    }

    @Test
    public void makeImprovement_True(){
        int x = 10;
        int y = 1;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        when(worker.getX()).thenReturn(x);
        when(worker.getY()).thenReturn(y);
        database.when(()->GameDatabase.getTileByXAndY(x,y)).thenReturn(tile);
        database.when(()->GameDatabase.getCivilizationByTile(tile)).thenReturn(civilization);
        Worker.setHashMap();
        when(worker.getTypeOfWork()).thenReturn("Quarry");
        when(tile.isImprovementForThisTile("Quarry")).thenReturn(true);
        when(civilization.isTechnologyInCivilization("Masonry")).thenReturn(true);
        Assertions.assertTrue(gameMenuController.makeImprovement(worker));
    }

    @Test
    public void makeImprovement_False(){
        int x = 10;
        int y = 1;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        when(worker.getX()).thenReturn(x);
        when(worker.getY()).thenReturn(y);
        database.when(()->GameDatabase.getTileByXAndY(x,y)).thenReturn(tile);
        database.when(()->GameDatabase.getCivilizationByTile(tile)).thenReturn(civilization);
        Worker.setHashMap();
        when(worker.getTypeOfWork()).thenReturn("Quarry");
        when(tile.isImprovementForThisTile("Quarry")).thenReturn(true);
        when(civilization.isTechnologyInCivilization("Masonry")).thenReturn(false);
        Assertions.assertFalse(gameMenuController.makeImprovement(worker));
    }



//    @Test
//    public void createCombatUnit(){
//        GameMenuController gameMenuController = new GameMenuController(gameModel);
//        String unitType = "Archer";
//        int x = 10;
//        int y = 12;
//        int civilizationIndex = 0;
//        database.when(()->GameDatabase.getTileByXAndY(x,y)).thenReturn(tile);
//        when(new Soldier(x, y, unitType, civilizationIndex)).thenReturn(soldier);
//        gameMenuController.createCombatUnit(unitType,x,y,civilizationIndex);
//        verify(tile).addUnit(soldier);
//        verify(soldier).setTileOfUnit(tile);
//
//    }

//    @Test
//    public void addTileToCivilization(){
//
//    }
}
