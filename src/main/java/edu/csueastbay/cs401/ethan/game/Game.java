package edu.csueastbay.cs401.ethan.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import javafx.util.Pair;

import java.time.Instant;
import java.util.*;

public abstract class Game {

    public final Pane pane;

    private Instant lastUpdate;
    private final Timeline timer;
    private double gameTime;
    private final Queue<Pair<Double, Runnable>> scheduledEvents;

    private final BooleanProperty playing, gameOver;
    private final ObservableSet<Entity> entities;
    private final ObservableSet<Entity> _entities;
    private final Set<Entity> toAdd, toRemove;
    private final Set<Collidable> collidables;
    private final Map<Collidable, Map<Collidable, Shape>> cache;

    public InputHandler input;
    public final Bounds bounds;
    public final double width, height;

    public Game(double width, double height) {
        this.width = width;
        this.height = height;
        pane = new Pane();
        pane.setPrefSize(width, height);
        pane.setMinSize(width, height);
        pane.setMaxSize(width, height);
        pane.setClip(new Rectangle(width, height));
        input = control -> false;
        bounds = new BoundingBox(0, 0, width, height);
        playing = new SimpleBooleanProperty(false);
        gameOver = new SimpleBooleanProperty(false);
        entities = FXCollections.observableSet(new HashSet<>());
        _entities = FXCollections.unmodifiableObservableSet(entities);
        toAdd = new HashSet<>();
        toRemove = new HashSet<>();
        collidables = new HashSet<>();
        cache = new HashMap<>();

        scheduledEvents = new PriorityQueue<>(Comparator.comparing(Pair::getKey));
        gameTime = 0;
        timer = new Timeline(new KeyFrame(Duration.millis(10), (e)->{
            Instant now = Instant.now();
            double delta = java.time.Duration.between(lastUpdate, now).toNanos() / 1e9;
            gameTime += delta;
            while(!scheduledEvents.isEmpty() && scheduledEvents.peek().getKey() < gameTime) {
                scheduledEvents.poll().getValue().run();
            }
            cache.clear();
            update(delta);
            commit();
            lastUpdate = now;
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
    }

    public final ObservableSet<Entity> getEntities() {
        return _entities;
    }

    public final void add(Entity entity) {
        toAdd.add(entity);
    }

    public final void addAll(Collection<Entity> entities) {
        toAdd.addAll(entities);
    }

    public final void remove(Entity entity) {
        toRemove.add(entity);
    }

    public final void removeAll(Collection<Entity> entities) {
        toRemove.addAll(entities);
    }

    public final void schedule(double delay, Runnable task) {
        scheduledEvents.add(new Pair<>(gameTime+delay, task));
    }

    public final void setPlaying(boolean playing) {
        if(this.playing.get() != playing) {
            if(playing) {
                lastUpdate = Instant.now();
                timer.play();
            } else {
                timer.pause();
            }
            this.playing.set(playing);
        }
    }

    public final boolean isPlaying() {
        return playing.get();
    }

    public final BooleanProperty playingProperty() { return playing; }

    public final void setGameOver(boolean gameOver) {
        this.gameOver.set(gameOver);
    }

    public final boolean isGameOver() {
        return gameOver.get();
    }

    public final BooleanProperty gameOverProperty() { return gameOver; }

    protected void update(double delta) {
        entities.forEach(e->e.update(delta));
    }

    public <T extends Collidable> Set<Collision<T>> getCollisionsWithType(Collidable collidable, Class<T> type) {
        Set<Collision<T>> out = new HashSet<>();
        Map<Collidable, Shape> localCache = cache.getOrDefault(collidable, Collections.emptyMap());
        Bounds boundsInScene = collidable.getCollisionShape().localToScene(collidable.getCollisionShape().getBoundsInLocal());
        for(Collidable c : collidables) {
            if(c == collidable) continue; // Don't collide with yourself
            if(type.isAssignableFrom(c.getClass())) {
                T other = type.cast(c);
                Shape intersect;
                if(localCache.containsKey(other)) {
                    intersect = localCache.get(other);
                } else {
                    // Check bounds first to avoid expensive Shape.intersect if possible
                    if(other.getCollisionShape().localToScene(other.getCollisionShape().getBoundsInLocal()).intersects(boundsInScene)) {
                        intersect = Shape.intersect(collidable.getCollisionShape(), other.getCollisionShape());
                    } else {
                        intersect = null;
                    }
                    cache.putIfAbsent(other, new HashMap<>());
                    cache.get(other).put(collidable, intersect);
                }
                if(intersect != null && intersect.getBoundsInLocal().getWidth() > 0) {
                    out.add(new Collision<T>(other, intersect));
                }
            }

        }
        return out;
    }

    public Set<Collision<Collidable>> getCollisions(Collidable collidable) {
        return getCollisionsWithType(collidable, Collidable.class);
    }

    protected void commit() {
        for(Entity e : toAdd) {
            if(entities.add(e)) {
                e.game = this;
                pane.getChildren().add(e.root);
                if(e instanceof Collidable c) {
                    collidables.add(c);
                }
            }
        }
        toAdd.clear();
        for(Entity e : toRemove) {
            if(entities.remove(e)) {
                pane.getChildren().remove(e.root);
                if(e instanceof Collidable c) {
                    collidables.remove(c);
                }
            }
        }
        toRemove.clear();
        entities.forEach(Entity::commit);
    }
}
