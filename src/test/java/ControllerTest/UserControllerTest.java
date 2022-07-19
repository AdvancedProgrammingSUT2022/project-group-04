package ControllerTest;

import Server.UserController;
import Server.UserDatabase;
import Server.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    static MockedStatic<UserDatabase> database;

    @Mock
    User user;

    @BeforeAll
    public static void setUp() {
        database = mockStatic(UserDatabase.class);
    }

    @AfterAll
    public static void salam(){
        database.close();
    }
    /**
     * Test of isPasswordCorrect method
     */
    @Test
    public void isPasswordCorrect_notUserExists() {
        database.when(() -> UserDatabase.getUserByUsername("")).thenReturn(null);
        UserController userController = new UserController();
        assertFalse(userController.isPasswordCorrect("", ""));
    }

    @Test
    public void isPasswordCorrect_correctPassword() {
        database.when(() -> UserDatabase.getUserByUsername("")).thenReturn(user);
        UserController userController = new UserController();
        when(user.getPassword()).thenReturn("");
        assertTrue(userController.isPasswordCorrect("", ""));
    }

    @Test
    public void isPasswordCorrect_incorrectPassword() {
        database.when(() -> UserDatabase.getUserByUsername("")).thenReturn(user);
        UserController userController = new UserController();
        when(user.getPassword()).thenReturn("p");
        assertFalse(userController.isPasswordCorrect("", ""));
    }

}
