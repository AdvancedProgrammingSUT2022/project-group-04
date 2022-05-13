package ControllerTest;

import Controller.ProfileMenuController;
import Controller.UserController;
import Database.UserDatabase;
import Model.ProfileMenuModel;
import Model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ProfileMenuControllerTest {

    static MockedStatic<UserDatabase> database;

    @Mock
    ProfileMenuModel profileMenuModel;

    @Mock
    UserController userController;

    @BeforeAll
    public static void setUp() {
        database = mockStatic(UserDatabase.class);
    }

    /**
     * Test of isNicknameUnique method
     */
    @Test
    public void isPasswordCorrect() {
        ProfileMenuController profileMenuController = new ProfileMenuController(profileMenuModel);
        database.when(() -> UserDatabase.getUserByNickname("")).thenReturn(null);
        assertTrue(profileMenuController.isNicknameUnique(""));

    }

    /**
     * Test of isNicknameUnique method
     */
    @Test
    public void isNicknameUnique_userNotExists() {
        ProfileMenuController profileMenuController = new ProfileMenuController(profileMenuModel);
        database.when(() -> UserDatabase.getUserByNickname("")).thenReturn(null);
        assertTrue(profileMenuController.isNicknameUnique(""));

    }

    @Test
    public void isNicknameUnique_userExists() {
        ProfileMenuController profileMenuController = new ProfileMenuController(profileMenuModel);
        User user = new User("", "", "");
        database.when(() -> UserDatabase.getUserByNickname("")).thenReturn(user);
        assertFalse(profileMenuController.isNicknameUnique(""));

    }

}
