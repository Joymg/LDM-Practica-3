package com.ldm.practica3.projectduality.gameObjects;

import com.ldm.practica3.projectduality.engine.GameEngine;
import com.ldm.practica3.projectduality.R;
import com.ldm.practica3.projectduality.engine.ScreenGameObject;
import com.ldm.practica3.projectduality.engine.Vector2;
import com.ldm.practica3.projectduality.gameObjects.enemies.Enemy;
import com.ldm.practica3.projectduality.input.InputController;
import com.ldm.practica3.projectduality.sound.GameEvent;

import java.util.ArrayList;
import java.util.List;

public class Player extends Sprite {

    private static final int INITIAL_BULLET_POOL_AMOUNT = 6;
    private static final long TIME_BETWEEN_BULLETS = 250;
    List<Bullet> bullets = new ArrayList<Bullet>();
    private long timeSinceLastFire;

    private int maxX;
    private int maxY;
    private double speedFactor;

    private float mobilityFactor = 500;
    private float maxSpeed = 1000;
    private Vector2 up = new Vector2(0,1);
    private Vector2 right = new Vector2(1,0);
    private Vector2 velocity = new Vector2();

    private final int initialLives = 3;
    public int currentLives;

    public Player(GameEngine gameEngine){
        super(gameEngine, R.drawable.player);
        speedFactor = pixelFactor * 100d / 1000d; // We want to move at 100px per second on a 400px tall screen
        maxX = gameEngine.width - width;
        maxY = gameEngine.height - height;

        currentLives = initialLives;
        gameEngine.SetLives(currentLives);
        initBulletPool(gameEngine);
    }

    private void initBulletPool(GameEngine gameEngine) {
        for (int i=0; i<INITIAL_BULLET_POOL_AMOUNT; i++) {
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
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        // Get the info from the inputController
        updatePosition(elapsedMillis, gameEngine.inputController);
        checkFiring(elapsedMillis, gameEngine);

    }

    private void updatePosition(long elapsedMillis, InputController inputController) {
        Vector2 input = new Vector2((float)inputController.horizontalFactor,(float)inputController.verticalFactor);


        if(input.x > 0.01 || input.y > 0.01 || input.x < -0.01 || input.y < -0.01 ) {
            double desiredRotation = (Math.atan2(input.y, input.x) * 57.2958) + 90;
            if(desiredRotation < 0){
                desiredRotation = 360 + desiredRotation;
            }

            FPSDisplay.instance.debugText = desiredRotation+"";


            if (desiredRotation > rotation) {
                rotation += mobilityFactor * elapsedMillis / 1000;
                if(rotation > 360){
                    rotation = 0 + (rotation - 360);
                }
            } else {
                rotation -= mobilityFactor * elapsedMillis / 1000;
                if(rotation < 0){
                    rotation = 360 + rotation;
                }
            }


        }


        up = Vector2.vecFromAngle((float)rotation);


        Vector2 acc = up;
        acc.x -= (velocity.x* velocity.x)/8000000;
        acc.y -= (velocity.y* velocity.y)/8000000;


        velocity.x += acc.x * elapsedMillis * 0.05f;
        velocity.y += acc.y * elapsedMillis * 0.05f;

        positionX += velocity.x  * elapsedMillis;
        positionY += velocity.y * elapsedMillis;




        //positionX += speedFactor * inputController.horizontalFactor * elapsedMillis;
        if (positionX < 0) {
            positionX = 0;
            velocity.x = -.5f *velocity.x;
            rotation += 90;
        }else        if (positionX > maxX) {
            positionX = maxX;
            velocity.x = -.5f *velocity.x;
            rotation += 90;
        }else        if (positionY < 0) {
            positionY = 0;
            velocity.y = -.5f *velocity.y;
            rotation += 90;
        }else         if (positionY > maxY) {
            positionY = maxY;
            velocity.y = -.5f *velocity.y;
            rotation += 90;
        }

        if(velocity.GetMagnitude() > speedFactor){
            velocity.normalize();
            velocity.x *= speedFactor;
            velocity.y *= speedFactor;
        }
    }

    private void checkFiring(long elapsedMillis, GameEngine gameEngine) {
        if (gameEngine.inputController.isFiring && timeSinceLastFire > TIME_BETWEEN_BULLETS) {
            Bullet bullet = getBullet();
            if (bullet == null) {
                return;
            }
            bullet.init(this, positionX + width/2, positionY);
            gameEngine.addGameObject(bullet);
            timeSinceLastFire = 0;
            gameEngine.onGameEvent(GameEvent.LaserFired);
        }
        else {
            timeSinceLastFire += elapsedMillis;
        }
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Enemy) {
            //gameEngine.removeGameObject(this);
            //gameEngine.stopGame();
            Enemy enemy = (Enemy) otherObject;
            enemy.removeObject(gameEngine);
            gameEngine.onGameEvent(GameEvent.SpaceshipHit);

            currentLives--;
            gameEngine.SetLives(currentLives);
            if (currentLives <= 0){
                //game over
                gameEngine.removeGameObject(this);

                //Todo: show game over screen
                gameEngine.stopGame();

            }

        }
    }


}
