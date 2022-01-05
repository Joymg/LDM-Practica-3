package com.ldm.practica3.projectduality.gameObjects.parallax;

import com.ldm.practica3.projectduality.engine.GameEngine;

public class Foreground3 extends BackgroundBase {


    public Foreground3(GameEngine gameEngine, int drawableRes, double speed) {
        super(gameEngine, drawableRes, speed);
        maxX = gameEngine.width - width;
        maxY = gameEngine.height - height;
    }

    @Override
    public void startGame() {
        positionX = maxX / 2;
        positionY = maxY/2 -height- maxY;
    }

}
