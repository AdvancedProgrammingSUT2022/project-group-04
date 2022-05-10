

import Controller.LoginMenuController;
import Model.LoginMenuModel;
import View.LoginMenu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.regex.Matcher;

import static org.mockito.Mockito.when;

public class LoginTest {
    @Mock
    LoginMenu loginMenu;
    @Mock
        LoginMenuController loginMenuController;
    @Mock
    LoginMenuModel loginMenuModel;


    @BeforeEach
    public void setUp() {
//        loginMenuModel = new LoginMenuModel();
//        loginMenuController = new LoginMenuController(loginMenuModel);
//        loginMenu = new LoginMenu(loginMenuController);
    }

    @Test
    public void testOne() {
        LoginMenuModel loginMenuModelTest = new LoginMenuModel();
        LoginMenuController loginMenuControllerTest = new LoginMenuController(loginMenuModelTest);
        LoginMenu loginMenuTest = new LoginMenu(loginMenuControllerTest);
        //System.out.println(loginMenu.menuShow());
        String loginMenuShow = "Login Menu";
        when(loginMenu.menuShow()).thenReturn(loginMenuShow);
        Assertions.assertNotEquals(loginMenu.menuShow(), loginMenuTest.menuShow());
    }

//    @Test
//    public void testTwo(){
//        when(loginMenu.kooft()).thenReturn();
//    }
}
