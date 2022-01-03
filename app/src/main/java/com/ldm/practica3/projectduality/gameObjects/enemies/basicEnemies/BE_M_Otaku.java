package com.ldm.practica3.projectduality.gameObjects.enemies.basicEnemies;

import com.ldm.practica3.projectduality.R;
import com.ldm.practica3.projectduality.engine.GameEngine;
import com.ldm.practica3.projectduality.gameObjects.GameController;

public class BE_M_Otaku extends MeleeEnemy{
    public BE_M_Otaku(GameController gameController, GameEngine gameEngine) {
        super(gameController, gameEngine, R.drawable.enemy4);
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
