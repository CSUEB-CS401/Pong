package edu.csueastbay.cs401.psander.engine.events;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventHubTest {
    static class SubscriberTest {
        double Value;

        void Observer(double value) {
            Value = value;
        }
    }

    @Test
    public void TestSubscribingAndPublishing() {
        var subscriber = new SubscriberTest();
        var instance = EventHub.getInstance();

        assertEquals(0.0, subscriber.Value);

        instance.publish(1.0);
        assertEquals(0.0, subscriber.Value);

        instance.subscribe(Double.class, subscriber, subscriber::Observer);
        instance.publish(2.0);
        assertEquals(2.0, subscriber.Value);

        instance.unsubscribe(Double.class, subscriber);
        instance.publish(3.0);
        assertEquals(2.0, subscriber.Value);

        instance.subscribe(Double.class, subscriber, subscriber::Observer);
        instance.publish(4.0);
        instance.init();
        instance.publish(5.0);
        assertEquals(4.0, subscriber.Value);
    }
}
