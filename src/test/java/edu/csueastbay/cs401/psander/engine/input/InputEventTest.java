package edu.csueastbay.cs401.psander.engine.input;

import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

public class InputEventTest {

    @Test
    void Player1InputCorrectlyIdentified() {
        assertTrue(InputEvent.PLAYER_1_UP.isPlayer1Input());
        assertTrue(InputEvent.PLAYER_1_DOWN.isPlayer1Input());
        assertTrue(InputEvent.PLAYER_1_LEFT.isPlayer1Input());
        assertTrue(InputEvent.PLAYER_1_RIGHT.isPlayer1Input());
        assertFalse(InputEvent.PLAYER_2_UP.isPlayer1Input());
        assertFalse(InputEvent.PLAYER_2_DOWN.isPlayer1Input());
        assertFalse(InputEvent.PLAYER_2_LEFT.isPlayer1Input());
        assertFalse(InputEvent.PLAYER_2_RIGHT.isPlayer1Input());
    }

    @Test
    void Player2InputCorrectlyIdentified() {
        assertTrue(InputEvent.PLAYER_2_UP.isPlayer2Input());
        assertTrue(InputEvent.PLAYER_2_DOWN.isPlayer2Input());
        assertTrue(InputEvent.PLAYER_2_LEFT.isPlayer2Input());
        assertTrue(InputEvent.PLAYER_2_RIGHT.isPlayer2Input());
        assertFalse(InputEvent.PLAYER_1_UP.isPlayer2Input());
        assertFalse(InputEvent.PLAYER_1_DOWN.isPlayer2Input());
        assertFalse(InputEvent.PLAYER_1_LEFT.isPlayer2Input());
        assertFalse(InputEvent.PLAYER_1_RIGHT.isPlayer2Input());
    }

    @Test
    void RetrievesUpInputs() {
        assertEquals(InputEvent.up(1), InputEvent.PLAYER_1_UP, "Player 1 Up is PLAYER_1_UP");
        assertEquals(InputEvent.up(2), InputEvent.PLAYER_2_UP, "Player 2 Up is PLAYER_2_UP");
        assertThrows(InvalidParameterException.class, () -> {
            InputEvent.up(3);
        });
    }

    @Test
    void RetrievesDownInputs() {
        assertEquals(InputEvent.down(1), InputEvent.PLAYER_1_DOWN, "Player 1 Down is PLAYER_1_DOWN");
        assertEquals(InputEvent.down(2), InputEvent.PLAYER_2_DOWN, "Player 2 Down is PLAYER_2_DOWN");
        assertThrows(InvalidParameterException.class, () -> {
            InputEvent.down(3);
        });
    }

    @Test
    void RetrievesLeftInputs() {
        assertEquals(InputEvent.left(1), InputEvent.PLAYER_1_LEFT, "Player 1 Left is PLAYER_1_LEFT");
        assertEquals(InputEvent.left(2), InputEvent.PLAYER_2_LEFT, "Player 2 Left is PLAYER_2_LEFT");
        assertThrows(InvalidParameterException.class, () -> {
            InputEvent.left(3);
        });
    }
    @Test
    void RetrievesRightInputs() {
        assertEquals(InputEvent.right(1), InputEvent.PLAYER_1_RIGHT, "Player 1 Right is PLAYER_1_RIGHT");
        assertEquals(InputEvent.right(2), InputEvent.PLAYER_2_RIGHT, "Player 2 Right is PLAYER_2_RIGHT");
        assertThrows(InvalidParameterException.class, () -> {
            InputEvent.right(3);
        });
    }
}
