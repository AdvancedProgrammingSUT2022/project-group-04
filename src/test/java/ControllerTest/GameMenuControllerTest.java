package ControllerTest;

import Controller.GameMenuController;
import Database.GameDatabase;
import Database.UserDatabase;
import Model.GameModel;
import Model.Tile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
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
}
