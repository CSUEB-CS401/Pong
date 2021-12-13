package edu.csueastbay.cs401.ethan;

import edu.csueastbay.cs401.ethan.game.Entity;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

/**
 * NeonEntity is a small extension of Entity to make matching styles between Pong entities simpler, and allow for some
 * extra visual control.
 */
public abstract class NeonEntity extends Entity {

    /** The color of the body of styled shapes */
    public final ObjectProperty<Color> fillColor;
    /** The color of the glow of styled shapes */
    public final ObjectProperty<Color> glowColor;
    /** Whether the shape should be solid (or an outline, if false) */
    public final BooleanProperty solid;
    /** The glow effect for neon entities is secretly a shadow */
    private final DropShadow glow;

    /**
     * Creates an outline NeonEntity with {@link Color#WHITE} as its {@link NeonEntity#fillColor} and {@link NeonEntity#glowColor}
     */
    public NeonEntity() {
        fillColor = new SimpleObjectProperty<>(Color.WHITE);
        glowColor = new SimpleObjectProperty<>(Color.WHITE);
        solid = new SimpleBooleanProperty(false);
        glow = new DropShadow(BlurType.TWO_PASS_BOX, Color.WHITE, 5, 0.6, 0, 0);
        glow.colorProperty().bind(glowColor);
    }

    /**
     * Styles the given shape to a fancy neon look.
     * @param shape the shape to style
     */
    protected void bindStyle(Shape shape) {
        shape.strokeProperty().bind(fillColor);
        shape.fillProperty().bind(Bindings.createObjectBinding(()->solid.get()? fillColor.get():Color.TRANSPARENT, fillColor, solid));
        shape.setEffect(glow);
    }

    /** Animates a subtle pulse for bound shapes */
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
