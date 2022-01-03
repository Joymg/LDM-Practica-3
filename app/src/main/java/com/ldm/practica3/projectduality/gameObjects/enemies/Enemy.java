package com.ldm.practica3.projectduality.gameObjects.enemies;

import com.ldm.practica3.projectduality.engine.GameEngine;
import com.ldm.practica3.projectduality.engine.ScreenGameObject;
import com.ldm.practica3.projectduality.gameObjects.GameController;
import com.ldm.practica3.projectduality.gameObjects.Sprite;
import com.ldm.practica3.projectduality.sound.GameEvent;

public abstract class Enemy extends Sprite {

    protected final GameController gameController;

    protected double speed;
    protected double speedX;
    protected double speedY;
    protected double rotationSpeed;

    protected int currHealth;
    protected boolean isInvencible;
    protected int pointsOnKill = 100;

    public EnemyType enemyType;

    protected Enemy(GameController gameController, GameEngine gameEngine, int drawable) {
        super(gameEngine, drawable);
        this.speed = 50d * pixelFactor/1000d;
        this.gameController = gameController;
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
        if (currHealth <= 0){
            gameEngine.AddPoints(pointsOnKill);
            gameEngine.onGameEvent(GameEvent.EnemyKilled);
            removeObject(gameEngine);
        }
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }

    public void GotHit(){
        currHealth --;
    }
}
