package com.ldm.practica3.projectduality.gameObjects;

import com.ldm.practica3.projectduality.engine.GameEngine;
import com.ldm.practica3.projectduality.R;
import com.ldm.practica3.projectduality.engine.ScreenGameObject;
import com.ldm.practica3.projectduality.engine.Vector2;
import com.ldm.practica3.projectduality.engine.components.Faction;
import com.ldm.practica3.projectduality.engine.components.MatterState;
import com.ldm.practica3.projectduality.gameObjects.enemies.Enemy;
import com.ldm.practica3.projectduality.sound.GameEvent;

public class Bullet extends Sprite {

    private double speedFactor;

    private Player parent;
    private Vector2 dir = new Vector2();

    private Faction faction;
    private MatterState state;


    public Bullet(GameEngine gameEngine){
        super(gameEngine, R.drawable.playerbullet);

        speedFactor = gameEngine.pixelFactor * -200d / 1000d;

        faction = Faction.Republic;
        state = MatterState.Determined;
    }

    @Override
    public void startGame() {}

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

        positionX += dir.x * speedFactor * elapsedMillis;
        positionY += dir.y * speedFactor * elapsedMillis;

        if (positionY < -height || positionY > gameEngine.height || positionX > gameEngine.width || positionX < 0) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            parent.releaseBullet(this);
        }
    }


    public void init(Player parentPlayer, double initPositionX, double initPositionY) {
        positionX = initPositionX - width/2;
        positionY = initPositionY - height/2;
        dir.x = 0;
        dir.y = 1;
        parent = parentPlayer;
    }

    public void init(Player parentPlayer, double initPositionX, double initPositionY, Vector2 dir) {
        positionX = initPositionX - width/2;
        positionY = initPositionY - height/2;
        this.dir = dir;
        parent = parentPlayer;
    }

    private void removeObject(GameEngine gameEngine) {
        gameEngine.removeGameObject(this);
        // And return it to the pool
        parent.releaseBullet(this);
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Actor) {
            if (((Actor) otherObject).faction != faction){
                if (((Actor) otherObject).state == state){
                    // Remove both from the game (and return them to their pools)
                    removeObject(gameEngine);
                    Enemy e = (Enemy) otherObject;
                    e.GotHit();
                    gameEngine.onGameEvent(GameEvent.AsteroidHit);
                    // Add some score
                }
            }

        }
        else if (otherObject instanceof UpgradeCrate){
            removeObject(gameEngine);
            UpgradeCrate uc = (UpgradeCrate) otherObject;
            uc.GotHit();
        }
    }


}
