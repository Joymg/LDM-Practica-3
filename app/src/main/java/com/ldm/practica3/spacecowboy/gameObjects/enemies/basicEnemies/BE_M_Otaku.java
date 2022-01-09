package com.ldm.practica3.spacecowboy.gameObjects.enemies.basicEnemies;

import com.ldm.practica3.spacecowboy.R;
import com.ldm.practica3.spacecowboy.engine.GameEngine;
import com.ldm.practica3.spacecowboy.gameObjects.GameController;

public class BE_M_Otaku extends MeleeEnemy{
    public BE_M_Otaku(GameController gameController, GameEngine gameEngine) {
        super(gameController, gameEngine, R.drawable.otaku);
        originalState = R.drawable.otaku;
        variantState = R.drawable.otakuinv;
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
