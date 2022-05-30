package DatabaseTest;

import Civilization.Database.GlobalVariables;
import Civilization.Database.UserDatabase;
import Civilization.Model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.*;

@ExtendWith(MockitoExtension.class)

public class UserDatabaseTest {

    static MockedStatic<User> user;

    @Test
    public void getUserByUsername_nullUser() {
        User user = new User("u", "n", "p");
        User.users.add(user);
        String username = "";
        assertNull(UserDatabase.getUserByUsername(username));
        User.users.remove(user);
    }

    @Test
    public void getUserByUsername_notNullUser() {
        User user = new User("u", "n", "p");
        User.users.add(user);
        String username = "u";
        assertNotNull(UserDatabase.getUserByUsername(username));
        User.users.remove(user);
    }

    @Test
    public void getUserByNickname_nullUser() {
        User user = new User("u", "n", "p");
        User.users.add(user);
        String nickname = "";
        assertNull(UserDatabase.getUserByNickname(nickname));
        User.users.remove(user);
    }

    @Test
    public void getUserByNickname_notNullUser() {
        User user = new User("u", "n", "p");
        User.users.add(user);
        String nickname = "n";
        assertNotNull(UserDatabase.getUserByNickname(nickname));
        User.users.remove(user);
    }

    @Test
    public void addUser() {
        user = mockStatic(User.class);
        User user = new User("u", "n", "p");
        UserDatabase.addUser(user);
        User.users.remove(user);
    }

    @Test
    public void getAllUsers() {
        ArrayList<User> users = new ArrayList<User>();
        assertEquals(users, UserDatabase.getAllUsers());
    }

    @Test
    public void readFromFile() throws IOException {
        UserDatabase.readFromFile(GlobalVariables.USER_DATABASE_FILE_NAME);
    }

    @Test
    public void writeOnFile() throws IOException {
        UserDatabase.writeInFile(GlobalVariables.USER_DATABASE_TEST_FILE_NAME);
    }
}

