//package ViewTest;
//
//import Civilization.Controller.CombatController;
//import Civilization.Controller.GameMenuController;
//import Civilization.Database.GameDatabase;
//
//import Civilization.Model.*;
//import Civilization.View.GameMenu;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.MockedStatic;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//
//import java.io.IOException;
//import java.util.Scanner;
//
//import static org.mockito.Mockito.*;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.regex.Matcher;
//
//import static org.testng.Assert.assertEquals;
//import static org.testng.Assert.assertNull;
//
//
//@ExtendWith(MockitoExtension.class)
//public class GameMenuTest {
//
//
//    static MockedStatic<GameDatabase> gameDatabase;
//    GameMenu gameMenu;
//
//    @Mock
//    ArrayList<Civilization> civilizations;
//
//    @Mock
//    GameMenuController gameMenuController;
//
//    @Mock
//    Settler settler;
//
//    @Mock
//    Matcher matcher;
//
//    @Mock
//    Tile tile;
//
//    @Mock
//    City city;
//
//    @Mock
//    Civilization civilization;
//
//    @Mock
//    CombatController combatController;
//
//    @Mock
//    Worker worker;
//
//    @Mock
//    Unit unit;
//
//    @Mock
//    Scanner scanner;
//
//
//    @BeforeEach
//    public void setUp(){
//        gameDatabase = mockStatic(GameDatabase.class);
//        gameMenu = new GameMenu(gameMenuController, combatController);
//        gameMenu.numberOfPlayers = 4;
//        gameMenu.turn = 0;
//    }
//
//    @Test
//    public void runTest() throws IOException {
//        gameDatabase.when(() -> GameDatabase.getPlayers()).thenReturn(civilizations);
//        when(civilizations.get(0)).thenReturn(civilization);
//        when(civilizations.get(gameMenu.getTurn())).thenReturn(civilization);
//        when(civilization.getNickname()).thenReturn("n1");
//        when(scanner.nextLine()).thenReturn("win");
//        gameMenu.run(scanner);
//    }
//
//    @Test
//    public void buyTileWithCoordinate() throws IOException {
//        int turn = 0;
//        when(matcher.group("x")).thenReturn("1");
//        when(matcher.group("y")).thenReturn("1");
//        gameDatabase.when(() -> GameDatabase.getTileByXAndY(1, 1)).thenReturn(tile);
//        gameDatabase.when(() -> GameDatabase.getCityByXAndY(1, 1)).thenReturn(city);
//        gameDatabase.when(() -> GameDatabase.getCityByName("tehran")).thenReturn(city);
//        gameDatabase.when(() -> GameDatabase.getCivilizationByTurn(turn)).thenReturn(civilization);
//        when(gameMenuController.isTileInCivilization(tile, turn)).thenReturn(false);
//        when(gameMenuController.isTileInAnyCivilization(tile)).thenReturn(false);
//        when(gameMenuController.isTileAdjacentToCivilization(tile, civilization)).thenReturn(true);
//        when(civilization.getGold()).thenReturn(100);
//        Assertions.assertEquals("congrats bro you bought it", gameMenu.buyTileWithCoordinate(matcher));
//    }
//
//    @Test
//    public void lockCitizen() throws IOException {
//        when(matcher.group("x")).thenReturn("1");
//        when(matcher.group("y")).thenReturn("1");
//        gameDatabase.when(() -> GameDatabase.getTileByXAndY(1, 1)).thenReturn(tile);
//        gameDatabase.when(() -> GameDatabase.getCityByXAndY(1, 1)).thenReturn(city);
//        gameDatabase.when(() -> GameDatabase.getCityByName("tehran")).thenReturn(city);
//        when(gameMenuController.isTileInCivilization(tile, gameMenu.turn)).thenReturn(true);
//        when(tile.getWorker()).thenReturn(worker);
//        Assertions.assertEquals("worker started locking process successfully!", gameMenu.lockCitizen(matcher));
//    }
//
//    @Test
//    public void changeCapital() throws IOException {
//        int turn = 0;
//        when(matcher.group("cityName")).thenReturn("tehran");
//        when(gameMenuController.isCityValid("tehran")).thenReturn(true);
//        gameDatabase.when(() -> GameDatabase.getCityByName("tehran")).thenReturn(city);
//        when(gameMenuController.isCityForThisCivilization(turn, city)).thenReturn(true);
//        when(gameMenuController.isCityCapital("tehran")).thenReturn(false);
//        gameDatabase.when(() -> GameDatabase.getPlayers()).thenReturn(civilizations);
//        when(civilizations.get(turn)).thenReturn(civilization);
//        Assertions.assertEquals("capital changed successfully", gameMenu.changeCapital(matcher));
//    }
//
//    @Test
//    public void unitStopWork() throws IOException {
//        int turn = 0;
//        when(matcher.group("x")).thenReturn("1");
//        when(matcher.group("y")).thenReturn("1");
//        gameDatabase.when(() -> GameDatabase.getTileByXAndY(1, 1)).thenReturn(tile);
//        gameDatabase.when(() -> GameDatabase.getCityByXAndY(1, 1)).thenReturn(city);
//        when(tile.isRaided()).thenReturn(false);
//        when(gameMenuController.isTileInCivilization(tile, turn)).thenReturn(true);
//        when(tile.getActiveWorker()).thenReturn(worker);
//        when(tile.getIsGettingWorkedOn()).thenReturn(true);
//        when(city.getIsGettingWorkedOn()).thenReturn(true);
//        Assertions.assertEquals("project stopped successfully", gameMenu.unitStopWork(matcher));
//    }
//
//    @Test
//    public void unitBuild() throws IOException {
//        int turn = 0;
//        when(matcher.group("improvement")).thenReturn("Quarry");
//        when(matcher.group("x")).thenReturn("1");
//        when(matcher.group("y")).thenReturn("1");
//        gameDatabase.when(() -> GameDatabase.getTileByXAndY(1, 1)).thenReturn(tile);
//        gameDatabase.when(() -> GameDatabase.getCityByXAndY(1, 1)).thenReturn(city);
//        gameDatabase.when(() -> GameDatabase.getCivilizationByTurn(turn)).thenReturn(civilization);
//        gameDatabase.when(() -> GameDatabase.isTileInCivilization(tile, civilization)).thenReturn(true);
//        when(tile.isRaided()).thenReturn(false);
//        when(gameMenuController.isTileInCivilization(tile, turn)).thenReturn(true);
//        when(gameMenuController.isImprovementValid("Quarry")).thenReturn(true);
//        when(tile.getAvailableWorker()).thenReturn(worker);
//        when(tile.getIsGettingWorkedOn()).thenReturn(false);
//        when(city.getIsGettingWorkedOn()).thenReturn(false);
//        when(gameMenuController.assignNewProject(worker, "Quarry")).thenReturn(false);
//        //GameMenu gameMenu1 = Mockito.spy(new GameMenu(gameMenuController,combatController));
//        Assertions.assertEquals("you can't do that because either this improvement/(rail)road is already in this tile or " +
//                "you don't have the pre-requisite technology", gameMenu.unitBuild(matcher));
//    }
//
//    @Test
//    public void unitRemoveFeature() throws IOException {
//        int turn = 0;
//        when(matcher.group("improvement")).thenReturn("Prairie");
//        when(matcher.group("x")).thenReturn("1");
//        when(matcher.group("y")).thenReturn("1");
//        gameDatabase.when(() -> GameDatabase.getTileByXAndY(1, 1)).thenReturn(tile);
//        gameDatabase.when(() -> GameDatabase.getCityByXAndY(1, 1)).thenReturn(city);
//        gameDatabase.when(() -> GameDatabase.getCivilizationByTurn(turn)).thenReturn(civilization);
//        gameDatabase.when(() -> GameDatabase.isTileInCivilization(tile, civilization)).thenReturn(true);
//        //when(tile.isRaided()).thenReturn(false);
//        when(gameMenuController.isTileInCivilization(tile, turn)).thenReturn(true);
//        //when(gameMenuController.isImprovementValid("Prairie")).thenReturn(true);
//        when(tile.getAvailableWorker()).thenReturn(worker);
//        //when(tile.getIsGettingWorkedOn()).thenReturn(false);
//        when(city.getIsGettingWorkedOn()).thenReturn(false);
//        when(tile.isRaided()).thenReturn(false);
//        when(gameMenuController.assignNewProject(worker, "remove" + "Prairie")).thenReturn(false);
//        Assertions.assertEquals("you can't do that because this feature is not in this tile", gameMenu.unitRemoveFeature(matcher));
//    }
//
//    @Test
//    public void unitRepair() throws IOException {
//        int turn = 0;
//        when(matcher.group("x")).thenReturn("1");
//        when(matcher.group("y")).thenReturn("1");
//        gameDatabase.when(() -> GameDatabase.getTileByXAndY(1, 1)).thenReturn(tile);
//        gameDatabase.when(() -> GameDatabase.getCityByXAndY(1, 1)).thenReturn(city);
//        gameDatabase.when(() -> GameDatabase.getCivilizationByTurn(turn)).thenReturn(civilization);
//        gameDatabase.when(() -> GameDatabase.isTileInCivilization(tile, civilization)).thenReturn(true);
//        when(gameMenuController.isTileInCivilization(tile, turn)).thenReturn(true);
//        when(tile.getAvailableWorker()).thenReturn(worker);
//        when(city.getIsGettingWorkedOn()).thenReturn(false);
//        when(tile.isRaided()).thenReturn(true);
//        when(gameMenuController.assignNewProject(worker, "repair")).thenReturn(false);
//        Assertions.assertEquals("worker successfully assigned", gameMenu.unitRepair(matcher));
//    }
//
//    @Test
//    public void dryUp() throws IOException {
//        when(matcher.group("x")).thenReturn("1");
//        when(matcher.group("y")).thenReturn("1");
//        gameDatabase.when(() -> GameDatabase.getTileByXAndY(1, 1)).thenReturn(tile);
//        when(gameMenuController.isPositionValid(1,1)).thenReturn(true);
//        when(gameMenuController.isTileOcean(tile)).thenReturn(false);
//        when(gameMenuController.tileHasRiver(tile)).thenReturn(true);
//        Assertions.assertEquals(null,gameMenu.dryUp(matcher));
//
//    }
//
//    @Test
//    public void mapShowPosition_invalid() {
//        when(matcher.group("x")).thenReturn("1");
//        when(matcher.group("y")).thenReturn("1");
//        when(gameMenuController.isPositionValid(1, 1)).thenReturn(false);
//        assertEquals(gameMenu.mapShowPosition(matcher), "position is not valid");
//    }
//
//    @Test
//    public void mapShowPosition_valid() {
//        when(matcher.group("x")).thenReturn("1");
//        when(matcher.group("y")).thenReturn("1");
//        when(gameMenuController.isPositionValid(1, 1)).thenReturn(true);
//        assertNull(gameMenu.mapShowPosition(matcher));
//    }
//
//    @Test
//    public void mapShowCity_invalid() throws IOException {
//        when(matcher.group("cityName")).thenReturn("tehran");
//        when(gameMenuController.isCityValid("tehran")).thenReturn(false);
//        assertEquals(gameMenu.mapShowCity(matcher), "selected city is not valid");
//    }
//
//    @Test
//    public void mapShowCity_valid() throws IOException {
//        when(matcher.group("cityName")).thenReturn("tehran");
//        when(gameMenuController.isCityValid("tehran")).thenReturn(true);
//        assertNull(gameMenu.mapShowCity(matcher));
//    }
//
//    @Test
//    public void selectCombat_invalidPosition() throws IOException {
//        when(matcher.group("x")).thenReturn("1");
//        when(matcher.group("y")).thenReturn("1");
//        when(gameMenuController.isPositionValid(1, 1)).thenReturn(false);
//        assertEquals(gameMenu.selectCombat(matcher), "position is not valid");
//    }
//
//    @Test
//    public void selectCombat_nonCombat() throws IOException {
//        when(matcher.group("x")).thenReturn("1");
//        when(matcher.group("y")).thenReturn("1");
//        when(gameMenuController.isPositionValid(1, 1)).thenReturn(true);
//        when(gameMenuController.isCombatUnitInThisPosition(1, 1)).thenReturn(false);
//        assertEquals(gameMenu.selectCombat(matcher), "no combat unit");
//    }
//
//    @Test
//    public void selectCombat() throws IOException {
//        when(matcher.group("x")).thenReturn("1");
//        when(matcher.group("y")).thenReturn("1");
//        when(gameMenuController.isPositionValid(1, 1)).thenReturn(true);
//        when(gameMenuController.isCombatUnitInThisPosition(1, 1)).thenReturn(true);
//        when(gameMenuController.selectCombatUnit(1, 1)).thenReturn(unit);
//        assertEquals(gameMenu.selectCombat(matcher), "unit selected");
//    }
//
//    @Test
//    public void selectNonCombat_invalidPosition() throws IOException {
//        when(matcher.group("x")).thenReturn("1");
//        when(matcher.group("y")).thenReturn("1");
//        when(gameMenuController.isPositionValid(1, 1)).thenReturn(false);
//        assertEquals(gameMenu.selectNonCombat(matcher), "position is not valid");
//    }
//
//    @Test
//    public void selectNonCombat_Combat() throws IOException {
//        when(matcher.group("x")).thenReturn("1");
//        when(matcher.group("y")).thenReturn("1");
//        when(gameMenuController.isPositionValid(1, 1)).thenReturn(true);
//        when(gameMenuController.isNonCombatUnitInThisPosition(1, 1)).thenReturn(false);
//        assertEquals(gameMenu.selectNonCombat(matcher), "no noncombat unit");
//    }
//
//    @Test
//    public void selectNonCombat() throws IOException {
//        when(matcher.group("x")).thenReturn("1");
//        when(matcher.group("y")).thenReturn("1");
//        when(gameMenuController.isPositionValid(1, 1)).thenReturn(true);
//        when(gameMenuController.isNonCombatUnitInThisPosition(1, 1)).thenReturn(true);
//        when(gameMenuController.selectNonCombatUnit(1, 1)).thenReturn(unit);
//        assertEquals(gameMenu.selectNonCombat(matcher), "unit selected");
//    }
//
//    @Test
//    public void mapMove_invalidDirection() {
//        when(matcher.group("direction")).thenReturn("UP");
//        when(matcher.group("c")).thenReturn("1");
//        when(gameMenuController.isDirectionForMapValid("UP")).thenReturn(false);
//        assertEquals(gameMenu.mapMove(matcher), "invalid direction");
//    }
//
//    @Test
//    public void mapMove_invalidPosition() {
//        when(matcher.group("direction")).thenReturn("UP");
//        when(matcher.group("c")).thenReturn("1");
//        when(gameMenuController.isDirectionForMapValid("UP")).thenReturn(true);
//        when(gameMenuController.getX()).thenReturn(1);
//        when(gameMenuController.getY()).thenReturn(1);
//        HashMap<String, Integer> directionX = new HashMap<String, Integer>();
//        {
//            directionX.put("UP", -1);
//            directionX.put("DOWN", 1);
//            directionX.put("RIGHT", 0);
//            directionX.put("LEFT", 0);
//        }
//        HashMap<String, Integer> directionY = new HashMap<String, Integer>();
//        {
//            directionY.put("UP", 0);
//            directionY.put("DOWN", 0);
//            directionY.put("RIGHT", 1);
//            directionY.put("LEFT", -1);
//        }
//        when(gameMenuController.getDirectionX()).thenReturn(directionX);
//        when(gameMenuController.getDirectionY()).thenReturn(directionY);
//        when(gameMenuController.isPositionValid(0, 1)).thenReturn(false);
//        assertEquals(gameMenu.mapMove(matcher), "position is not valid");
//    }
//
//    @Test
//    public void mapMove() {
//        when(matcher.group("direction")).thenReturn("UP");
//        when(matcher.group("c")).thenReturn("1");
//        when(gameMenuController.isDirectionForMapValid("UP")).thenReturn(true);
//        when(gameMenuController.getX()).thenReturn(1);
//        when(gameMenuController.getY()).thenReturn(1);
//        HashMap<String, Integer> directionX = new HashMap<String, Integer>();
//        {
//            directionX.put("UP", -1);
//            directionX.put("DOWN", 1);
//            directionX.put("RIGHT", 0);
//            directionX.put("LEFT", 0);
//        }
//        HashMap<String, Integer> directionY = new HashMap<String, Integer>();
//        {
//            directionY.put("UP", 0);
//            directionY.put("DOWN", 0);
//            directionY.put("RIGHT", 1);
//            directionY.put("LEFT", -1);
//        }
//        when(gameMenuController.getDirectionX()).thenReturn(directionX);
//        when(gameMenuController.getDirectionY()).thenReturn(directionY);
//        when(gameMenuController.isPositionValid(0, 1)).thenReturn(true);
//        assertNull(gameMenu.mapMove(matcher));
//    }
//
//
//    @Test
//    public void unitSleepTest(){
//        Unit unit = mock(Unit.class);
//        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
//        gameMenu.unitSelected = unit;
//        gameMenu.numberOfPlayers = 1;
//        when(gameMenuController.isUnitForThisCivilization(0, unit)).thenReturn(true);
//        String result = gameMenu.unitSleep();
//        Assertions.assertEquals("unit slept", result);
//    }
//
//    @Test
//    public void unitAlertTest(){
//        Unit unit = mock(Unit.class);
//        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
//        gameMenu.unitSelected = unit;
//        gameMenu.numberOfPlayers = 1;
//        when(gameMenuController.isUnitForThisCivilization(0, unit)).thenReturn(true);
//        when(unit.isCombatUnit()).thenReturn(true);
//        String result = gameMenu.unitAlert();
//        Assertions.assertEquals("unit is ready", result);
//    }
//
//    @Test
//    public void unitFortifyTest(){
//        Unit unit = mock(Unit.class);
//        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
//        gameMenu.unitSelected = unit;
//        gameMenu.numberOfPlayers = 1;
//        when(gameMenuController.isUnitForThisCivilization(0, unit)).thenReturn(true);
//        when(unit.isCombatUnit()).thenReturn(true);
//        String result = gameMenu.unitFortify();
//        Assertions.assertEquals("unit fortified", result);
//    }
//
//    @Test
//    public void unitHealTest() throws IOException {
//        Unit unit = mock(Unit.class);
//        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
//        gameMenu.unitSelected = unit;
//        gameMenu.numberOfPlayers = 1;
//        when(gameMenuController.isUnitForThisCivilization(0, unit)).thenReturn(true);
//        when(unit.isCombatUnit()).thenReturn(true);
//        String result = gameMenu.unitHeal();
//        Assertions.assertEquals("unit fortifyHealed", result);
//    }
//
//    @Test
//    public void unitFoundCityTest1() throws IOException {
//        Unit unit = mock(Unit.class);
//        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
//        gameMenu.unitSelected = unit;
//        gameMenu.numberOfPlayers = 1;
//        when(gameMenuController.isUnitForThisCivilization(0, unit)).thenReturn(true);
//        when(unit.isCombatUnit()).thenReturn(false);
//        String result = gameMenu.unitFoundCity(matcher);
//        Assertions.assertEquals("this is a worker", result);
//    }
//
//    @Test
//    public void unitFoundCityTest2() throws IOException {
//        Settler settler = mock(Settler.class);
//        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
//        gameMenu.unitSelected = settler;
//        gameMenu.numberOfPlayers = 1;
//        when(gameMenuController.isUnitForThisCivilization(0, settler)).thenReturn(true);
//        when(settler.isCombatUnit()).thenReturn(false);
//        String result = gameMenu.unitFoundCity(matcher);
//        Assertions.assertEquals("unit found city", result);
//    }
//
//    @Test
//    public void unitPillageCurrentTileTest() throws IOException {
//        Unit unit = mock(Unit.class);
//        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
//        gameMenu.unitSelected = unit;
//        gameMenu.numberOfPlayers = 1;
//        when(gameMenuController.isUnitForThisCivilization(0, unit)).thenReturn(true);
//        when(unit.isCombatUnit()).thenReturn(true);
//        String result = gameMenu.unitPillageCurrentTile();
//        Assertions.assertEquals("unit pillaged tile", result);
//    }
//
//    @Test
//    public void unitWakeTest(){
//        Unit unit = mock(Unit.class);
//        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
//        gameMenu.unitSelected = unit;
//        gameMenu.numberOfPlayers = 1;
//        when(gameMenuController.isUnitForThisCivilization(0, unit)).thenReturn(true);
//        String result = gameMenu.unitWake();
//        Assertions.assertEquals("unit awakened", result);
//    }
//
//    @Test
//    public void unitAttackTest() throws IOException {
//        gameDatabase.when(()->GameDatabase.getTileByXAndY(1,1)).thenReturn(tile);
//        when(matcher.group("x")).thenReturn("1");
//        when(matcher.group("y")).thenReturn("1");
//        Soldier soldier = mock(Soldier.class);
//        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
//        gameMenu.unitSelected = soldier;
//        gameMenu.numberOfPlayers = 1;
//        when(gameMenuController.isUnitForThisCivilization(0, soldier)).thenReturn(true);
//        when(soldier.isTileInRangeOfUnit(tile)).thenReturn(true);
//        when(soldier.getCombatType()).thenReturn("siege");
//        when(combatController.UnitAttackPosition(soldier, 1,1)).thenReturn(true);
//        String result = gameMenu.unitAttack(matcher);
//        Assertions.assertEquals("unit attacked desired position", result);
//    }
//
//    @Test
//    public void unitAttackTest2() throws IOException {
//        gameDatabase.when(()->GameDatabase.getTileByXAndY(1,1)).thenReturn(tile);
//        when(matcher.group("x")).thenReturn("1");
//        when(matcher.group("y")).thenReturn("1");
//        Soldier soldier = mock(Soldier.class);
//        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
//        gameMenu.unitSelected = soldier;
//        gameMenu.numberOfPlayers = 1;
//        when(gameMenuController.isUnitForThisCivilization(0, soldier)).thenReturn(true);
//        when(soldier.isTileInRangeOfUnit(tile)).thenReturn(true);
//        when(combatController.UnitAttackPosition(soldier, 1,1)).thenReturn(false);
//        String result = gameMenu.unitAttack(matcher);
//        Assertions.assertEquals("siege unit is not ready", result);
//    }
//
//    @Test
//    public void unitAttackTes3() throws IOException {
//        gameDatabase.when(()->GameDatabase.getTileByXAndY(1,1)).thenReturn(tile);
//        when(matcher.group("x")).thenReturn("1");
//        when(matcher.group("y")).thenReturn("1");
//        Soldier soldier = mock(Soldier.class);
//        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
//        gameMenu.unitSelected = soldier;
//        gameMenu.numberOfPlayers = 1;
//        when(gameMenuController.isUnitForThisCivilization(0, soldier)).thenReturn(true);
//        when(soldier.isTileInRangeOfUnit(tile)).thenReturn(false);
//        String result = gameMenu.unitAttack(matcher);
//        Assertions.assertEquals("selected position is in not in range of unit", result);
//    }
//
//    @Test
//    public void unitSetupRangeTest(){
//        Soldier soldier = mock(Soldier.class);
//        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
//        gameMenu.unitSelected = soldier;
//        gameMenu.numberOfPlayers = 1;
//        when(gameMenuController.isUnitForThisCivilization(0, soldier)).thenReturn(true);
//        String result = gameMenu.unitSetupRange();
//        Assertions.assertEquals("this is not a siege unit", result);
//    }
//    @Test
//    public void unitSetupRangeTest2(){
//        Soldier soldier = mock(Soldier.class);
//        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
//        gameMenu.unitSelected = soldier;
//        gameMenu.numberOfPlayers = 1;
//        when(combatController.getSiegeUnitReady(soldier)).thenReturn(true);
//        when(gameMenuController.isUnitForThisCivilization(0, soldier)).thenReturn(true);
//        String result = gameMenu.unitSetupRange();
//        Assertions.assertEquals("siege unit is setup", result);
//    }
//
//    @Test
//    public void unitGarrisonTest() throws IOException {
//        Soldier soldier = mock(Soldier.class);
//        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
//        gameMenu.unitSelected = soldier;
//        gameMenu.numberOfPlayers = 1;
//        when(gameMenuController.isUnitForThisCivilization(0, soldier)).thenReturn(true);
//        String result = gameMenu.unitGarrison();
//        Assertions.assertEquals("this units tile is not a city", result);
//    }
//
//    @Test
//    public void unitGarrisonTest2() throws IOException {
//        Soldier soldier = mock(Soldier.class);
//        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
//        gameMenu.unitSelected = soldier;
//        gameMenu.numberOfPlayers = 1;
//        when(gameMenuController.isUnitForThisCivilization(0, soldier)).thenReturn(true);
//        when(gameMenuController.garrisonUnitToCity(soldier)).thenReturn(true);
//        String result = gameMenu.unitGarrison();
//        Assertions.assertEquals("unit garrisoned to city", result);
//    }
//
//    @Test
//    public void unitDeleteTest() throws IOException {
//        Soldier soldier = mock(Soldier.class);
//        GameMenu gameMenu = new GameMenu(gameMenuController, combatController);
//        gameMenu.unitSelected = soldier;
//        gameMenu.numberOfPlayers = 1;
//        when(gameMenuController.isUnitForThisCivilization(0, soldier)).thenReturn(true);
//        String result = gameMenu.unitDelete();
//        Assertions.assertEquals("unit deleted", result);
//    }
//
//    @Test
//    public void menuShowTest() {
//        assertEquals(gameMenu.menuShow(), "Game Menu");
//    }
//
//    @Test
//    public void menuEnter() {
//        assertEquals(gameMenu.menuEnter(matcher), "menu navigation is not possible");
//    }
//
//    @Test
//    public void menuExit() {
//        assertEquals(gameMenu.menuExit(matcher), "you must finish the game to exit");
//    }
//
//    @Test
//    public void changeTurn_invalidCivilization() throws IOException {
//        when(matcher.group("civilizationName")).thenReturn("n1");
//        when(gameMenuController.isCivilizationValid("n1")).thenReturn(false);
//        assertEquals(gameMenu.changeTurn(matcher), "there is no player with nickname n1");
//    }
//
//    @Test
//    public void changeTurn_notCheat() throws IOException {
//        when(matcher.group("civilizationName")).thenReturn("n1");
//        when(gameMenuController.isCivilizationValid("n1")).thenReturn(true);
//        when(gameMenuController.isCheatForTurn("n1", gameMenu.getTurn())).thenReturn(false);
//        assertEquals(gameMenu.changeTurn(matcher), "there is already your turn!");
//    }
//
//    @Test
//    public void changeTurn() throws IOException {
//        when(matcher.group("civilizationName")).thenReturn("n1");
//        when(gameMenuController.isCivilizationValid("n1")).thenReturn(true);
//        when(gameMenuController.isCheatForTurn("n1", gameMenu.getTurn())).thenReturn(true);
//        assertEquals(gameMenu.changeTurn(matcher), "now it's your turn!");
//    }
//
//    @Test
//    public void makeHappy_happy() {
//        gameDatabase.when(() -> GameDatabase.getPlayers()).thenReturn(civilizations);
//        when(civilizations.get(gameMenu.getTurn())).thenReturn(civilization);
//        when(civilization.isHappy()).thenReturn(true);
//        assertEquals(gameMenu.makeHappy(), "you are happy now");
//    }
//
//    @Test
//    public void makeHappy() {
//        gameDatabase.when(() -> GameDatabase.getPlayers()).thenReturn(civilizations);
//        when(civilizations.get(gameMenu.getTurn())).thenReturn(civilization);
//        when(civilization.isHappy()).thenReturn(false);
//        assertEquals(gameMenu.makeHappy(), "now your happiness is 0");
//        verify(gameMenuController).makeHappy(gameMenu.getTurn());
//    }
//
//    @Test
//    public void addScience_invalid() {
//        when(matcher.group("science")).thenReturn("20");
//        when(gameMenuController.isAmountValidForScience(20)).thenReturn(false);
//        assertEquals(gameMenu.addScience(matcher), "invalid amount");
//    }
//
//    @Test
//    public void addScience() {
//        when(matcher.group("science")).thenReturn("20");
//        when(gameMenuController.isAmountValidForScience(20)).thenReturn(true);
//        gameDatabase.when(() -> GameDatabase.getPlayers()).thenReturn(civilizations);
//        when(civilizations.get(gameMenu.getTurn())).thenReturn(civilization);
//        when(civilization.getScience()).thenReturn(25);
//        assertEquals(gameMenu.addScience(matcher), "Now you have 25 science.");
//        verify(gameMenuController).addScience(gameMenu.getTurn(), 20);
//    }
//
//    @Test
//    public void cheatGold_invalidAmount() {
//        when(matcher.group("amount")).thenReturn("20");
//        when(gameMenuController.isAmountValidForGold(20)).thenReturn(false);
//        assertEquals(gameMenu.cheatGold(matcher), "invalid amount");
//    }
//
//    @Test
//    public void cheatGold() {
//        when(matcher.group("amount")).thenReturn("20");
//        when(gameMenuController.isAmountValidForGold(20)).thenReturn(true);
//        gameDatabase.when(() -> GameDatabase.getPlayers()).thenReturn(civilizations);
//        when(civilizations.get(gameMenu.getTurn())).thenReturn(civilization);
//        when(civilization.getGold()).thenReturn(25);
//        assertEquals(gameMenu.cheatGold(matcher), "Now you have 25 golds.");
//        verify(gameMenuController).addGold(gameMenu.getTurn(), 20);
//    }
//
//    @Test
//    public void addHPUnit_invalidAmount() throws IOException {
//        when(matcher.group("x")).thenReturn("1");
//        when(matcher.group("y")).thenReturn("1");
//        when(matcher.group("amount")).thenReturn("5");
//        when(gameMenuController.isAmountValidForHP(5)).thenReturn(false);
//        assertEquals(gameMenu.addHitPointUnit(matcher), "invalid amount");
//    }
//
//    @Test
//    public void addHPUnit_aLotAmount() throws IOException {
//        when(matcher.group("x")).thenReturn("1");
//        when(matcher.group("y")).thenReturn("1");
//        when(matcher.group("amount")).thenReturn("5");
//        when(gameMenuController.isAmountValidForHP(5)).thenReturn(true);
//        when(gameMenuController.isAmountALot(5)).thenReturn(true);
//        assertEquals(gameMenu.addHitPointUnit(matcher), "please cheat with another amount of HP!");
//    }
//
//    @Test
//    public void addHPUnit_invalidPosition() throws IOException {
//        when(matcher.group("x")).thenReturn("1");
//        when(matcher.group("y")).thenReturn("1");
//        when(matcher.group("amount")).thenReturn("5");
//        when(gameMenuController.isAmountValidForHP(5)).thenReturn(true);
//        when(gameMenuController.isAmountALot(5)).thenReturn(false);
//        when(gameMenuController.isPositionValid(1, 1)).thenReturn(false);
//        assertEquals(gameMenu.addHitPointUnit(matcher), "position is not valid");
//    }
//
//    @Test
//    public void addHPUnit_notCombatPosition() throws IOException {
//        when(matcher.group("x")).thenReturn("1");
//        when(matcher.group("y")).thenReturn("1");
//        when(matcher.group("amount")).thenReturn("5");
//        when(gameMenuController.isAmountValidForHP(5)).thenReturn(true);
//        when(gameMenuController.isAmountALot(5)).thenReturn(false);
//        when(gameMenuController.isPositionValid(1, 1)).thenReturn(true);
//        when(gameMenuController.isCombatUnitInThisPosition(1, 1)).thenReturn(false);
//        assertEquals(gameMenu.addHitPointUnit(matcher), "you can't change hit point of non combat units");
//    }
//
//    @Test
//    public void addHPUnit_unitIsNotForYou() throws IOException {
//        when(matcher.group("x")).thenReturn("1");
//        when(matcher.group("y")).thenReturn("1");
//        when(matcher.group("amount")).thenReturn("5");
//        when(gameMenuController.isAmountValidForHP(5)).thenReturn(true);
//        when(gameMenuController.isAmountALot(5)).thenReturn(false);
//        when(gameMenuController.isPositionValid(1, 1)).thenReturn(true);
//        when(gameMenuController.isCombatUnitInThisPosition(1, 1)).thenReturn(true);
//        when(gameMenuController.selectCombatUnit(1, 1)).thenReturn(unit);
//        when(gameMenuController.isUnitForThisCivilization(gameMenu.getTurn(), unit)).thenReturn(false);
//        assertEquals(gameMenu.addHitPointUnit(matcher), "unit in this position is not for your civilization");
//    }
//
//    @Test
//    public void addHPUnit() throws IOException {
//        when(matcher.group("x")).thenReturn("1");
//        when(matcher.group("y")).thenReturn("1");
//        when(matcher.group("amount")).thenReturn("5");
//        when(gameMenuController.isAmountValidForHP(5)).thenReturn(true);
//        when(gameMenuController.isAmountALot(5)).thenReturn(false);
//        when(gameMenuController.isPositionValid(1, 1)).thenReturn(true);
//        when(gameMenuController.isCombatUnitInThisPosition(1, 1)).thenReturn(true);
//        when(gameMenuController.selectCombatUnit(1, 1)).thenReturn(unit);
//        when(gameMenuController.isUnitForThisCivilization(gameMenu.getTurn(), unit)).thenReturn(true);
//        assertEquals(gameMenu.addHitPointUnit(matcher), "5 hit point added to unit in position 1 and 1");
//        verify(gameMenuController).addHP(1, 1, 5);
//    }
//
//    @Test
//    public void addHPCity_invalidAmount() throws IOException {
//        when(matcher.group("cityName")).thenReturn("tehran");
//        when(matcher.group("amount")).thenReturn("5");
//        when(gameMenuController.isAmountValidForHP(5)).thenReturn(false);
//        assertEquals(gameMenu.addHitPointCity(matcher), "invalid amount");
//    }
//
//    @Test
//    public void addHPCity_aLotAmount() throws IOException {
//        when(matcher.group("cityName")).thenReturn("tehran");
//        when(matcher.group("amount")).thenReturn("5");
//        when(gameMenuController.isAmountValidForHP(5)).thenReturn(true);
//        when(gameMenuController.isAmountALot(5)).thenReturn(true);
//        assertEquals(gameMenu.addHitPointCity(matcher), "please cheat with another amount of HP!");
//    }
//
//    @Test
//    public void addHPCity_invalidCity() throws IOException {
//        when(matcher.group("cityName")).thenReturn("tehran");
//        when(matcher.group("amount")).thenReturn("5");
//        when(gameMenuController.isAmountValidForHP(5)).thenReturn(true);
//        when(gameMenuController.isAmountALot(5)).thenReturn(false);
//        when(gameMenuController.isCityValid("tehran")).thenReturn(false);
//        assertEquals(gameMenu.addHitPointCity(matcher), "invalid city");
//
//    }
//
//    @Test
//    public void addHPCity_cityIsNotForYou() throws IOException {
//        when(matcher.group("cityName")).thenReturn("tehran");
//        when(matcher.group("amount")).thenReturn("5");
//        when(gameMenuController.isAmountValidForHP(5)).thenReturn(true);
//        when(gameMenuController.isAmountALot(5)).thenReturn(false);
//        when(gameMenuController.isCityValid("tehran")).thenReturn(true);
//        gameDatabase.when(() -> GameDatabase.getCityByName("tehran")).thenReturn(city);
//        when(gameMenuController.isCityForThisCivilization(gameMenu.getTurn(), city)).thenReturn(false);
//        assertEquals(gameMenu.addHitPointCity(matcher), "city is not for your civilization");
//    }
//
//    @Test
//    public void addHPCity() throws IOException {
//        when(matcher.group("cityName")).thenReturn("tehran");
//        when(matcher.group("amount")).thenReturn("5");
//        when(gameMenuController.isAmountValidForHP(5)).thenReturn(true);
//        when(gameMenuController.isAmountALot(5)).thenReturn(false);
//        when(gameMenuController.isCityValid("tehran")).thenReturn(true);
//        gameDatabase.when(() -> GameDatabase.getCityByName("tehran")).thenReturn(city);
//        when(gameMenuController.isCityForThisCivilization(gameMenu.getTurn(), city)).thenReturn(true);
//        assertEquals(gameMenu.addHitPointCity(matcher), "5 hit point added to city tehran");
//        verify(gameMenuController).addHP("tehran", 5);
//    }
//
//    @Test
//    public void selectCityByName_null() throws IOException {
//        when(matcher.group("cityName")).thenReturn("tehran");
//        when(gameMenuController.isCityValid("tehran")).thenReturn(true);
//        assertNull(gameMenu.citySelectByName(matcher));
//    }
//
//    @Test
//    public void selectCityByName() throws IOException {
//        when(matcher.group("cityName")).thenReturn("tehran");
//        when(gameMenuController.isCityValid("tehran")).thenReturn(false);
//        assertEquals(gameMenu.citySelectByName(matcher), "invalid city");
//    }
//
//    @Test
//    public void citySelectByPosition_invalidPosition() throws IOException {
//        when(matcher.group("x")).thenReturn("1");
//        when(matcher.group("y")).thenReturn("1");
//        when(gameMenuController.isPositionValid(1,1)).thenReturn(false);
//        assertEquals(gameMenu.citySelectByPosition(matcher), "invalid position");
//    }
//
//    @Test
//    public void citySelectByPosition_invalidCityPosition() throws IOException {
//        when(matcher.group("x")).thenReturn("1");
//        when(matcher.group("y")).thenReturn("1");
//        when(gameMenuController.isPositionValid(1,1)).thenReturn(true);
//        when(gameMenuController.isCityPositionValid(1,1)).thenReturn(false);
//        assertEquals(gameMenu.citySelectByPosition(matcher), "no city in position 1 and 1");
//    }
//
//    @Test
//    public void citySelectByPosition() throws IOException {
//        when(matcher.group("x")).thenReturn("1");
//        when(matcher.group("y")).thenReturn("1");
//        when(gameMenuController.isPositionValid(1, 1)).thenReturn(true);
//        when(gameMenuController.isCityPositionValid(1, 1)).thenReturn(true);
//        assertNull(gameMenu.citySelectByPosition(matcher));
//
//    }
//
//    @Test
//    public void getAddTileToCity() throws IOException {
//        when(matcher.group("cityName")).thenReturn("tehran");
//        when(matcher.group("x")).thenReturn("1");
//        when(matcher.group("y")).thenReturn("1");
//        gameDatabase.when(() -> GameDatabase.getTileByXAndY(1, 1)).thenReturn(tile);
//        gameDatabase.when(() -> GameDatabase.getCityByXAndY(1, 1)).thenReturn(null);
//        gameDatabase.when(() -> GameDatabase.getCityByName("tehran")).thenReturn(city);
//        when(gameMenuController.isTileInCivilization(tile,0)).thenReturn(true);
//        when(gameMenuController.isAdjacent(tile, city)).thenReturn(true);
//        Assertions.assertEquals("tile added to the city successfully!",gameMenu.getAddTileToCity(matcher));
//    }
//
//    @Test
//    public void addProduction() throws IOException {
//        int amount = 10;
//        String cityName = "tehran";
//        when(matcher.group("cityName")).thenReturn(cityName);
//        when(matcher.group("amount")).thenReturn(Integer.toString(amount));
//        when(gameMenuController.isCityValid(cityName)).thenReturn(true);
//        when(gameMenuController.isAmountValidForProduction(amount)).thenReturn(true);
//        gameDatabase.when(()->GameDatabase.getCityByName(cityName)).thenReturn(city);
//        when(gameMenuController.isCityForThisCivilization(gameMenu.turn, city)).thenReturn(true);
//        Assertions.assertEquals(Integer.toString(amount) + " production added to " + cityName,gameMenu.addProduction(matcher));
//    }
//
//    @Test
//    public void addScore(){
//        int score = 10;
//        when(matcher.group("score")).thenReturn(Integer.toString(score));
//        when(gameMenuController.isAmountValidForScore(score)).thenReturn(true);
//        gameDatabase.when(()->GameDatabase.getPlayers()).thenReturn(civilizations);
//        when(civilizations.get(gameMenu.turn)).thenReturn(civilization);
//        when(civilization.getScore()).thenReturn(10);
//        Assertions.assertEquals("Now you have " + Integer.toString(GameDatabase.getPlayers().get(gameMenu.turn).getScore()) + " score.",gameMenu.addScore(matcher));
//    }
//
//    @Test
//    public void buildCity() throws IOException {
//        int x = 1;
//        int y = 1;
//        when(matcher.group("x")).thenReturn(Integer.toString(x));
//        when(matcher.group("y")).thenReturn(Integer.toString(y));
//        String cityName = "tehran";
//        when(matcher.group("cityName")).thenReturn(cityName);
//        gameDatabase.when(()->GameDatabase.getCityByName(cityName)).thenReturn(null);
//        gameDatabase.when(()->GameDatabase.getCityByXAndY(x, y)).thenReturn(null);
//        gameDatabase.when(()->GameDatabase.getTileByXAndY(x, y)).thenReturn(tile);
//        when(tile.getSettler()).thenReturn(settler);
//        when(gameMenuController.isUnitForThisCivilization(gameMenu.turn, settler)).thenReturn(true);
//        Assertions.assertEquals("city created successfully!",gameMenu.buildCity(matcher));
//    }
//
//    @Test
//    public void changeTurnByNumber() throws IOException {
//        int amount = 0;
//        when(matcher.group("amount")).thenReturn(Integer.toString(amount));
//        when(gameMenuController.isAmountValidForTurn(amount)).thenReturn(true);
//        gameDatabase.when(()->GameDatabase.getPlayers()).thenReturn(civilizations);
//        when(civilizations.get(gameMenu.turn)).thenReturn(civilization);
//        when(civilization.getNickname()).thenReturn("nesfejahan");
//        Assertions.assertEquals("now it's " + "nesfejahan" + " turn!",gameMenu.changeTurnByNumber(matcher));
//    }
//
//    @Test
//    public void sendMessage() throws IOException {
//        String nickname = "Amiri";
//        String text = "I love nojoom";
//        when(matcher.group("Text")).thenReturn(text);
//        when(matcher.group("Nickname")).thenReturn(nickname);
//        when(gameMenuController.isCivilizationValid(nickname)).thenReturn(true);
//        gameDatabase.when(()->GameDatabase.getPlayers()).thenReturn(civilizations);
//        when(civilizations.get(gameMenu.turn)).thenReturn(civilization);
//        when(civilization.getNickname()).thenReturn("sepehr");
//        Assertions.assertEquals("Message sent.",gameMenu.sendMessage(matcher));
//    }
//
//    @AfterEach
//    public void after() {
//        gameDatabase.close();
//    }
//
//
//}
