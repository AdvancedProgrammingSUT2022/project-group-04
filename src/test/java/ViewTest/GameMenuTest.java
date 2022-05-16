package ViewTest;

import Controller.CombatController;
import Controller.GameMenuController;
import Database.GameDatabase;
import Model.City;
import Model.Civilization;
import Model.Tile;
<<<<<<< HEAD
import Model.Worker;
=======
import Model.Unit;
>>>>>>> 1190b8418696ff490006d97027e646235dc0c1a9
import View.GameMenu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.asserts.Assertion;

<<<<<<< HEAD
import java.util.ArrayList;
=======
import java.rmi.server.UnicastRemoteObject;
>>>>>>> 1190b8418696ff490006d97027e646235dc0c1a9
import java.util.regex.Matcher;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

@ExtendWith(MockitoExtension.class)
public class GameMenuTest {



    static MockedStatic<GameDatabase> gameDatabase;
    GameMenu gameMenu;

    @Mock
    ArrayList<Civilization> civilizations;

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

    @Mock
    Worker worker;

    @Mock
    Unit unit;

    @BeforeEach
    public void setUp(){
        gameDatabase = mockStatic(GameDatabase.class);
        gameMenu = new GameMenu(gameMenuController, combatController);
    }

    @Test
    public void buyTileWithCoordinate(){
        int turn = 0;
        when(matcher.group("x")).thenReturn("1");
        when(matcher.group("y")).thenReturn("1");
        //when(matcher.group("cityName")).thenReturn("tehran");
        //when(Integer.parseInt("1")).thenReturn(1);
        gameDatabase.when(()->GameDatabase.getTileByXAndY(1,1)).thenReturn(tile);
        gameDatabase.when(()->GameDatabase.getCityByXAndY(1,1)).thenReturn(city);
        gameDatabase.when(()->GameDatabase.getCityByName("tehran")).thenReturn(city);
        gameDatabase.when(()->GameDatabase.getCivilizationByTurn(turn)).thenReturn(civilization);
        ////////
        when(gameMenuController.isTileInCivilization(tile,turn)).thenReturn(false);
        when(gameMenuController.isTileInAnyCivilization(tile)).thenReturn(false);
        when(gameMenuController.isTileAdjacentToCivilization(tile, civilization)).thenReturn(true);
        when(civilization.getGold()).thenReturn(100);

        //GameMenu gameMenu = new GameMenu(gameMenuController,combatController);
        Assertions.assertEquals("congrats bro you bought it",gameMenu.buyTileWithCoordinate(matcher));
    }

    @Test
    public void changeCapital(){
        int turn = 0;
        when(matcher.group("cityName")).thenReturn("tehran");
        when(gameMenuController.isCityValid("tehran")).thenReturn(true);
        gameDatabase.when(()->GameDatabase.getCityByName("tehran")).thenReturn(city);
        when(gameMenuController.isCityForThisCivilization(turn, city)).thenReturn(true);
        when(gameMenuController.isCityCapital("tehran")).thenReturn(false);
        //GameMenu gameMenu = new GameMenu(gameMenuController,combatController);
        gameDatabase.when(()->GameDatabase.getPlayers()).thenReturn(civilizations);
        when(civilizations.get(turn)).thenReturn(civilization);
        Assertions.assertEquals("capital changed successfully",gameMenu.changeCapital(matcher));
    }

    @Test
    public void unitStopWork(){
        int turn = 0;
        when(matcher.group("x")).thenReturn("1");
        when(matcher.group("y")).thenReturn("1");
        gameDatabase.when(()->GameDatabase.getTileByXAndY(1,1)).thenReturn(tile);
        gameDatabase.when(()->GameDatabase.getCityByXAndY(1,1)).thenReturn(city);
        when(tile.isRaided()).thenReturn(false);
        when(gameMenuController.isTileInCivilization(tile, turn)).thenReturn(true);
        when(tile.getActiveWorker()).thenReturn(worker);
        when(tile.getIsGettingWorkedOn()).thenReturn(true);
        when(city.getIsGettingWorkedOn()).thenReturn(true);
        Assertions.assertEquals("project stopped successfully",gameMenu.unitStopWork(matcher));
    }
    @Test
    public void mapShowPosition_invalid() {
        when(matcher.group("x")).thenReturn("1");
        when(matcher.group("y")).thenReturn("1");
        when(gameMenuController.isPositionValid(1, 1)).thenReturn(false);
        //GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
        assertEquals(gameMenu.mapShowPosition(matcher), "position is not valid");
    }

    @Test
    public void mapShowPosition_valid() {
        when(matcher.group("x")).thenReturn("1");
        when(matcher.group("y")).thenReturn("1");
        when(gameMenuController.isPositionValid(1, 1)).thenReturn(true);
        //GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
        assertNull(gameMenu.mapShowPosition(matcher));
    }

    @Test
    public void mapShowCity_invalid() {
        when(matcher.group("cityName")).thenReturn("tehran");
        when(gameMenuController.isCityValid("tehran")).thenReturn(false);
        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
        assertEquals(gameMenu.mapShowCity(matcher), "selected city is not valid");
    }

    @Test
    public void mapShowCity_valid() {
        when(matcher.group("cityName")).thenReturn("tehran");
        when(gameMenuController.isCityValid("tehran")).thenReturn(true);
        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
        assertNull(gameMenu.mapShowCity(matcher));
    }

    @Test
    public void selectCombat_invalidPosition() {
        when(matcher.group("x")).thenReturn("1");
        when(matcher.group("y")).thenReturn("1");
        when(gameMenuController.isPositionValid(1, 1)).thenReturn(false);
        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
        assertEquals(gameMenu.selectCombat(matcher), "position is not valid");
    }

    @Test
    public void selectCombat_nonCombat() {
        when(matcher.group("x")).thenReturn("1");
        when(matcher.group("y")).thenReturn("1");
        when(gameMenuController.isPositionValid(1, 1)).thenReturn(true);
        when(gameMenuController.isCombatUnitInThisPosition(1, 1)).thenReturn(false);
        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
        assertEquals(gameMenu.selectCombat(matcher), "no combat unit");
    }

    @Test
    public void selectCombat() {
        when(matcher.group("x")).thenReturn("1");
        when(matcher.group("y")).thenReturn("1");
        when(gameMenuController.isPositionValid(1, 1)).thenReturn(true);
        when(gameMenuController.isCombatUnitInThisPosition(1, 1)).thenReturn(true);
        when(gameMenuController.selectCombatUnit(1, 1)).thenReturn(unit);
        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
        assertEquals(gameMenu.selectCombat(matcher), "unit selected");
    }

    @Test
    public void selectNonCombat_invalidPosition() {
        when(matcher.group("x")).thenReturn("1");
        when(matcher.group("y")).thenReturn("1");
        when(gameMenuController.isPositionValid(1, 1)).thenReturn(false);
        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
        assertEquals(gameMenu.selectNonCombat(matcher), "position is not valid");
    }

    @Test
    public void selectNonCombat_Combat() {
        when(matcher.group("x")).thenReturn("1");
        when(matcher.group("y")).thenReturn("1");
        when(gameMenuController.isPositionValid(1, 1)).thenReturn(true);
        when(gameMenuController.isNonCombatUnitInThisPosition(1, 1)).thenReturn(false);
        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
        assertEquals(gameMenu.selectNonCombat(matcher), "no noncombat unit");
    }

    @Test
    public void selectNonCombat() {
        when(matcher.group("x")).thenReturn("1");
        when(matcher.group("y")).thenReturn("1");
        when(gameMenuController.isPositionValid(1, 1)).thenReturn(true);
        when(gameMenuController.isNonCombatUnitInThisPosition(1, 1)).thenReturn(true);
        when(gameMenuController.selectNonCombatUnit(1, 1)).thenReturn(unit);
        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
        assertEquals(gameMenu.selectNonCombat(matcher), "unit selected");
    }
}
