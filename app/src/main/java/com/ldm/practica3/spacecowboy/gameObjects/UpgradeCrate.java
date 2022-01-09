package com.ldm.practica3.spacecowboy.gameObjects;

import com.ldm.practica3.spacecowboy.R;
import com.ldm.practica3.spacecowboy.engine.GameEngine;
import com.ldm.practica3.spacecowboy.engine.ScreenGameObject;
import com.ldm.practica3.spacecowboy.sound.GameEvent;

public class UpgradeCrate extends Sprite {

    private final GameController gameController;
    private final int INITIAL_HEALTH = 1;

    private double speedFactor;

    private int currHealth;

    protected UpgradeCrate(GameController gameController, GameEngine gameEngine) {
        super(gameEngine, R.drawable.poweruphealth);
        this.gameController = gameController;
        this.speedFactor = 30d * pixelFactor / 1000d;
        currHealth = INITIAL_HEALTH;
    }

    public void init(GameEngine gameEngine) {
        // Crates initialize in the central 50% of the screen horizontally
        positionX = gameEngine.random.nextInt(gameEngine.width / 2) + gameEngine.width / 4;
        // They initialize outside of the screen vertically
        positionY = -height;
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionY += speedFactor * elapsedMillis;
        if (currHealth <= 0) {

        }
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Player) {
            removeObject(gameEngine);
            gameEngine.onGameEvent(GameEvent.PowerUp);
            ((Player) otherObject).bulletPerShot++;
            if (((Player) otherObject).bulletPerShot > 3) {
                ((Player) otherObject).bulletPerShot = 3;
            }
        }
    }

    public void GotHit() {
        currHealth--;
    }

    public void removeObject(GameEngine gameEngine) {
        // Return to the pool
        gameEngine.removeGameObject(this);
        gameController.returnToPool(this);
    }
}
