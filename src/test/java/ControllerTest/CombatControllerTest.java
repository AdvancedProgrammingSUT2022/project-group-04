package ControllerTest;
import Controller.CombatController;
import Controller.GameMenuController;
import Database.GameDatabase;
import Database.GlobalVariables;
import Database.UserDatabase;
import Model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.Assert;

import java.util.ArrayList;

import static Database.GameDatabase.getCityByXAndY;
import static Database.GameDatabase.getTileByXAndY;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@ExtendWith(MockitoExtension.class)
public class CombatControllerTest {
    CombatController combatController;
    static MockedStatic<GameDatabase> database1;

    @Mock
    ArrayList<Tile> tiles;

    @Mock
    Tile tile;

    @Mock
    Civilization civilization;

    @Mock
    City city;

    @Mock
    ArrayList<Unit> units;


    @BeforeEach
    public void setUp(){
        database1 = mockStatic(GameDatabase.class);
        combatController = new CombatController();
    }
    @Test
    public void declareWarTest(){
        Civilization civilization1 = new Civilization("boos", "boos");
        Civilization civilization2 = new Civilization("boos", "boos");
        combatController.declareWar(civilization1, civilization2);
        boolean result1 = civilization1.isInWar();
        boolean result2 = civilization2.isInWar();
        Assertions.assertEquals(true, result1);
        Assertions.assertEquals(true, result2);
    }

    @Test
    public void areInWarTest(){
        Civilization civilization1 = new Civilization("boos", "boos");
        Civilization civilization2 = new Civilization("boos", "boos");
        combatController.declareWar(civilization1, civilization2);
        boolean result = combatController.areInWar(civilization1, civilization2);
        Assertions.assertEquals(true, result);
    }

    @Test
    public void takeHostageTest(){
        ArrayList<Citizen> citizens = new ArrayList<Citizen>(10);
        for (int i = 0; i < 10; i++){
            Citizen citizen = new Citizen(1,1,"worker", 10, 0, false);
            citizens.add(citizen);
        }
        combatController.takeHostage(citizens);
        for (Citizen citizen : citizens){
            Assertions.assertEquals(true, citizen.isHostage());
        }
    }

    @Mock
    GameDatabase gameDatabase;
    @Test
    public void unitAttackPositionTest(){
        City city = mock(City.class);
        Soldier soldier1 = mock(Soldier.class);
        Soldier soldier2 = mock(Soldier.class);
        Soldier soldier3 = mock(Soldier.class);
        Unit unit2 = mock(Unit.class);
        Tile tile = mock(Tile.class);
        Tile soldierTile = mock(Tile.class);
        ArrayList<Unit> units = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            Unit unitInPosition = new Unit(1,1,"Panzer",10, 0, 0);
            units.add(unitInPosition);
        }
        database1.when(()->GameDatabase.getCivilizationByTurn(0)).thenReturn(civilization);
        database1.when(()-> getTileByXAndY(5, 5)).thenReturn(tile);
        database1.when(()->getCityByXAndY(5, 5)).thenReturn(city);
        boolean result = combatController.UnitAttackPosition(soldier1, 5, 5);
        Assertions.assertEquals(false, result);
        when(soldier2.isTileInRangeOfUnit(GameDatabase.getTileByXAndY(5,5))).thenReturn(true);
        when(soldier2.getRange()).thenReturn(0);
        boolean result2 = combatController.UnitAttackPosition(soldier2, 5, 5);
        Assertions.assertEquals(true, result2);
        boolean result3 = combatController.UnitAttackPosition(unit2, 5, 5);
        Assertions.assertEquals(false , result3);

        when(soldier3.isTileInRangeOfUnit(GameDatabase.getTileByXAndY(5,5))).thenReturn(true);
        when(soldier3.getRange()).thenReturn(1);
        boolean result4 = combatController.UnitAttackPosition(soldier3, 5, 5);
        Assertions.assertEquals(true, result4);
    }

    @Test
    public void addXPTest(){
        ArrayList<Unit> units = new ArrayList<>();
        for (int i = 0 ; i < 10; i++){
            Unit unit = new Unit(1,1,"blah", 10, 0, 0);
            units.add(unit);
        }
        combatController.addXP(units, 5);
        Assertions.assertEquals(5, units.get(0).getXP());
    }

    @Test
    public void healUnitTest_1(){
        Civilization civilization = mock(Civilization.class);
        Tile tile = mock(Tile.class);
        Unit unit = new Unit(1,1,"blah", 10, 0, 0);
        database1.when(()->GameDatabase.getTileByXAndY(unit.getX(), unit.getY())).thenReturn(tile);
        database1.when(()->GameDatabase.getCivilizationByTile(tile)).thenReturn(civilization);
        when(civilization.getAllUnitsOfCivilization()).thenReturn(units);
        when(units.contains(unit)).thenReturn(true);
        when(tile.getCity()).thenReturn(city);
        combatController.healUnit(unit);
        int result = unit.getHP();
        Assertions.assertEquals(13, result);
    }

    @Test
    public void healUnitTest_2(){
        Civilization civilization = mock(Civilization.class);
        Tile tile = mock(Tile.class);
        Unit unit = new Unit(1,1,"blah", 10, 0, 0);
        database1.when(()->GameDatabase.getTileByXAndY(unit.getX(), unit.getY())).thenReturn(tile);
        database1.when(()->GameDatabase.getCivilizationByTile(tile)).thenReturn(civilization);
        when(civilization.getAllUnitsOfCivilization()).thenReturn(units);
        when(units.contains(unit)).thenReturn(false);
        combatController.healUnit(unit);
        int result = unit.getHP();
        Assertions.assertEquals(11, result);
    }

    @Test
    public void fortifyUnitTest(){
        Soldier unit = new Soldier(1,1,"Panzer", 0);
        combatController.fortifyUnit(unit);
        int result = unit.getCombatStrength();
        Assertions.assertEquals(63, result);
    }

    @Test
    public void killUnitTest(){
        Unit unit = new Unit(1,1,"blah", 10, 0, 0);
        Tile tile = new Tile("Clear", "Plain", 1,1);
        unit.setTileOfUnit(tile);
        tile.addUnit(unit);
        combatController.killUnit(unit);
        ArrayList<Unit> result = tile.getUnits();
        ArrayList<Unit> expected = new ArrayList<>();
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void getSiegeUnitReadyTest(){
        Soldier unit = new Soldier(1,1,"Trebuchet", 0);
        combatController.getSiegeUnitReady(unit);
        int result = unit.getSiegeReady();
        Assertions.assertEquals(1, result);
        Soldier unit1 = new Soldier(1,1,"Panzer", 0);
        boolean result1 = combatController.getSiegeUnitReady(unit1);
        Assertions.assertEquals(false, result1);
    }

    @Test
    public void destroyCity(){
        City city = mock(City.class);
        when(city.getCitizens()).thenReturn(null);
        when(city.getSettler()).thenReturn(null);
        when(city.getWorker()).thenReturn(null);
        when(city.getBuildings()).thenReturn(null);
        when(city.getTiles()).thenReturn(tiles);
        when(tiles.size()).thenReturn(1);
        when(tiles.get(0)).thenReturn(tile);
        when(tile.hasRoad()).thenReturn(true);
        when(tile.hasRailroad()).thenReturn(true);
        when(tile.getImprovements()).thenReturn(null);
        combatController.destroyCity(city);
        verify(tile).setRoadBroken(true);
        verify(tile).setRailroadBroken(true);
    }

    @AfterEach
    public void after() {
        database1.close();
    }

}
