package com.ldm.practica3.spacecowboy.gameObjects.enemies.basicEnemies;

import com.ldm.practica3.spacecowboy.R;
import com.ldm.practica3.spacecowboy.engine.GameEngine;
import com.ldm.practica3.spacecowboy.gameObjects.GameController;

public class BE_R_Tiger extends RangedEnemy{
    public BE_R_Tiger(GameController gameController, GameEngine gameEngine) {
        super(gameController, gameEngine, R.drawable.tiger);
        initBulletPool(gameEngine, R.drawable.bullet3,R.drawable.bullet3inv);
        originalState = R.drawable.tiger;
        variantState = R.drawable.tigerinv;
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
