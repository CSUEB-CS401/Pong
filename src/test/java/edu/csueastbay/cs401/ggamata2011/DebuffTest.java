import edu.csueastbay.cs401.ggamata2011.Debuff;
import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class DebuffTest {

    Debuff TestDebuff;

    @BeforeEach
    void setUp() {
        TestDebuff = new Debuff("Debuff",
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
        Collision bang = TestDebuff.getCollision(rect);
        assertTrue(bang.isCollided());
        assertEquals("Debuff", bang.getType());
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
        Collision bang = TestDebuff.getCollision(rect);
        assertFalse(bang.isCollided());
        assertEquals("Debuff", bang.getType());
        assertEquals(15, bang.getCenterX());
        assertEquals(75, bang.getCenterY());
        assertEquals(50, bang.getTop());
        assertEquals(100, bang.getBottom());
        assertEquals(10, bang.getLeft());
        assertEquals(20, bang.getRight());
    }

    @Test
    void getType() {
        assertEquals("Debuff",TestDebuff.getType(),"Should Return 'Debuff'");
    }

    @Test
    void TestinPlay() {
        TestDebuff.InPlay();
        assertEquals(true,TestDebuff.PlayState(),"should return true");
    }

    @Test
    void TestOfPlay()
    {
        TestDebuff.OutOfPlay();
        assertEquals(false,TestDebuff.PlayState(),"should return false");
    }

    @Test
    void debuffRandomizer()
    {
        double value = TestDebuff.DebuffRandomizer();
      assertTrue((value >= 1 && value <= 10),"Should Give Value between 1 and 10 inclusive");
    }

    @Test
    void debuffHeight()
    {
        double value = TestDebuff.DebuffHeight();
        assertTrue((value >= -25 && value <= -5),"Should Give Value between 1 and 21 inclusive");
    }

    @Test
    void debuffSpeed()
    {
        double value = TestDebuff.DebuffSpeed();
        assertTrue((value >= -2.50 && value <= -0.5),"Should Give Value between 1 and 10 inclusive");
    }

}