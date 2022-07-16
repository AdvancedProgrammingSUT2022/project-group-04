 module project.group {
     requires javafx.controls;
     requires javafx.fxml;
     requires javafx.graphics;
     requires com.google.gson;
     requires java.sql;
     requires java.desktop;
     requires org.json;
     requires java.xml;
     requires xstream;

     exports Civilization.Database;
     opens Civilization.Database to com.google.gson, java.xml;

     exports Civilization.Model;
     opens Civilization.Model to com.google.gson;
     opens Civilization.View.Components to com.google.gson,java.xml;

     exports Civilization.View.FXMLControllers;
     opens Civilization.View.FXMLControllers to javafx.fxml;

     exports Civilization.View.Transitions;
     opens Civilization.View.Transitions to javafx.fxml,java.desktop;

      exports Civilization.View;
     opens Civilization.View to javafx.fxml;

     exports Civilization;
     opens Civilization to javafx.fxml;

     exports Civilization.Controller;
     opens Civilization.Controller to java.xml;



 }