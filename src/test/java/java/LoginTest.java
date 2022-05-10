package java;


import Controller.LoginMenuController;
import Model.LoginMenuModel;
import View.LoginMenu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.testng.asserts.Assertion;

import java.util.regex.Matcher;

import static org.mockito.Mockito.when;

public class LoginTest {
    @Mock
    LoginMenu loginMenu;
    LoginMenuController loginMenuController;
    LoginMenuModel loginMenuModel;
    Matcher matcher;
    @BeforeAll
    public void kooft(){
        loginMenuModel = new LoginMenuModel();
        loginMenuController = new LoginMenuController(loginMenuModel);
        loginMenu = new LoginMenu(loginMenuController);
    }
    @Test
    public void testOne(){
        LoginMenuModel loginMenuModelTest = new LoginMenuModel();
        LoginMenuController loginMenuControllerTest = new LoginMenuController(loginMenuModelTest);
        LoginMenu loginMenuTest = new LoginMenu(loginMenuControllerTest);
        when(loginMenu.menuShow(matcher)).thenReturn("Login Menu");
        Assertions.assertEquals(loginMenu.menuShow(matcher),loginMenuTest.menuShow(matcher));
    }
}
