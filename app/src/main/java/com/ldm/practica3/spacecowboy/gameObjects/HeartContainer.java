package com.ldm.practica3.spacecowboy.gameObjects;

import com.ldm.practica3.spacecowboy.R;
import com.ldm.practica3.spacecowboy.engine.GameEngine;
import com.ldm.practica3.spacecowboy.engine.ScreenGameObject;
import com.ldm.practica3.spacecowboy.engine.Vector2;

public class HeartContainer extends Sprite{
    public HeartContainer(GameEngine gameEngine) {
        super(gameEngine, R.drawable.heart);
    }

    public void init(GameEngine gameEngine, Vector2 pos) {
        positionX = pos.x - width/2;
        positionY = pos.y - height/2;

    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }
}
