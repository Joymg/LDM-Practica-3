package com.ldm.practica3.projectduality.gameObjects.parallax;

import com.ldm.practica3.projectduality.R;
import com.ldm.practica3.projectduality.engine.GameEngine;
import com.ldm.practica3.projectduality.engine.ScreenGameObject;
import com.ldm.practica3.projectduality.gameObjects.Sprite;

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
