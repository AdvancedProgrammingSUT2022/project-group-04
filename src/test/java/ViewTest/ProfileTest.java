package ViewTest;


import Civilization.Controller.ProfileMenuController;
import Civilization.Model.User;
import Civilization.View.ProfileMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProfileTest {

    @Mock
    ProfileMenuController profileMenuController;

    @Mock
    Matcher matcher;

    @Mock
    User loggedInUser;

    @BeforeEach
    public void setUp() {

    }

    /**
     * Test of menu show-current command
     */
    @Test
    public void menuShow() {
        ProfileMenu profileMenu = new ProfileMenu(profileMenuController);
        assertEquals("Profile Menu", profileMenu.menuShow());
    }

    /**
     * Test of menu enter command
     */
    @Test
    public void menuEnter() {
        ProfileMenu profileMenu = new ProfileMenu(profileMenuController);
        assertEquals("menu navigation is not possible", profileMenu.menuEnter(matcher));
    }

    /**
     * Test of profile change -u command
     */
    @Test
    public void changeUsername_notUniqueUsername() {
        ProfileMenu profileMenu = new ProfileMenu(profileMenuController);
        when(matcher.group("username")).thenReturn("");
        when(profileMenuController.isUsernameUnique("")).thenReturn(false);
        assertEquals("user with username  already exists", profileMenu.changeUsername(loggedInUser, matcher));
    }

    @Test
    public void changeUsername_uniqueUsername() {
        ProfileMenu profileMenu = new ProfileMenu(profileMenuController);
        when(matcher.group("username")).thenReturn("");
        when(profileMenuController.isUsernameUnique("")).thenReturn(true);
        assertEquals("username changed successfully!", profileMenu.changeUsername(loggedInUser, matcher));
    }

    /**
     * Test of profile change -n command
     */
    @Test
    public void changeNickname_notUniqueNickname() {
        ProfileMenu profileMenu = new ProfileMenu(profileMenuController);
        when(matcher.group("nickname")).thenReturn("");
        when(profileMenuController.isNicknameUnique("")).thenReturn(false);
        assertEquals("user with nickname  already exists", profileMenu.changeNickname(loggedInUser, matcher));
    }

    @Test
    public void changeNickname_uniqueNickname() {
        ProfileMenu profileMenu = new ProfileMenu(profileMenuController);
        when(matcher.group("nickname")).thenReturn("");
        when(profileMenuController.isNicknameUnique("")).thenReturn(true);
        assertEquals("nickname changed successfully!", profileMenu.changeNickname(loggedInUser, matcher));
    }

    /**
     * Test of profile change -p command
     */
    @Test
    public void changePassword_incorrectPassword() {
        ProfileMenu profileMenu = new ProfileMenu(profileMenuController);
        when(matcher.group("currentPassword")).thenReturn("");
        when(matcher.group("newPassword")).thenReturn("");
        when(loggedInUser.getUsername()).thenReturn("");
        when(profileMenuController.isPasswordCorrect("", "")).thenReturn(false);
        assertEquals("current password is invalid", profileMenu.changePassword(loggedInUser, matcher));
    }

    @Test
    public void changePassword_notNewPassword() {
        ProfileMenu profileMenu = new ProfileMenu(profileMenuController);
        when(matcher.group("currentPassword")).thenReturn("");
        when(matcher.group("newPassword")).thenReturn("");
        when(loggedInUser.getUsername()).thenReturn("");
        when(profileMenuController.isPasswordCorrect("", "")).thenReturn(true);
        when(profileMenuController.isNewPasswordDifferent("", "")).thenReturn(false);
        assertEquals("please enter a new password", profileMenu.changePassword(loggedInUser, matcher));
    }

    @Test
    public void changePassword_successful() {
        ProfileMenu profileMenu = new ProfileMenu(profileMenuController);
        when(matcher.group("currentPassword")).thenReturn("");
        when(matcher.group("newPassword")).thenReturn("");
        when(loggedInUser.getUsername()).thenReturn("");
        when(profileMenuController.isPasswordCorrect("", "")).thenReturn(true);
        when(profileMenuController.isNewPasswordDifferent("", "")).thenReturn(true);
        assertEquals("password changed successfully!", profileMenu.changePassword(loggedInUser, matcher));
    }
}
