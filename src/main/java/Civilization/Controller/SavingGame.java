package Civilization.Controller;

import Civilization.Database.GameDatabase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class SavingGame {
    public static boolean saveGame(CopyOfGameDatabase game){
        try {
            FileWriter fileWriter;
            if (Files.exists(Paths.get("save/game.xml")))
                fileWriter = new FileWriter("save/game.xml");
            else{
                new File("save").mkdir();
                fileWriter = new FileWriter("save/game.xml");
            }
            XStream xStream = new XStream();
            fileWriter.write(xStream.toXML(game));
            fileWriter.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static CopyOfGameDatabase readGame(){
        try {
            String xml = new String(Files.readAllBytes(Paths.get("save/game.xml")));
            XStream xStream = new XStream();
            xStream.addPermission(AnyTypePermission.ANY);
            if(xml.length() != 0){
                CopyOfGameDatabase game = (CopyOfGameDatabase) xStream.fromXML(xml);
                game.setGameDatabase();
                return game;
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }
}
