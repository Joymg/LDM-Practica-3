package com.ldm.practica3.projectduality.gameObjects;

import com.ldm.practica3.projectduality.engine.GameEngine;
import com.ldm.practica3.projectduality.R;
import com.ldm.practica3.projectduality.engine.ScreenGameObject;
import com.ldm.practica3.projectduality.engine.Vector2;
import com.ldm.practica3.projectduality.gameObjects.enemies.Enemy;
import com.ldm.practica3.projectduality.input.InputController;
import com.ldm.practica3.projectduality.sound.GameEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Player extends Sprite {

    private static final int INITIAL_BULLET_POOL_AMOUNT = 30;
    private static final long TIME_BETWEEN_BULLETS = 150;
    List<Bullet> bullets = new ArrayList<Bullet>();
    private long timeSinceLastFire;

    private int maxX;
    private int maxY;
    private double speedFactor;

    private float mobilityFactor = 700;
    private float maxSpeed = 1000;
    private Vector2 up = new Vector2(0, 1);
    private Vector2 right = new Vector2(1, 0);
    private Vector2 velocity = new Vector2();

    private final int initialLives = 3;
    public int currentLives;
    public int invincibilityTime = 1000;
    Date endOfInvincibilityTime;


    public Player(GameEngine gameEngine) {
        super(gameEngine, R.drawable.player);
        speedFactor = pixelFactor * 100d / 1000d; // We want to move at 100px per second on a 400px tall screen
        maxX = gameEngine.width - width;
        maxY = gameEngine.height - height;

        currentLives = initialLives;
        gameEngine.SetLives(currentLives);
        initBulletPool(gameEngine);
    }

    private void initBulletPool(GameEngine gameEngine) {
        for (int i = 0; i < INITIAL_BULLET_POOL_AMOUNT; i++) {
            bullets.add(new Bullet(gameEngine));
        }
    }

    private Bullet getBullet() {
        if (bullets.isEmpty()) {
            return null;
        }
        return bullets.remove(0);
    }

    void releaseBullet(Bullet bullet) {
        bullets.add(bullet);
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


        //Vector2 acc = up.multiply(input.GetMagnitude());
        Vector2 acc = up;
        acc.x -= (velocity.x * velocity.x) / 4000000;
        acc.y -= (velocity.y * velocity.y) / 4000000;


        velocity.x += acc.x * elapsedMillis * 0.05f;
        velocity.y += acc.y * elapsedMillis * 0.05f;

        positionX += velocity.x * elapsedMillis;
        positionY += velocity.y * elapsedMillis;

        //positionX += speedFactor * inputController.horizontalFactor * elapsedMillis;
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

        if (velocity.GetMagnitude() > speedFactor) {
            velocity.normalize();
            velocity.x *= speedFactor;
            velocity.y *= speedFactor;
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
            Bullet bullet = getBullet();
            if (bullet == null) {
                return;
            }
            bullet.init(this, positionX + width / 2 - (width * up.x), positionY + height / 2 - (height * up.y), new Vector2(up.x, up.y));
            gameEngine.addGameObject(bullet);
            timeSinceLastFire = 0;
            gameEngine.onGameEvent(GameEvent.LaserFired);
        } else {
            timeSinceLastFire += elapsedMillis;
        }
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Enemy) {

            if (new Date().before(endOfInvincibilityTime))
                return;

            endOfInvincibilityTime = new Date(new Date().getTime() + invincibilityTime);

            //gameEngine.removeGameObject(this);
            //gameEngine.stopGame();
            Enemy enemy = (Enemy) otherObject;
            enemy.removeObject(gameEngine);
            gameEngine.onGameEvent(GameEvent.SpaceshipHit);

            currentLives--;
            gameEngine.SetLives(currentLives);
            if (currentLives <= 0) {
                //game over
                gameEngine.removeGameObject(this);

                //Todo: show game over screen
                //gameEngine.stopGame();

            }

        }
    }


}
