//package ModelTest;
//
//import Civilization.Model.Civilization;
//import Civilization.Model.Notification;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//
//import static org.mockito.Mockito.mockStatic;
//import static org.mockito.Mockito.when;
//import static org.testng.Assert.*;
//
//@ExtendWith(MockitoExtension.class)
//
//public class NotificationTest {
//
//    String source;
//    String destination;
//    String text;
//    String notificationToString;
//    Notification notification;
//
//    @Mock
//    Civilization civilization;
//
//    @BeforeEach
//    public void setUp() {
//        text = "Hi";
//        source = "n1";
//        destination = "n2";
//        notificationToString = "New Message From n1:\n\t Hi";
//        notification = new Notification(source, destination, text);
//    }
//
//    @Test
//    public void addNotification() {
//        Notification.addNotification(notification);
//    }
//
//    @Test
//    public void get() {
//        Notification.addNotification(notification);
//        when(civilization.getNickname()).thenReturn("n2");
//        ArrayList<Notification> notificationArrayList1 = Notification.get(civilization);
//        ArrayList<Notification> notificationArrayList2 = new ArrayList<Notification>();
//        notificationArrayList2.add(notification);
//        assertEquals(notificationArrayList1, notificationArrayList2);
//    }
//
//    @Test
//    public void getUnreadMessagesNumber() {
//        Notification.addNotification(notification);
//        when(civilization.getNickname()).thenReturn("n2");
//        assertEquals(Notification.getUnreadMessagesNumber(civilization), 1);
//
//    }
//
//    @Test
//    public void notificationToString_unread() {
//        assertFalse(notification.wasRead());
//        assertEquals(notification.toString(), notificationToString);
//    }
//
//    @Test
//    public void notificationToString_read() {
//        notification.read();
//        assertTrue(notification.wasRead());
//        assertEquals(notification.toString(), notificationToString.substring(4));
//    }
//
//    @AfterEach
//    public void end() {
//        Notification.notifications.remove(notification);
//    }
//
//
//}
