package edu.csueastbay.cs401.ethan;

import edu.csueastbay.cs401.ethan.game.Entity;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public abstract class NeonEntity extends Entity {

    public final ObjectProperty<Color> fillColor, glowColor;
    public final BooleanProperty solid;
    private final DropShadow glow;

    public NeonEntity() {
        fillColor = new SimpleObjectProperty<>(Color.WHITE);
        glowColor = new SimpleObjectProperty<>(Color.WHITE);
        solid = new SimpleBooleanProperty(false);
        glow = new DropShadow(BlurType.TWO_PASS_BOX, Color.WHITE, 5, 0.6, 0, 0);
        glow.colorProperty().bind(glowColor);
    }

    protected void bindStyle(Shape shape) {
        shape.strokeProperty().bind(fillColor);
        shape.fillProperty().bind(Bindings.createObjectBinding(()->solid.get()? fillColor.get():Color.TRANSPARENT, fillColor, solid));
        shape.setEffect(glow);
    }

    public void pulse() {
        Timeline pulse = new Timeline();
        pulse.getKeyFrames().add(new KeyFrame(Duration.ZERO,
                new KeyValue(glow.spreadProperty(), 0.75),
                new KeyValue(fillColor, Color.WHITE)));
        pulse.getKeyFrames().add(new KeyFrame(Duration.seconds(0.25),
                new KeyValue(glow.spreadProperty(), glow.getSpread()),
                new KeyValue(fillColor, fillColor.get())));
        pulse.play();
    }

}
