import edu.csueastbay.cs401.ggamata2011.UpgradeablePaddle;
import edu.csueastbay.cs401.pong.Collision;
import edu.csueastbay.cs401.pong.Paddle;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpgradeablePaddleTest {

    UpgradeablePaddle UpdatedPaddleTest;

    @BeforeEach
    void setUp() { UpdatedPaddleTest = new UpgradeablePaddle("Test Paddle", 10, 50, 10, 50, 10, 200);
    }

    @Test
    void TestUpgradeModifiers()
    {
        UpdatedPaddleTest.ModifySpeed(5);
        UpdatedPaddleTest.ModifyHeight(5);

        assertEquals(5.0,UpdatedPaddleTest.GetSpeedModifier(),"Should return 5.0");
        assertEquals(5.0,UpdatedPaddleTest.GetHeightModifier(),"Should return 5.0");

    }

    @Test
    void getID() {
        assertEquals("Test Paddle", UpdatedPaddleTest.getID(),
                "Should return the paddle ID.");
    }

    @Test
    void getType() {
        assertEquals("Paddle", UpdatedPaddleTest.getType(),
                "Should return 'Paddle' for the type.");
    }

    @Test
    void moveUp() {
        UpdatedPaddleTest.moveUp();
        UpdatedPaddleTest.move();
        assertEquals(45, UpdatedPaddleTest.getY(),
                "Should have a Y of 45 after moving up once.");
        UpdatedPaddleTest.move();
        UpdatedPaddleTest.move();
        assertEquals(35, UpdatedPaddleTest.getY(),
                "Should have a Y of 35 after moving up 3 times.");

    }

    @Test
    void moveDown() {
        UpdatedPaddleTest.moveDown();
        UpdatedPaddleTest.move();
        assertEquals(55, UpdatedPaddleTest.getY(),
                "Should have a Y of 55 after moving down once.");
        UpdatedPaddleTest.move();
        UpdatedPaddleTest.move();
        assertEquals(65, UpdatedPaddleTest.getY(),
                "Should have a Y of 65 after moving down 3 times.");
    }

    @Test
    void dontMoveOffTop() {
        UpdatedPaddleTest.moveUp();
        for (int i = 0; i < 20; i++) {
            UpdatedPaddleTest.move();
        }
        assertEquals(10, UpdatedPaddleTest.getY(),
                "Should not move off the top of the field");
    }

    @Test
    void dontMoveOffBottom() {
        UpdatedPaddleTest.moveDown();
        for (int i = 0; i < 20; i++) {
            UpdatedPaddleTest.move();
        }
        assertEquals(150, UpdatedPaddleTest.getY(),
                "Should not move off the bottom of the field");
    }

    @Test
    void stop() {
        UpdatedPaddleTest.moveDown();
        UpdatedPaddleTest.move();
        UpdatedPaddleTest.stop();
        UpdatedPaddleTest.move();
        UpdatedPaddleTest.move();
        assertEquals(55, UpdatedPaddleTest.getY(),
                "Paddle should stop moving after stop is called.");
    }

    @Test
    void getCollision() {
        Rectangle rect = new Rectangle(10, 50, 10, 10);
        Collision bang = UpdatedPaddleTest.getCollision(rect);
        assertTrue(bang.isCollided());
        assertEquals("Paddle", bang.getType());
        assertEquals("Test Paddle", bang.getObjectID());
        assertEquals(15, bang.getCenterX());
        assertEquals(75, bang.getCenterY());
        assertEquals(50, bang.getTop());
        assertEquals(100, bang.getBottom());
        assertEquals(10, bang.getLeft());
        assertEquals(20, bang.getRight());
    }

    @Test
    void getNoCollision() {
        Rectangle rect = new Rectangle(50, 50, 10, 10);
        Collision bang = UpdatedPaddleTest.getCollision(rect);
        assertFalse(bang.isCollided());
        assertEquals("Paddle", bang.getType());
        assertEquals("Test Paddle", bang.getObjectID());
        assertEquals(15, bang.getCenterX());
        assertEquals(75, bang.getCenterY());
        assertEquals(50, bang.getTop());
        assertEquals(100, bang.getBottom());
        assertEquals(10, bang.getLeft());
        assertEquals(20, bang.getRight());
    }
}