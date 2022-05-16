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
        when(matcher.group("x")).thenReturn("1");
        when(matcher.group("y")).thenReturn("1");
        gameDatabase = mockStatic(GameDatabase.class);
        //when(Integer.parseInt("1")).thenReturn(1);
        gameDatabase.when(()->GameDatabase.getTileByXAndY(1,1)).thenReturn(tile);
        gameDatabase.when(()->GameDatabase.getCityByXAndY(1,1)).thenReturn(city);
    }

    @Test
    public void buyTileWithCoordinate(){
        int turn = 0;
        gameDatabase.when(()->GameDatabase.getCivilizationByTurn(turn)).thenReturn(civilization);
        when(gameMenuController.isTileInCivilization(tile,turn)).thenReturn(true);
        GameMenu gameMenu = new GameMenu(gameMenuController,combatController);
        Assertions.assertEquals("you already have this tile!",gameMenu.buyTileWithCoordinate(matcher));
    }
}
