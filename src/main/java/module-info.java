 module project.group {
     requires javafx.controls;
     requires javafx.fxml;
     requires javafx.graphics;
     requires com.google.gson;
     requires java.sql;

     opens Civilization.Database to com.google.gson;
     opens Civilization.Model to com.google.gson;

     exports Civilization.View.FXMLControllers;
     opens Civilization.View.FXMLControllers to javafx.fxml;

     exports Civilization.View.Transitions;
     opens Civilization.View.Transitions to javafx.fxml;

     exports Civilization.View;
     opens Civilization.View to javafx.fxml;

     exports Civilization;
     opens Civilization to javafx.fxml;
 }