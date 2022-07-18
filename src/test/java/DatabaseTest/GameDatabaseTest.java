package DatabaseTest;

import Civilization.Database.GameDatabase;
import Civilization.Model.City;
import Civilization.Model.Civilization;
import Civilization.Model.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.mockStatic;
import static org.testng.Assert.*;

@ExtendWith(MockitoExtension.class)

public class GameDatabaseTest {


    ArrayList<Civilization> players;




    @BeforeEach
    public void setUp() throws IOException {
        GameDatabase.players.add(new Civilization("u1", "n1"));
        GameDatabase.players.add(new Civilization("u2", "n2"));
        GameDatabase.generateMap(2);
        GameDatabase.players.get(0).getCities().add(new City("City1"));
        players = GameDatabase.players;
    }

    @Test
    public void setPlayers() throws IOException {
        ArrayList<Civilization> players = GameDatabase.players;
        Civilization civilization = new Civilization("u3", "n3");
        players.add(civilization);
        GameDatabase.setPlayers(players);
        assertEquals(GameDatabase.players, players);
        GameDatabase.players.remove(civilization);
    }

    @Test
    public void getCivilizationByUsername_notNull() throws IOException {
        Civilization civilization = GameDatabase.getCivilizationByUsername("u2");
        assertEquals(civilization.getUsername(), "u2");
    }

    @Test
    public void getCivilizationByUsername_null() throws IOException {
        Civilization civilization = GameDatabase.getCivilizationByUsername("u3");
        assertNull(civilization);
    }

    @Test
    public void getCivilizationByNickname_notNull() throws IOException {
        Civilization civilization = GameDatabase.getCivilizationByNickname("n2");
        assertEquals(civilization.getNickname(), "n2");
    }

    @Test
    public void getCivilizationByNickname_null() throws IOException {
        Civilization civilization = GameDatabase.getCivilizationByNickname("n3");
        assertNull(civilization);
    }

    @Test
    public void getCityByCityName_notNull() throws IOException {
        City city = GameDatabase.getCityByName("City1");
        assertEquals(city.getName(), "City1");
    }

    @Test
    public void getCityByCityName_null() throws IOException {
        City city = GameDatabase.getCityByName("City2");
        assertNull(city);
    }

    @Test
    public void getCityByCityXAndY_notNull() throws IOException {
        City city = GameDatabase.getCityByXAndY(0, 0);
        assertEquals(city.getX(), 0);
        assertEquals(city.getY(), 0);
    }

    @Test
    public void getCityByCityXAndY_null() throws IOException {
        City city = GameDatabase.getCityByXAndY(0, 1);
        assertNull(city);
    }

    @Test
    public void getMap() {
        ArrayList<Tile> map = GameDatabase.getMap();
        assertEquals(GameDatabase.map, map);
    }

    @Test
    public void getTileByXAndY_notNull() throws IOException {
        Tile tile = GameDatabase.getTileByXAndY(0, 0);
        assertEquals(tile.getX(), 0);
        assertEquals(tile.getY(), 0);
    }

    @Test
    public void getTileByXAndY_null() throws IOException {
        Tile tile = GameDatabase.getTileByXAndY(70, 0);
        assertNull(tile);
    }

    @Test
    public void isTileForACity_True() throws IOException {
        Tile tile = GameDatabase.map.get(0);
        assertTrue(GameDatabase.isTileForACity(tile));
    }

    @Test
    public void getCivilizationByTile_notNull() throws IOException {
        Tile tile = GameDatabase.players.get(0).getTiles().get(0);
        Civilization civilization = GameDatabase.getCivilizationByTile(tile);
        assertEquals(civilization.getNickname(), "n1");
    }

    @Test
    public void getCivilizationIndex() throws IOException {
        assertEquals(GameDatabase.getCivilizationIndex("n2"), 1);
        assertEquals(GameDatabase.getCivilizationIndex("n3"), -1);
    }

    @Test
    public void getCivilizationForCity_notNull() throws IOException {
        Civilization civilization = GameDatabase.getCivilizationForCity("City1");
        assertEquals(civilization.getNickname(), "n1");
    }

    @Test
    public void getCivilizationForCity_null() throws IOException {
        Civilization civilization = GameDatabase.getCivilizationForCity("City2");
        assertNull(civilization);
    }

    @Test
    public void nextTurn() throws IOException {
        GameDatabase.nextTurn();
    }

    @Test
    public void getCivilizationByTurn() throws IOException {
        assertEquals(GameDatabase.getCivilizationByTurn(1).getNickname(), "n2");
    }

    @Test
    public void getPlayers() {
        assertEquals(GameDatabase.getPlayers(), players);
    }

    @Test
    public void isTileInCivilization_True() throws IOException {
        Tile tile = GameDatabase.players.get(1).getTiles().get(1);
        assertTrue(GameDatabase.isTileInCivilization(tile, GameDatabase.players.get(1)));
    }



}
