package com.ldm.practica3.projectduality.gameObjects;

import com.ldm.practica3.projectduality.R;
import com.ldm.practica3.projectduality.engine.GameEngine;
import com.ldm.practica3.projectduality.engine.ScreenGameObject;
import com.ldm.practica3.projectduality.engine.Vector2;

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
