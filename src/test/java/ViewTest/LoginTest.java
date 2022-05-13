package ViewTest;

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


    @BeforeEach
    public void setUp() {

    }

    /**
     * Test of menu show-current command
     */
    @Test
    public void menuShow() {
        LoginMenu loginMenu = new LoginMenu(loginMenuController);
        assertEquals("Login Menu", loginMenu.menuShow());
    }

    /**
     * Test of menu enter command
     */
    @Test
    public void menuEnter() {
        LoginMenu loginMenu = new LoginMenu(loginMenuController);
        assertEquals("please login first", loginMenu.menuEnter(matcher));
    }

    /**
     * Test of user create -u -p -n command
     */
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

    /**
     * Test of user login -u -p command
     */
    @Test
    public void userLogin_userNotExists() {
        LoginMenu loginMenu = new LoginMenu(loginMenuController);
        when(matcher.group("username")).thenReturn("");
        when(matcher.group("password")).thenReturn("");
        when(loginMenuController.isUserExists("")).thenReturn(false);
        assertEquals("Username and password didn't match", loginMenu.userLogin(matcher));
    }

    @Test
    public void userLogin_incorrectPassword() {
        LoginMenu loginMenu = new LoginMenu(loginMenuController);
        when(matcher.group("username")).thenReturn("");
        when(matcher.group("password")).thenReturn("");
        when(loginMenuController.isUserExists("")).thenReturn(true);
        when(loginMenuController.isPasswordCorrect("", "")).thenReturn(false);
        assertEquals("Username and password didn't match", loginMenu.userLogin(matcher));
    }

    @Test
    public void userLogin_correctUsernameAndPassword() {
        LoginMenu loginMenu = new LoginMenu(loginMenuController);
        when(matcher.group("username")).thenReturn("");
        when(matcher.group("password")).thenReturn("");
        when(loginMenuController.isUserExists("")).thenReturn(true);
        when(loginMenuController.isPasswordCorrect("", "")).thenReturn(true);
        assertEquals("user logged in successfully!", loginMenu.userLogin(matcher));
    }
}
