package ModelTest;

import Civilization.Database.GlobalVariables;
import Civilization.Model.Resources;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ResourcesTest {
    @Test
    public void newResources() {
        for (String resources : GlobalVariables.RESOURCES) {
           Resources resource = new Resources(resources);
        }
    }
}
