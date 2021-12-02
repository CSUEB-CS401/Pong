package edu.csueastbay.cs401.psander.game.scripts;

import edu.csueastbay.cs401.psander.engine.events.EventHub;
import edu.csueastbay.cs401.psander.engine.physics.CollisionInformation;
import edu.csueastbay.cs401.psander.engine.physics.CollisionListener;
import edu.csueastbay.cs401.psander.game.messages.GoalHitMessage;

import java.util.ArrayList;

public class GoalListener extends CollisionListener {

    private int _owner;

    public GoalListener(int owner) {
        _owner = owner;
    }

    @Override
    public void processCollisions(ArrayList<CollisionInformation> collisions) {
        for(var c : collisions ) {
            if (c.other().getOwner().getName() == "ball") {
                EventHub.getInstance().publish(new GoalHitMessage(_owner));
            }
        }
    }
}
