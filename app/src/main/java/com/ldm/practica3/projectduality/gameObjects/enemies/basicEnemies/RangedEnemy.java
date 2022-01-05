package com.ldm.practica3.projectduality.gameObjects.enemies.basicEnemies;

import com.ldm.practica3.projectduality.engine.GameEngine;
import com.ldm.practica3.projectduality.engine.ScreenGameObject;
import com.ldm.practica3.projectduality.gameObjects.GameController;
import com.ldm.practica3.projectduality.gameObjects.enemies.Enemy;
import com.ldm.practica3.projectduality.gameObjects.enemies.EnemyType;

public abstract class RangedEnemy extends Enemy {
    private final int INIT_HEALTH = 1;

    protected RangedEnemy(GameController gameController, GameEngine gameEngine, int drawable) {
        super(gameController, gameEngine, drawable);
        currHealth = INIT_HEALTH;
        enemyType= EnemyType.Ranged;

    }

    public void init(GameEngine gameEngine) {
        // They initialize in a [-30, 30] degrees angle
        double angle = gameEngine.random.nextDouble()*Math.PI/3d-Math.PI/6d;
        speedX = speedFactor * Math.sin(angle);
        speedY = speedFactor * Math.cos(angle);
        // Asteroids initialize in the central 50% of the screen horizontally
        positionX = gameEngine.random.nextInt(gameEngine.width/2)+gameEngine.width/4;
        // They initialize outside of the screen vertically
        positionY = -height;
       /* rotationSpeed = angle*(180d / Math.PI)/250d; // They rotate 4 times their ange in a second.
        rotation = gameEngine.random.nextInt(360);*/
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
        //Todo: modify speed to do things

        positionX += speedX * elapsedMillis;
        positionY += speedY * elapsedMillis;

        super.onUpdate(elapsedMillis,gameEngine);
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }
}
