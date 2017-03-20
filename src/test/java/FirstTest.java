import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Created by Vakz on 2017-03-20.
 */
class FirstTest {
    @Test
    void getS() {
        First f = new First();
        assertEquals("something", f.getS());
    }

}