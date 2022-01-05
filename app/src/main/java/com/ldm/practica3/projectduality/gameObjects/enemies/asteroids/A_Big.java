package com.ldm.practica3.projectduality.gameObjects.enemies.asteroids;

import com.ldm.practica3.projectduality.R;
import com.ldm.practica3.projectduality.engine.GameEngine;
import com.ldm.practica3.projectduality.engine.ScreenGameObject;
import com.ldm.practica3.projectduality.gameObjects.GameController;

public class A_Big extends Asteroid{
    private final int INIT_HEALTH = 3;
    public A_Big(GameController gameController, GameEngine gameEngine) {
        super(gameController, gameEngine, R.drawable.rock3);
        currHealth = INIT_HEALTH;
        asteroidType =AsteroidType.Big;
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
        rotationSpeed = angle*(180d / Math.PI)/250d; // They rotate 4 times their ange in a second.
        rotation = gameEngine.random.nextInt(360);
    }

    @Override
    public void startGame() {
    }


    public void removeObject(GameEngine gameEngine) {

        gameController.onAsteroidDestroyed(asteroidType,positionX,positionY);
        // Return to the pool
        super.removeObject(gameEngine);
        /*gameEngine.removeGameObject(this);
        gameController.returnToPool(this);*/
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        super.onUpdate(elapsedMillis,gameEngine);

    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }
}
