package ControllerTest;

import Controller.GameMenuController;
import Database.GameDatabase;
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
    static MockedStatic<Tile> tile;

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
    public void isUnitCivilianEqual(){
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        when(unit.getUnitType()).thenReturn("");
        Assertions.assertTrue(gameMenuController.isUnitSoldier(unit));
    }

    @Test
    public void isUnitSoldier(){
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        when(unit.getUnitType()).thenReturn("Civilian worker");
        Assertions.assertTrue(gameMenuController.isUnitCivilian(unit));
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
    public void addHPCityName(){
        int x = 10;
        int y = 10;
        GameMenuController gameMenuController = mock(GameMenuController.class);
        gameMenuController.addHP(x,y,10);
        verify(gameMenuController).selectCombatUnit(x,y);
    }

//    @Test
//    public void addHPCoordinate(){
//        int x = 1;
//        int y = 2;
//        int amount = 10;
//        GameMenuController gameMenuController = mock(GameMenuController.class);
//        gameMenuController.addHP("",amount);
//        when(gameMenuController.selectUnitCombat(x,y)).thenReturn(unit);
//        verify(unit).addHP(amount);
//        //Assertions.assertTrue(gameMenuController.addHP(x,y,amount));
//    }

    @Test
    public void isCityPositionValid_Equals(){
        int x = 10;
        int y = 20;
        GameMenuController gameMenuController = new GameMenuController(gameModel);
        database.when(()->GameDatabase.getCityByXAndY(x,y)).thenReturn(city);
        Assertions.assertEquals(true,gameMenuController.isCityPositionValid(x,y));
    }

    @Test
    public void isDestinationOKForMove_False1(){

    }

    @Test
    public void isDestinationOKForMove_False2(){

    }

    @Test
    public void isDestinationOKForMove_False3(){

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

}
