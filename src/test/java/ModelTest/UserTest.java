package ModelTest;

import Civilization.Model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.testng.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)

public class UserTest {

    User user;

    @BeforeEach
    public void setUp() {
        user = new User("u", "n", "p");
    }

    @Test
    public void username() {
        user.changeUsername("u1");
        assertEquals("u1", user.getUsername());
    }

    @Test
    public void password() {
        user.changePassword("p1");
        assertEquals("p1", user.getPassword());
    }

    @Test
    public void nickname() {
        user.changeNickname("n1");
        assertEquals("n1", user.getNickname());
    }

    @Test
    public void score() {
        assertEquals(0, user.getScore());
        user.addScore(1);
        user.addScore(2);
        assertEquals(3, user.getScore());
    }
}
