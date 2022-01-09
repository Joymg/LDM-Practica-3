package com.ldm.practica3.spacecowboy.gameObjects.parallax;

import com.ldm.practica3.spacecowboy.engine.GameEngine;

public class Foreground2 extends BackgroundBase {


    public Foreground2(GameEngine gameEngine, int drawableRes, double speed) {
        super(gameEngine, drawableRes, speed);
        maxX = gameEngine.width - width;
        maxY = gameEngine.height - height;
    }

    @Override
    public void startGame() {
        positionX = maxX / 2;
        positionY = maxY / 2 - maxY;
    }

}
