package ModelTest;

import Civilization.Database.GlobalVariables;
import Civilization.Model.TerrainFeatures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TerrainFeatureTest {
    @Test
    public void newTerrainFeatures() {
        for (String feature : GlobalVariables.TERRAIN_FEATURES) {
            TerrainFeatures terrainFeatures = new TerrainFeatures(feature);
        }
    }
}
