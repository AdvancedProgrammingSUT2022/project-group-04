
import Controller.LoginMenuController;
import Model.LoginMenuModel;
import View.LoginMenu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Scanner;
import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginTest {
    @Mock
    LoginMenuController loginMenuController;

    @Mock
    Matcher matcher;

    @Mock
    Scanner scanner;

    LoginMenuController loginMenuControllerTest;
    LoginMenuModel loginMenuModelTest;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void menuShow() {
        LoginMenu loginMenu = new LoginMenu(loginMenuController);
        assertEquals("Login Menu", loginMenu.menuShow());
    }

    @Test
    public void userCreate_testUsernameNotUnique() {
        LoginMenu loginMenu = new LoginMenu(loginMenuController);
        when(matcher.group("username")).thenReturn("");
        when(matcher.group("password")).thenReturn("");
        when(matcher.group("nickname")).thenReturn("");
        when(loginMenuController.isUsernameUnique("")).thenReturn(false);
        assertEquals("user with username  already exists", loginMenu.userCreate(matcher));
    }

    @Test
    public void userCreate_testNicknameNotUnique() {
        LoginMenu loginMenu = new LoginMenu(loginMenuController);
        when(matcher.group("username")).thenReturn("");
        when(matcher.group("password")).thenReturn("");
        when(matcher.group("nickname")).thenReturn("");
        when(loginMenuController.isUsernameUnique("")).thenReturn(true);
        when(loginMenuController.isNicknameUnique("")).thenReturn(false);
        assertEquals("user with nickname  already exists", loginMenu.userCreate(matcher));
    }

    @Test
    public void userCreate_testCreateUser() {
        LoginMenu loginMenu = new LoginMenu(loginMenuController);
        when(matcher.group("username")).thenReturn("");
        when(matcher.group("password")).thenReturn("");
        when(matcher.group("nickname")).thenReturn("");
        when(loginMenuController.isUsernameUnique("")).thenReturn(true);
        when(loginMenuController.isNicknameUnique("")).thenReturn(true);
        assertEquals("user created successfully!", loginMenu.userCreate(matcher));
    }
}
