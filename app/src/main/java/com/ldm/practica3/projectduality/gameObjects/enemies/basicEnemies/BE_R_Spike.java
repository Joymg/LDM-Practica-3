package com.ldm.practica3.projectduality.gameObjects.enemies.basicEnemies;

import com.ldm.practica3.projectduality.R;
import com.ldm.practica3.projectduality.engine.GameEngine;
import com.ldm.practica3.projectduality.gameObjects.GameController;

public class BE_R_Spike extends RangedEnemy {
    public BE_R_Spike(GameController gameController, GameEngine gameEngine) {
        super(gameController, gameEngine, R.drawable.spike);
        initBulletPool(gameEngine, R.drawable.bullet2,R.drawable.bullet2inv);
        originalState = R.drawable.spike;
        variantState = R.drawable.spikeinv;
    }

    public void init(GameEngine gameEngine){
        super.init(gameEngine);
    }

    public  void onUpdate(long elapsedMillis, GameEngine gameEngine){
        //Todo: modify speed to do things

        positionX += speedX * elapsedMillis;
        positionY += speedY * elapsedMillis;

        super.onUpdate(elapsedMillis,gameEngine);
    }
}
