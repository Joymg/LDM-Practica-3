package com.ldm.practica3.spacecowboy.gameObjects.enemies.asteroids;


import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.ldm.practica3.spacecowboy.engine.GameEngine;
import com.ldm.practica3.spacecowboy.engine.ScreenGameObject;
import com.ldm.practica3.spacecowboy.engine.components.MatterState;
import com.ldm.practica3.spacecowboy.gameObjects.GameController;
import com.ldm.practica3.spacecowboy.gameObjects.enemies.Enemy;
import com.ldm.practica3.spacecowboy.gameObjects.enemies.EnemyType;

public class Asteroid extends Enemy {

    protected final GameController gameController;

    public AsteroidType asteroidType;

    public Asteroid(GameController gameController, GameEngine gameEngine, int drawable) {
        super(gameController, gameEngine, drawable);
        this.speedFactor = 50d * pixelFactor / 1000d;
        this.gameController = gameController;
        enemyType = EnemyType.Asteroid;
    }

    public void init(GameEngine gameEngine) {
        // They initialize in a [-30, 30] degrees angle
        double angle = gameEngine.random.nextDouble() * Math.PI / 3d - Math.PI / 6d;
        speedX = speedFactor * Math.sin(angle);
        speedY = speedFactor * Math.cos(angle);
        // Asteroids initialize in the central 50% of the screen horizontally
        positionX = gameEngine.random.nextInt(gameEngine.width / 2) + gameEngine.width / 4;
        // They initialize outside of the screen vertically
        positionY = -height;
        rotationSpeed = angle * (180d / Math.PI) / 250d; // They rotate 4 times their ange in a second.
        rotation = gameEngine.random.nextInt(360);

        Resources r = gameEngine.getContext().getResources();
        Drawable spriteDrawable = state == MatterState.Determined ?
                r.getDrawable(originalState) : r.getDrawable(variantState);
        bitmap = ((BitmapDrawable) spriteDrawable).getBitmap();
    }

    public void SpawnAtPosition(GameEngine gameEngine, double posX, double posY) {
        // They initialize in a [-30, 30] degrees angle
        double angle = GameEngine.random.nextDouble() * Math.PI / 3d - Math.PI / 6d;
        speedX = speedFactor * Math.sin(angle);
        speedY = speedFactor * Math.cos(angle);
        // Asteroids initialize in the central 50% of the screen horizontally
        positionX = posX;
        // They initialize outside of the screen vertically
        positionY = posY;
        rotationSpeed = angle * (180d / Math.PI) / 250d; // They rotate 4 times their ange in a second.
        rotation = GameEngine.random.nextInt(360);

        Resources r = gameEngine.getContext().getResources();
        Drawable spriteDrawable = state == MatterState.Determined ?
                r.getDrawable(originalState) : r.getDrawable(variantState);
        bitmap = ((BitmapDrawable) spriteDrawable).getBitmap();
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
        } else if (rotation < 0) {
            rotation = 360;
        }

        super.onUpdate(elapsedMillis, gameEngine);


    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }

}

