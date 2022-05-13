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
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@ExtendWith(MockitoExtension.class)
public class GameMenuControllerTest {


    //static ArrayList<Tile> tiles;
    static MockedStatic<GameDatabase> database;

    @Mock
    GameModel gameModel;

    @Mock
    ArrayList<Tile> tiles;

    @Mock
    Tile tile;

    @Mock
    ArrayList<Improvement> improvements;

    @Mock
    Improvement improvement;

    @Mock
    Unit unit;

    @Mock
    City city;

    @Mock
    ArrayList<Worker> workers;

    @Mock
    ArrayList<Settler> settlers;

    @Mock
    Worker worker;

    @Mock
    Settler settler;

    @Mock
    ArrayList<City> cities;

    @Mock
    Civilization civilization;



    @BeforeAll
    public static void setUp() {
        //tiles = GameDatabase.map;
        database = mockStatic(GameDatabase.class);
    }




    @Test
    public void isPositionValid() {
        int index = 0;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        database.when(() -> GameDatabase.getMap()).thenReturn(tiles);
        when(tiles.size()).thenReturn(10);
        when(tiles.get(index)).thenReturn(tile);
        when(tile.getX()).thenReturn(5);
        when(tile.getY()).thenReturn(10);
        assertTrue(gameMenuController.isPositionValid(5, 10));

    }


//    @Test
//    public void isPositionValidTestOne(){
//        GameMenuController gameMenuController = new GameMenuController(gameModel);
//        Tile tile0 = new Tile("","",0,1);
//        Tile tile1 = new Tile("","",20,30);
//        Tile tile2 = new Tile("","",89,40);
//        Tile tile3 = new Tile("","",2,2);
//        Tile tile4 = new Tile("","",0,3);
//        Tile tile5 = new Tile("","",3,1);
//        Tile tile6 = new Tile("","",5,1);
//        ArrayList<Tile> tiles = new ArrayList<>();
//        tiles.add(tile0);tiles.add(tile1);tiles.add(tile2);tiles.add(tile3);tiles.add(tile4);tiles.add(tile5);tiles.add(tile6);
//        int x = 20;
//        int y = 30;
//        //when(GameDatabase.map).thenReturn(tiles);
//        //when(GameDatabase.map).thenReturn(tiles);
//        when(GameDatabase.map).thenReturn(tiles);
//
//        //database.when(() -> GameDatabase.map.size()).thenReturn(tiles.size());
//        //when(GameDatabase.map.size()).thenReturn(tiles.size());
////        for (int i=0;i<tiles.size();i++){
//////            when(GameDatabase.map.get(i).getX()).thenReturn(tiles.get(i).getX());
//////            when(GameDatabase.map.get(i).getY()).thenReturn(tiles.get(i).getY());
////            int finalI = i;
////            database.when(() -> GameDatabase.map.get(finalI).getX()).thenReturn(tiles.get(i).getX());
////            database.when(() -> GameDatabase.map.get(finalI).getX()).thenReturn(tiles.get(i).getX());
////        }
//        Assertions.assertFalse(gameMenuController.isPositionValid(x,y));
//    }
//
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
