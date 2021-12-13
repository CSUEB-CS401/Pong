package edu.csueastbay.cs401.thansen;

import edu.csueastbay.cs401.classic.ClassicPong;
import edu.csueastbay.cs401.pong.*;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import edu.csueastbay.cs401.pong.Game;

import java.util.Random;

/**
 * Pong game with 4 paddles on each side of the screen. Here, paddles are
 * removed from the game if their goals have been hit multiple times, with the
 * last one standing being the winner.
 */
public final class FourWayPong extends Game {
    public static final int NUM_PLAYERS = 4;
    public static final double WALL_THICKNESS = 10;
    public static final double PADDLE_OFFSET = 50;
    public static final double PADDLE_LENGTH = 100;
    public static final double PADDLE_THICKNESS = 10;
    /**
     * Max angle (relative to the normal) by which the paddle can angle the
     * puck.
     */
    public static final double PADDLE_COLLISION_ANGLE = 60;

    private final IntegerProperty[] scores = new IntegerProperty[NUM_PLAYERS];
    private final HorizontalPaddle playerThreePaddle;
    private final HorizontalPaddle playerFourPaddle;

    private final ReadOnlyIntegerProperty victorProperty;
    private final BooleanBinding stillPlaying;
    private final BooleanBinding gameOver;

    /**
     * Creates a FourWayPong game.
     *
     * @param loseScore   Score at which a player will be eliminated from the
     *                    game.
     * @param fieldWidth  Width of the field.
     * @param fieldHeight Height of the field.
     */
    public FourWayPong(int loseScore, double fieldWidth, double fieldHeight) {
        super(loseScore);

        for (int i = 0; i < scores.length; ++i) {
            scores[i] = new SimpleIntegerProperty();
        }

        victorProperty = new SimpleIntegerProperty();
        ((IntegerProperty) victorProperty).bind(
                Bindings.createIntegerBinding(this::getVictorImpl, scores)
        );
        stillPlaying = victorProperty.lessThanOrEqualTo(0);
        gameOver = victorProperty.greaterThan(0);

        final FourWayPuck puck = new FourWayPuck(fieldWidth, fieldHeight, new Random());
        puck.setID("Four Way");
        addPuck(puck);
        puck.visibleProperty().bind(stillPlaying);

        final double wallWidth = fieldWidth / 5;
        final double wallHeight = fieldHeight / 5;

        // Corner walls.
        // These are active as long as the player behind them hasn't been
        // eliminated yet.

        final var player1Alive = playerAlive(1);
        final var player2Alive = playerAlive(2);
        final var player3Alive = playerAlive(3);
        final var player4Alive = playerAlive(4);

        final Wall topLeft = new Wall(
                "Top Left Wall",
                0, 0,
                wallWidth, WALL_THICKNESS
        );
        topLeft.setFill(Color.WHITE);
        addObject(topLeft);
        topLeft.visibleProperty().bind(player3Alive);

        final Wall leftTop = new Wall(
                "Left Top Wall",
                0, 0,
                WALL_THICKNESS, wallHeight
        );
        leftTop.setFill(Color.WHITE);
        addObject(leftTop);
        leftTop.visibleProperty().bind(player1Alive);

        final Wall topRight = new Wall(
                "Top Right Wall",
                fieldWidth - wallWidth, 0,
                wallWidth, WALL_THICKNESS
        );
        topRight.setFill(Color.WHITE);
        addObject(topRight);
        topRight.visibleProperty().bind(player3Alive);

        final Wall rightTop = new Wall(
                "Right Top Wall",
                fieldWidth - WALL_THICKNESS, 0,
                WALL_THICKNESS, wallHeight
        );
        rightTop.setFill(Color.WHITE);
        addObject(rightTop);
        rightTop.visibleProperty().bind(player2Alive);

        final Wall bottomLeft = new Wall(
                "Bottom Left Wall",
                0, fieldHeight - WALL_THICKNESS,
                wallWidth, WALL_THICKNESS
        );
        bottomLeft.setFill(Color.WHITE);
        addObject(bottomLeft);
        bottomLeft.visibleProperty().bind(player4Alive);

        final Wall leftBottom = new Wall(
                "Left Bottom Wall",
                0, fieldHeight - wallHeight,
                WALL_THICKNESS, wallHeight
        );
        leftBottom.setFill(Color.WHITE);
        addObject(leftBottom);
        leftBottom.visibleProperty().bind(player1Alive);

        final Wall bottomRight = new Wall(
                "Bottom Right Wall",
                fieldWidth - wallWidth, fieldHeight - WALL_THICKNESS,
                wallWidth, WALL_THICKNESS
        );
        bottomRight.setFill(Color.WHITE);
        addObject(bottomRight);
        bottomRight.visibleProperty().bind(player4Alive);

        final Wall rightBottom = new Wall(
                "Right Bottom Wall",
                fieldWidth - WALL_THICKNESS, fieldHeight - wallHeight,
                WALL_THICKNESS, wallHeight
        );
        rightBottom.setFill(Color.WHITE);
        addObject(rightBottom);
        rightBottom.visibleProperty().bind(player2Alive);

        // Side walls.
        // These replace the corner walls once the corresponding side's player
        // is eliminated.

        final Wall topWall = new Wall(
                "Top Wall",
                0, 0,
                fieldWidth, WALL_THICKNESS
        );
        topWall.setFill(Color.WHITE);
        addObject(topWall);
        topWall.visibleProperty().bind(playerDead(3));

        final Wall bottomWall = new Wall(
                "Bottom Wall",
                0, fieldHeight - WALL_THICKNESS,
                fieldWidth, WALL_THICKNESS
        );
        bottomWall.setFill(Color.WHITE);
        addObject(bottomWall);
        bottomWall.visibleProperty().bind(playerDead(4));

        final Wall leftWall = new Wall(
                "Left Wall",
                0, 0,
                WALL_THICKNESS, fieldHeight
        );
        leftWall.setFill(Color.WHITE);
        addObject(leftWall);
        leftWall.visibleProperty().bind(playerDead(1));

        final Wall rightWall = new Wall(
                "Right Wall",
                fieldWidth - WALL_THICKNESS, 0,
                WALL_THICKNESS, fieldHeight
        );
        rightWall.setFill(Color.WHITE);
        addObject(rightWall);
        rightWall.visibleProperty().bind(playerDead(2));

        // Goals.
        // Note: Don't also bind visible property with gameOver since we want to
        // display the side that won.

        final Goal left = new Goal(
                "Player 1 Goal",
                0, wallHeight,
                WALL_THICKNESS, fieldHeight - (2 * wallHeight)
        );
        left.setFill(Color.RED);
        addObject(left);
        left.visibleProperty().bind(player1Alive);

        final Goal right = new Goal(
                "Player 2 Goal",
                fieldWidth - WALL_THICKNESS, wallHeight,
                WALL_THICKNESS, fieldHeight - (2 * wallHeight)
        );
        right.setFill(Color.BLUE);
        addObject(right);
        right.visibleProperty().bind(player2Alive);

        final Goal top = new Goal(
                "Player 3 Goal",
                wallWidth, 0,
                fieldWidth - (2 * wallWidth), WALL_THICKNESS
        );
        top.setFill(Color.YELLOW);
        addObject(top);
        top.visibleProperty().bind(player3Alive);

        final Goal bottom = new Goal(
                "Player 4 Goal",
                wallWidth, fieldHeight - WALL_THICKNESS,
                fieldWidth - (2 * wallWidth), WALL_THICKNESS
        );
        bottom.setFill(Color.GREEN);
        addObject(bottom);
        bottom.visibleProperty().bind(player4Alive);

        // Vertical paddles.

        final Paddle playerOne = new Paddle(
                "Player 1 Paddle",
                PADDLE_OFFSET, (fieldHeight / 2) - PADDLE_OFFSET,
                PADDLE_THICKNESS, PADDLE_LENGTH,
                WALL_THICKNESS, fieldHeight - WALL_THICKNESS
        );
        playerOne.setFill(Color.RED);
        addPlayerPaddle(1, playerOne);
        playerOne.visibleProperty().bind(player1Alive.and(stillPlaying));

        final Paddle playerTwo = new Paddle(
                "Player 2 Paddle",
                fieldWidth - PADDLE_OFFSET, (fieldHeight / 2) - PADDLE_OFFSET,
                PADDLE_THICKNESS, PADDLE_LENGTH,
                WALL_THICKNESS, fieldHeight - WALL_THICKNESS
        );
        playerTwo.setFill(Color.BLUE);
        addPlayerPaddle(2, playerTwo);
        playerTwo.visibleProperty().bind(player2Alive.and(stillPlaying));

        // Horizontal paddles.

        playerThreePaddle = new HorizontalPaddle(
                "Player 3 Paddle",
                (fieldWidth / 2) - PADDLE_OFFSET, PADDLE_OFFSET,
                PADDLE_LENGTH, PADDLE_THICKNESS,
                WALL_THICKNESS, fieldWidth - WALL_THICKNESS
        );
        playerThreePaddle.setFill(Color.YELLOW);
        addObject(playerThreePaddle);
        playerThreePaddle.visibleProperty().bind(player3Alive.and(stillPlaying));

        playerFourPaddle = new HorizontalPaddle(
                "Player 4 Paddle",
                (fieldWidth / 2) - PADDLE_OFFSET, fieldHeight - PADDLE_OFFSET,
                PADDLE_LENGTH, PADDLE_THICKNESS,
                WALL_THICKNESS, fieldWidth - WALL_THICKNESS
        );
        playerFourPaddle.setFill(Color.GREEN);
        addObject(playerFourPaddle);
        playerFourPaddle.visibleProperty().bind(player4Alive.and(stillPlaying));
    }

    @Override
    public int getPlayerScore(int player) {
        if (player < 1 || player > scores.length) return 0;
        return scores[player - 1].get();
    }

    /**
     * @return Property version of {@link #getPlayerScore(int)}.
     */
    public ReadOnlyIntegerProperty playerScoreProperty(int player) {
        if (player < 1 || player > scores.length) return null;
        return scores[player - 1];
    }

    @Override
    public void addPointsToPlayer(int player, int value) {
        if (player < 1 || player > scores.length) return;
        scores[player - 1].set(scores[player - 1].get() + value);
    }

    /**
     * @return Whether the given player hasn't been eliminated yet.
     */
    public boolean isPlayerAlive(int player) {
        if (player < 1 || player > scores.length) return false;
        return scores[player - 1].get() < getLoseScore();
    }

    /**
     * @return Property binding version of {@link #isPlayerAlive(int)}.
     */
    public BooleanBinding playerAlive(int player) {
        if (player < 1 || player > scores.length) return null;
        return scores[player - 1].lessThan(getLoseScore());
    }

    /**
     * @return Whether the given player has been eliminated.
     */
    public boolean isPlayerDead(int player) {
        if (player < 1 || player > scores.length) return true;
        return scores[player - 1].get() >= getLoseScore();
    }

    /**
     * @return Property binding version of {@link #isPlayerDead(int)}.
     */
    public BooleanBinding playerDead(int player) {
        if (player < 1 || player > scores.length) return null;
        return scores[player - 1].greaterThanOrEqualTo(getLoseScore());
    }

    /**
     * @return The score required to eliminate a player.
     */
    public int getLoseScore() {
        // Reinterpret victoryScore.
        return getVictoryScore();
    }

    @Override
    public int getVictor() {
        return victorProperty().get();
    }

    /**
     * @return Property version of {@link #getVictor()}.
     */
    public ReadOnlyIntegerProperty victorProperty() {
        return victorProperty;
    }

    private int getVictorImpl() {
        int victor = 0;
        for (int player = 1; player <= NUM_PLAYERS; ++player) {
            if (isPlayerDead(player)) continue;
            // More than one player left, so we haven't reached the game-over
            // state yet.
            if (victor > 0) return 0;
            // Player that hasn't been eliminated yet.
            victor = player;
        }
        return victor;
    }

    /**
     * @return Whether the game has ended and a victor has been decided.
     */
    public boolean isGameOver() {
        return gameOver().get();
    }

    /**
     * @return Property binding version of {@link #isGameOver()}.
     */
    public BooleanBinding gameOver() {
        return gameOver;
    }

    /**
     * @return Whether the game is still going and a victor hasn't been decided
     * yet.
     */
    public boolean isStillPlaying() {
        return stillPlaying().get();
    }

    /**
     * @return Property binding version of {@link #isStillPlaying()}.
     */
    public BooleanBinding stillPlaying() {
        return stillPlaying;
    }

    @Override
    public void move() {
        playerThreePaddle.move();
        playerFourPaddle.move();
        super.move();
    }

    @Override
    public void collisionHandler(Puckable puck, Collision collision) {
        switch (collision.getType()) {
            case "Wall" -> {
                final int player = switch (collision.getObjectID()) {
                    case "Top Wall", "Top Left Wall", "Top Right Wall" -> 3;
                    case "Bottom Wall", "Bottom Left Wall", "Bottom Right Wall" -> 4;
                    case "Left Wall", "Left Top Wall", "Left Bottom Wall" -> 1;
                    case "Right Wall", "Right Top Wall", "Right Bottom Wall" -> 2;
                    default -> throw new Error("Unknown wall " + collision.getObjectID());
                };
                final boolean isCorner = switch (collision.getObjectID()) {
                    case "Top Wall", "Bottom Wall", "Left Wall", "Right Wall" -> false;
                    default -> true;
                };
                // Use corner walls if player is alive, otherwise use the full
                // wall.
                // Note: If we instead used the corner walls and the disabled
                // Goals reinterpreted as walls, we could get a corner case
                // where the puck collides between thw two causing some weird
                // behavior with duplicate collisions.
                if (isPlayerAlive(player) != isCorner) break;
                final double direction = puck.getDirection();
                puck.setDirection(switch (player) {
                            // Vertical wall.
                            case 1, 2 -> 180 - direction;
                            // Horizontal wall.
                            case 3, 4 -> -direction;
                            default -> direction;
                        }
                );
            }
            case "Goal" -> {
                final int player = switch (collision.getObjectID()) {
                    case "Player 1 Goal" -> 1;
                    case "Player 2 Goal" -> 2;
                    case "Player 3 Goal" -> 3;
                    case "Player 4 Goal" -> 4;
                    default -> 0;
                };
                // Disable collision handling for dead players.
                if (!isPlayerAlive(player)) break;
                addPointsToPlayer(player, 1);
                puck.reset();
            }
            case "Paddle" -> {
                final int player = switch (collision.getObjectID()) {
                    case "Player 1 Paddle" -> 1;
                    case "Player 2 Paddle" -> 2;
                    default -> throw new Error("Unknown Paddle " + collision.getObjectID());
                };
                // Disable collision handling for dead players.
                if (!isPlayerAlive(player)) break;

                final double puckCenter = ((Puck) puck).getCenterY();
                switch (player) {
                    // Left: [-60, 60]
                    case 1 -> puck.setDirection(
                            ClassicPong.mapRange(
                                    collision.getTop(), collision.getBottom(),
                                    -PADDLE_COLLISION_ANGLE, PADDLE_COLLISION_ANGLE,
                                    puckCenter
                            )
                    );
                    // Right: [240, 150]
                    case 2 -> puck.setDirection(
                            ClassicPong.mapRange(
                                    collision.getTop(), collision.getBottom(),
                                    180 + PADDLE_COLLISION_ANGLE, 90 + PADDLE_COLLISION_ANGLE,
                                    puckCenter
                            )
                    );
                }
            }
            case "HorizontalPaddle" -> {
                final int player = switch (collision.getObjectID()) {
                    case "Player 3 Paddle" -> 3;
                    case "Player 4 Paddle" -> 4;
                    default -> throw new Error("Unknown HorizontalPaddle " + collision.getObjectID());
                };
                // Disable collision handling for dead players.
                if (!isPlayerAlive(player)) break;

                final double puckCenter = ((Puck) puck).getCenterX();
                switch (player) {
                    // Top: [150, 60]
                    case 3 -> puck.setDirection(
                            ClassicPong.mapRange(
                                    collision.getLeft(), collision.getRight(),
                                    90 + PADDLE_COLLISION_ANGLE, PADDLE_COLLISION_ANGLE,
                                    puckCenter
                            )
                    );
                    // Bottom: [-150, -60]
                    case 4 -> puck.setDirection(
                            ClassicPong.mapRange(
                                    collision.getLeft(), collision.getRight(),
                                    -90 - PADDLE_COLLISION_ANGLE, -PADDLE_COLLISION_ANGLE,
                                    puckCenter
                            )
                    );
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyCode code) {
        switch (code) {
            case T -> playerThreePaddle.moveLeft();
            case Y -> playerThreePaddle.moveRight();
            case V -> playerFourPaddle.moveLeft();
            case B -> playerFourPaddle.moveRight();
            default -> super.keyPressed(code);
        }
    }

    @Override
    public void keyReleased(KeyCode code) {
        switch (code) {
            case T, Y -> playerThreePaddle.stop();
            case V, B -> playerFourPaddle.stop();
            default -> super.keyReleased(code);
        }
    }
}
