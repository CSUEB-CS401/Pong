package edu.csueastbay.cs401.psander.demo.demo3renderlayers;

import edu.csueastbay.cs401.psander.engine.math.Vector2D;
import edu.csueastbay.cs401.psander.engine.scripts.Script;

public class FakeBounce2Script extends Script {
    private double _width;
    private double _height;
    private Vector2D _velocity;

    public FakeBounce2Script(double width, double height, Vector2D velocity) {
        _width = width;
        _height = height;
        _velocity = velocity;
    }

    @Override
    public void update(double delta) {
        var position  = getOwner().Transform().Position();
        position.add(_velocity);

        if (position.X() < 0) {
            position.setX( -1 * position.X());
            _velocity.setX( -1 * _velocity.X());
        }
        else if (position.X() + _width > 1280) {
            position.setX(1280 - (position.X() + _width - 1280 ) - _width );
            _velocity.setX( -1 * _velocity.X());
        }

        if (position.Y() < 0) {
            position.setY( -1 * position.Y());
            _velocity.setY( -1 * _velocity.Y());
        }
        else if (position.Y() + _height > 720) {
            position.setY(720 - (position.Y() + _height - 720) - _height );
            _velocity.setY( -1 * _velocity.Y());
        }
    }
}
