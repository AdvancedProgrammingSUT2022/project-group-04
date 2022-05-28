package ModelTest;


import Database.GlobalVariables;
import Model.Soldier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.testng.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)

public class SoldiersTest {

    Soldier soldier;

    String soldierToString;

    @BeforeEach
    public void setUp() {
        soldierToString = "Infantry\nX = 0 Y = 0\nHit point = 10\nPower = 36";
    }

    @Test
    public void newSoldiers() {
        for (String unit : GlobalVariables.UNITS) {
            if(unit.equals("Settler") || unit.equals("worker")) {
                continue;
            }
            soldier = new Soldier(0, 0, unit, 0);
        }
        soldier = new Soldier(0, 0, "Infantry", 0);
        assertEquals(soldier.getUnitType(), "Infantry");
        assertEquals(soldier.getRange(), 2);
        assertEquals(soldier.getCombatType(), "Gunpowder");
        assertEquals(soldier.getRangedCombatStrength(), 0);
        assertEquals(soldier.getCombatStrength(), 36);
        assertEquals(soldier.getTechnologiesNeed().getName(), "ReplaceableParts");
        assertEquals(soldier.getCost(), 300);
        assertEquals(soldier.getSpeed(), 2);
        assertEquals(soldier.getEra(), "Industrial");
        assertEquals(soldier.toString(), soldierToString);
    }
}
