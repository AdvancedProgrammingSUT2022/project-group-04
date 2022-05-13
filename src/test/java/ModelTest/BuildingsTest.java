package ModelTest;

import Database.GlobalVariables;
import Model.BaseTerrain;
import Model.Building;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BuildingsTest {

    @Test
    public void newBuildings() {
        for (String buildings : GlobalVariables.BUILDINGS) {
            Building building = new Building(buildings);
        }
    }
}
