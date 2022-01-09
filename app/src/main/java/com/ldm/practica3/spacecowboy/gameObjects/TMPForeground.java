package com.ldm.practica3.spacecowboy.gameObjects;

import com.ldm.practica3.spacecowboy.R;
import com.ldm.practica3.spacecowboy.engine.GameEngine;
import com.ldm.practica3.spacecowboy.engine.ScreenGameObject;

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

