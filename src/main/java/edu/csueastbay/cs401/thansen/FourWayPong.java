package edu.csueastbay.cs401.thansen;

import edu.csueastbay.cs401.classic.ClassicPong;
import edu.csueastbay.cs401.pong.*;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import edu.csueastbay.cs401.pong.Game;

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

        final FourWayPuck puck = new FourWayPuck(fieldWidth, fieldHeight);
        puck.setID("Four Way");
        addPuck(puck);

        final double wallWidth = fieldWidth / 5;
        final double wallHeight = fieldHeight / 5;

        // Corner walls.

        final Wall topLeft = new Wall(
                "Top Left Wall",
                0, 0,
                wallWidth, WALL_THICKNESS
        );
        topLeft.setFill(Color.WHITE);
        addObject(topLeft);

        final Wall leftTop = new Wall(
                "Left Top Wall",
                0, 0,
                WALL_THICKNESS, wallHeight
        );
        leftTop.setFill(Color.WHITE);
        addObject(leftTop);

        final Wall topRight = new Wall(
                "Top Right Wall",
                fieldWidth - wallWidth, 0,
                wallWidth, WALL_THICKNESS
        );
        topRight.setFill(Color.WHITE);
        addObject(topRight);

        final Wall rightTop = new Wall(
                "Right Top Wall",
                fieldWidth - WALL_THICKNESS, 0,
                WALL_THICKNESS, wallHeight
        );
        rightTop.setFill(Color.WHITE);
        addObject(rightTop);

        final Wall bottomLeft = new Wall(
                "Bottom Left Wall",
                0, fieldHeight - WALL_THICKNESS,
                wallWidth, WALL_THICKNESS
        );
        bottomLeft.setFill(Color.WHITE);
        addObject(bottomLeft);

        final Wall leftBottom = new Wall(
                "Left Bottom Wall",
                0, fieldHeight - wallHeight,
                WALL_THICKNESS, wallHeight
        );
        leftBottom.setFill(Color.WHITE);
        addObject(leftBottom);

        final Wall bottomRight = new Wall(
                "Bottom Right Wall",
                fieldWidth - wallWidth, fieldHeight - WALL_THICKNESS,
                wallWidth, WALL_THICKNESS
        );
        bottomRight.setFill(Color.WHITE);
        addObject(bottomRight);

        final Wall rightBottom = new Wall(
                "Right Bottom Wall",
                fieldWidth - WALL_THICKNESS, fieldHeight - wallHeight,
                WALL_THICKNESS, wallHeight
        );
        rightBottom.setFill(Color.WHITE);
        addObject(rightBottom);

        // Goals.

        final Goal left = new Goal(
                "Player 1 Goal",
                0, wallHeight,
                WALL_THICKNESS, fieldHeight - (2 * wallHeight)
        );
        left.setFill(Color.RED);
        addObject(left);

        final Goal right = new Goal(
                "Player 2 Goal",
                fieldWidth - WALL_THICKNESS, wallHeight,
                WALL_THICKNESS, fieldHeight - (2 * wallHeight)
        );
        right.setFill(Color.BLUE);
        addObject(right);

        final Goal top = new Goal(
                "Player 3 Goal",
                wallWidth, 0,
                fieldWidth - (2 * wallWidth), WALL_THICKNESS
        );
        top.setFill(Color.YELLOW);
        addObject(top);

        final Goal bottom = new Goal(
                "Player 4 Goal",
                wallWidth, fieldHeight - WALL_THICKNESS,
                fieldWidth - (2 * wallWidth), WALL_THICKNESS
        );
        bottom.setFill(Color.GREEN);
        addObject(bottom);

        // Paddles.

        final Paddle playerOne = new Paddle(
                "Player 1 Paddle",
                PADDLE_OFFSET, (fieldHeight / 2) - PADDLE_OFFSET,
                PADDLE_THICKNESS, PADDLE_LENGTH,
                WALL_THICKNESS, fieldHeight - WALL_THICKNESS
        );
        playerOne.setFill(Color.RED);
        addPlayerPaddle(1, playerOne);

        final Paddle playerTwo = new Paddle(
                "Player 2 Paddle",
                fieldWidth - PADDLE_OFFSET, (fieldHeight / 2) - PADDLE_OFFSET,
                PADDLE_THICKNESS, PADDLE_LENGTH,
                WALL_THICKNESS, fieldHeight - WALL_THICKNESS
        );
        playerTwo.setFill(Color.BLUE);
        addPlayerPaddle(2, playerTwo);

        playerThreePaddle = new HorizontalPaddle(
                "Player 3 Paddle",
                (fieldWidth / 2) - PADDLE_OFFSET, PADDLE_OFFSET,
                PADDLE_LENGTH, PADDLE_THICKNESS,
                WALL_THICKNESS, fieldWidth - WALL_THICKNESS
        );
        playerThreePaddle.setFill(Color.YELLOW);
        addObject(playerThreePaddle);

        playerFourPaddle = new HorizontalPaddle(
                "Player 4 Paddle",
                (fieldWidth / 2) - PADDLE_OFFSET, fieldHeight - PADDLE_OFFSET,
                PADDLE_LENGTH, PADDLE_THICKNESS,
                WALL_THICKNESS, fieldWidth - WALL_THICKNESS
        );
        playerFourPaddle.setFill(Color.GREEN);
        addObject(playerFourPaddle);
    }

    @Override
    public int getPlayerScore(int player) {
        if (player < 1 || player > scores.length) return 0;
        return scores[player - 1].get();
    }

    public ReadOnlyIntegerProperty playerScoreProperty(int player) {
        if (player < 1 || player > scores.length) return null;
        return scores[player - 1];
    }

    @Override
    public void addPointsToPlayer(int player, int value) {
        if (player < 1 || player > scores.length) return;
        scores[player - 1].set(scores[player - 1].get() + value);
    }

    public boolean isPlayerAlive(int player) {
        if (player < 1 || player > scores.length) return false;
        return scores[player - 1].get() < getLoseScore();
    }

    public BooleanBinding playerAlive(int player) {
        if (player < 1 || player > scores.length) return null;
        return scores[player - 1].lessThan(getLoseScore());
    }

    public int getLoseScore() {
        // Reinterpret victoryScore.
        return getVictoryScore();
    }

    @Override
    public int getVictor() {
        int victor = 0;
        for (int i = 0; i < scores.length; ++i) {
            final int score = scores[i].get();
            if (score < getVictoryScore()) {
                if (victor > 0) {
                    // More than one player left, so we haven't reached a
                    // game-over state yet.
                    return 0;
                }
                // Player that hasn't been eliminated yet.
                victor = i + 1;
            }
        }
        return victor;
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
            case "Wall" -> puck.setDirection(switch (collision.getObjectID()) {
                // Horizontal wall.
                case "Top Left Wall",
                        "Top Right Wall",
                        "Bottom Left Wall",
                        "Bottom Right Wall" -> -puck.getDirection();
                // Vertical wall.
                case "Left Top Wall",
                        "Left Bottom Wall",
                        "Right Top Wall",
                        "Right Bottom Wall" -> 180 - puck.getDirection();
                default -> throw new Error("Unknown wall " + collision.getObjectID());
            });
            case "Goal" -> {
                final int player = switch (collision.getObjectID()) {
                    case "Player 1 Goal" -> 1;
                    case "Player 2 Goal" -> 2;
                    case "Player 3 Goal" -> 3;
                    case "Player 4 Goal" -> 4;
                    default -> 0;
                };
                if (isPlayerAlive(player)) {
                    addPointsToPlayer(player, 1);
                    puck.reset();
                } else {
                    // Treat inactive goal as a wall instead.
                    puck.setDirection(switch (player) {
                        // Vertical wall.
                        case 1, 2 -> 180 - puck.getDirection();
                        // Horizontal wall.
                        case 3, 4 -> -puck.getDirection();
                        default -> puck.getDirection();
                    });
                }
            }
            case "Paddle" -> {
                // Disable collision handling for dead players.
                final int player = switch (collision.getObjectID()) {
                    case "Player 1 Paddle" -> 1;
                    case "Player 2 Paddle" -> 2;
                    default -> throw new Error("Unknown Paddle " + collision.getObjectID());
                };
                if (!isPlayerAlive(player)) break;

                final double puckCenter = ((Puck) puck).getCenterY();
                switch (player) {
                    case 1 -> puck.setDirection(
                            ClassicPong.mapRange(
                                    collision.getTop(), collision.getBottom(),
                                    -PADDLE_COLLISION_ANGLE, PADDLE_COLLISION_ANGLE,
                                    puckCenter
                            )
                    );
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
                // Disable collision handling for dead players.
                final int player = switch (collision.getObjectID()) {
                    case "Player 3 Paddle" -> 3;
                    case "Player 4 Paddle" -> 4;
                    default -> throw new Error("Unknown HorizontalPaddle " + collision.getObjectID());
                };
                if (!isPlayerAlive(player)) break;

                final double puckCenter = ((Puck) puck).getCenterX();
                switch (player) {
                    case 3 -> puck.setDirection(
                            ClassicPong.mapRange(
                                    collision.getLeft(), collision.getRight(),
                                    90 + PADDLE_COLLISION_ANGLE, PADDLE_COLLISION_ANGLE,
                                    puckCenter
                            )
                    );
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
