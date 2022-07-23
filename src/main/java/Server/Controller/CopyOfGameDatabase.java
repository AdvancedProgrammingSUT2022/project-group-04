package Server.Controller;

import Civilization.Database.GameDatabase;
import Client.Model.Civilization;
import Client.Model.Tile;

import java.util.ArrayList;

public class CopyOfGameDatabase {
    public ArrayList<Civilization> players;
    public ArrayList<Tile> map;

    public int length;
    public int width;

    public int turn;
    public int year;
    public boolean cheated;
    public Civilization cheatedCivilization;
    public CopyOfGameDatabase(ArrayList<Civilization> civilizations,ArrayList<Tile> tiles,int tool,int arz,int nobat
            ,int sal, boolean taghalobKarde,Civilization civilizationtaghalob){
        players = civilizations;
        map = tiles;
        length = tool;
        width = arz;
        year = sal;
        turn = nobat;
        cheated = taghalobKarde;
        cheatedCivilization = civilizationtaghalob;
    }
    public void setGameDatabase(){
        GameDatabase.setStaticFields(players,map,length,width,turn,year,cheated,cheatedCivilization);
    }
}
