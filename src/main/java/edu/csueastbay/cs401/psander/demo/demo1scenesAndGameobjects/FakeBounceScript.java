package edu.csueastbay.cs401.psander.demo.demo1scenesAndGameobjects;

import edu.csueastbay.cs401.psander.engine.math.Vector2D;
import edu.csueastbay.cs401.psander.engine.scripts.Script;

public class FakeBounceScript extends Script {
    private double _size = 10.0;
    private Vector2D _velocity = new Vector2D(1.0, 1.0);
    @Override
    public void update(double delta) {
        var position  = getOwner().Transform().Position();
        position.add(_velocity);

        if (position.X() < 0) {
            position.setX( -1 * position.X());
            _velocity.setX( -1 * _velocity.X());
        }
        else if (position.X() + _size > 1280) {
            position.setX(1280 - (position.X() + _size - 1280 ) - _size );
            _velocity.setX( -1 * _velocity.X());
        }

        if (position.Y() < 0) {
            position.setY( -1 * position.Y());
            _velocity.setY( -1 * _velocity.Y());
        }
        else if (position.Y() + _size > 720) {
            position.setY(720 - (position.Y() + _size - 720) - _size );
            _velocity.setY( -1 * _velocity.Y());
        }
    }
}
