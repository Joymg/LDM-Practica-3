package com.ldm.practica3.spacecowboy.gameObjects.enemies.basicEnemies;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.ldm.practica3.spacecowboy.engine.GameEngine;
import com.ldm.practica3.spacecowboy.engine.ScreenGameObject;
import com.ldm.practica3.spacecowboy.engine.Vector2;
import com.ldm.practica3.spacecowboy.engine.components.MatterState;
import com.ldm.practica3.spacecowboy.gameObjects.Bullet;
import com.ldm.practica3.spacecowboy.gameObjects.GameController;
import com.ldm.practica3.spacecowboy.gameObjects.enemies.Enemy;
import com.ldm.practica3.spacecowboy.gameObjects.enemies.EnemyType;
import com.ldm.practica3.spacecowboy.sound.GameEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class RangedEnemy extends Enemy {
    protected final int INIT_HEALTH = 1;
    protected static final int INITIAL_BULLET_POOL_AMOUNT = 15;
    protected static final long TIME_BETWEEN_BULLETS = 400;
    List<Bullet> bullets = new ArrayList<Bullet>();
    List<Bullet> bulletsInv = new ArrayList<Bullet>();
    private long timeSinceLastFire;
    protected int bulletDrawableRes;

    private Vector2 up = new Vector2(0, -1);
    private Vector2 right = new Vector2(-1, 0);

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
        // Enemys initialize in the central 50% of the screen horizontally
        positionX = gameEngine.random.nextInt(gameEngine.width/2)+gameEngine.width/4;
        // They initialize outside of the screen vertically
        positionY = -height;
       /* rotationSpeed = angle*(180d / Math.PI)/250d; // They rotate 4 times their ange in a second.
        rotation = gameEngine.random.nextInt(360);*/

        Resources r = gameEngine.getContext().getResources();
        Drawable spriteDrawable = state == MatterState.Determined  ?
                r.getDrawable(originalState):r.getDrawable(variantState);
        bitmap = ((BitmapDrawable) spriteDrawable).getBitmap();
    }

    protected void initBulletPool(GameEngine gameEngine, int bulletDrawableRes, int bulletDrawableResVariant) {
        for (int i = 0; i < INITIAL_BULLET_POOL_AMOUNT; i++) {
            bullets.add(new Bullet(gameEngine, bulletDrawableRes));
            bulletsInv.add(new Bullet(gameEngine, bulletDrawableResVariant));
        }
    }

    protected Bullet getBullet() {
        switch (state) {
            case Determined:
                if (bullets.isEmpty()) {
                    return null;
                }
                return bullets.remove(0);

            case Quantic:
                if (bulletsInv.isEmpty()) {
                    return null;
                }
                return bulletsInv.remove(0);

            default:
                return null;
        }

    }

    public void releaseBullet(Bullet bullet) {
        switch (bullet.state) {
            case Determined:
                bullets.add(bullet);
                break;
            case Quantic:
                bulletsInv.add(bullet);
                break;
        }

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
        checkFiring(elapsedMillis,gameEngine);
    }

    private void checkFiring(long elapsedMillis, GameEngine gameEngine) {
        if (timeSinceLastFire > TIME_BETWEEN_BULLETS) {//gameEngine.inputController.isFiring &&
            Bullet bullet = getBullet();
            if (bullet == null) {
                return;
            }
            bullet.init(this, positionX + width/2 - (width * up.x), positionY + height/2 - (height * up.y), new Vector2(up.x, up.y));
            gameEngine.addGameObject(bullet);
            timeSinceLastFire = 0;
            gameEngine.onGameEvent(GameEvent.Shot);
        } else {
            timeSinceLastFire += elapsedMillis;
        }
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }
}
