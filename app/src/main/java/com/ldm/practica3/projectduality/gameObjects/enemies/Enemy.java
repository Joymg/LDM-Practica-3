package com.ldm.practica3.projectduality.gameObjects.enemies;

import com.ldm.practica3.projectduality.engine.GameEngine;
import com.ldm.practica3.projectduality.engine.ScreenGameObject;
import com.ldm.practica3.projectduality.engine.components.Faction;
import com.ldm.practica3.projectduality.engine.components.MatterState;
import com.ldm.practica3.projectduality.gameObjects.Actor;
import com.ldm.practica3.projectduality.gameObjects.GameController;
import com.ldm.practica3.projectduality.gameObjects.Sprite;
import com.ldm.practica3.projectduality.sound.GameEvent;

import java.util.Random;

public abstract class Enemy extends Actor {

    protected final GameController gameController;

    protected double speedFactor;
    protected double speedX;
    protected double speedY;
    protected double rotationSpeed;

    protected int currHealth;
    protected int pointsOnKill = 100;

    public EnemyType enemyType;

    protected Enemy(GameController gameController, GameEngine gameEngine, int drawable) {
        super(gameEngine, drawable);
        this.speedFactor = 50d * pixelFactor / 1000d;
        this.gameController = gameController;

        faction = Faction.Imperium;

        state = GameEngine.random.nextInt(2) == 0 ? MatterState.Determined : MatterState.Quantic;
    }


    public void removeObject(GameEngine gameEngine) {
        // Return to the pool
        gameEngine.removeGameObject(this);
        gameController.returnToPool(this);
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

        // Check of the sprite goes out of the screen and return it to the pool if so
        if (positionY > gameEngine.height) {
            // Return to the pool
            gameEngine.removeGameObject(this);
            gameController.returnToPool(this);
        }
        if (currHealth <= 0) {
            gameEngine.AddPoints(pointsOnKill);
            gameEngine.onGameEvent(GameEvent.EnemyKilled);
            removeObject(gameEngine);
        }
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }

    public void GotHit() {
        currHealth--;
    }
}
