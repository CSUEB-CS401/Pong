package edu.csueastbay.cs401.psander.demo.demo2collisionTests;

import edu.csueastbay.cs401.psander.engine.math.Vector2D;
import edu.csueastbay.cs401.psander.engine.gameObjects.GameObject;
import edu.csueastbay.cs401.psander.engine.physics.BoxCollider;
import edu.csueastbay.cs401.psander.engine.physics.ColliderMode;
import edu.csueastbay.cs401.psander.engine.render.RectangleRenderer;
import edu.csueastbay.cs401.psander.engine.scenes.Scene;
import javafx.scene.paint.Color;

public class CollisionTestScene extends Scene {
    @Override
    public void init() {
        this.addGameObject(makeWall(100, 50, 200, 50, Color.RED));
        this.addGameObject(makeWall(50, 100, 50, 200, Color.GREEN));
        this.addGameObject(makeWall(100, 300, 200, 50, Color.BLUE));
        this.addGameObject(makeWall(300, 100, 50, 200, Color.YELLOW));

        addGameObject(makeBall(125, 125, 50, 50, 46.9*10, -23.5*10, Color.WHITE));
        addGameObject(makeBall(125, 180, 10, 25, 25, 46, Color.GREEN));


        // Okay, let's do a just _insane_ number of balls here.
        var c = 1;
        for(int x = 110; x < 300; x += 10) {
            for(int y = 210; y < 230; y += 10 ) {
                c++;
                addGameObject(makeBall(x, y, 5, 5, 25 + c*2, 45 + c*3, Color.PINK));
            }

            this.addGameObject(makeWall(450, 50, 200, 50, Color.RED));
            this.addGameObject(makeWall(400, 100, 50, 200, Color.GREEN));
            this.addGameObject(makeWall(450, 300, 200, 50, Color.BLUE));
            this.addGameObject(makeWall(650, 100, 50, 200, Color.YELLOW));
            this.addGameObject(makeWall(550-2.5, 100, 5, 200, Color.ORCHID));
            this.addGameObject(makeBall(450, 200, 5, 5, 2000, 10, Color.WHITE));
            this.addGameObject(makeBall(650, 200, 5, 5, -2000, 0, Color.TEAL));
            this.addGameObject(makeBall(550+5, 200, 5, 5, 2000, 0, Color.HOTPINK));

            this.addGameObject(makeWall(800, 50, 200, 50, Color.RED));
            this.addGameObject(makeWall(750, 100, 50, 200, Color.GREEN));
            this.addGameObject(makeWall(800, 300, 200, 50, Color.BLUE));
            this.addGameObject(makeWall(1000, 100, 50, 200, Color.YELLOW));
            this.addGameObject(makePaddle(825, 175, 5, 50, Color.WHITE));
            this.addGameObject(makeBall(1000-25, 200, 10, 10, 45, 45, Color.CYAN));
        }
    }

    private GameObject makeWall(double x, double y, double width, double height, Color color) {
        var wall = new GameObject("Wall");
        wall.Transform().Position().set(x, y);
        wall.addComponent(new BoxCollider(width, height, ColliderMode.STATIC));
        wall.addComponent(new RectangleRenderer(width, height, color));
        return wall;
    }

    private GameObject makeBall(double x, double y, double width, double height, double vx, double vy, Color color) {
        var ball =new GameObject("Ball");
        ball.Transform().Position().set(x, y);
        var ball1Collider = new BoxCollider(width, height, ColliderMode.DYNAMIC);
        ball1Collider.setVelocity(new Vector2D(vx, vy));
        ball.addComponent(ball1Collider);
        ball.addComponent(new BallListener());
        ball.addComponent(new RectangleRenderer(width, height, color));
        return ball;
    }

    private GameObject makePaddle(double x, double y, double width, double height, Color color) {
        var paddle =new GameObject("Paddle");
        paddle.Transform().Position().set(x, y);
        paddle.addComponent(new BoxCollider(width, height, ColliderMode.KINEMATIC));
        paddle.addComponent(new BallListener());
        paddle.addComponent(new SimplePaddleScript());
        paddle.addComponent(new RectangleRenderer(width, height, color));
        return paddle;
    }
}
