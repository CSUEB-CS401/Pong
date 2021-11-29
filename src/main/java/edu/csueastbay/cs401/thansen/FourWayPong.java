package edu.csueastbay.cs401.thansen;

import edu.csueastbay.cs401.classic.ClassicPong;
import edu.csueastbay.cs401.pong.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import edu.csueastbay.cs401.pong.Game;

/**
 * Pong game with 4 paddles on each side of the screen. Here, paddles are
 * removed from the game if their goals have been hit multiple times, with the
 * last one standing being the winner.
 */
public class FourWayPong extends Game {
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

    private final int[] scores = new int[NUM_PLAYERS];
    private final HorizontalPaddle playerThreePaddle;
    private final HorizontalPaddle playerFourPaddle;

    /**
     * Creates a FourWayPong game.
     *
     * @param loseScore   Score at which a paddle will be removed.
     * @param fieldWidth  Width of the field.
     * @param fieldHeight Height of the field.
     */
    public FourWayPong(int loseScore, double fieldWidth, double fieldHeight) {
        super(loseScore);

        final FourWayPuck puck = new FourWayPuck(fieldWidth, fieldHeight);
        puck.setID("Four Way");
        addPuck(puck);

        final double wallWidth = fieldWidth / 5;
        final double wallHeight = fieldHeight / 5;

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
        return scores[player - 1];
    }

    @Override
    public void addPointsToPlayer(int player, int value) {
        if (player < 1 || player > scores.length) return;
        scores[player - 1] += value;
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
                case "Top Left Wall",
                        "Top Right Wall",
                        "Bottom Left Wall",
                        "Bottom Right Wall" -> -puck.getDirection();
                case "Left Top Wall",
                        "Left Bottom Wall",
                        "Right Top Wall",
                        "Right Bottom Wall" -> 180 - puck.getDirection();
                default -> throw new Error("Unknown wall " + collision.getObjectID());
            });
            case "Goal" -> {
                addPointsToPlayer(
                        switch (collision.getObjectID()) {
                            case "Player 1 Goal" -> 1;
                            case "Player 2 Goal" -> 2;
                            case "Player 3 Goal" -> 3;
                            case "Player 4 Goal" -> 4;
                            default -> 0;
                        },
                        1
                );
                puck.reset();
            }
            case "Paddle" -> {
                final double puckCenter = ((Puck) puck).getCenterY();
                puck.setDirection(switch (collision.getObjectID()) {
                    case "Player 1 Paddle" -> ClassicPong.mapRange(
                            collision.getTop(), collision.getBottom(),
                            -PADDLE_COLLISION_ANGLE, PADDLE_COLLISION_ANGLE,
                            puckCenter
                    );
                    case "Player 2 Paddle" -> ClassicPong.mapRange(
                            collision.getTop(), collision.getBottom(),
                            180 + PADDLE_COLLISION_ANGLE, 90 + PADDLE_COLLISION_ANGLE,
                            puckCenter
                    );
                    default -> throw new Error("Unknown Paddle " + collision.getObjectID());
                });
            }
            case "HorizontalPaddle" -> {
                final double puckCenter = ((Puck) puck).getCenterX();
                puck.setDirection(switch (collision.getObjectID()) {
                    case "Player 3 Paddle" -> ClassicPong.mapRange(
                            collision.getLeft(), collision.getRight(),
                            90 + PADDLE_COLLISION_ANGLE, PADDLE_COLLISION_ANGLE,
                            puckCenter
                    );
                    case "Player 4 Paddle" -> ClassicPong.mapRange(
                            collision.getLeft(), collision.getRight(),
                            -90 - PADDLE_COLLISION_ANGLE, -PADDLE_COLLISION_ANGLE,
                            puckCenter
                    );
                    default -> throw new Error("Unknown HorizontalPaddle " + collision.getObjectID());
                });
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
