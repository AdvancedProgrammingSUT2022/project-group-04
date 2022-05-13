package ControllerTest;

import Controller.GameMenuController;
import Database.GameDatabase;
import Model.GameModel;
import Model.Tile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

public class GameMenuControllerTest {
////    @Mock
////    GameMenuController gameMenuController;
//    @Mock
//    GameMenuController gameMenuController;
//    @Mock
//    GameModel gameModel;
//    @Mock
//    static GameDatabase gameDatabase;
//
//
//    @Test
//    public void isPositionValidTestOne(){
//        //GameModel gameModel = new GameModel();
////        GameMenuController gameMenuController = new GameMenuController(gameModel);
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
//        when(GameDatabase.map.size()).thenReturn(tiles.size());
//        for (int i=0;i<tiles.size();i++){
//            when(GameDatabase.map.get(i).getX()).thenReturn(tiles.get(i).getX());
//            when(GameDatabase.map.get(i).getY()).thenReturn(tiles.get(i).getY());
//        }
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
