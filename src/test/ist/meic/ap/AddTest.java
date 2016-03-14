package ist.meic.ap;

import org.testng.annotations.*;
import org.testng.Assert;

public class AddTest {
    @BeforeClass
    public void setUp() {
    }

    @Test
    public void simple() {
        Assert.assertEquals(1, 1);
    }

    @Test
    public void simpleAdd() {
        Assert.assertEquals(5, Add.foo(2, 3));
    }
}
