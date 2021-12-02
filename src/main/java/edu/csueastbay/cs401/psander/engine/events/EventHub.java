package edu.csueastbay.cs401.psander.engine.events;

import java.util.*;
import java.util.function.Consumer;

/**
 * Allows system components to subscribe to and publish
 * various events to communicate in a loosely-coupled
 * way.
 */
public class EventHub {

    private static EventHub _instance;

    private final Map<Class, Hashtable<Object, Consumer>> _listeners = new Hashtable<>();

    public static EventHub getInstance() {
        if (_instance == null)
            _instance = new EventHub();
        return _instance;
    }

    /**
     * Reinitialize the event manager. Necessary
     * since it is implemented as a singleton.
     */
    public void init() {
        _listeners.clear();
    }

    /**
     * Allows a subscriber to subscribe to an event of a
     * particular type.
     * @param type       The type of event to subscribe to.
     * @param subscriber The subscriber subscribing to the event. (Used to
     *                   identify it later when unsubscribing.)
     * @param method     A method with a single parameter matching the
     *                   event type, to be called when an event is
     *                   published.
     * @param <T>        The type of the event being subscribed to.
     */
    public <T> void subscribe(Class<T> type, Object subscriber, Consumer<T> method) {
        if (!_listeners.containsKey(type))
            _listeners.put(type, new Hashtable<>());
        _listeners.get(type).put(subscriber, method);
    }

    /**
     * Unsubscribe from events of the specified type.
     * @param type       The type of event to unsubscribe from.
     * @param subscriber The object unsubscribing from the event.
     *                   (Must be the same object that initially
     *                   subscribed.)
     * @param <T>        The type of event being unsubscribed from.
     */
    public <T> void unsubscribe(Class<T> type, Object subscriber) {
        if (!_listeners.containsKey(type)) return;

        _listeners.get(type).remove(subscriber);
    }

    /**
     * Publish an event of a specified top.
     * @param data An object of the event type to be published.
     * @param <T>  The type of event being published.
     */
    public <T> void publish(T data) {
        var c = data.getClass();
        if (!_listeners.containsKey(c)) return;

        _listeners.get(c).forEach( (o, method) -> method.accept(data));
    }
}
