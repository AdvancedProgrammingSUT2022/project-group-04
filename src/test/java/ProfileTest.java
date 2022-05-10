import View.Menu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.when;

public class ProfileTest {

    @Mock
    Menu menu;

    @Mock
    String menuShowReturn;

    @BeforeEach
    public void setUp() {
        when(menu.menuShow()).thenReturn(menuShowReturn);
    }

    @Test
    public void testMenuShow() {
        when(menu.menuShow()).thenReturn(menuShowReturn);
        Assertions.assertEquals(menu.menuShow(), menuShowReturn);
    }
}
