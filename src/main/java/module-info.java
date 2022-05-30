 module project.group {
     requires javafx.controls;
     requires javafx.fxml;
     requires javafx.graphics;
     requires com.google.gson;

     opens Civilization.Database to com.google.gson;
     exports Civilization.Controller;
     opens Civilization.Controller to javafx.fxml;

     exports Civilization.View;
     opens Civilization.View to javafx.fxml;

     exports Civilization;
     opens Civilization to javafx.fxml;
 }