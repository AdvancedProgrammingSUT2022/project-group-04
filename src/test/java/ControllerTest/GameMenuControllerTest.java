package ControllerTest;

import Controller.GameMenuController;
import Database.GameDatabase;
import Database.GlobalVariables;
import Database.UserDatabase;
import Model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

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
        //tiles = GameDatabase.map;
        database = mockStatic(GameDatabase.class);
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

//    @Test
//    public void tileHasRiver_True(){
//        GameMenuController gameMenuController = new GameMenuController(gameModel);
//        boolean river0 = mock(Boolean.class);
//        boolean river1 = mock(Boolean.class);
//        boolean river2 = mock(Boolean.class);
//        boolean river3 = mock(Boolean.class);
//        boolean river4 = mock(Boolean.class);
//        boolean river5 = mock(Boolean.class);
//        ArrayList<Boolean> rivers = mock(ArrayList.class);
//        when(rivers.get(1)).thenReturn(river1);
//        when(river0).thenReturn(true);
//        when(river1).thenReturn(true);
//        when(river2).thenReturn(true);
//        when(river3).thenReturn(true);
//        when(river4).thenReturn(true);
//        when(river5).thenReturn(true);
//        when(tile0.getIsRiver()).thenReturn(rivers);
//        Assertions.assertTrue(gameMenuController.tileHasRiver(tile0));
//    }

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

    //TODO fix this and implement unit test for others like : isNonCombatUnitInThisPosition, selectNonCombatUnit, selectCombatUnit
//    @Test
//    public void isCombatUnitInThisPosition_True(){
//        int x = 10;
//        int y = 20;
//        GameMenuController gameMenuController = new GameMenuController(gameModel) ;
//        Tile tile1 = new Tile("","",x,y);
//        database.when(()->GameDatabase.getTileByXAndY(x,y)).thenReturn(tile1);
//        //ArrayList<Unit> units1 = new ArrayList<>();
//        when(tile1.getUnits()).thenReturn(units);
//        when(units.size()).thenReturn(1);
//        when(units.get(0)).thenReturn(unit);
//        when(unit.getUnitType()).thenReturn("Civilian");
//        Assertions.assertTrue(gameMenuController.isCombatUnitInThisPosition(x,y));
//    }
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
    public void isDestinationOKForMove_False3(){
        int x = 19;
        int y = 20;
        GameMenuController gameMenuController = Mockito.spy(new GameMenuController(gameModel));
        Mockito.doReturn(true).when(gameMenuController).isPositionValid(x,y);
        Mockito.doReturn(false).when(gameMenuController).isUnitSoldier(unit);
        when(GameDatabase.getTileByXAndY(x,y)).thenReturn(tile);
        when(tile.getUnits()).thenReturn(units);
        when(units.size()).thenReturn(1);
        when(units.get(0)).thenReturn(unit);
        //Mockito.doReturn(true).when(gameMenuController).isUnitSoldier(unit);
        boolean result = gameMenuController.isDestinationOkForMove(unit,x,y);
        Assertions.assertFalse(result);
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
        gameMenuController.addTileToCivilization(tile, civilization);
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
    public void createCombatUnit() {
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        gameMenuController.createNonCombatUnit("Spearman", 0, 0, 0);
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



}
