package com.ldm.practica3.projectduality.gameObjects.parallax;

import com.ldm.practica3.projectduality.engine.GameEngine;
import com.ldm.practica3.projectduality.engine.ScreenGameObject;
import com.ldm.practica3.projectduality.gameObjects.Sprite;

public abstract class BackgroundBase extends Sprite {

    protected int maxX;
    protected int maxY;
    protected double speedFactor;

    public BackgroundBase(GameEngine gameEngine, int drawableRes,double speed) {
        super(gameEngine, drawableRes);
        //pixelFactor = gameEngine.height / 260d;
        speedFactor = speed * pixelFactor / 1000d;
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionY += speedFactor * elapsedMillis;
        if (positionY >=maxY/2 +height){
            positionY = maxY/2 -height;
        }
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }
}
