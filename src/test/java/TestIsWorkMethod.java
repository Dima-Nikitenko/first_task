import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;

public class TestIsWorkMethod extends Assert implements CreateObjectsInterface {

    Random rand = new Random();

    @Test
    void isWorkTrue() {
        Pen iWT = createObjectUsingOneArgumentConstructor(rand.nextInt(2147483647) + 1);
        assertEquals(iWT.isWork(), true);
    }

    @Test
    void isWorkFalse() {
        Pen iWF = createObjectUsingOneArgumentConstructor(rand.nextInt(2147483647) - 2147483647);
        assertEquals(iWF.isWork(), false);
    }
}
