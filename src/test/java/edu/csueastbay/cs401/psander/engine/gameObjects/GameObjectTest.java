package edu.csueastbay.cs401.psander.engine.gameObjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameObjectTest {

    static private class ComponentMock1 extends Component {
        @Override
        public void update(double delta) { }
    }

    static private class ComponentMock2 extends Component {
        @Override
        public void update(double delta) { }
    }

    @Test
    public void TestConstructor() {
        var go = new GameObject("test");

        assertEquals("test", go.getName());
    }

    @Test
    public void GetterAndSetterForName() {
        var go = new GameObject("test");

        go.setName("new name");
        assertEquals("new name", go.getName());
    }

    @Test
    public void GetObjectsTransform() {
        var go = new GameObject("test");

        assertDoesNotThrow( () -> go.Transform());
    }

    @Test
    public void TestParentAndChildSetting() {
        var go1 = new GameObject("parent");
        var go2 = new GameObject("child");
        var children = go1.getChildren();

        assertNull(go2.Parent());
        assertFalse(children.contains(go2));

        go1.addChild(go2);
        children = go1.getChildren();
        assertEquals(go1, go2.Parent());
        assertTrue(children.contains(go2));

        go1.removeChild(go2);
        children = go1.getChildren();
        assertNull(go2.Parent());
        assertFalse(children.contains(go2));
    }

    @Test
    public void AddAndRemoveComponents() {
        var go = new GameObject("test");
        var comp1 = new ComponentMock1();

        assertNull(go.getComponent(ComponentMock1.class));
        assertNull(go.getComponent(ComponentMock2.class));

        go.addComponent(comp1);
        assertEquals(comp1, go.getComponent(ComponentMock1.class));
        assertNull(go.getComponent(ComponentMock2.class));

        go.removeComponent(ComponentMock1.class);
        assertNull(go.getComponent(ComponentMock1.class));
        assertNull(go.getComponent(ComponentMock2.class));
    }
}
