

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
    @Mock
    Matcher matcher;

    LoginMenuController loginMenuControllerTest;
    LoginMenuModel loginMenuModelTest;

    @BeforeEach
    public void setUp() {
        loginMenuModelTest = new LoginMenuModel();
        loginMenuControllerTest = new LoginMenuController(loginMenuModelTest);
    }

    @Test
    public void testOne() {
        LoginMenu loginMenuTest = new LoginMenu(loginMenuControllerTest);
        String loginMenuShow = "Login Menu";
        ///when(loginMenu.menuShow()).thenReturn(loginMenuShow);
        Assertions.assertEquals(loginMenuShow, loginMenuTest.menuShow());
    }

    @Test
    public void testTwo(){
        //when(loginMenuController.isNicknameUnique()).thenReturn();

    }

//    @Test
//    public void testTwo(){
//        when(loginMenu.kooft()).thenReturn();
//    }
}
