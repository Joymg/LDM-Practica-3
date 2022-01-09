package com.ldm.practica3.spacecowboy.gameObjects.parallax;

import com.ldm.practica3.spacecowboy.engine.GameEngine;

public class Background1 extends BackgroundBase {

    public Background1(GameEngine gameEngine, int drawableRes, double speed) {
        super(gameEngine, drawableRes, speed);
        maxX = gameEngine.width - width;
        maxY = gameEngine.height - height;
    }

    @Override
    public void startGame() {
        positionX = maxX / 2;
        positionY = maxY / 2 + height - maxY;
    }

}
