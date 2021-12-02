package edu.csueastbay.cs401.psander.game.scripts;

import edu.csueastbay.cs401.psander.PongWare;
import edu.csueastbay.cs401.psander.engine.events.EventHub;
import edu.csueastbay.cs401.psander.engine.gameObjects.GameObject;
import edu.csueastbay.cs401.psander.engine.math.Utility;
import edu.csueastbay.cs401.psander.engine.math.Vector2D;
import edu.csueastbay.cs401.psander.engine.physics.BoxCollider;
import edu.csueastbay.cs401.psander.engine.render.TextRenderer;
import edu.csueastbay.cs401.psander.engine.scenes.SceneManager;
import edu.csueastbay.cs401.psander.engine.scripts.Script;
import edu.csueastbay.cs401.psander.game.messages.GoalHitMessage;
import edu.csueastbay.cs401.psander.game.scenes.GameOverScene;

public class BasicGameManager extends Script {
    private final double _launchRange = 45;
    private GameObject _ball;
    private TextRenderer _player1ScoreText;
    private TextRenderer _player2ScoreText;


    public void init(GameObject ball, TextRenderer player1ScoreText, TextRenderer player2ScoreText) {
        _ball = ball;
        _player1ScoreText = player1ScoreText;
        _player2ScoreText = player2ScoreText;

        EventHub.getInstance().subscribe(GoalHitMessage.class, this, this::goalHit);
    }

    @Override
    public void update(double delta) {

    }

    public void goalHit(GoalHitMessage message) {
        if (message.playerOwner() == 1) {
            var score = PongWare.State().get_player1Score();
            score++;
            PongWare.State().set_player1Score(score);
            _player1ScoreText.setText(Integer.toString(score));

            if (score == PongWare.State().get_winningScore()) {
                PongWare.State().set_winningPlayer(1);
                goToGameOverScreen();
            }
        } else if (message.playerOwner() == 2) {
            var score = PongWare.State().get_player2Score();
            score++;
            PongWare.State().set_player2Score(score);
            _player2ScoreText.setText(Integer.toString(score));

            if (score == PongWare.State().get_winningScore()) {
                PongWare.State().set_winningPlayer(2);
                goToGameOverScreen();
            }
        }

        var col = _ball.getComponent(BoxCollider.class);
        _ball.Transform().Position().set(PongWare.getFieldWidth()/2 - col.getWidth()/2,
                PongWare.getFieldHeight()/2 - col.getHeight() / 2);

        // Get new angle
        var rand = Math.random();

        double center, min, max;

        if (message.playerOwner() == 1) { // Go rightwards
            center = 0.0;
            min = center - _launchRange/2;
            max = center + _launchRange/2;
        } else {
            center = 180;
            min = center + _launchRange/2;
            max = center - _launchRange/2;
        }

        var newAngle = Utility.MapRange(rand, 0.0, 1.0, min, max);
        var h = col.getVelocity().length();

        var newX = h * Math.cos(newAngle);
        var newY = h * Math.sin(newAngle);
        col.setVelocity(new Vector2D(newX, newY));
    }

    private void goToGameOverScreen() {
        EventHub.getInstance().unsubscribe(GoalHitMessage.class, this);
        var scene = new GameOverScene();
        scene.init();
        SceneManager.getInstance().swap(scene);
    }
}
