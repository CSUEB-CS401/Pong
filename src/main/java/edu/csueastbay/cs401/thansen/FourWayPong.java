package edu.csueastbay.cs401.thansen;

import edu.csueastbay.cs401.classic.ClassicPong;
import edu.csueastbay.cs401.pong.*;
import javafx.scene.paint.Color;

import edu.csueastbay.cs401.pong.Game;

import java.lang.reflect.AnnotatedWildcardType;

/**
 * Pong game with 4 paddles on each side of the screen.
 */
public class FourWayPong extends Game {

    /**
     * Scores for each player.
     */
    private final int[] scores = new int[4];

    public static final double WALL_THICKNESS = 10;

    public FourWayPong(int victoryScore, double fieldWidth, double fieldHeight) {
        super(victoryScore);

        final Puck puck = new Puck(fieldWidth, fieldHeight);
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
                50, (fieldHeight / 2) - 50,
                10, 100,
                10, fieldHeight - 10
        );
        playerOne.setFill(Color.RED);
        addPlayerPaddle(1, playerOne);

        final Paddle playerTwo = new Paddle(
                "Player 2 Paddle",
                fieldWidth - 50, (fieldHeight / 2) - 50,
                10, 100,
                10, fieldHeight - 10
        );
        playerTwo.setFill(Color.BLUE);
        addPlayerPaddle(2, playerTwo);
    }

    @Override
    public int getPlayerScore(int player) {
        if (player < 1 || player > 4) return 0;
        return scores[player - 1];
    }

    @Override
    public void addPointsToPlayer(int player, int value) {
        if (player < 1 || player > 4) return;
        scores[player - 1] += value;
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
                            collision.getTop(), collision.getBottom(), -45, 45, puckCenter
                    );
                    case "Player 2 Paddle" -> ClassicPong.mapRange(
                            collision.getTop(), collision.getBottom(), 225, 135, puckCenter
                    );
                    case "Player 3 Paddle" -> ClassicPong.mapRange(
                            collision.getLeft(), collision.getRight(), -45, -135, puckCenter
                    );
                    case "Player 4 Paddle" -> ClassicPong.mapRange(
                            collision.getLeft(), collision.getRight(), 45, 135, puckCenter
                    );
                    default -> throw new Error("Unknown paddle " + collision.getObjectID());
                });
            }
        }
    }
}
