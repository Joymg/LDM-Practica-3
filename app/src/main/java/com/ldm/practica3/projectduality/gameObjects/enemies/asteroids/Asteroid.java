package com.ldm.practica3.projectduality.gameObjects.enemies.asteroids;


import com.ldm.practica3.projectduality.engine.GameEngine;
import com.ldm.practica3.projectduality.engine.ScreenGameObject;
import com.ldm.practica3.projectduality.gameObjects.GameController;
import com.ldm.practica3.projectduality.gameObjects.enemies.Enemy;
import com.ldm.practica3.projectduality.gameObjects.enemies.EnemyType;

import java.util.Random;

public class Asteroid extends Enemy {

    protected final GameController gameController;

    public AsteroidType asteroidType;

    protected double speed;
    protected double speedX;
    protected double speedY;
    protected double rotationSpeed;

    public Asteroid(GameController gameController, GameEngine gameEngine,int drawable) {
        super(gameController, gameEngine, drawable);
        this.speed = 50d * pixelFactor/1000d;
        this.gameController = gameController;
        enemyType = EnemyType.Asteroid;
    }

    public void init(GameEngine gameEngine) {
        // They initialize in a [-30, 30] degrees angle
        double angle = gameEngine.random.nextDouble()*Math.PI/3d-Math.PI/6d;
        speedX = speed * Math.sin(angle);
        speedY = speed * Math.cos(angle);
        // Asteroids initialize in the central 50% of the screen horizontally
        positionX = gameEngine.random.nextInt(gameEngine.width/2)+gameEngine.width/4;
        // They initialize outside of the screen vertically
        positionY = -height;
        rotationSpeed = angle*(180d / Math.PI)/250d; // They rotate 4 times their ange in a second.
        rotation = gameEngine.random.nextInt(360);
    }

    public void SpawnAtPosition(double posX, double posY){
        // They initialize in a [-30, 30] degrees angle
        Random random = new Random();
        double angle = random.nextDouble()*Math.PI/3d-Math.PI/6d;
        speedX = speed * Math.sin(angle);
        speedY = speed * Math.cos(angle);
        // Asteroids initialize in the central 50% of the screen horizontally
        positionX = posX;
        // They initialize outside of the screen vertically
        positionY = posY;
        rotationSpeed = angle*(180d / Math.PI)/250d; // They rotate 4 times their ange in a second.
        rotation = random.nextInt(360);
    }

    @Override
    public void startGame() {
    }


    public void removeObject(GameEngine gameEngine) {
        // Return to the pool
        super.removeObject(gameEngine);

        /*gameEngine.removeGameObject(this);
        gameController.returnToPool(this);*/
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionX += speedX * elapsedMillis;
        positionY += speedY * elapsedMillis;
        rotation += rotationSpeed * elapsedMillis;
        if (rotation > 360) {
            rotation = 0;
        }
        else if (rotation < 0) {
            rotation = 360;
        }

        super.onUpdate(elapsedMillis,gameEngine);


    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }

}

