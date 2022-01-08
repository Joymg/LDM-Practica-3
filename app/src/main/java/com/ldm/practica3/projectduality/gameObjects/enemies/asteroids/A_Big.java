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
        originalState = R.drawable.rock3;
        variantState = R.drawable.rock3inv;
    }
    public void init(GameEngine gameEngine) {

        super.init(gameEngine);
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
