package edu.csueastbay.cs401.psander.engine.gameObjects;

import edu.csueastbay.cs401.psander.engine.gameObjects.Component;
import edu.csueastbay.cs401.psander.engine.gameObjects.GameObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComponentTest {
    static class ComponentMock extends Component {

        @Override
        public void update(double delta) {}
    }

    @Test
    public void TestSettingOwner() {
        var go = new GameObject("test");
        var comp = new ComponentMock();

        comp.setOwner(go);
        assertEquals(go, comp.getOwner());
    }
}
