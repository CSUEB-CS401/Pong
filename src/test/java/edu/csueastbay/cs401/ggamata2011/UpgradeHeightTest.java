import edu.csueastbay.cs401.ggamata2011.UpgradeHeight;
import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpgradeHeightTest {

    UpgradeHeight TestHeight;

    @BeforeEach
    void setUp() {
        TestHeight = new UpgradeHeight("UpgradeHeight",
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
        Collision bang = TestHeight.getCollision(rect);
        assertTrue(bang.isCollided());
        assertEquals("UpgradeHeight", bang.getType());
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
        Collision bang = TestHeight.getCollision(rect);
        assertFalse(bang.isCollided());
        assertEquals("UpgradeHeight", bang.getType());
        assertEquals(15, bang.getCenterX());
        assertEquals(75, bang.getCenterY());
        assertEquals(50, bang.getTop());
        assertEquals(100, bang.getBottom());
        assertEquals(10, bang.getLeft());
        assertEquals(20, bang.getRight());
    }

    @Test
    void getType() {
        assertEquals("UpgradeHeight",TestHeight.getType(),"Should Return 'UpgradeHeight'");
    }

    @Test
    void TestinPlay() {
        TestHeight.InPlay();
        assertEquals(true,TestHeight.PlayState(),"should return true");
    }

    @Test
    void TestOfPlay()
    {
        TestHeight.OutOfPlay();
        assertEquals(false,TestHeight.PlayState(),"should return false");
    }



    @Test
    void heightModify()
    {
        double value = TestHeight.HeightModify();
        assertTrue((value >= 1 && value <= 15),"Should Give Value between 1 and 15 inclusive");
    }
}