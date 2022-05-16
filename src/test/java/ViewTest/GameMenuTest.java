package ViewTest;

import Controller.CombatController;
import Controller.GameMenuController;
import Database.GameDatabase;
import Model.City;
import Model.Civilization;
import Model.Tile;
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

import java.util.regex.Matcher;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

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
        gameDatabase = mockStatic(GameDatabase.class);
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

        GameMenu gameMenu = new GameMenu(gameMenuController,combatController);
        Assertions.assertEquals("congrats bro you bought it",gameMenu.buyTileWithCoordinate(matcher));
    }

    @Test
    public void changeCapital(){
        int turn = 0;
        when(matcher.group("cityName")).thenReturn("tehran");
        when(gameMenuController.isCityValid("tehran")).thenReturn(true);
        when(gameMenuController.isCityForThisCivilization(turn, city)).thenReturn(true);
        when(gameMenuController.isCityCapital("tehran")).thenReturn(false);
        GameMenu gameMenu = new GameMenu(gameMenuController,combatController);
        Assertions.assertEquals("capital changed successfully",gameMenu.changeCapital(matcher));
    }

//    @Test
//    public void
    @Test
    public void mapShowPosition_invalid() {
        when(matcher.group("x")).thenReturn("1");
        when(matcher.group("y")).thenReturn("1");
        when(gameMenuController.isPositionValid(1, 1)).thenReturn(false);
        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
        assertEquals(gameMenu.mapShowPosition(matcher), "position is not valid");
    }

    @Test
    public void mapShowPosition_valid() {
        when(matcher.group("x")).thenReturn("1");
        when(matcher.group("y")).thenReturn("1");
        when(gameMenuController.isPositionValid(1, 1)).thenReturn(true);
        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
        assertNull(gameMenu.mapShowPosition(matcher));
    }

    @Test
    public void mapShowCity_invalid() {
        when(matcher.group("cityName")).thenReturn("tehran");
    }
}
