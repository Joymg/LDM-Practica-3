package com.ldm.practica3.projectduality.gameObjects;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.ldm.practica3.projectduality.R;
import com.ldm.practica3.projectduality.engine.GameEngine;
import com.ldm.practica3.projectduality.engine.ScreenGameObject;
import com.ldm.practica3.projectduality.engine.Vector2;
import com.ldm.practica3.projectduality.engine.components.Faction;
import com.ldm.practica3.projectduality.engine.components.MatterState;
import com.ldm.practica3.projectduality.gameObjects.enemies.Enemy;
import com.ldm.practica3.projectduality.input.InputController;
import com.ldm.practica3.projectduality.sound.GameEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Player extends Actor {

    private static final int INITIAL_LIVES = 3;
    private static final int INITIAL_BULLET_POOL_AMOUNT = 30;
    private static final long TIME_BETWEEN_BULLETS = 120;
    List<Bullet> bullets = new ArrayList<Bullet>();
    List<Bullet> bulletsInv = new ArrayList<Bullet>();
    private long timeSinceLastFire;

    private int maxX;
    private int maxY;
    private double speedFactor;

    private float mobilityFactor = 500;
    private float maxSpeed = 500;
    private Vector2 up = new Vector2(0, 1);
    private Vector2 right = new Vector2(1, 0);
    private Vector2 velocity = new Vector2();

    public int currHealth;
    public int invincibilityTime = 1000;
    Date endOfInvincibilityTime;
    public int bulletPerShot = 1;

    MatterState lastState;

    int originalState;
    int variantState;


    public Player(GameEngine gameEngine, int[] resources) {
        super(gameEngine, resources[0]);
        speedFactor = pixelFactor * 100d / 1000d; // We want to move at 100px per second on a 400px tall screen
        maxX = gameEngine.width - width;
        maxY = gameEngine.height - height;

        originalState = resources[0];
        variantState = resources[1];

        currHealth = INITIAL_LIVES;
        gameEngine.SetLives(currHealth);
        initBulletPool(gameEngine, R.drawable.playerbullet, R.drawable.playerbulletinv);

        faction = Faction.Republic;
        state = MatterState.Determined;
        lastState = state;
    }

    private void initBulletPool(GameEngine gameEngine, int bulletDrawableRes, int bulletDrawableResVariant) {
        for (int i = 0; i < INITIAL_BULLET_POOL_AMOUNT; i++) {
            bullets.add(new Bullet(gameEngine, bulletDrawableRes));
            bulletsInv.add(new Bullet(gameEngine, bulletDrawableResVariant));
        }
    }

    private Bullet getBullet() {
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


    @Override
    public void startGame() {
        positionX = maxX / 2;
        positionY = maxY / 2;

        endOfInvincibilityTime = new Date(new Date().getTime() + invincibilityTime);
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        // Get the info from the inputController
        updatePosition(elapsedMillis, gameEngine.inputController);
        checkFiring(elapsedMillis, gameEngine);
        checkStateChange(gameEngine);

    }

    private void checkStateChange(GameEngine gameEngine) {
        state = gameEngine.inputController.state ? MatterState.Quantic : MatterState.Determined;

        if (state != lastState) {
            lastState = state;
            Resources r = gameEngine.getContext().getResources();
            Drawable spriteDrawable = gameEngine.inputController.state ?
                    r.getDrawable(variantState) : r.getDrawable(originalState);
            bitmap = ((BitmapDrawable) spriteDrawable).getBitmap();
        }
    }

    private void updatePosition(long elapsedMillis, InputController inputController) {
        Vector2 input = new Vector2((float) inputController.horizontalFactor, (float) inputController.verticalFactor);


        if (input.x > 0.01 || input.y > 0.01 || input.x < -0.01 || input.y < -0.01) {
            double desiredRotation = (Math.atan2(input.y, input.x) * 57.2958) + 90;
            if (desiredRotation < 0) {
                desiredRotation = 360 + desiredRotation;
            }

            //FPSDisplay.instance.debugText = desiredRotation+"";


            if (desiredRotation > rotation) {
                if (desiredRotation - rotation < 360 - desiredRotation + rotation)
                    Rotate(elapsedMillis);
                else
                    Rotate(-elapsedMillis);
            } else {
                if (rotation - desiredRotation < 360 - rotation + desiredRotation)
                    Rotate(-elapsedMillis);
                else
                    Rotate(+elapsedMillis);
            }


        }


        up = Vector2.vecFromAngle((float) rotation);

        positionX += up.x * maxSpeed * elapsedMillis/1000;
        positionY += up.y * maxSpeed * elapsedMillis/1000;

        if (positionX < 0) {
            positionX = 0;
            velocity.x = -.5f * velocity.x;
            rotation += 90;
        } else if (positionX > maxX) {
            positionX = maxX;
            velocity.x = -.5f * velocity.x;
            rotation += 90;
        } else if (positionY < 0) {
            positionY = 0;
            velocity.y = -.5f * velocity.y;
            rotation += 90;
        } else if (positionY > maxY) {
            positionY = maxY;
            velocity.y = -.5f * velocity.y;
            rotation += 90;
        }
    }

    private void Rotate(double amount) {
        rotation += mobilityFactor * amount / 1000;
        if (rotation > 360) {
            rotation = 0 + (rotation - 360);
        }
        if (rotation < 0) {
            rotation = 360 + rotation;
        }
    }

    private void checkFiring(long elapsedMillis, GameEngine gameEngine) {
        if (timeSinceLastFire > TIME_BETWEEN_BULLETS) {//gameEngine.inputController.isFiring &&
            for (int i = 0; i < bulletPerShot; i++) {
                Bullet bullet = getBullet();
                if (bullet == null) {
                    return;
                }
                //TODO: make diferent shots
                bullet.init(this, positionX + width / 2 - (width/2 * up.x), positionY + height / 2 - (height/2 * up.y), new Vector2(up.x, up.y));
                gameEngine.addGameObject(bullet);
            }
            timeSinceLastFire = 0;
            gameEngine.onGameEvent(GameEvent.Shot);
        } else {
            timeSinceLastFire += elapsedMillis;
        }
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Enemy) {

            if (new Date().before(endOfInvincibilityTime))
                return;

            Enemy enemy = (Enemy) otherObject;

            if (enemy.state != state)
                return;

            endOfInvincibilityTime = new Date(new Date().getTime() + invincibilityTime);

            enemy.removeObject(gameEngine);
            gameEngine.onGameEvent(GameEvent.Hurt);

            currHealth--;
            gameEngine.SetLives(currHealth);
            if (currHealth <= 0) {
                //game over
                gameEngine.removeGameObject(this);

                //Todo: show game over screen
                gameEngine.onPlayerDie();


            }

        }

        if (otherObject instanceof Bullet) {
            //gameEngine.stopGame();
            Bullet bullet = (Bullet) otherObject;
            if (bullet.faction != faction && bullet.state == state) {

                if (new Date().before(endOfInvincibilityTime))
                    return;

                endOfInvincibilityTime = new Date(new Date().getTime() + invincibilityTime);
                bullet.removeObject(gameEngine);
                gameEngine.onGameEvent(GameEvent.Hurt);

                currHealth--;
                gameEngine.SetLives(currHealth);
                if (currHealth <= 0) {
                    //game over
                    gameEngine.removeGameObject(this);

                    //Todo: show game over screen
                    gameEngine.onPlayerDie();


                }
            }
        }

    }
}



