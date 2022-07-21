 module project.group {
     requires javafx.controls;
     requires javafx.fxml;
     requires javafx.graphics;
     requires javafx.media;
     requires com.google.gson;
     requires java.sql;
     requires java.desktop;
     requires org.json;
     requires xstream;

     exports Civilization.Database to xstream;
     opens Civilization.Database to com.google.gson, xstream;

     exports Server to xstream;

      exports Civilization.Model to xstream;
     opens Civilization.Model to com.google.gson,xstream;
     opens Civilization.View.Components to com.google.gson,xstream;

     exports Civilization.View.FXMLControllers;
     opens Civilization.View.FXMLControllers to javafx.fxml;

     exports Civilization.View.Transitions;
     opens Civilization.View.Transitions to javafx.fxml,java.desktop;

      exports Civilization.View;
     opens Civilization.View to javafx.fxml, javafx.media;

     exports Civilization;
     opens Civilization to javafx.fxml,xstream;

     exports Civilization.Controller to xstream;
     opens Civilization.Controller to xstream;
      opens Server to com.google.gson, xstream;


 }