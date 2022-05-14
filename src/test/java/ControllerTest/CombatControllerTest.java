package ControllerTest;
import Controller.CombatController;
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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.Assert;

import java.util.ArrayList;

import static Database.GameDatabase.getTileByXAndY;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@ExtendWith(MockitoExtension.class)
public class CombatControllerTest {
    CombatController combatController;

    @BeforeEach
    public void setUp(){
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
        Soldier soldier1 = mock(Soldier.class);
        Unit unit2 = mock(Unit.class);
        Tile tile = mock(Tile.class);
        Tile soldierTile = mock(Tile.class);
        when(soldier1.isCombatUnit()).thenReturn(true);
        when(soldier1.getRange()).thenReturn(2);
        when(unit2.isCombatUnit()).thenReturn(false);
        when(tile.getX()).thenReturn(5);
        when(tile.getY()).thenReturn(5);
        when(soldier1.getTileOfUnit()).thenReturn(soldierTile);
        when(soldierTile.getX()).thenReturn(5);
        when(soldierTile.getY()).thenReturn(6);
        //boolean result = combatController.UnitAttackPosition(soldier1, 5, 5);
        //Assertions.assertEquals(true, result);


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
    public void healUnitTest(){
        Civilization civilization = mock(Civilization.class);
        Tile tile = mock(Tile.class);
        City city = new City("blah", 10, 1,1,1,1,1,1,"tehran", true, "salap", "ocean", 11, 1, null);
        Unit unit = new Unit(1,1,"blah", 10, 0, 0);
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


}
