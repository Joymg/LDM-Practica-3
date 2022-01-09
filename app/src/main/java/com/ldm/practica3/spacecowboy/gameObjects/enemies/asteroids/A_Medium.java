package com.ldm.practica3.spacecowboy.gameObjects.enemies.asteroids;

import com.ldm.practica3.spacecowboy.R;
import com.ldm.practica3.spacecowboy.engine.GameEngine;
import com.ldm.practica3.spacecowboy.engine.ScreenGameObject;
import com.ldm.practica3.spacecowboy.gameObjects.GameController;

public class A_Medium extends Asteroid {

    private final int INIT_HEALTH = 2;


    public A_Medium(GameController gameController, GameEngine gameEngine) {
        super(gameController, gameEngine, R.drawable.rock2);
        currHealth = INIT_HEALTH;
        asteroidType = AsteroidType.Medium;
        originalState = R.drawable.rock2;
        variantState = R.drawable.rock2inv;
    }

    public void init(GameEngine gameEngine) {

        super.init(gameEngine);
    }

    @Override
    public void startGame() {
    }


    public void removeObject(GameEngine gameEngine) {

        gameController.onAsteroidDestroyed(asteroidType, positionX, positionY);
        // Return to the pool
        super.removeObject(gameEngine);
        /*gameEngine.removeGameObject(this);
        gameController.returnToPool(this);*/
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        super.onUpdate(elapsedMillis, gameEngine);

    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }
}
