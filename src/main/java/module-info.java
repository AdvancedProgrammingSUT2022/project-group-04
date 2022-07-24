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

      exports Client.Model to xstream;
     opens Client.Model to com.google.gson,xstream;
     opens Client.View.Components to com.google.gson,xstream;

     exports Client.View.FXMLControllers;
     opens Client.View.FXMLControllers to javafx.fxml;

     exports Client.View.Transitions;
     opens Client.View.Transitions to javafx.fxml,java.desktop;

      exports Client.View;
     opens Client.View to javafx.fxml, javafx.media;

     exports Civilization;
     opens Civilization to javafx.fxml,xstream;

     exports Server.Controller to xstream;
     opens Server.Controller to xstream;
      opens Server to com.google.gson, xstream;


 }