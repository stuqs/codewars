package Walking_in_the_hallway;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class CheckHallway {
    @Test
    public void testContact() {
        assertEquals(1, Hallway.contact("---><----"));
        assertEquals(1, Hallway.contact("--->-<------->----<-"));
        assertEquals(-1, Hallway.contact("----<----->----"));
        assertEquals(2, Hallway.contact(">-----<-->--<-----"));
        assertEquals(3, Hallway.contact(">>-----<<"));
        assertEquals(5, Hallway.contact(">---------------<--------------------------<-------->---------<------->----------<----<---->>----------<------->>>---------------<<------>"));
    }
}