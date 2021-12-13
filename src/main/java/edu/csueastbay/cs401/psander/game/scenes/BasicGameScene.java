package edu.csueastbay.cs401.psander.game.scenes;

import edu.csueastbay.cs401.psander.PongWare;
import edu.csueastbay.cs401.psander.engine.common.HorizontalAlignment;
import edu.csueastbay.cs401.psander.engine.gameObjects.GameObject;
import edu.csueastbay.cs401.psander.engine.math.Vector2D;
import edu.csueastbay.cs401.psander.engine.physics.BoxCollider;
import edu.csueastbay.cs401.psander.engine.physics.ColliderMode;
import edu.csueastbay.cs401.psander.engine.render.RectangleRenderer;
import edu.csueastbay.cs401.psander.engine.render.TextRenderer;
import edu.csueastbay.cs401.psander.engine.scenes.Scene;
import edu.csueastbay.cs401.psander.game.scripts.*;
import javafx.scene.paint.Color;

public class BasicGameScene extends Scene {
    @Override
    public void init() {
        var p1Color = Color.CYAN;
        var p2Color = Color.ORANGE;

        // Goals
        addGameObject(makeGoal(1280 - 20, 0, 20, 720, p1Color, 1));
        addGameObject(makeGoal(0, 0, 20, 720, p2Color, 2));

        // Walls
        addGameObject(makeWall(0, -20, 1280, 20));
        addGameObject(makeWall(0, 720, 1280, 20));

        // Paddles
        var paddleWidth = 10.0;
        var paddleHeight = 100.0;
        var hMargin = 40.0;
        var y = (720 - paddleHeight) / 2;
        addGameObject(makePaddle(hMargin, y, paddleWidth, paddleHeight,
                1, p1Color));
        addGameObject(makePaddle(PongWare.getFieldWidth()-hMargin-paddleWidth, y, paddleWidth, paddleHeight,
                2, p2Color));

        // Ball
        var ballSize = 20.0;
        var ball = makeBall(PongWare.getFieldWidth() / 2 - ballSize / 2,
                PongWare.getFieldHeight() / 2 - ballSize / 2, ballSize, ballSize,
                new Vector2D(500, 500), Color.WHITE);
        addGameObject(ball);

        var player1Score = new GameObject("Player 1 Score");
        player1Score.Transform().Position().set(35, 5);
        var p1ScoreText = new TextRenderer("0", Color.WHITE, 40, 99);
        player1Score.addComponent(p1ScoreText);
        addGameObject(player1Score);

        var player2Score = new GameObject("Player 2 Score");
        player2Score.Transform().Position().set(PongWare.getFieldWidth() - 35, 5);
        var p2ScoreText = new TextRenderer("0", Color.WHITE, 40, 99, HorizontalAlignment.RIGHT);
        player2Score.addComponent(p2ScoreText);
        addGameObject(player2Score);

        var manager = new GameObject("manager");
        var managerScript = new BasicGameManager();
        managerScript.init(ball, p1ScoreText, p2ScoreText);
        manager.addComponent(managerScript);
        addGameObject(manager);

        // Stripe
        var stripe = new GameObject("stripe");
        var stripeWidth = 10.0;
        stripe.Transform().Position().set(PongWare.getFieldWidth() / 2 - stripeWidth/2, 0);
        stripe.addComponent(new RectangleRenderer(stripeWidth, PongWare.getFieldHeight(), Color.WHITE, -1));
        addGameObject(stripe);
    }

    private GameObject makeGoal(double x, double y, double width, double height, Color color, int owner) {
        var goal = new GameObject("goal");
        goal.Transform().Position().set(x, y);
        goal.addComponent(new BoxCollider(width, height, ColliderMode.STATIC));
        goal.addComponent(new RectangleRenderer(width, height, color, -1));
        goal.addComponent(new GoalListener(owner));
        return goal;
    }

    private GameObject makeWall(double x, double y, double width, double height) {
        var wall = new GameObject("wall");
        wall.Transform().Position().set(x, y);
        wall.addComponent(new BoxCollider(width, height, ColliderMode.STATIC));
        return wall;
    }

    private GameObject makePaddle(double x, double y, double width, double height,
                                  int player, Color color) {
        var paddle = new GameObject("vertical paddle");
        paddle.Transform().Position().set(x, y);
        paddle.addComponent(new VerticalPaddle(player));
        paddle.addComponent(new BoxCollider(width, height, ColliderMode.KINEMATIC));
        paddle.addComponent(new PaddleCollisionListener());
        paddle.addComponent(new RectangleRenderer(width, height, color));
        return paddle;
    }

    private GameObject makeBall(double x, double y, double width, double height, Vector2D velocity, Color color) {
        var ball = new GameObject("ball");
        ball.Transform().Position().set(x, y);
        var collider = new BoxCollider(width, height, ColliderMode.DYNAMIC);
        collider.setVelocity(velocity);
        ball.addComponent(collider);
        ball.addComponent(new PlainBallCollisionListener());
        ball.addComponent(new RectangleRenderer(width, height, color));
        return ball;
    }
}
