//package ModelTest;
//
//import Civilization.Database.GlobalVariables;
//import BaseTerrain;
//import Improvement;
//import TerrainFeatures;
//import Tile;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.mockito.Mockito.when;
//import static org.testng.Assert.assertEquals;
//
//@ExtendWith(MockitoExtension.class)
//
//public class ImprovementsTest {
//
//    Improvement improvement;
//
//    @Mock
//    Tile tile;
//
//    @Mock
//    TerrainFeatures terrainFeatures;
//
//    @Mock
//    BaseTerrain baseTerrain;
//
//    @BeforeEach
//    public void setUp() {
//        improvement = new Improvement("Farm");
//    }
//
//    @Test
//    public void newImprovements() {
//        for (String improvementName : GlobalVariables.IMPROVEMENTS) {
//            improvement = new Improvement(improvementName);
//        }
//    }
//
//    @Test
//    public void setTurnsNeed_10() {
//        when(tile.getBaseTerrain()).thenReturn(baseTerrain);
//        when(baseTerrain.getFeature()).thenReturn(terrainFeatures);
//        when(terrainFeatures.getType()).thenReturn("Jungle");
//        improvement.setTurnsNeed(tile);
//        assertEquals(improvement.getTurnsNeed(), 10);
//    }
//
//    @Test
//    public void setTurnsNeed_13() {
//        when(tile.getBaseTerrain()).thenReturn(baseTerrain);
//        when(baseTerrain.getFeature()).thenReturn(terrainFeatures);
//        when(terrainFeatures.getType()).thenReturn("DenseJungle");
//        improvement.setTurnsNeed(tile);
//        assertEquals(improvement.getTurnsNeed(), 13);
//    }
//
//    @Test
//    public void setTurnsNeed_12() {
//        when(tile.getBaseTerrain()).thenReturn(baseTerrain);
//        when(baseTerrain.getFeature()).thenReturn(terrainFeatures);
//        when(terrainFeatures.getType()).thenReturn("Swamp");
//        improvement.setTurnsNeed(tile);
//        assertEquals(improvement.getTurnsNeed(), 12);
//    }
//
//    @Test
//    public void setTurnsNeed_6Type1() {
//        when(tile.getBaseTerrain()).thenReturn(baseTerrain);
//        when(baseTerrain.getFeature()).thenReturn(terrainFeatures);
//        when(terrainFeatures.getType()).thenReturn("Oasis");
//        improvement.setTurnsNeed(tile);
//        assertEquals(improvement.getTurnsNeed(), 6);
//    }
//
//    @Test
//    public void setTurnsNeed_6Type2() {
//        improvement = new Improvement("Camp");
//        when(tile.getBaseTerrain()).thenReturn(baseTerrain);
//        when(baseTerrain.getFeature()).thenReturn(terrainFeatures);
//        improvement.setTurnsNeed(tile);
//        assertEquals(improvement.getTurnsNeed(), 6);
//    }
//
//    @Test
//    public void setTurnsNeed_6Type3() {
//        when(tile.getBaseTerrain()).thenReturn(baseTerrain);
//        when(baseTerrain.getFeature()).thenReturn(null);
//        improvement.setTurnsNeed(tile);
//        assertEquals(improvement.getTurnsNeed(), 6);
//    }
//
//}
