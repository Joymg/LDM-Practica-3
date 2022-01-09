package com.ldm.practica3.spacecowboy.gameObjects.enemies.basicEnemies;

import com.ldm.practica3.spacecowboy.R;
import com.ldm.practica3.spacecowboy.engine.GameEngine;
import com.ldm.practica3.spacecowboy.gameObjects.GameController;

public class BE_R_Faye extends RangedEnemy{
    public BE_R_Faye(GameController gameController, GameEngine gameEngine) {
        super(gameController, gameEngine, R.drawable.faye);
        initBulletPool(gameEngine, R.drawable.bullet1,R.drawable.bullet1inv);
        originalState = R.drawable.faye;
        variantState = R.drawable.fayeinv;
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
