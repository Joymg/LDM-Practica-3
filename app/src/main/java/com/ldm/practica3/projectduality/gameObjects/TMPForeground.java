package com.ldm.practica3.projectduality.gameObjects;

import com.ldm.practica3.projectduality.R;
import com.ldm.practica3.projectduality.engine.GameEngine;
import com.ldm.practica3.projectduality.engine.ScreenGameObject;

public class TMPForeground extends Sprite{

    private int maxX;
    private int maxY;

    public TMPForeground(GameEngine gameEngine) {
        super(gameEngine, R.drawable.fg);
        maxX = gameEngine.width - width;
        maxY = gameEngine.height - height;
    }

    @Override
    public void startGame() {
        positionX = maxX/2;
        positionY = maxY/2;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }
}

