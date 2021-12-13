import edu.csueastbay.cs401.ggamata2011.UpgradeSpeed;
import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpgradeSpeedTest {

    UpgradeSpeed TestSpeed;

    @BeforeEach
    void setUp() {
        TestSpeed = new UpgradeSpeed("UpgradeHeight",
                10,
                50,
                10,
                50,
                10,
                200,
                200.0,
                200);
    }

    @Test
    void getCollision()
    {
        Rectangle rect = new Rectangle(10, 50, 10, 10);
        Collision bang = TestSpeed.getCollision(rect);
        assertTrue(bang.isCollided());
        assertEquals("UpgradeSpeed", bang.getType());
        assertEquals(15, bang.getCenterX());
        assertEquals(75, bang.getCenterY());
        assertEquals(50, bang.getTop());
        assertEquals(100, bang.getBottom());
        assertEquals(10, bang.getLeft());
        assertEquals(20, bang.getRight());
    }

    @Test
    void NoCollision()
    {
        Rectangle rect = new Rectangle(50, 50, 10, 10);
        Collision bang = TestSpeed.getCollision(rect);
        assertFalse(bang.isCollided());
        assertEquals("UpgradeSpeed", bang.getType());
        assertEquals(15, bang.getCenterX());
        assertEquals(75, bang.getCenterY());
        assertEquals(50, bang.getTop());
        assertEquals(100, bang.getBottom());
        assertEquals(10, bang.getLeft());
        assertEquals(20, bang.getRight());
    }

    @Test
    void getType() {
        assertEquals("UpgradeSpeed",TestSpeed.getType(),"Should Return 'UpgradeSpeed'");
    }

    @Test
    void TestinPlay() {
        TestSpeed.InPlay();
        assertEquals(true,TestSpeed.PlayState(),"should return true");
    }

    @Test
    void TestOfPlay()
    {
        TestSpeed.OutOfPlay();
        assertEquals(false,TestSpeed.PlayState(),"should return false");
    }

    @Test
    void speedModify() {

        double value = TestSpeed.SpeedModify();
        assertTrue((value >= 1 && value <= 2),"Should Give Value between 1 and 2 inclusive");
    }
}