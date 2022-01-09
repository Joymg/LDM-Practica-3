package com.ldm.practica3.spacecowboy.gameObjects;

import android.util.Log;

import com.ldm.practica3.spacecowboy.engine.GameEngine;
import com.ldm.practica3.spacecowboy.engine.ScreenGameObject;
import com.ldm.practica3.spacecowboy.engine.Vector2;
import com.ldm.practica3.spacecowboy.engine.components.Faction;
import com.ldm.practica3.spacecowboy.engine.components.MatterState;
import com.ldm.practica3.spacecowboy.gameObjects.enemies.Enemy;
import com.ldm.practica3.spacecowboy.gameObjects.enemies.basicEnemies.RangedEnemy;
import com.ldm.practica3.spacecowboy.sound.GameEvent;

public class Bullet extends Sprite {

    private double speedFactor;

    private Actor parent;
    private Vector2 dir = new Vector2();

    public Faction faction;
    public MatterState state;


    public Bullet(GameEngine gameEngine, int bulletDrawableRes){
        super(gameEngine, bulletDrawableRes);

        speedFactor = gameEngine.pixelFactor * -150d / 1000d;

        faction = Faction.Republic;
        state = MatterState.Determined;
    }

    @Override
    public void startGame() {}

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

        positionX += dir.x * speedFactor * elapsedMillis;
        positionY += dir.y * speedFactor * elapsedMillis;

        if (positionY < -height || positionY > gameEngine.height || positionX > gameEngine.width || positionX < 0) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            if (parent instanceof Player){
                ((Player) parent).releaseBullet(this);
            }
            else if (parent instanceof RangedEnemy){
                ((RangedEnemy) parent).releaseBullet(this);
            }
        }
    }


    public void init(Actor parentPlayer, double initPositionX, double initPositionY) {
        positionX = initPositionX - width/2;
        positionY = initPositionY - height/2;
        dir.x = 0;
        dir.y = 1;
        parent = parentPlayer;
        faction = parent.faction;
        state = parent.state;
    }

    public void init(Actor parentPlayer, double initPositionX, double initPositionY, Vector2 dir) {
        positionX = initPositionX - width/2;
        positionY = initPositionY - height/2;
        this.dir = dir;
        parent = parentPlayer;
        faction = parent.faction;
        state = parent.state;
    }

    public void removeObject(GameEngine gameEngine) {
        gameEngine.removeGameObject(this);
        // And return it to the pool
        if (parent instanceof Player){
            ((Player) parent).releaseBullet(this);
        }
        else if (parent instanceof RangedEnemy){
            ((RangedEnemy) parent).releaseBullet(this);
        }
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Player){
            return;
        }
        if (otherObject instanceof Actor) {
            if (((Actor) otherObject).faction != faction){
                if (((Actor) otherObject).state == state){
                    String className = otherObject.getClass().toString().split("\\.")[ otherObject.getClass().toString().split("\\.").length-1];
                    Log.d("Collision", "Bullet: " + state.toString() + ", " + className + ": " + ((Actor) otherObject).state + ", " + ((Actor) otherObject).bitmap.toString());
                    // Remove both from the game (and return them to their pools)
                    removeObject(gameEngine);
                    Enemy e = (Enemy) otherObject;
                    e.GotHit();
                    gameEngine.onGameEvent(GameEvent.Explosion);
                    // Add some score
                }
            }

        }
        else if (otherObject instanceof UpgradeCrate){
            removeObject(gameEngine);
            UpgradeCrate uc = (UpgradeCrate) otherObject;
            uc.GotHit();
        }
    }


}
