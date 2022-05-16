package ViewTest;

import Controller.CombatController;
import Controller.GameMenuController;
import Database.GameDatabase;
import Model.*;
import View.GameMenu;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.asserts.Assertion;

import java.util.Set;
import java.util.regex.Matcher;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameMenuTest {



    static MockedStatic<GameDatabase> gameDatabase;

    @Mock
    GameMenuController gameMenuController;

    @Mock
    Matcher matcher;

    @Mock
    Tile tile;

    @Mock
    City city;

    @Mock
    Civilization civilization;

    @Mock
    CombatController combatController;

    @BeforeEach
    public void setUp(){
        //when(matcher.group("x")).thenReturn("1");
        //when(matcher.group("y")).thenReturn("1");
        gameDatabase = mockStatic(GameDatabase.class);
        //when(Integer.parseInt("1")).thenReturn(1);
        gameDatabase.when(()->GameDatabase.getTileByXAndY(1,1)).thenReturn(tile);
        gameDatabase.when(()->GameDatabase.getCityByXAndY(1,1)).thenReturn(city);
    }
    @AfterEach
    public void salam(){
        gameDatabase.close();
    }

    @Test
    public void buyTileWithCoordinate(){
        int turn = 0;
        gameDatabase.when(()->GameDatabase.getCivilizationByTurn(turn)).thenReturn(civilization);
        when(gameMenuController.isTileInCivilization(tile,turn)).thenReturn(true);
        GameMenu gameMenu = new GameMenu(gameMenuController,combatController);
        Assertions.assertEquals("you already have this tile!",gameMenu.buyTileWithCoordinate(matcher));
    }


    @Test
    public void unitSleepTest(){
        Unit unit = mock(Unit.class);
        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
        gameMenu.unitSelected = unit;
        gameMenu.numberOfPlayers = 1;
        when(gameMenuController.isUnitForThisCivilization(0, unit)).thenReturn(true);
        String result = gameMenu.unitSleep();
        Assertions.assertEquals("unit slept", result);
    }

    @Test
    public void unitAlertTest(){
        Unit unit = mock(Unit.class);
        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
        gameMenu.unitSelected = unit;
        gameMenu.numberOfPlayers = 1;
        when(gameMenuController.isUnitForThisCivilization(0, unit)).thenReturn(true);
        when(unit.isCombatUnit()).thenReturn(true);
        String result = gameMenu.unitAlert();
        Assertions.assertEquals("unit is ready", result);
    }

    @Test
    public void unitFortifyTest(){
        Unit unit = mock(Unit.class);
        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
        gameMenu.unitSelected = unit;
        gameMenu.numberOfPlayers = 1;
        when(gameMenuController.isUnitForThisCivilization(0, unit)).thenReturn(true);
        when(unit.isCombatUnit()).thenReturn(true);
        String result = gameMenu.unitFortify();
        Assertions.assertEquals("unit fortified", result);
    }

    @Test
    public void unitHealTest(){
        Unit unit = mock(Unit.class);
        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
        gameMenu.unitSelected = unit;
        gameMenu.numberOfPlayers = 1;
        when(gameMenuController.isUnitForThisCivilization(0, unit)).thenReturn(true);
        when(unit.isCombatUnit()).thenReturn(true);
        String result = gameMenu.unitHeal();
        Assertions.assertEquals("unit fortifyHealed", result);
    }

    @Test
    public void unitFoundCityTest1(){
        Unit unit = mock(Unit.class);
        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
        gameMenu.unitSelected = unit;
        gameMenu.numberOfPlayers = 1;
        when(gameMenuController.isUnitForThisCivilization(0, unit)).thenReturn(true);
        when(unit.isCombatUnit()).thenReturn(false);
        String result = gameMenu.unitFoundCity(matcher);
        Assertions.assertEquals("this is a worker", result);
    }

    @Test
    public void unitFoundCityTest2(){
        Settler settler = mock(Settler.class);
        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
        gameMenu.unitSelected = settler;
        gameMenu.numberOfPlayers = 1;
        when(gameMenuController.isUnitForThisCivilization(0, settler)).thenReturn(true);
        when(settler.isCombatUnit()).thenReturn(false);
        String result = gameMenu.unitFoundCity(matcher);
        Assertions.assertEquals("unit found city", result);
    }

    @Test
    public void unitPillageCurrentTileTest(){
        Unit unit = mock(Unit.class);
        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
        gameMenu.unitSelected = unit;
        gameMenu.numberOfPlayers = 1;
        when(gameMenuController.isUnitForThisCivilization(0, unit)).thenReturn(true);
        when(unit.isCombatUnit()).thenReturn(true);
        String result = gameMenu.unitPillageCurrentTile();
        Assertions.assertEquals("unit pillaged tile", result);
    }

    @Test
    public void unitWakeTest(){
        Unit unit = mock(Unit.class);
        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
        gameMenu.unitSelected = unit;
        gameMenu.numberOfPlayers = 1;
        when(gameMenuController.isUnitForThisCivilization(0, unit)).thenReturn(true);
        String result = gameMenu.unitWake();
        Assertions.assertEquals("unit awakened", result);
    }

    @Test
    public void unitAttackTest(){
        gameDatabase.when(()->GameDatabase.getTileByXAndY(1,1)).thenReturn(tile);
        when(matcher.group("x")).thenReturn("1");
        when(matcher.group("y")).thenReturn("1");
        Soldier soldier = mock(Soldier.class);
        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
        gameMenu.unitSelected = soldier;
        gameMenu.numberOfPlayers = 1;
        when(gameMenuController.isUnitForThisCivilization(0, soldier)).thenReturn(true);
        when(soldier.isTileInRangeOfUnit(tile)).thenReturn(true);
        when(soldier.getCombatType()).thenReturn("siege");
        when(combatController.UnitAttackPosition(soldier, 1,1)).thenReturn(true);
        String result = gameMenu.unitAttack(matcher);
        Assertions.assertEquals("unit attacked desired position", result);
    }

    @Test
    public void unitAttackTest2(){
        gameDatabase.when(()->GameDatabase.getTileByXAndY(1,1)).thenReturn(tile);
        when(matcher.group("x")).thenReturn("1");
        when(matcher.group("y")).thenReturn("1");
        Soldier soldier = mock(Soldier.class);
        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
        gameMenu.unitSelected = soldier;
        gameMenu.numberOfPlayers = 1;
        when(gameMenuController.isUnitForThisCivilization(0, soldier)).thenReturn(true);
        when(soldier.isTileInRangeOfUnit(tile)).thenReturn(true);
        when(combatController.UnitAttackPosition(soldier, 1,1)).thenReturn(false);
        String result = gameMenu.unitAttack(matcher);
        Assertions.assertEquals("siege unit is not ready", result);
    }

    @Test
    public void unitAttackTes3(){
        gameDatabase.when(()->GameDatabase.getTileByXAndY(1,1)).thenReturn(tile);
        when(matcher.group("x")).thenReturn("1");
        when(matcher.group("y")).thenReturn("1");
        Soldier soldier = mock(Soldier.class);
        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
        gameMenu.unitSelected = soldier;
        gameMenu.numberOfPlayers = 1;
        when(gameMenuController.isUnitForThisCivilization(0, soldier)).thenReturn(true);
        when(soldier.isTileInRangeOfUnit(tile)).thenReturn(false);
        String result = gameMenu.unitAttack(matcher);
        Assertions.assertEquals("selected position is in not in range of unit", result);
    }

    @Test
    public void unitSetupRangeTest(){
        Soldier soldier = mock(Soldier.class);
        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
        gameMenu.unitSelected = soldier;
        gameMenu.numberOfPlayers = 1;
        when(gameMenuController.isUnitForThisCivilization(0, soldier)).thenReturn(true);
        String result = gameMenu.unitSetupRange();
        Assertions.assertEquals("this is not a siege unit", result);
    }
    @Test
    public void unitSetupRangeTest2(){
        Soldier soldier = mock(Soldier.class);
        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
        gameMenu.unitSelected = soldier;
        gameMenu.numberOfPlayers = 1;
        when(combatController.getSiegeUnitReady(soldier)).thenReturn(true);
        when(gameMenuController.isUnitForThisCivilization(0, soldier)).thenReturn(true);
        String result = gameMenu.unitSetupRange();
        Assertions.assertEquals("siege unit is setup", result);
    }

    @Test
    public void unitGarrisonTest(){
        Soldier soldier = mock(Soldier.class);
        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
        gameMenu.unitSelected = soldier;
        gameMenu.numberOfPlayers = 1;
        when(gameMenuController.isUnitForThisCivilization(0, soldier)).thenReturn(true);
        String result = gameMenu.unitGarrison();
        Assertions.assertEquals("this units tile is not a city", result);
    }

    @Test
    public void unitGarrisonTest2(){
        Soldier soldier = mock(Soldier.class);
        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
        gameMenu.unitSelected = soldier;
        gameMenu.numberOfPlayers = 1;
        when(gameMenuController.isUnitForThisCivilization(0, soldier)).thenReturn(true);
        when(gameMenuController.garrisonUnitToCity(soldier)).thenReturn(true);
        String result = gameMenu.unitGarrison();
        Assertions.assertEquals("unit garrisoned to city", result);
    }

    @Test
    public void unitDeleteTest(){
        Soldier soldier = mock(Soldier.class);
        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
        gameMenu.unitSelected = soldier;
        gameMenu.numberOfPlayers = 1;
        when(gameMenuController.isUnitForThisCivilization(0, soldier)).thenReturn(true);
        String result = gameMenu.unitDelete();
        Assertions.assertEquals("unit deleted", result);
    }







}
