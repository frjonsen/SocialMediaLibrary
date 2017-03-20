import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Created by Vakz on 2017-03-20.
 */
public class FirstTest extends junit.framework.TestCase {

    @Test
    public void testGetS() {
        First f = new First();
        assertEquals("something", f.getS());
    }

}