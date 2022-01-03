package com.ldm.practica3.projectduality.gameObjects.enemies.asteroids;

import com.ldm.practica3.projectduality.R;
import com.ldm.practica3.projectduality.engine.GameEngine;
import com.ldm.practica3.projectduality.engine.ScreenGameObject;
import com.ldm.practica3.projectduality.gameObjects.GameController;

public class A_Small extends Asteroid{
    private final int INIT_HEALTH = 1;
    public A_Small(GameController gameController, GameEngine gameEngine) {
        super(gameController, gameEngine, R.drawable.rock1);
        currHealth = INIT_HEALTH;
        asteroidType = AsteroidType.Small;
    }

    @Override
    public void startGame() {
    }


    public void removeObject(GameEngine gameEngine) {
        // Return to the pool
        gameEngine.removeGameObject(this);
        gameController.returnToPool(this);
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        super.onUpdate(elapsedMillis,gameEngine);

        if (currHealth <= 0){
            removeObject(gameEngine);
            //TODO: Spawn smaller asteroids
        }
        if (currHealth <= 0){
            removeObject(gameEngine);
            //TODO: Spawn smaller asteroids
        }
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }
}
